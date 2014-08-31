/*
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
package org.jclouds.openstack.neutron.v2_0.domain;

import com.google.common.base.Objects;

import java.beans.ConstructorProperties;

/**
 * A Nuage net partition
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/Networks.html">api doc</a>
 */
public class NuageNetPartition {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromNuageNetPartition(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String id;
      protected String name;
      protected String enterpriseId;

      /**
       * @see NuageNetPartition#getId()
       */
      public T id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see NuageNetPartition#getName()
       */
      public T name(String name) {
         this.name = name;
         return self();
      }

      /**
       * @see NuageNetPartition#getEnterpriseId()
       */
      public T enterpriseId(String enterpriseId) {
         this.enterpriseId = enterpriseId;
         return self();
      }

      public NuageNetPartition build() {
         return new NuageNetPartition(id, name, enterpriseId);
      }

      public T fromNuageNetPartition(NuageNetPartition in) {
         return this
               .id(in.getId())
               .name(in.getName())
               .enterpriseId(in.getEnterpriseId());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String id;
   private final String name;
   private final String enterpriseId;

   @ConstructorProperties({
         "id", "name", "enterprise_id"
   })
   protected NuageNetPartition(String id, String name, String enterpriseId) {
      this.id = id;
      this.name = name;
      this.enterpriseId = enterpriseId;
   }

   /**
    * @return The ID of the net partition.
    */
   public String getId() {
      return id;
   }

   /**
    * @return The name of the net partition.
    */
   public String getName() {
      return name;
   }

   /**
    * @return The ID of the enterprise this net partition is linked to
    */
   public String getEnterpriseId() {
      return enterpriseId;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), id, name, enterpriseId);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      NuageNetPartition that = NuageNetPartition.class.cast(obj);
      return super.equals(obj)
            && Objects.equal(this.id, that.id)
            && Objects.equal(this.name, that.name)
            && Objects.equal(this.enterpriseId, that.enterpriseId);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this).add("id", id).add("name", name).add("enterpriseId", enterpriseId);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}