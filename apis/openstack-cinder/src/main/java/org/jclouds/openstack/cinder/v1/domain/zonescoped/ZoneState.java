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
 * zone state for availability zones
 */
public class ZoneState {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromZoneState(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected Boolean isAvailable;


      /**
       * @see ZoneState#isAvailable() ()
       */
      public T isAvailable(Boolean isAvailable) {
         this.isAvailable = isAvailable;
         return self();
      }


      public ZoneState build() {
         return new ZoneState(isAvailable);
      }

      public T fromZoneState(ZoneState in) {
         return this
               .isAvailable(in.isAvailable());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final Boolean isAvailable;

   @ConstructorProperties({
         "available"
   })
   protected ZoneState(Boolean isAvailable) {
      this.isAvailable = isAvailable;
   }

   public Boolean isAvailable() {
      return this.isAvailable;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(isAvailable);
   }

   @Override
   public boolean equals(Object obj) {
      if (this != obj) return false;
      if (obj == null || getClass() != obj.getClass()) return false;
      ZoneState that = ZoneState.class.cast(obj);
      return Objects.equal(this.isAvailable, that.isAvailable);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("isAvailable", isAvailable);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}