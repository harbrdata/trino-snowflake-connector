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

import com.google.inject.Binder;
import com.google.inject.Module;
import io.trino.plugin.jdbc.mapping.IdentifierMapping;

import javax.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.google.inject.Scopes.SINGLETON;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class SnowflakeIdentifierMappingModule
        implements Module
{
    @Override
    public void configure(Binder binder)
    {
        binder.bind(IdentifierMapping.class)
                .annotatedWith(ForSnowflakeIdentifierMapping.class)
                .to(SnowflakeIdentifierMapping.class)
                .in(SINGLETON);
    }

    @Retention(RUNTIME)
    @Target({FIELD, PARAMETER, METHOD})
    @Qualifier
    public @interface ForSnowflakeIdentifierMapping {}
}
