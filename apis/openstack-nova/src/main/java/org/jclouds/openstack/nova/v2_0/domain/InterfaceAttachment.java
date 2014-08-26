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
import com.google.common.collect.ImmutableSet;
import java.beans.ConstructorProperties;
import java.util.Set;

/**
 *
 */
public class InterfaceAttachment {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromInterfaceAttachment(this);
   }

   public static abstract class Builder<T extends Builder<T>> {

      protected abstract T self();
      protected String portState;
      protected Set<FixedIp> fixedIps = ImmutableSet.of();
      protected String portId;
      protected String netId;
      protected String macAddr;

      /**
       * @see InterfaceAttachment#getPortState()
       */
      public T portState(String portState) {
         this.portState = portState;
         return self();
      }

      /**
       * @see InterfaceAttachment#getFixedIp()
       */
      public T fixedIp( Set<FixedIp> fixedIp) {
         this.fixedIps = fixedIp;
         return self();
      }

      public T fixedIp(FixedIp... in) {
         return fixedIp(ImmutableSet.copyOf(in));
      }

      /**
       * @see InterfaceAttachment#getPortId()
       */
      public T portId(String portId) {
         this.portId = portId;
         return self();
      }

      /**
       * @see InterfaceAttachment#getNetId()
       */
      public T netId(String netId) {
         this.netId = netId;
         return self();
      }

      /**
       * @see InterfaceAttachment#getMacAddr()
       */
      public T macAddr(String macAddr) {
         this.macAddr = macAddr;
         return self();
      }

      public InterfaceAttachment build() {
         return new InterfaceAttachment(portState, fixedIps, portId, netId, macAddr);
      }

      public T fromInterfaceAttachment(InterfaceAttachment in) {
         return this.portState(in.getPortState()).fixedIp(in.getFixedIp()).portId(in.getPortId()).netId(in.getNetId()).macAddr(in.getMacAddr());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {

      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   protected String portState;
   protected Set<FixedIp> fixedIp;
   protected String portId;
   protected String netId;
   protected String macAddr;

   @ConstructorProperties({"port_state", "fixed_ips", "port_id", "net_id", "mac_addr"})
   public InterfaceAttachment(String portState, Set<FixedIp> fixedIp, String portId, String netId, String macAddr) {

      this.portState = portState;
      this.fixedIp = fixedIp == null ? ImmutableSet.<FixedIp>of() : ImmutableSet.copyOf(fixedIp);
      this.portId = portId;
      this.netId = netId;
      this.macAddr = macAddr;
   }

   public String getPortState() {
      return portState;
   }

   public Set<FixedIp> getFixedIp() {
      return fixedIp;
   }

   public String getPortId() {
      return portId;
   }

   public String getNetId() {
      return netId;
   }

   public String getMacAddr() {
      return macAddr;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      InterfaceAttachment that = InterfaceAttachment.class.cast(obj);
      return Objects.equal(this.portState, that.portState)
            && Objects.equal(this.fixedIp, that.fixedIp)
            && Objects.equal(this.portId, that.portId)
            && Objects.equal(this.netId, that.netId)
            && Objects.equal(this.macAddr, that.macAddr);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper("")
            .omitNullValues()
            .add("portState", portState)
            .add("fixedIps", fixedIp)
            .add("portId", portId)
            .add("netId", netId)
            .add("macAddr", macAddr);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}