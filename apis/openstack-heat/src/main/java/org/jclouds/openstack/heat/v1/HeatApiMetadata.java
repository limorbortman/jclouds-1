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
package org.jclouds.openstack.heat.v1;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.openstack.heat.v1.config.HeatHttpApiModule;
import org.jclouds.openstack.heat.v1.config.HeatParserModule;
import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.rest.internal.BaseHttpApiMetadata;
import org.jclouds.rest.internal.BaseRestApiMetadata;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;

/**
 * User: Maty Grosz
 * Date: 19/Nov/2013
 * <p/>
 * Implementation of {@link org.jclouds.apis.ApiMetadata} for Heat v1 API
 */
public class HeatApiMetadata extends BaseHttpApiMetadata<HeatApi> {


   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public HeatApiMetadata() {
      this(new Builder());
   }

   protected HeatApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseRestApiMetadata.defaultProperties();

      properties.setProperty(SERVICE_TYPE, ServiceType.ORCHESTRATION);
      properties.setProperty(CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);

      return properties;
   }

   public static class Builder extends  BaseHttpApiMetadata.Builder<HeatApi, Builder> {

      protected Builder() {
         id("openstack-heat")
               .name("OpenStack Heat Havana API")
               .identityName("${tenantName}:${userName} or ${userName}, if your keystone supports a default tenant")
               .credentialName("${password}")
               .endpointName("Keystone base URL ending in /v2.0/")
               .documentation(URI.create("http://api.openstack.org/"))
               .version("1")
               .defaultEndpoint("http://localhost:5000/v2.0/")
               .defaultProperties(HeatApiMetadata.defaultProperties())
               .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                     .add(KeystoneAuthenticationModule.class)
                     .add(KeystoneAuthenticationModule.ZoneModule.class)
                     .add(HeatParserModule.class)
                     .add(HeatHttpApiModule.class)
                     .build());
      }

      @Override
      public HeatApiMetadata build() {
         return new HeatApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }
}