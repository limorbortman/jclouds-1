/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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

public class UpdateOptions implements MapBinder {
   public static final UpdateOptions NONE = new UpdateOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String templateUrl;
   protected String template;
   protected Map<String, Object> parameters = ImmutableMap.of();

   static class UpdateOptionsRequest {

      @Named("template_url")
      String templateUrl;
      String template;
      Map<String, Object> parameters = ImmutableMap.of();

      private UpdateOptionsRequest() {

      }

   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      UpdateOptionsRequest updateOptionsRequest = new UpdateOptionsRequest();
      if (templateUrl != null) {
         updateOptionsRequest.templateUrl = templateUrl;
      }
      if (template != null) {
         updateOptionsRequest.template = template;
      }
      if (!parameters.isEmpty()) {
         updateOptionsRequest.parameters = parameters;
      }

      return jsonBinder.bindToRequest(request, updateOptionsRequest);
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("updateOptionsRequest is a PUT operation");
   }

   /**
    * @param templateUrl - A URI to the location containing the stack template to instantiate.
    */
   public UpdateOptions templateUrl(String templateUrl) {
      this.templateUrl = templateUrl;
      return this;
   }

   /**
    * @param template - The stack template to instantiate.
    */
   public UpdateOptions template(String template) {
      this.template = template;
      return this;
   }

   /**
    * @param parameters - The properties for the template
    */
   public UpdateOptions parameters(Map<String, Object> parameters) {
      this.parameters = parameters;
      return this;
   }

   public String getTemplateUrl() {
      return templateUrl;
   }

   public String getTemplate() {
      return template;
   }

   public Map<String, Object> getParameters() {
      return parameters;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateStackOptions)) return false;
      final CreateStackOptions other = CreateStackOptions.class.cast(object);
      return equal(templateUrl, other.templateUrl)
            && equal(template, other.template)
            && equal(parameters, other.parameters);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(templateUrl, template, parameters);
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("")
            .add("templateUrl", templateUrl)
            .add("template", template)
            .add("parameters", parameters);
   }

   @Override
   public String toString() {
      return string().toString();
   }


   public static class Builder {

      /**
       * @see UpdateOptions#getTemplateUrl()
       */
      public static UpdateOptions templateUrl(String templateUrl) {
         return new UpdateOptions().templateUrl(templateUrl);
      }

      /**
       * @see UpdateOptions#getTemplate()
       */
      public static UpdateOptions template(String template) {
         return new UpdateOptions().template(template);
      }

      /**
       * @see UpdateOptions#getParameters() () ()
       */
      public static UpdateOptions parameters(Map<String, Object> parameters) {
         return new UpdateOptions().parameters(parameters);
      }


   }
}
