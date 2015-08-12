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
import org.jclouds.openstack.v2_0.domain.Resource;

import javax.inject.Named;
import java.beans.ConstructorProperties;
import java.util.Date;

public class Session extends Resource {
   private final Date updated;
   @Named("environment_id")
   private final String environmentId;
   @Named("user_id")
   private final String userId;
   private final Date created;
   private final String state;
   private final int version;

   @ConstructorProperties({"id", "name", "updated", "environment_id", "user_id", "created", "state", "version"})
   public Session(String id, String name, Date updated, String environmentId, String userId, Date created, String state, int version) {
      super(id, name, null);
      this.updated = updated;
      this.environmentId = environmentId;
      this.userId = userId;
      this.created = created;
      this.state = state;
      this.version = version;
   }

   public Date getUpdated() {
      return updated;
   }

   public String getEnvironmentId() {
      return environmentId;
   }

   public String getUserId() {
      return userId;
   }

   public Date getCreated() {
      return created;
   }

   public String getState() {
      return state;
   }

   public int getVersion() {
      return version;
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (updated != null ? updated.hashCode() : 0);
      result = 31 * result + (environmentId != null ? environmentId.hashCode() : 0);
      result = 31 * result + (userId != null ? userId.hashCode() : 0);
      result = 31 * result + (created != null ? created.hashCode() : 0);
      result = 31 * result + (state != null ? state.hashCode() : 0);
      result = 31 * result + version;
      return result;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Session)) return false;
      if (!super.equals(o)) return false;

      Session session = (Session) o;

      if (version != session.version) return false;
      if (updated != null ? !updated.equals(session.updated) : session.updated != null) return false;
      if (environmentId != null ? !environmentId.equals(session.environmentId) : session.environmentId != null)
         return false;
      if (userId != null ? !userId.equals(session.userId) : session.userId != null) return false;
      if (created != null ? !created.equals(session.created) : session.created != null) return false;
      return !(state != null ? !state.equals(session.state) : session.state != null);

   }

   protected Objects.ToStringHelper string() {
      return super.string()
            .add("updated", updated)
            .add("environmentId", environmentId)
            .add("userId", userId)
            .add("created", created)
            .add("state", state)
            .add("version", version);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromSession(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {

      protected Date updated;
      protected String environmentId;
      protected String userId;
      protected Date created;
      protected String state;
      protected int version;

      /**
       * @param updated The updated date of this Session.
       * @return The builder object.
       * @see Session#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }


      /**
       * @param environmentId The environmentId of this Session.
       * @return The builder object.
       * @see Session#getEnvironmentId()
       */
      public T environmentId(String environmentId) {
         this.environmentId = environmentId;
         return self();
      }

      /**
       * @param userId The userId of this Session.
       * @return The builder object.
       * @see Session#getUserId()
       */
      public T userId(String userId) {
         this.userId = userId;
         return self();
      }

      /**
       * @param created The creation time of this Session.
       * @return The builder object.
       * @see Session#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param state The state of this Session.
       * @return The builder object.
       * @see Session#getState() ()
       */
      public T state(String state) {
         this.state = state;
         return self();
      }

      /**
       * @param version The version of this Session.
       * @return The builder object.
       * @see Session#getVersion()
       */
      public T version(int version) {
         this.version = version;
         return self();
      }


      /**
       * @return A new Session object.
       */
      public Session build() {
         return new Session(id, name, updated, environmentId, userId, created, state, version);
      }

      public T fromSession(Session in) {
         return this.fromResource(in)
               .updated(in.getUpdated())
               .environmentId(in.getEnvironmentId())
               .userId(in.getUserId())
               .created(in.getCreated())
               .state(in.getState())
               .version(in.getVersion());
      }

   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
