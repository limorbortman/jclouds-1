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
import java.util.Date;

/**
 * Stats for a port (extension)
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/1.0/content/Ports.html">api doc</a>
 */
public class PortStats {

   private Date timestamp;
   @Named("bytes_in")
   private Long bytesIn;
   @Named("bytes_out")
   private Long bytesOut;

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

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
            .add("timestamp", timestamp)
            .add("bytesIn", bytesIn)
            .add("bytesOut", bytesOut)
            .toString();
   }

   public Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromPortStats(this);
   }

   public class Builder {
      protected Date timestamp;
      protected Long bytesIn;
      protected Long bytesOut;

      /**
       * @see PortStats#getTimestamp()
       */
      public Builder timestamp(Date timestamp) {
         this.timestamp = timestamp;
         return this;
      }

      /**
       * @see PortStats#getBytesIn()
       */
      public Builder bytesIn(Long bytesIn) {
         this.bytesIn = bytesIn;
         return this;
      }

      /**
       * @see PortStats#getBytesOut()
       */
      public Builder bytesOut(Long bytesOut) {
         this.bytesOut = bytesOut;
         return this;
      }

      public PortStats build() {
         return new PortStats(timestamp, bytesIn, bytesOut);
      }

      public Builder fromPortStats(PortStats in) {
         return this.timestamp(in.getTimestamp())
               .bytesIn(in.getBytesIn())
               .bytesOut(in.getBytesOut());
      }
   }

}
