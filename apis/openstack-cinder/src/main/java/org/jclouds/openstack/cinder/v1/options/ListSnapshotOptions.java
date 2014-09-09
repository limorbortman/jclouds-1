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
public class ListSnapshotOptions extends ListOptions {

   public static final ListSnapshotOptions NONE = new ListSnapshotOptions();

   /**
    * {@inheritDoc}
    */
   @Override
   public ListSnapshotOptions withDetails() {
      super.withDetails();
      return this;
   }

   /**
    * list snapshots of all tenants
    */
   @Override
   public ListSnapshotOptions allTenants() {
      super.allTenants();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListSnapshotOptions changesSince(Date ifModifiedSince) {
      super.changesSince(ifModifiedSince);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListSnapshotOptions limit(int limit) {
      super.limit(limit);
      return this;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListSnapshotOptions marker(String marker) {
      super.marker(marker);
      return this;
   }

   public static class Builder {

      /**
       * @see ListSnapshotOptions#allTenants()
       */
      public static ListSnapshotOptions allTenants() {
         ListSnapshotOptions options = new ListSnapshotOptions();
         return options.allTenants();
      }

      /**
       * @see ListOptions#withDetails()
       */
      public static ListSnapshotOptions withDetails() {
         ListSnapshotOptions options = new ListSnapshotOptions();
         return options.withDetails();
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#marker(String)
       */
      public static ListSnapshotOptions marker(String marker) {
         ListSnapshotOptions options = new ListSnapshotOptions();
         return options.marker(marker);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#limit(long)
       */
      public static ListSnapshotOptions maxResults(int maxKeys) {
         ListSnapshotOptions options = new ListSnapshotOptions();
         return options.limit(maxKeys);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#changesSince(Date)
       */
      public static ListSnapshotOptions changesSince(Date since) {
         ListSnapshotOptions options = new ListSnapshotOptions();
         return options.changesSince(since);
      }
   }

}