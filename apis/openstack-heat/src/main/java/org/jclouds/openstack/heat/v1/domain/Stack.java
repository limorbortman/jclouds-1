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
package org.jclouds.openstack.heat.v1.domain;

import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.inject.name.Named;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stack of Heat
 */
public class Stack {

   public static enum Status {
      INIT_IN_PROGRESS, INIT_FAILED, INIT_COMPLETED,
      CREATE_IN_PROGRESS, CREATE_FAILED, CREATE_COMPLETED,
      DELETE_IN_PROGRESS, DELETE_FAILED, DELETE_COMPLETED,
      UPDATE_IN_PROGRESS, UPDATE_FAILED, UPDATE_COMPLETED,
      ROLLBACK_IN_PROGRESS, ROLLBACK_FAILED, ROLLBACK_COMPLETED,
      SUSPEND_IN_PROGRESS, SUSPEND_FAILED, SUSPEND_COMPLETED,
      RESUME_IN_PROGRESS, RESUME_FAILED, RESUME_COMPLETED,
      UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
      }

      @Override
      public String toString() {
         return value();
      }

      public static Status fromValue(String status) {
         try {
            return valueOf(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, checkNotNull(status, "status")));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }

      public static Status fromActionAndState(String action, String actionState) {
         try {
            checkNotNull(action, "action");
            checkNotNull(actionState, "state");
            return valueOf(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, action + "_" + actionState));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromStack(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String id;
      protected String stackName;
      protected String description;
      protected Map<String, String> parameters = ImmutableMap.of();
      protected Date creationTime;
      protected Stack.Status stackStatus;
      protected String stackStatusReason;
      protected Map<String, String> outputs = ImmutableMap.of();
      protected Date updateTime;

      /**
       * @see Stack#getId()
       */
      public T id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see Stack#getStackName()
       */
      public T stackName(String stackName) {
         this.stackName = stackName;
         return self();
      }

      /**
       * @see Stack#getDescription()
       */
      public T description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @see Stack#getParameters()
       */
      public T parameters(Map<String, String> parameters) {
         this.parameters = ImmutableMap.copyOf(checkNotNull(parameters, "parameters"));
         return self();
      }

      /**
       * @see Stack#getCreationTime()
       */
      public T creationTime(Date creationTime) {
         this.creationTime = creationTime;
         return self();
      }

      /**
       * @see Stack#getStackStatus()
       */
      public T stackStatus(Stack.Status stackStatus) {
         this.stackStatus = stackStatus;
         return self();
      }

      /**
       * @see Stack#getStackStatusReason()
       */
      public T stackStatusReason(String stackStatusReason) {
         this.stackStatusReason = stackStatusReason;
         return self();
      }

      /**
       * @see Stack#getOutputs()
       */
      public T outputs(Map<String, String> outputs) {
         this.outputs = ImmutableMap.copyOf(checkNotNull(outputs, "outputs"));
         return self();
      }

      /**
       * @see Stack#getUpdateTime()
       */
      public T updateTime(Date updateTime) {
         this.updateTime = updateTime;
         return self();
      }

      public Stack build() {
         return new Stack(id, stackName, description, parameters, creationTime, stackStatus, stackStatusReason, outputs, updateTime);
      }

      public T fromStack(Stack in) {
         return this
               .id(in.getId())
               .stackName(in.getStackName())
               .description(in.getDescription())
               .parameters(in.getParameters())
               .creationTime(in.getCreationTime())
               .stackStatus(in.getStackStatus())
               .stackStatusReason(in.getStackStatusReason())
               .outputs(in.getOutputs())
               .updateTime(in.getUpdateTime());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private String id;
   @Named("stack_name")
   private String stackName;
   private String description;
   private Map<String, String> parameters;
   @Named("creation_time")
   private Date creationTime;
   @Named("stack_status")
   private Stack.Status stackStatus;
   @Named("stack_status_reason")
   private String stackStatusReason;
   private java.util.Map<String, String> outputs;
   @Named("updated_time")
   private Date updateTime;

   @ConstructorProperties({
         "id", "stack_name", "description", "parameters", "creation_time", "stack_status", "stack_status_reason", "outputs", "updated_time"
   })

   protected Stack(String id,
                   String stackName,
                   String description,
                   Map<String, String> parameters,
                   Date creationTime,
                   Stack.Status stackStatus,
                   String stackStatusReason,
                   Map<String, String> outputs,
                   Date updateTime) {

      this.id = checkNotNull(id, "id");
      this.stackName = stackName;
      this.description = description;
      this.parameters = parameters == null ? ImmutableMap.<String, String>of() : ImmutableMap.copyOf(parameters);
      this.creationTime = checkNotNull(creationTime, "creationTime");
      this.stackStatus = checkNotNull(stackStatus, "stackStatus");
      this.stackStatusReason = stackStatusReason;
      this.outputs = outputs == null ? ImmutableMap.<String, String>of() : ImmutableMap.copyOf(outputs);
      this.updateTime = updateTime;
   }

   /**
    * @return the id of this stack
    */
   public String getId() {
      return this.id;
   }

   /**
    * @return the name of this stack
    */
   public String getStackName() {
      return this.stackName;
   }

   /**
    * @return the description of this stack
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * @return the template parameters for this stack
    */
   public Map<String, String> getParameters() {
      return this.parameters;
   }

   /**
    * @return the time this stack was created
    */
   public Date getCreationTime() {
      return this.creationTime;
   }

   /**
    * @return the status of this stack
    */
   public Stack.Status getStackStatus() {
      return this.stackStatus;
   }

   /**
    * @return the status reason of this stack
    */
   public String getStackStatusReason() {
      return this.stackStatusReason;
   }

   /**
    * @return the outputs of this stack
    */
   public Map<String, String> getOutputs() {
      return this.outputs;
   }

   /**
    * @return the time this stack was last updated
    */
   public Date getUpdateTime() {
      return this.updateTime;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, stackName, description, parameters, creationTime, stackStatus, stackStatusReason, outputs, updateTime);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Stack that = Stack.class.cast(obj);
      return Objects.equal(this.id, that.id)
            && Objects.equal(this.stackName, that.stackName)
            && Objects.equal(this.description, that.description)
            && Objects.equal(this.parameters, that.parameters)
            && Objects.equal(this.creationTime, that.creationTime)
            && Objects.equal(this.stackStatus, that.stackStatus)
            && Objects.equal(this.stackStatusReason, that.stackStatusReason)
            && Objects.equal(this.outputs, that.outputs)
            && Objects.equal(this.updateTime, that.updateTime);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("stackName", stackName)
            .add("description", description)
            .add("parameters", parameters)
            .add("creationTime", creationTime)
            .add("stackStatus", stackStatus)
            .add("stackStatusReason", stackStatusReason)
            .add("outputs", outputs)
            .add("updateTime", updateTime);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}