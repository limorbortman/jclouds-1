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

import com.google.common.base.Function;
import com.google.common.base.Objects;

import java.util.List;

import static com.google.common.base.Objects.toStringHelper;


public class CreatePackageOptions implements Function<Object, String> {

   public static final CreatePackageOptions NONE = new CreatePackageOptions();


   protected List<String> categories;
   protected List<String> tags;
   protected boolean isPublic;
   protected boolean enabled;

   @Override
   public String apply(Object o) {
      if (o != null) {
         CreatePackageOptions createPackageOptions = (CreatePackageOptions) o;
         StringBuilder json = new StringBuilder();
         json.append("{");
         if (createPackageOptions.getCategories() != null && !createPackageOptions.getCategories().isEmpty()) {
            json.append("\"categories\": [").append(generateListFromJson(createPackageOptions.getCategories()).toString()).append("],");
         }
         if (createPackageOptions.getTags() != null && !createPackageOptions.getTags().isEmpty()) {
            json.append("\"tags\": [").append(generateListFromJson(createPackageOptions.getTags()).toString()).append("],");
         }
         json.append("\"is_public\":").append(createPackageOptions.isPublic()).append(",");
         json.append("\"enabled\":").append(createPackageOptions.isEnabled());
         json.append("}");
         return json.toString();
      }
      return null;
   }


   public CreatePackageOptions categories(List<String> categories) {
      this.categories = categories;
      return this;
   }


   public CreatePackageOptions tags(List<String> tags) {
      this.tags = tags;
      return this;
   }

   public CreatePackageOptions isPublic(boolean isPublic) {
      this.isPublic = isPublic;
      return this;
   }

   public CreatePackageOptions enabled(boolean enabled) {
      this.enabled = enabled;
      return this;
   }

   public List<String> getCategories() {
      return categories;
   }

   public List<String> getTags() {
      return tags;
   }

   public boolean isPublic() {
      return isPublic;
   }

   public boolean isEnabled() {
      return enabled;
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("")
            .add("categories", categories)
            .add("tags", tags)
            .add("isPublic", isPublic)
            .add("enabled", enabled);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   private StringBuilder generateListFromJson(List<String> list) {
      StringBuilder sb = new StringBuilder();
      for (String s : list) {
         sb.append("\"");
         sb.append(s);
         sb.append("\"");
         sb.append(",");
      }
      sb.setLength(sb.length() - 1);
      return sb;
   }

   public static class Builder {


      public static CreatePackageOptions categories(List<String> categories) {
         return new CreatePackageOptions().categories(categories);
      }


      public static CreatePackageOptions tags(List<String> tags) {
         return new CreatePackageOptions().tags(tags);
      }


      public static CreatePackageOptions enabled(boolean enabled) {
         return new CreatePackageOptions().enabled(enabled);
      }


      public static CreatePackageOptions isPublic(boolean isPublic) {
         return new CreatePackageOptions().isPublic(isPublic);
      }
   }

}
