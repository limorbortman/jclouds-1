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
package org.jclouds.openstack.heat.v1.options;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

public class Environment {

   public static final Environment NONE = new Environment();

   @Named("resource_registry")
   Map<String, String> resourceRegistry;
   @Named("parameters")
   Map<String, String> parameters;
   @Named("parameter_defaults")
   Map<String, String> parameterDefaults;

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof Environment)) return false;
      final Environment other = Environment.class.cast(object);
      return equal(parameters, other.parameters) && equal(resourceRegistry, other.resourceRegistry) && equal(parameterDefaults, other.parameterDefaults);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(parameters, resourceRegistry, parameterDefaults);
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("").add("parameters", parameters).add("resourceRegistry", resourceRegistry).add("parameterDefaults", parameterDefaults);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   /**
    * @param parameters - The parameters to Pass into Heat
    */
   public Environment parameters(Map<String, String> parameters) {
      this.parameters = parameters;
      return this;
   }

   /**
    * @param resourceRegistry - Deal with the mapping of Quantum to Neutron
    */
   public Environment resourceRegistry(Map<String, String> resourceRegistry) {
      this.resourceRegistry = resourceRegistry;
      return this;
   }

   /**
    * @param parameterDefaults - Defaults parameters .
    */
   public Environment parameterDefaults(Map<String, String> parameterDefaults) {
      this.parameterDefaults = parameterDefaults;
      return this;
   }

   public Map<String, String> getResourceRegistry() {
      return resourceRegistry;
   }


   public Map<String, String> getParameters() {
      return parameters;
   }

   public Map<String, String> getParameterDefaults() {
      return parameterDefaults;
   }

   public static class Builder {

      /**
       * @see .Environment#getResourceRegistry()
       */
      public static Environment resourceRegistry(Map<String, String> resourceRegistry) {
         return new Environment().resourceRegistry(resourceRegistry);
      }

      /**
       * @see Environment#parameters()
       */
      public static Environment parameters(Map<String, String> parameters) {
         return new Environment().parameters(parameters);
      }

      /**
       * @see Environment#parameterDefaults()
       */
      public static Environment parameterDefaults(Map<String, String> parameterDefaults) {
         return new Environment().parameterDefaults(parameterDefaults);
      }

   }
}