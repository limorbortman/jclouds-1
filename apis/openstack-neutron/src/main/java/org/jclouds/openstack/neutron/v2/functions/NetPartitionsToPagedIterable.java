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
package org.jclouds.openstack.neutron.v2.functions;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.internal.Arg0ToPagedIterable;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.NetPartition;
import org.jclouds.openstack.neutron.v2.extensions.NetPartitionApi;
import org.jclouds.openstack.v2_0.options.PaginationOptions;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Ensures NetPartitions works as PagedIterable.
 */
public class NetPartitionsToPagedIterable extends Arg0ToPagedIterable.FromCaller<NetPartition, NetPartitionsToPagedIterable> {

   private final NeutronApi api;

   @Inject
   protected NetPartitionsToPagedIterable(NeutronApi api) {
      this.api = checkNotNull(api, "api");
   }

   @Override
   protected Function<Object, IterableWithMarker<NetPartition>> markerToNextForArg0(Optional<Object> arg0) {
      String region = arg0.isPresent() ? arg0.get().toString() : null;
      final NetPartitionApi netPartitionApi = api.getNetPartitionExtensionApi(region).get();
      return new Function<Object, IterableWithMarker<NetPartition>>() {

         @SuppressWarnings("unchecked")
         @Override
         public IterableWithMarker<NetPartition> apply(Object input) {
            PaginationOptions paginationOptions = PaginationOptions.class.cast(input);
            return IterableWithMarker.class.cast(netPartitionApi.list(paginationOptions));
         }

         @Override
         public String toString() {
            return "listFloatingIps()";
         }
      };
   }

}
