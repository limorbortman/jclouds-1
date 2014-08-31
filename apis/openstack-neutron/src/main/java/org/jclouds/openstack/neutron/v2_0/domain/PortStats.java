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

package org.jclouds.openstack.neutron.v2_0.domain;

import com.google.common.base.Objects;

import java.beans.ConstructorProperties;
import java.util.Date;

/**
 * Stats for a port (extension)
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/1.0/content/Ports.html">api doc</a>
 */
public class PortStats {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromPortStats(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected Date timestamp;
      protected Long bytesIn;
      protected Long bytesOut;

      /**
       * @see PortStats#getTimestamp()
       */
      public T timestamp(Date timestamp) {
         this.timestamp = timestamp;
         return self();
      }

      /**
       * @see PortStats#getBytesIn()
       */
      public T bytesIn(Long bytesIn) {
         this.bytesIn = bytesIn;
         return self();
      }

      /**
       * @see PortStats#getBytesOut()
       */
      public T bytesOut(Long bytesOut) {
         this.bytesOut = bytesOut;
         return self();
      }

      public PortStats build() {
         return new PortStats(timestamp, bytesIn, bytesOut);
      }

      public T fromPortStats(PortStats in) {
         return this.timestamp(in.getTimestamp())
               .bytesIn(in.getBytesIn())
               .bytesOut(in.getBytesOut());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final Date timestamp;
   private final Long bytesIn;
   private final Long bytesOut;

   @ConstructorProperties({
         "timestamp", "bytes_in", "bytes_out"
   })
   protected PortStats(Date timestamp, Long bytesIn, Long bytesOut) {
      this.timestamp = timestamp;
      this.bytesIn = bytesIn;
      this.bytesOut = bytesOut;
   }

   /**
    * @return the timestamp of retrieval of stats
    */
   public Date getTimestamp() {
      return timestamp;
   }

   /**
    * @return the number of bytes received
    */
   public Long getBytesIn() {
      return bytesIn;
   }

   /**
    * @return the number of bytes sent
    */
   public Long getBytesOut() {
      return bytesOut;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(timestamp, bytesIn, bytesOut);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      PortStats that = PortStats.class.cast(obj);
      return Objects.equal(this.timestamp, that.timestamp)
            && Objects.equal(this.bytesIn, that.bytesIn)
            && Objects.equal(this.bytesOut, that.bytesOut);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this).add("timestamp", timestamp)
            .add("bytesIn", bytesIn).add("bytesOut", bytesOut);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}