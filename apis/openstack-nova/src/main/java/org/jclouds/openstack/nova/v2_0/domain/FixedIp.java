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
package org.jclouds.openstack.nova.v2_0.domain;

import com.google.common.base.Objects;
import java.beans.ConstructorProperties;

/**
 *
 */
public class FixedIp {
   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }
   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromFixedIp(this);
   }
   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();
      protected String subnetId;
      protected String ipAddress;
      /**
       * @see FixedIp#getSubnetId()
       */
      public T subnetId(String subnetId) {
         this.subnetId = subnetId;
         return self();
      }
      /**
       * @see FixedIp#getIpAddress()
       */
      public T ipAddress(String ipAddress) {
         this.ipAddress = ipAddress;
         return self();
      }
      public FixedIp build() {
         return new FixedIp(subnetId, ipAddress);
      }
      public T fromFixedIp(FixedIp in) {
         return this.subnetId(in.getSubnetId()).ipAddress(in.getIpAddress());
      }
   }
   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
   protected String subnetId;
   protected String ipAddress;
   @ConstructorProperties({"subnet_id", "ip_address"})
   public FixedIp(String subnetId, String ipAddress) {
      this.subnetId = subnetId;
      this.ipAddress = ipAddress;
   }
   public String getSubnetId() {
      return subnetId;
   }
   public String getIpAddress() {
      return ipAddress;
   }
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      FixedIp that = FixedIp.class.cast(obj);
      return Objects.equal(this.subnetId, that.subnetId)
            && Objects.equal(this.ipAddress, that.ipAddress);
   }
   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper("")
            .omitNullValues()
            .add("subnetId", subnetId)
            .add("ipAddress", ipAddress);
   }
   @Override
   public String toString() {
      return string().toString();
   }
}