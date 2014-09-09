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

package org.jclouds.openstack.cinder.v1.options;

import java.util.Date;

/**
 * Options used to control list of volumes results.
 */
public class ListVolumeOptions extends ListOptions {

   public static final ListVolumeOptions NONE = new ListVolumeOptions();

   /**
    * {@inheritDoc}
    */
   @Override
   public ListVolumeOptions withDetails() {
      super.withDetails();
      return this;
   }
   
   /**
    * list volumes of all tenants
    */
   @Override
   public ListVolumeOptions allTenants() {
      super.allTenants();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListVolumeOptions changesSince(Date ifModifiedSince) {
      super.changesSince(ifModifiedSince);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListVolumeOptions limit(int limit) {
      super.limit(limit);
      return this;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListVolumeOptions marker(String marker) {
      super.marker(marker);
      return this;
   }

   public static class Builder {

      /**
       * @see ListVolumeOptions#allTenants()
       */
      public static ListVolumeOptions allTenants() {
         ListVolumeOptions options = new ListVolumeOptions();
         return options.allTenants();
      }

      /**
       * @see ListOptions#withDetails()
       */
      public static ListVolumeOptions withDetails() {
         ListVolumeOptions options = new ListVolumeOptions();
         return options.withDetails();
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#marker(String)
       */
      public static ListVolumeOptions marker(String marker) {
         ListVolumeOptions options = new ListVolumeOptions();
         return options.marker(marker);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#limit(long)
       */
      public static ListVolumeOptions maxResults(int maxKeys) {
         ListVolumeOptions options = new ListVolumeOptions();
         return options.limit(maxKeys);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#changesSince(Date)
       */
      public static ListVolumeOptions changesSince(Date since) {
         ListVolumeOptions options = new ListVolumeOptions();
         return options.changesSince(since);
      }
   }

}