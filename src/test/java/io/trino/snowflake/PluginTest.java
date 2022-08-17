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

import io.trino.testing.TestingConnectorContext;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PluginTest
{
    @Test
    public void TestDependencyInjection()
    {
        SnowflakePlugin plugin = new SnowflakePlugin();
        Iterable<io.trino.spi.connector.ConnectorFactory> factories = plugin.getConnectorFactories();
        io.trino.spi.connector.ConnectorFactory f = factories.iterator().next();

        Map<String, String> conf = new HashMap<String, String>() {
            {
                put("connection-url", "jdbc:snowflake://xc95956.eu-west-1.snowflakecomputing.com");
                put("snowflake.passcodeInPassword", "false");
                put("connection-user", "eng");
                put("connection-password", "PWD");
                put("snowflake.database", "SNOWFLAKE_SAMPLE_DATA");
            }
        };
        f.create("test", conf, new TestingConnectorContext());
    }
}
