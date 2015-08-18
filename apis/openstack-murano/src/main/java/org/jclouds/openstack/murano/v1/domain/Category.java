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
import com.google.common.collect.ImmutableList;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.v2_0.domain.Resource;

import javax.inject.Named;
import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Category extends Resource {

   private final Date updated;
   private final Date created;
   @Named("package_count")
   private final int packageCount;
   private final List<MuranoPackage> packages;


   @ConstructorProperties({"id", "name", "updated", "created", "package_count", "packages"})
   public Category(String id, String name, Date updated, Date created, int packageCount, @Nullable List<MuranoPackage> packages) {
      super(id, name, null);
      this.updated = updated;
      this.created = created;
      this.packages = packages == null ? ImmutableList.<MuranoPackage>of() : ImmutableList.copyOf(packages);
      this.packageCount = packageCount;
   }

   public Date getUpdated() {
      return updated;
   }

   public Date getCreated() {
      return created;
   }

   public int getPackageCount() {
      return packageCount;
   }

   public List<MuranoPackage> getPackages() {
      return packages;
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (updated != null ? updated.hashCode() : 0);
      result = 31 * result + (created != null ? created.hashCode() : 0);
      result = 31 * result + packageCount;
      result = 31 * result + (packages != null ? packages.hashCode() : 0);
      return result;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Category)) return false;
      if (!super.equals(o)) return false;

      Category category = (Category) o;

      if (packageCount != category.packageCount) return false;
      if (updated != null ? !updated.equals(category.updated) : category.updated != null) return false;
      if (created != null ? !created.equals(category.created) : category.created != null) return false;
      return !(packages != null ? !packages.equals(category.packages) : category.packages != null);

   }

   protected Objects.ToStringHelper string() {
      return super.string()
            .add("updated", updated)
            .add("created", created)
            .add("packageCount", packageCount)
            .add("packages", packages);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromCategory(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {
      protected Date updated;
      protected Date created;
      protected int packageCount;
      protected List<MuranoPackage> packages = ImmutableList.of();

      /**
       * @param updated The updated date of this Category.
       * @return The builder object.
       * @see Category#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }

      /**
       * @param created The creation time of this Category.
       * @return The builder object.
       * @see Category#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param packageCount The package count of this Category.
       * @return The builder object.
       * @see Category#getPackageCount() ()
       */
      public T packageCount(int packageCount) {
         this.packageCount = packageCount;
         return self();
      }

      /**
       * @param packages The packages of this Category.
       * @return The builder object.
       * @see Category#getPackages() ()
       */
      public T packages(List<MuranoPackage> packages) {
         this.packages = ImmutableList.copyOf(checkNotNull(packages, "packages"));
         return self();
      }

      /**
       * @return A new Category object.
       */
      public Category build() {
         return new Category(id, name, updated, created, packageCount, packages);
      }

      public T fromCategory(Category in) {
         return this.fromResource(in)
               .updated(in.getUpdated())
               .created(in.getCreated())
               .packageCount(in.getPackageCount())
               .packages(in.getPackages());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
