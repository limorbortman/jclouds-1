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
public class DeleteNuageNetPartitionOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromDeleteNuageNetPartitionOptions(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected Boolean cascadeDelete;

      /**
       * @see DeleteNuageNetPartitionOptions#getCascadeDelete() ()
       */
      public T cascadeDelete(Boolean cascadeDelete) {
         this.cascadeDelete = cascadeDelete;
         return self();
      }

      public DeleteNuageNetPartitionOptions build() {
         return new DeleteNuageNetPartitionOptions(cascadeDelete);
      }

      public T fromDeleteNuageNetPartitionOptions(DeleteNuageNetPartitionOptions in) {
         return this.cascadeDelete(in.getCascadeDelete());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   protected static class DeleteNuageNetPartitionRequest {
      protected Boolean cascade_delete;
   }

   private final Boolean cascadeDelete;

   public DeleteNuageNetPartitionOptions(Boolean cascadeDelete) {
      this.cascadeDelete = cascadeDelete;
   }

   /**
    * @return The ID of the enterprise to which this net partition should be linked to
    */
   public Boolean getCascadeDelete() {
      return cascadeDelete;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      DeleteNuageNetPartitionRequest deleteNuageNetPartitionRequest = new DeleteNuageNetPartitionRequest();

      if (this.cascadeDelete != null)
         deleteNuageNetPartitionRequest.cascade_delete = cascadeDelete;

      return bindToRequest(request, deleteNuageNetPartitionRequest);
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }

}