/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.openstack.neutron.v2_0.options;

import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

/**
 *
 */
public class CreateNuageNetPartitionOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromCreateNuageNetPartitionOptions(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String enterpriseId;

      /**
       * @see CreateNuageNetPartitionOptions#getEnterpriseId()
       */
      public T enterpriseId(String enterpriseId) {
         this.enterpriseId = enterpriseId;
         return self();
      }

      public CreateNuageNetPartitionOptions build() {
         return new CreateNuageNetPartitionOptions(enterpriseId);
      }

      public T fromCreateNuageNetPartitionOptions(CreateNuageNetPartitionOptions in) {
         return this.enterpriseId(in.getEnterpriseId());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   protected static class CreateNuageNetPartitionRequest {
      protected String enterprise_id;
   }

   private final String enterpriseId;

   public CreateNuageNetPartitionOptions(String enterpriseId) {
      this.enterpriseId = enterpriseId;
   }

   /**
    * @return The ID of the enterprise to which this net partition should be linked to
    */
   public String getEnterpriseId() {
      return enterpriseId;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      CreateNuageNetPartitionRequest createNuageNetPartitionRequest = new CreateNuageNetPartitionRequest();

      if (this.enterpriseId != null)
         createNuageNetPartitionRequest.enterprise_id = enterpriseId;

      return bindToRequest(request, ImmutableMap.of("net_partition", createNuageNetPartitionRequest));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }

}