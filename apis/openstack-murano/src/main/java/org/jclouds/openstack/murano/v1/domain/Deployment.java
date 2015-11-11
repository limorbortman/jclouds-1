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

public class Deployment extends Resource {

   private final Date updated;
   @Named("environment_id")
   private final String environmentId;
   private final Map<String, Object> description;
   private final Date created;
   private final Date started;
   private final Date finished;
   private final String state;
   private final Map<String, Object> result;
   @Nullable
   private final Map<String, Object> action;


   @ConstructorProperties({"id", "updated", "environment_id", "description", "created", "started", "finished", "state", "result", "action"})
   public Deployment(String id, Date updated, String environmentId, Map<String, Object> description, Date created,
                     Date started, Date finished, String state, Map<String, Object> result, Map<String, Object>
                           action) {
      super(id, null, null);
      this.updated = updated;
      this.environmentId = environmentId;
      this.description = ImmutableMap.copyOf(description);
      this.created = created;
      this.started = started;
      this.finished = finished;
      this.state = state;
      this.result = ImmutableMap.copyOf(result);
      this.action = action != null ? ImmutableMap.copyOf(action) : null;
   }

   public Date getUpdated() {
      return updated;
   }

   public String getEnvironmentId() {
      return environmentId;
   }

   public Map<String, Object> getDescription() {
      return description;
   }

   public Date getCreated() {
      return created;
   }

   public Date getStarted() {
      return started;
   }

   public Date getFinished() {
      return finished;
   }

   public String getState() {
      return state;
   }

   public Map<String, Object> getResult() {
      return result;
   }

   public Map<String, Object> getAction() {
      return action;
   }


   @Override
   public int hashCode() {
      int result1 = super.hashCode();
      result1 = 31 * result1 + (updated != null ? updated.hashCode() : 0);
      result1 = 31 * result1 + (environmentId != null ? environmentId.hashCode() : 0);
      result1 = 31 * result1 + (description != null ? description.hashCode() : 0);
      result1 = 31 * result1 + (created != null ? created.hashCode() : 0);
      result1 = 31 * result1 + (started != null ? started.hashCode() : 0);
      result1 = 31 * result1 + (finished != null ? finished.hashCode() : 0);
      result1 = 31 * result1 + (state != null ? state.hashCode() : 0);
      result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
      result1 = 31 * result1 + (action != null ? action.hashCode() : 0);
      return result1;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Deployment)) return false;
      if (!super.equals(o)) return false;

      Deployment that = (Deployment) o;

      if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
      if (environmentId != null ? !environmentId.equals(that.environmentId) : that.environmentId != null)
         return false;
      if (description != null ? !description.equals(that.description) : that.description != null) return false;
      if (created != null ? !created.equals(that.created) : that.created != null) return false;
      if (started != null ? !started.equals(that.started) : that.started != null) return false;
      if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
      if (state != null ? !state.equals(that.state) : that.state != null) return false;
      if (result != null ? !result.equals(that.result) : that.result != null) return false;
      return !(action != null ? !action.equals(that.action) : that.action != null);

   }


   protected Objects.ToStringHelper string() {
      return super.string()
            .add("updated", updated)
            .add("environmentId", environmentId)
            .add("description", description)
            .add("created", created)
            .add("started", started)
            .add("finished", finished)
            .add("state", state)
            .add("result", result)
            .add("action", action);

   }


   @Override
   public String toString() {
      return string().toString();
   }


   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromDeployment(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {
      protected Date updated;
      protected String environmentId;
      protected Map<String, Object> description;
      protected Date created;
      protected Date started;
      protected Date finished;
      protected String state;
      protected Map<String, Object> result;
      @Nullable
      protected Map<String, Object> action;

      /**
       * @param updated The updated date of this Deployment.
       * @return The builder object.
       * @see Deployment#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }

      /**
       * @param environmentId The environmentId of this Deployment.
       * @return The builder object.
       * @see Deployment#getEnvironmentId()
       */
      public T environmentId(String environmentId) {
         this.environmentId = environmentId;
         return self();
      }

      /**
       * @param description The description of this Deployment.
       * @return The builder object.
       * @see Deployment#getDescription()
       */
      public T description(Map<String, Object> description) {
         this.description = description;
         return self();
      }

      /**
       * @param created The creation time of this Deployment.
       * @return The builder object.
       * @see Deployment#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param started The start time of this Deployment.
       * @return The builder object.
       * @see Deployment#getStarted()
       */
      public T started(Date started) {
         this.started = started;
         return self();
      }

      /**
       * @param finished The finish time of this Deployment.
       * @return The builder object.
       * @see Deployment#getFinished()
       */
      public T finished(Date finished) {
         this.finished = finished;
         return self();
      }

      /**
       * @param state The state of this Deployment.
       * @return The builder object.
       * @see Deployment#getState()
       */
      public T state(String state) {
         this.state = state;
         return self();
      }

      /**
       * @param result The result of this Deployment.
       * @return The builder object.
       * @see Deployment#getResult()
       */
      public T result(Map<String, Object> result) {
         this.result = result;
         return self();
      }


      /**
       * @param action The action of this Deployment.
       * @return The builder object.
       * @see Deployment#getAction()
       */
      public T action(Map<String, Object> action) {
         this.action = action;
         return self();
      }

      /**
       * @return A new Deployment object.
       */
      public Deployment build() {
         return new Deployment(id, updated, environmentId, description, created, started, finished, state, result, action);
      }

      public T fromDeployment(Deployment in) {
         return this.fromResource(in)
               .updated(in.getUpdated())
               .environmentId(in.getEnvironmentId())
               .description(in.getDescription())
               .created(in.getCreated())
               .started(in.getStarted())
               .finished(in.getFinished())
               .state(in.getState())
               .result(in.getResult())
               .action(in.getAction());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
