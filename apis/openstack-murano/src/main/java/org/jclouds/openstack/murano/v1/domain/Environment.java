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
package org.jclouds.openstack.murano.v1.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.v2_0.domain.Resource;

import javax.inject.Named;
import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Map;

public class Environment extends Resource {
   private final Date updated;
   private final Map<String, String> networking;
   private final Date created;
   @Named("tenant_id")
   private final String tenantId;
   private final int version;
   @Nullable
   private final String status;

   @ConstructorProperties({"id", "name", "updated", "networking", "created", "tenant_id", "version", "status"})
   public Environment(String id, String name, Date updated, Map<String, String> networking, Date created, String
         tenantId, int version, @Nullable String status) {
      super(id, name, null);
      this.updated = updated;
      this.networking = networking == null ? ImmutableMap.<String, String>of() : ImmutableMap.copyOf(networking);
      this.created = created;
      this.tenantId = tenantId;
      this.version = version;
      this.status = status;
   }

   public Date getUpdated() {
      return updated;
   }

   public Map<String, String> getNetworking() {
      return networking;
   }

   public Date getCreated() {
      return created;
   }

   public String getTenantId() {
      return tenantId;
   }

   public int getVersion() {
      return version;
   }

   public String getStatus() {
      return status;
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (updated != null ? updated.hashCode() : 0);
      result = 31 * result + (networking != null ? networking.hashCode() : 0);
      result = 31 * result + (created != null ? created.hashCode() : 0);
      result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
      result = 31 * result + version;
      return result;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Environment)) return false;
      if (!super.equals(o)) return false;

      Environment that = (Environment) o;

      if (version != that.version) return false;
      if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
      if (networking != null ? !networking.equals(that.networking) : that.networking != null) return false;
      if (created != null ? !created.equals(that.created) : that.created != null) return false;
      return !(tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null);

   }

   protected Objects.ToStringHelper string() {
      return super.string()
            .add("updated", updated)
            .add("networking", networking)
            .add("created", created)
            .add("tenantId", tenantId)
            .add("version", version)
            .add("status", status);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromEnvironment(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {

      protected Date updated;
      protected Map<String, String> networking;
      protected Date created;
      protected String tenantId;
      protected int version;
      protected String status;

      /**
       * @param updated The updated date of this Environment.
       * @return The builder object.
       * @see Environment#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }


      /**
       * @param networking The networking of this Environment.
       * @return The builder object.
       * @see Environment#getNetworking()
       */
      public T networking(Map<String, String> networking) {
         this.networking = networking;
         return self();
      }


      /**
       * @param created The creation time of this Environment.
       * @return The builder object.
       * @see Environment#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param tenantId The tenantId of this Environment.
       * @return The builder object.
       * @see Environment#getTenantId()
       */
      public T tenantId(String tenantId) {
         this.tenantId = tenantId;
         return self();
      }

      /**
       * @param version The version of this Environment.
       * @return The builder object.
       * @see Environment#getVersion()
       */
      public T version(int version) {
         this.version = version;
         return self();
      }

      /**
       * @param status The status of this Environment.
       * @return The builder object.
       * @see Environment#getStatus() ()
       */
      public T status(String status) {
         this.status = status;
         return self();
      }

      /**
       * @return A new Environment object.
       */
      public Environment build() {
         return new Environment(id, name, updated, networking, created, tenantId, version, status);
      }

      public T fromEnvironment(Environment in) {
         return this.fromResource(in)
               .updated(in.getUpdated())
               .networking(in.getNetworking())
               .created(in.getCreated())
               .tenantId(in.getTenantId())
               .version(in.getVersion())
               .status(in.getStatus());
      }

   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
