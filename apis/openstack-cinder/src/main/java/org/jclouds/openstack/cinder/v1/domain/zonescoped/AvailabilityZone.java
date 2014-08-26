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
package org.jclouds.openstack.cinder.v1.domain.zonescoped;

import com.google.common.base.Objects;

import java.beans.ConstructorProperties;

/**
 * availability zone for cinder
 */
public class AvailabilityZone {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromAvailabilityZone(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected ZoneState zoneState;
      protected String name;

      /**
       * @see AvailabilityZone#getName()
       */
      public T name(String name) {
         this.name = name;
         return self();
      }

      /**
       * @see org.jclouds.openstack.cinder.v1.domain.zonescoped.AvailabilityZone#getZoneState()
       */
      public T isAvailable(ZoneState zoneState) {
         this.zoneState = zoneState;
         return self();
      }


      public AvailabilityZone build() {
         return new AvailabilityZone(name, zoneState);
      }

      public T fromAvailabilityZone(AvailabilityZone in) {
         return this
               .name(in.getName()).isAvailable(in.getZoneState());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String name;
   private final ZoneState zoneState;

   @ConstructorProperties({
         "zoneName", "zoneState"
   })
   protected AvailabilityZone(String name, ZoneState zoneState) {
      this.name = name;
      this.zoneState = zoneState;
   }

   public String getName() {
      return this.name;
   }

   public ZoneState getZoneState() {
      return this.zoneState;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, zoneState);
   }

   @Override
   public boolean equals(Object obj) {
      if (this != obj) return false;
      if (obj == null || getClass() != obj.getClass()) return false;
      AvailabilityZone that = AvailabilityZone.class.cast(obj);
      return Objects.equal(this.name, that.name) && Objects.equal(this.zoneState, that.zoneState);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("zoneName", name)
            .add("zoneState", zoneState);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}