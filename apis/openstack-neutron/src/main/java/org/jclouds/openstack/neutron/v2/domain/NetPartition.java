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
package org.jclouds.openstack.neutron.v2.domain;

import com.google.common.base.Objects;

import javax.inject.Named;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An extension for Nuage called Net Partition (reflects on enterprise on VSD)
 */
public class NetPartition {

   private String id;
   private String name;
   @Named("enterprise_id")
   private String enterpriseId;

   @ConstructorProperties({"id", "name", "enterprise_id"})
   protected NetPartition(String id, String name, String enterpriseId) {
      this.id = id;
      this.name = name;
      this.enterpriseId = enterpriseId;
   }

   /**
    * Default constructor.
    */
   private NetPartition() {}

   /**
    * Copy constructor
    * @param netPartition
    */
   private NetPartition(NetPartition netPartition) {
      this(netPartition.id,
            netPartition.name,
            netPartition.enterpriseId);
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
      return Objects.hashCode(id, name, enterpriseId);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      NetPartition that = NetPartition.class.cast(obj);
      return Objects.equal(this.id, that.id)
            && Objects.equal(this.name, that.name)
            && Objects.equal(this.enterpriseId, that.enterpriseId);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("name", name)
            .add("enterpriseId", enterpriseId).toString();
   }

   /*
    * Methods to get the Create and Update builders follow
    */

   /**
    * @return the Builder for creating a new NetPartition
    */
   public static CreateBuilder createOptions(String name) {
      return new CreateBuilder(name);
   }

   private static abstract class Builder<ParameterizedBuilderType> {
      protected NetPartition netPartition;

      /**
       * No-parameters constructor used when updating.
       * */
      private Builder() {
         netPartition = new NetPartition();
      }

      protected abstract ParameterizedBuilderType self();

      /**
       * Provide the name to the NetPartition's Builder.
       *
       * @return the Builder.
       * @see  NetPartition#getName()
       */
      public ParameterizedBuilderType name(String name) {
         netPartition.name = name;
         return self();
      }

      /**
       * Provide the adminStateUp to the NetPartition's Builder.
       *
       * @return the Builder.
       * @see  NetPartition#getEnterpriseId()
       */
      public ParameterizedBuilderType enterpriseId(String enterpriseId) {
         netPartition.enterpriseId = enterpriseId;
         return self();
      }
   }

   /**
    * Create and Update builders (inheriting from Builder)
    */
   public static class CreateBuilder extends Builder<CreateBuilder> {
      /**
       * Supply required properties for creating a Builder
       */
      private CreateBuilder(String name) {
         netPartition.name = name;
      }

      /**
       * @return a CreateOptions constructed with this Builder.
       */
      public CreateOptions build() {
         return new CreateOptions(netPartition);
      }

      protected CreateBuilder self() {
         return this;
      }
   }

   /**
    * Create and Update options - extend the domain class, passed to API update and create calls.
    * Essentially the same as the domain class. Ensure validation and safe typing.
    */
   public static class CreateOptions extends NetPartition {
      /**
       * Copy constructor
       */
      private CreateOptions(NetPartition netPartition) {
         super(netPartition);
         checkNotNull(netPartition.name, "name should not be null");
      }
   }

}
