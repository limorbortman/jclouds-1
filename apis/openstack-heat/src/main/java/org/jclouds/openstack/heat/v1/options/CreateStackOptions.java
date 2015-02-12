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
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 *
 */
public class CreateStackOptions implements MapBinder {

   public static final CreateStackOptions NONE = new CreateStackOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String name;
   protected String templateUrl;
   protected String template;
   protected Map<String, String> parameters = ImmutableMap.of();
   private boolean disableRollback = true;
   protected Map<String, String> files = ImmutableMap.of();
   protected Optional<Environment> environment;

   static class CreateStackRequest {

      @Named("stack_name")
      String name;
      @Named("template_url")
      String templateUrl;
      @Named("template")
      String template;
      @Named("parameters")
      Map<String, String> parameters;
      @Named("disable_rollback")
      boolean disableRollback;
      @Named("files")
      Map<String, String> files = ImmutableMap.of();
//      @Named("environment")
      Optional<Environment> environment;

   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      CreateStackRequest creaetStackRequest = new CreateStackRequest();
      if (name != null)
         creaetStackRequest.name = name;
      if (templateUrl != null)
         creaetStackRequest.templateUrl = templateUrl;
      if (template != null)
         creaetStackRequest.template = template;
      if (!parameters.isEmpty())
         creaetStackRequest.parameters = parameters;
      if (!files.isEmpty())
         creaetStackRequest.files = files;
      if (environment != null)
         creaetStackRequest.environment = environment;
      creaetStackRequest.disableRollback = disableRollback;

      return jsonBinder.bindToRequest(request, creaetStackRequest);
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("createStackRequest is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateStackOptions)) return false;
      final CreateStackOptions other = CreateStackOptions.class.cast(object);
      return equal(name, other.name) && equal(templateUrl, other.templateUrl) && equal(template, other.template) && equal(parameters, other.parameters) && equal(disableRollback, other.parameters)
            && equal(files, other.files) && equal(environment, other.environment);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, templateUrl, template, parameters, disableRollback, files, environment);
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("").add("name", name).add("templateUrl", templateUrl).add("template", template).add("parameters", parameters).add("disableRollback", disableRollback)
            .add("files", files).add("environment", environment);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   /**
    * @param name - The name of the stack to create.
    */
   public CreateStackOptions name(String name) {
      this.name = name;
      return this;
   }

   /**
    * @param templateUrl - A URI to the location containing the stack template to instantiate.
    */
   public CreateStackOptions templateUrl(String templateUrl) {
      this.templateUrl = templateUrl;
      return this;
   }

   /**
    * @param template - The stack template to instantiate.
    */
   public CreateStackOptions template(String template) {
      this.template = template;
      return this;
   }

   /**
    * @param parameters - The properties for the template
    */
   public CreateStackOptions parameters(Map<String, String> parameters) {
      this.parameters = parameters;
      return this;
   }

   /**
    * @param disableRollback - Controls whether a failure during stack creation causes deletion of all previously-created resources in that stack. The default is True
    */
   public CreateStackOptions disableRollback(boolean disableRollback) {
      this.disableRollback = disableRollback;
      return this;
   }

   /**
    * @param files - The properties for the template
    */
   public CreateStackOptions files(Map<String, String> files) {
      this.files = files;
      return this;
   }

   /**
    * @param environment - used to affect the runtime behaviour of the template
    */
   public CreateStackOptions environment(Environment environment) {
      this.environment = Optional.fromNullable(environment);
      return this;
   }

   public String getName() {
      return name;
   }

   public String getTemplateUrl() {
      return templateUrl;
   }

   public String getTemplate() {
      return template;
   }

   public Map<String, String> getParameters() {
      return parameters;
   }

   public boolean isDisableRollback() {
      return disableRollback;
   }

   public Map<String, String> getFiles() {
      return files;
   }

   public Optional<Environment> getEnvironment() {
      return environment;
   }

   public static class Builder {

      /**
       * @see CreateStackOptions#getName()
       */
      public static CreateStackOptions name(String name) {
         return new CreateStackOptions().name(name);
      }

      /**
       * @see CreateStackOptions#getTemplateUrl()
       */
      public static CreateStackOptions templateUrl(String templateUrl) {
         return new CreateStackOptions().templateUrl(templateUrl);
      }

      /**
       * @see CreateStackOptions#getTemplate()
       */
      public static CreateStackOptions template(String template) {
         return new CreateStackOptions().template(template);
      }

      /**
       * @see CreateStackOptions#getParameters() () ()
       */
      public static CreateStackOptions parameters(Map<String, String> parameters) {
         return new CreateStackOptions().parameters(parameters);
      }

      /**
       * @see CreateStackOptions#isDisableRollback()
       */
      public static CreateStackOptions disableRollback(boolean disableRollback) {
         return new CreateStackOptions().disableRollback(disableRollback);
      }

      /**
       * @see CreateStackOptions#getFiles()
       */
      public static CreateStackOptions files(Map<String, String> files) {
         return new CreateStackOptions().parameters(files);
      }

      /**
       * @see CreateStackOptions#getEnvironment()
       */
      public static CreateStackOptions environment(Environment environment) {
         return new CreateStackOptions().environment(environment);
      }

   }

}