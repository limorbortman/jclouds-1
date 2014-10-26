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
import java.util.Map;

/**
 * A Meter the Ceilometer server knows about
 *
 * @see <a href= "http://developer.openstack.org/api-ref-telemetry-v2.html" />
 * @see <a href= "https://github.com/openstack/ceilometer/tree/master/" />
 */
public class Sample {
   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromSample(this);
   }

   public static class Builder {
      protected String id;
      protected String userId;
      protected String resourceId;
      protected String timestamp;
      protected String meter;
      protected String volume;
      protected String source;
      protected String recordedAt;
      protected String projectId;
      protected String type;
      protected String unit;
      protected Map<String, Object> metadata;

      public Builder id(String id) {
         this.id = id;
         return this;
      }

      public Builder userId(String userId) {
         this.userId = userId;
         return this;
      }

      public Builder resourceId(String resourceId) {
         this.resourceId = resourceId;
         return this;
      }

      public Builder timestamp(String timestamp) {
         this.timestamp = timestamp;
         return this;
      }

      public Builder meter(String meter) {
         this.meter = meter;
         return this;
      }

      public Builder volume(String volume) {
         this.volume = volume;
         return this;
      }

      public Builder source(String source) {
         this.source = source;
         return this;
      }

      public Builder recordedAt(String recordedAt) {
         this.recordedAt = recordedAt;
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

      public Builder metadata(Map<String, Object> metadata) {
         this.metadata = metadata;
         return this;
      }
       //////////////////////////////////////////////////

      public Sample build() {
         return new Sample(id, userId, resourceId, timestamp, meter, volume, source, recordedAt, projectId, type, unit, metadata);
      }

      public Builder fromSample(Sample in) {
         return this
               .id(in.getId())
               .userId(in.getUserId())
               .resourceId(in.getResourceId())
               .timestamp(in.getTimestamp())
               .meter(in.getMeter())
               .volume(in.getVolume())
               .source(in.getVolume())
               .recordedAt(in.getRecordedAt())
               .projectId(in.getProjectId())
               .type(in.getType())
               .unit(in.getUnit())
               .metadata(in.getMetadata());
      }
   }
   protected String id;
   @Named("user_id")
   protected String userId;
   @Named("resource_id")
   protected String resourceId;
   protected String timestamp;
   protected String meter;
   protected String volume;
   protected String source;
   @Named("recorded_at")
   protected String recordedAt;
   @Named("project_id")
   protected String projectId;
   protected String type;
   protected String unit;
   protected Map<String, Object> metadata;


   @ConstructorProperties({"id", "user_id", "resource_id", "timestamp", "meter", "volume", "source", "recorded_at",
         "project_id", "type", "unit", "metadata"})
   public Sample(String id, String userId, String resourceId, String timestamp, String meter, String volume, String source, String recordedAt, String
         projectId, String type, String unit, Map<String, Object> metadata) {
      this.id = id;
      this.userId = userId;
      this.resourceId = resourceId;
      this.timestamp = timestamp;
      this.meter = meter;
      this.volume = volume;
      this.source = source;
      this.recordedAt = recordedAt;
      this.projectId = projectId;
      this.type = type;
      this.unit = unit;
      this.metadata = metadata;
   }

   public String getId() {
      return id;
   }

   public String getUserId() {
      return userId;
   }

   public String getResourceId() {
      return resourceId;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public String getMeter() {
      return meter;
   }

   public String getVolume() {
      return volume;
   }

   public String getSource() {
      return source;
   }

   public String getRecordedAt() {
      return recordedAt;
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

   public Map<String, Object> getMetadata() {
      return metadata;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Sample sample = (Sample) o;

      return Objects.equal(this.id, sample.getId()) &&
            Objects.equal(this.userId, sample.getUserId()) &&
            Objects.equal(this.resourceId, sample.getResourceId()) &&
            Objects.equal(this.timestamp, sample.getTimestamp()) &&
            Objects.equal(this.meter, sample.getMeter()) &&
            Objects.equal(this.volume, sample.getVolume()) &&
            Objects.equal(this.source, sample.getSource()) &&
            Objects.equal(this.recordedAt, sample.getRecordedAt()) &&
            Objects.equal(this.projectId, sample.getProjectId()) &&
            Objects.equal(this.type, sample.getType()) &&
            Objects.equal(this.unit, sample.getUnit()) &&
            Objects.equal(this.metadata, sample.getMetadata());

   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, userId, resourceId, timestamp, meter, volume, source, recordedAt, projectId, type, unit, metadata);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id).add("userId", userId).add("resourceId", resourceId).add("timestamp", timestamp).add("meter", meter)
            .add("volume", volume).add("source", source).add("recordedAt", recordedAt).add("projectId", projectId).add("type", type)
            .add("unit", unit).add("metadata", metadata);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}
