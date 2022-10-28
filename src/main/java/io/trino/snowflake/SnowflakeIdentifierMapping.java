/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.snowflake;

import io.trino.plugin.jdbc.mapping.IdentifierMapping;
import io.trino.spi.connector.SchemaTableName;
import io.trino.spi.security.ConnectorIdentity;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Locale.ENGLISH;

public class SnowflakeIdentifierMapping
        implements IdentifierMapping
{
    protected Map<String, String> remoteSchemaNames = new ConcurrentHashMap<>();
    protected Map<SchemaTableName, String> remoteTableNames = new ConcurrentHashMap<>();

    @Override
    public String fromRemoteSchemaName(String remoteSchemaName)
    {
        String normalizesSchemaName = remoteSchemaName.toLowerCase(ENGLISH);
        remoteSchemaNames.put(normalizesSchemaName, remoteSchemaName);
        return normalizesSchemaName;
    }

    @Override
    public String fromRemoteTableName(String remoteSchemaName, String remoteTableName)
    {
        String normalizesSchemaName = fromRemoteSchemaName(remoteSchemaName);
        String normalizesTableName = remoteTableName.toLowerCase(ENGLISH);
        remoteTableNames.put(new SchemaTableName(normalizesSchemaName, normalizesTableName), remoteTableName);
        return normalizesTableName;
    }

    @Override
    public String fromRemoteColumnName(String remoteColumnName)
    {
        return remoteColumnName.toLowerCase(ENGLISH);
    }

    @Override
    public String toRemoteSchemaName(ConnectorIdentity identity, Connection connection, String schemaName)
    {
        return remoteSchemaNames.getOrDefault(schemaName, schemaName.toUpperCase(ENGLISH));
    }

    @Override
    public String toRemoteTableName(ConnectorIdentity identity, Connection connection, String remoteSchema, String tableName)
    {
        String normalizesSchemaName = remoteSchema.toLowerCase(ENGLISH);
        return remoteTableNames.getOrDefault(new SchemaTableName(normalizesSchemaName, tableName), tableName.toUpperCase(ENGLISH));
    }

    @Override
    public String toRemoteColumnName(Connection connection, String columnName)
    {
        return columnName.toUpperCase(ENGLISH);
    }
}
