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
import com.google.common.collect.ImmutableSet;
import org.jclouds.openstack.v2_0.domain.Resource;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Set;
import javax.inject.Named;

public class MuranoPackage extends Resource {

   @Named("class_definitions")
   private final Set<String> classDefinitions;
   private final String description;
   private final Set<String> tags;
   private final Date updated;
   @Named("is_public")
   private final boolean isPublic;
   private final Set<String> categories;
   private final Date created;
   private final String author;
   private final boolean enabled;
   @Named("fully_qualified_name")
   private final String fullyQualifiedName;
   private final String type;
   @Named("owner_id")
   private final String owner;

   @ConstructorProperties({"id", "name", "class_definitions", "description", "tags", "updated", "is_public",
         "categories", "created", "author", "enabled", "fully_qualified_name", "type", "owner_id"})
   public MuranoPackage(String id, String name, Set<String> classDefinitions, String description, Set<String> tags, Date
         updated, boolean isPublic, Set<String> categories, Date created, String author, boolean enabled,
                        String fullyQualifiedName, String type, String owner) {
      super(id, name, null);
      this.classDefinitions = classDefinitions == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(classDefinitions);
      this.description = description;
      this.tags = tags == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(tags);
      this.updated = updated;
      this.isPublic = isPublic;
      this.categories = categories == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(categories);
      this.created = created;
      this.author = author;
      this.enabled = enabled;
      this.fullyQualifiedName = fullyQualifiedName;

      this.type = type;
      this.owner = owner;
   }

   public Set<String> getClassDefinitions() {
      return classDefinitions;
   }

   public String getDescription() {
      return description;
   }

   public Set<String> getTags() {
      return tags;
   }

   public Date getUpdated() {
      return updated;
   }

   public boolean isPublic() {
      return isPublic;
   }

   public Set<String> getCategories() {
      return categories;
   }

   public Date getCreated() {
      return created;
   }

   public String getAuthor() {
      return author;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public String getFullyQualifiedName() {
      return fullyQualifiedName;
   }

   public String getType() {
      return type;
   }

   public String getOwner() {
      return owner;
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (classDefinitions != null ? classDefinitions.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      result = 31 * result + (tags != null ? tags.hashCode() : 0);
      result = 31 * result + (updated != null ? updated.hashCode() : 0);
      result = 31 * result + (isPublic ? 1 : 0);
      result = 31 * result + (categories != null ? categories.hashCode() : 0);
      result = 31 * result + (created != null ? created.hashCode() : 0);
      result = 31 * result + (author != null ? author.hashCode() : 0);
      result = 31 * result + (enabled ? 1 : 0);
      result = 31 * result + (fullyQualifiedName != null ? fullyQualifiedName.hashCode() : 0);
      result = 31 * result + (type != null ? type.hashCode() : 0);
      result = 31 * result + (owner != null ? owner.hashCode() : 0);
      return result;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof MuranoPackage)) return false;
      if (!super.equals(o)) return false;

      MuranoPackage aPackage = (MuranoPackage) o;

      if (isPublic != aPackage.isPublic) return false;
      if (enabled != aPackage.enabled) return false;
      if (classDefinitions != null ? !classDefinitions.equals(aPackage.classDefinitions) : aPackage.classDefinitions != null)
         return false;
      if (description != null ? !description.equals(aPackage.description) : aPackage.description != null)
         return false;
      if (tags != null ? !tags.equals(aPackage.tags) : aPackage.tags != null) return false;
      if (updated != null ? !updated.equals(aPackage.updated) : aPackage.updated != null) return false;
      if (categories != null ? !categories.equals(aPackage.categories) : aPackage.categories != null) return false;
      if (created != null ? !created.equals(aPackage.created) : aPackage.created != null) return false;
      if (author != null ? !author.equals(aPackage.author) : aPackage.author != null) return false;
      if (fullyQualifiedName != null ? !fullyQualifiedName.equals(aPackage.fullyQualifiedName) : aPackage.fullyQualifiedName != null)
         return false;
      if (type != null ? !type.equals(aPackage.type) : aPackage.type != null) return false;
      return !(owner != null ? !owner.equals(aPackage.owner) : aPackage.owner != null);

   }


   protected Objects.ToStringHelper string() {
      return super.string()
            .add("classDefinitions", classDefinitions)
            .add("description", description)
            .add("tags", tags)
            .add("updated", updated)
            .add("isPublic", isPublic)
            .add("categories", categories)
            .add("created", created)
            .add("author", author)
            .add("enabled", enabled)
            .add("fullyQualifiedName", fullyQualifiedName)
            .add("type", type)
            .add("owner", owner);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromPackage(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {

      protected Set<String> classDefinitions;
      protected String description;
      protected Set<String> tags;
      protected Date updated;
      protected boolean isPublic;
      protected Set<String> categories;
      protected Date created;
      protected String author;
      protected boolean enabled;
      protected String fullyQualifiedName;
      protected String type;
      protected String owner;

      /**
       * @param classDefinitions The classDefinitions of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getTags()
       */
      public T classDefinitions(Set<String> classDefinitions) {
         this.classDefinitions = classDefinitions;
         return self();
      }

      /**
       * @param description The description of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getDescription()
       */
      public T description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @param tags The tags of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getTags()
       */
      public T tags(Set<String> tags) {
         this.tags = tags;
         return self();
      }

      /**
       * @param updated The updated date of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }

      /**
       * @param isPublic describes if the package can be used by all users or specific user that uploaded it.
       * @return The builder object.
       * @see MuranoPackage#isPublic()
       */
      public T isPublic(boolean isPublic) {
         this.isPublic = isPublic;
         return self();
      }

      /**
       * @param categories The categories of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getCategories()
       */
      public T categories(Set<String> categories) {
         this.categories = categories;
         return self();
      }

      /**
       * @param created The creation time of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param author The author of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getAuthor()
       */
      public T author(String author) {
         this.author = author;
         return self();
      }

      /**
       * @param enabled Controls if the MuranoPackage is enabled for deployment.
       * @return The builder object.
       * @see MuranoPackage#isEnabled()
       */
      public T enabled(boolean enabled) {
         this.enabled = enabled;
         return self();
      }


      /**
       * @param fullyQualifiedName The fully qualified name of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getFullyQualifiedName()
       */
      public T fullyQualifiedName(String fullyQualifiedName) {
         this.fullyQualifiedName = fullyQualifiedName;
         return self();
      }

      /**
       * @param type The type of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getType()
       */
      public T type(String type) {
         this.type = type;
         return self();
      }

      /**
       * @param owner The owner of this MuranoPackage.
       * @return The builder object.
       * @see MuranoPackage#getOwner()
       */
      public T owner(String owner) {
         this.owner = owner;
         return self();
      }


      /**
       * @return A new MuranoPackage object.
       */
      public MuranoPackage build() {
         return new MuranoPackage(id, name, classDefinitions, description, tags, updated, isPublic, categories,
               created, author, enabled, fullyQualifiedName, type, owner);
      }

      public T fromPackage(MuranoPackage in) {
         return this.fromResource(in)
               .classDefinitions(in.getClassDefinitions())
               .description(in.getDescription())
               .tags(in.getTags())
               .updated(in.getUpdated())
               .isPublic(in.isPublic())
               .categories(in.getCategories())
               .created(in.getCreated())
               .author(in.getAuthor())
               .enabled(in.isEnabled())
               .fullyQualifiedName(in.getFullyQualifiedName())
               .type(in.getType())
               .owner(in.getOwner());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
