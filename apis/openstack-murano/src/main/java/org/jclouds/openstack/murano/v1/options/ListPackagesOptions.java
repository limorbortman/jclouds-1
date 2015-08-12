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

import com.google.common.base.CaseFormat;
import org.jclouds.openstack.v2_0.options.PaginationOptions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Options used to control the amount of detail in the request.
 *
 * @see PaginationOptions
 */
public class ListPackagesOptions extends PaginationOptions {

   public static final ListPackagesOptions NONE = new ListPackagesOptions();

   public enum OrderBy {
      FQN, NAME, CREATED, UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
      }

      @Override
      public String toString() {
         return value();
      }

      /**
       * This provides GSON enum support in jclouds.
       *
       * @param orderBy The string representation of this enum value.
       * @return The corresponding enum value.
       */

      public static OrderBy fromValue(String orderBy) {
         if (orderBy != null) {
            for (OrderBy value : OrderBy.values()) {
               if (orderBy.equalsIgnoreCase(value.name())) {
                  return value;
               }
            }
         }
         return UNRECOGNIZED;
      }

      public static boolean contains(OrderBy orderBy) {
         for (OrderBy key : OrderBy.values()) {
            if (key.equals(orderBy)) {
               return true;
            }
         }
         return false;
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListPackagesOptions limit(int limit) {
      super.limit(limit);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListPackagesOptions marker(String marker) {
      super.marker(marker);
      return this;
   }

   /**
    * Filters the stack list by the specified status. You can use this filter multiple
    * times to filter by multiple statuses.
    */
   public ListPackagesOptions category(String category) {
      this.queryParameters.put("category", checkNotNull(category, "category"));
      return this;
   }


   /**
    * Sorts the stack list by one of these attributes:
    * name, status, created_at, or updated_at
    */
   public ListPackagesOptions orderBy(OrderBy key) {
      checkState(OrderBy.contains(key), "invalid order key");
      this.queryParameters.put("order_by", checkNotNull(key.toString(), "key"));
      return this;
   }


   public static Builder builder() {
      return new Builder();
   }

   public static class Builder {

      /**
       * @see PaginationOptions#limit(int)
       */
      public static ListPackagesOptions limit(int limit) {
         return new ListPackagesOptions().limit(limit);
      }

      /**
       * @see PaginationOptions#marker(String)
       */
      public static ListPackagesOptions marker(String marker) {
         return new ListPackagesOptions().marker(marker);
      }

      /**
       * @see ListPackagesOptions#category(String) (String)
       */
      public static ListPackagesOptions category(String category) {
         return new ListPackagesOptions().category(category);
      }

      /**
       * @see ListPackagesOptions#orderBy(OrderBy) (String)
       */
      public static ListPackagesOptions orderBy(OrderBy key) {
         return new ListPackagesOptions().orderBy(key);
      }

   }
}
