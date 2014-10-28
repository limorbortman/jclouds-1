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
package org.jclouds.openstack.ceilometer.v2.domain;

import com.google.common.base.Objects;
import com.google.inject.name.Named;

import java.beans.ConstructorProperties;

/**
 * A Meter the Ceilometer server knows about
 *
 * @see <a href= "http://developer.openstack.org/api-ref-telemetry-v2.html" />
 * @see <a href= "https://github.com/openstack/ceilometer/tree/master/" />
 */
public class Meter {

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromMeter(this);
   }

   public static class Builder {
      protected String userId;
      protected String name;
      protected String resourceId;
      protected String source;
      protected String meterId;
      protected String projectId;
      protected String type;
      protected String unit;

      public Builder userId(String userId) {
         this.userId = userId;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder resourceId(String resourceId) {
         this.resourceId = resourceId;
         return this;
      }

      public Builder source(String source) {
         this.source = source;
         return this;
      }

      public Builder meterId(String meterId) {
         this.meterId = meterId;
         return this;
      }

      public Builder projectId(String projectId) {
         this.projectId = projectId;
         return this;
      }

      public Builder type(String type) {
         this.type = type;
         return this;
      }

      public Builder unit(String unit) {
         this.unit = unit;
         return this;
      }

      public Meter build() {
         return new Meter(userId, name, resourceId, source, meterId, projectId, type, unit);
      }

      public Builder fromMeter(Meter in) {
         return this
               .userId(in.getUserId())
               .name(in.getName())
               .resourceId(in.getResourceId())
               .source(in.getSource())
               .meterId(in.getMeterId())
               .projectId(in.getProjectId())
               .type(in.getType())
               .unit(in.getUnit());
      }
   }

   @Named("user_id")
   protected String userId;
   protected String name;
   @Named("resource_id")
   protected String resourceId;
   protected String source;
   @Named("meter_id")
   protected String meterId;
   @Named("project_id")
   protected String projectId;
   protected String type;
   protected String unit;

   @ConstructorProperties({"user_id", "name", "resource_id", "source", "meter_id", "project_id", "type", "unit"})
   public Meter(String userId, String name, String resourceId, String source, String meterId, String projectId, String type, String unit) {
      this.userId = userId;
      this.name = name;
      this.resourceId = resourceId;
      this.source = source;
      this.meterId = meterId;
      this.projectId = projectId;
      this.type = type;
      this.unit = unit;
   }

   public String getUserId() {
      return userId;
   }

   public String getName() {
      return name;
   }

   public String getResourceId() {
      return resourceId;
   }

   public String getSource() {
      return source;
   }

   public String getMeterId() {
      return meterId;
   }

   public String getProjectId() {
      return projectId;
   }

   public String getType() {
      return type;
   }

   public String getUnit() {
      return unit;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Meter meter = (Meter) o;

      return Objects.equal(this.userId, meter.userId) &&
            Objects.equal(this.name, meter.name) &&
            Objects.equal(this.resourceId, meter.resourceId) &&
            Objects.equal(this.source, meter.source) &&
            Objects.equal(this.meterId, meter.meterId) &&
            Objects.equal(this.projectId, meter.projectId) &&
            Objects.equal(this.type, meter.type) &&
            Objects.equal(this.unit, meter.unit);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(userId, name, resourceId, source, meterId, projectId, type, unit);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("userId", userId).add("name", name).add("resourceId", resourceId).add("source", source).add("meterId", meterId)
            .add("projectId", projectId).add("type", type).add("unit", unit);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}
