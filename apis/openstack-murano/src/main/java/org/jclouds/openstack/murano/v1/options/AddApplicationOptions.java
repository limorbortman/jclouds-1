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
package org.jclouds.openstack.murano.v1.options;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.jclouds.http.HttpRequest;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static com.google.common.base.Objects.toStringHelper;

/**
 *
 */
public class AddApplicationOptions implements MapBinder {

   public static final AddApplicationOptions NONE = new AddApplicationOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String name;

   @Nullable
   protected String hotEnvironment;

   @Named("?")
   protected Map<String, Object> properties = ImmutableMap.of();

   static class AddApplicationRequest {
      String name;
      @Nullable
      String hotEnvironment;
      @Named("?")
      Map<String, Object> properties = ImmutableMap.of();
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      AddApplicationRequest addApplicationRequest = new AddApplicationRequest();
      if (name != null) {
         addApplicationRequest.name = name;
      }
      if (hotEnvironment != null) {
         addApplicationRequest.hotEnvironment = hotEnvironment;
      }
      if (properties != null && !properties.isEmpty()) {
         addApplicationRequest.properties = properties;
      }
      return jsonBinder.bindToRequest(request, addApplicationRequest);
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("addApplicationRequest is a POST operation");
   }

   /**
    * @param properties - the properties of the application to be added.
    */
   public AddApplicationOptions properties(Map<String, Object> properties) {
      this.properties = properties;
      return this;
   }

   /**
    * @param name - the name of the application to be added.
    */
   public AddApplicationOptions name(String name) {
      this.name = name;
      return this;
   }

   /**
    * @param hotEnvironment - the HOT Environment of the application to be added.
    */
   public AddApplicationOptions hotEnvironment(String hotEnvironment) {
      this.hotEnvironment = hotEnvironment;
      return this;
   }

   public String getName() {
      return name;
   }

   public Map<String, Object> getProperties() {
      return properties;
   }

   @Nullable
   public String getHotEnvironment() {
      return hotEnvironment;
   }


   protected Objects.ToStringHelper string() {
      return toStringHelper("")
            .add("jsonBinder", jsonBinder)
            .add("name", name)
            .add("hotEnvironment", hotEnvironment)
            .add("properties", properties);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static class Builder {

      private static final String TYPE = "type";
      private static final String ID = "id";

      public static AddApplicationOptions muranoPackage(MuranoPackage muranoPackage, String name) {
         return muranoPackage(muranoPackage, name, null);
      }

      public static AddApplicationOptions muranoPackage(MuranoPackage muranoPackage, String name, @Nullable String environmentName) {
         Map<String, Object> properties = Maps.newHashMap();
         properties.put(TYPE, muranoPackage.getFullyQualifiedName());
         properties.put(ID, muranoPackage.getId());
         AddApplicationOptions addApplicationOptions = new AddApplicationOptions().properties(properties).name(name);
         if (environmentName != null) {
            addApplicationOptions.hotEnvironment(environmentName);
         }
         return addApplicationOptions;

      }

      /**
       * @see AddApplicationOptions#getProperties()
       */
      public static AddApplicationOptions properties(Map<String, Object> properties) {
         return new AddApplicationOptions().properties(properties);
      }

      /**
       * @see AddApplicationOptions#getName()
       */
      public static AddApplicationOptions name(String name) {
         return new AddApplicationOptions().name(name);
      }

      /**
       * @see AddApplicationOptions#getHotEnvironment()
       */
      public static AddApplicationOptions hotEnvironment(String hotEnvironment) {
         return new AddApplicationOptions().hotEnvironment(hotEnvironment);
      }
   }

}
