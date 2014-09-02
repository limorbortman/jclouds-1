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

package org.jclouds.openstack.nova.v2_0.options;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Options used to control list of servers results.
 */
public class ListServerOptions extends ListOptions {

   public static final ListServerOptions NONE = new ListServerOptions();

   /**
    * list servers by host name
    *
    */
   public ListServerOptions host(String host) {
      queryParameters.put("host", checkNotNull(host, "host"));
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListServerOptions withDetails() {
      super.withDetails();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListServerOptions changesSince(Date ifModifiedSince) {
      super.changesSince(ifModifiedSince);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListServerOptions limit(int limit) {
      super.limit(limit);
      return this;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListServerOptions marker(String marker) {
      super.marker(marker);
      return this;
   }


   public static class Builder {

      /**
       * @see ListServerOptions#host(String)
       */
      public static ListServerOptions host(String host) {
         ListServerOptions options = new ListServerOptions();
         return options.host(host);
      }


      /**
       * @see ListOptions#withDetails()
       */
      public static ListServerOptions withDetails() {
         ListServerOptions options = new ListServerOptions();
         return options.withDetails();
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#marker(String)
       */
      public static ListServerOptions marker(String marker) {
         ListServerOptions options = new ListServerOptions();
         return options.marker(marker);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#limit(long)
       */
      public static ListServerOptions maxResults(int maxKeys) {
         ListServerOptions options = new ListServerOptions();
         return options.limit(maxKeys);
      }

      /**
       * @see org.jclouds.openstack.v2_0.options.PaginationOptions#changesSince(Date)
       */
      public static ListServerOptions changesSince(Date since) {
         ListServerOptions options = new ListServerOptions();
         return options.changesSince(since);
      }
   }
}