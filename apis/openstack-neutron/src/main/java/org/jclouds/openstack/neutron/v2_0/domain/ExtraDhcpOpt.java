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
 * Extra DHCP option for Port
 *
 */
public class ExtraDhcpOpt {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromExtraDhcpOpt(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String optName;
      protected String optValue;

      /**
       * @see ExtraDhcpOpt#getOptName()
       */
      public T optName(String optName) {
         this.optName = optName;
         return self();
      }

      /**
       * @see ExtraDhcpOpt#getOptValue()
       */
      public T optValue(String optValue) {
         this.optValue = optValue;
         return self();
      }

      public ExtraDhcpOpt build() {
         return new ExtraDhcpOpt(optName, optValue);
      }

      public T fromExtraDhcpOpt(ExtraDhcpOpt in) {
         return this.optName(in.getOptName())
               .optValue(in.getOptValue());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String optName;
   private final String optValue;

   @ConstructorProperties({"opt_name", "opt_value"})
   protected ExtraDhcpOpt(String optName, String optValue) {
      this.optName = optName;
      this.optValue = optValue;
   }

   /**
    * @return The name of the extra DHCP option
    */
   public String getOptName() {
      return optName;
   }

   /**
    * @return The value of the extra DHCP option
    */
   public String getOptValue() {
      return optValue;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(optName, optValue);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ExtraDhcpOpt that = ExtraDhcpOpt.class.cast(obj);
      return Objects.equal(this.optName, that.optName)
            && Objects.equal(this.optValue, that.optValue);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this).add("optName", optName).add("optValue", optValue);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}