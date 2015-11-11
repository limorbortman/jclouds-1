/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jclouds.openstack.murano.v1.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.murano.v1.domain.Deployment;

import java.beans.ConstructorProperties;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * Created by nkoffman on 11/10/15.
 */
public class MuranoParserModule extends AbstractModule {
    @Provides
    @Singleton
    public Map<Type, Object> provideCustomAdapterBindings() {
        return ImmutableMap.<Type, Object>of(
                Deployment.class, new DeploymentAdapter()
        );
    }
        @Override
        protected void configure() {
            bind(GsonModule.DateAdapter.class).to(GsonModule.Iso8601DateAdapter.class);
        }

    @Singleton
    public static class DeploymentAdapter implements JsonDeserializer<Deployment> {
        @Override
        public Deployment deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            Deployment deployment;

            if (jsonElement.getAsJsonObject().get("action").isJsonObject()) {
                deployment = apply((DeploymentInternal) context.deserialize(jsonElement, DeploymentInternal.class));
            } else {
                deployment = apply((DeploymentInternalWithoutAction) context.deserialize(jsonElement, DeploymentInternalWithoutAction.class));
            }

            Deployment.Builder<?> result = Deployment.builder().fromDeployment(deployment);
            return result.build();
        }

        public Deployment apply(Deployment in) {
            return in.toBuilder().build();
        }

        private static class DeploymentInternal extends Deployment {
            @ConstructorProperties({"id", "updated", "environment_id", "description", "created", "started", "finished", "state", "result", "action"})
            protected DeploymentInternal(String id, Date updated, String environmentId, Map<String, Object> description, Date created,
                                                   Date started, Date finished, String state, Map<String, Object> result, Map<String, Object>
                                                                       action) {
                super(id, updated, environmentId, description, created, started, finished, state, result, action);

            }
        }

        private static class DeploymentInternalWithoutAction extends Deployment {
            @ConstructorProperties({"id", "updated", "environment_id", "description", "created", "started", "finished", "state", "result"})
            protected DeploymentInternalWithoutAction(String id, Date updated, String environmentId, Map<String, Object> description, Date created,
                                                      Date started, Date finished, String state, Map<String, Object> result){
                super(id, updated, environmentId, description, created, started, finished, state, result, null);
            }
        }
    }
}
