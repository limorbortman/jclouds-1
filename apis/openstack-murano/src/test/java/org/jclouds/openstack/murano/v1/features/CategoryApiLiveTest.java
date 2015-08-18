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
package org.jclouds.openstack.murano.v1.features;

import com.google.common.collect.ImmutableList;
import org.jclouds.openstack.murano.v1.domain.Category;
import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.openstack.murano.v1.options.CreatePackageOptions;
import org.testng.annotations.Test;
import org.jclouds.openstack.murano.v1.internal.BaseMuranoApiLiveTest;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Test(groups = "live", testName = "CategoryApiLiveTest")
public class CategoryApiLiveTest extends BaseMuranoApiLiveTest {

   public final String CATEGORY_NAME = "test-category";
   public final String PACKAGE_WITH_TAGS = "/package_with_tags.zip";

   public void testCreateCategory() {
      for (String region : api.getConfiguredRegions()) {
         CategoryApi categoryApi = api.getCategoryApi(region);
         Category category = categoryApi.create(CATEGORY_NAME);
         assertThat(category).isNotNull();
         assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
         ImmutableList<Category> categories = categoryApi.list().toList();
         assertThat(categories).isNotNull();
         assertThat(categories.contains(category));
         try {
            categoryApi.create(CATEGORY_NAME);
            fail("category creation should have failed");
         } catch (IllegalStateException e){
            assertThat(e.getMessage().contains("already exist"));
         }
         categoryApi.delete(category.getId());
      }
   }

   public void testListCategories() {
      for (String region : api.getConfiguredRegions()) {
         CategoryApi categoryApi = api.getCategoryApi(region);
         ImmutableList<Category> categories = categoryApi.list().toList();
         assertThat(categories).isNotNull();
      }
   }

   public void testGetCategoryWithPackage() {
      for (String region : api.getConfiguredRegions()) {
         CategoryApi categoryApi = api.getCategoryApi(region);
         Category createdCategory = categoryApi.create(CATEGORY_NAME);

         MuranoPackageApi muranoPackageApi = api.getPackageApi(region);
         List<String> categories = Collections.singletonList(CATEGORY_NAME);
         CreatePackageOptions createPackageOptions = CreatePackageOptions.Builder
               .categories(categories)
               .enabled(true);
         String path = getClass().getResource(PACKAGE_WITH_TAGS).getFile();
         File file = new File(path);
         MuranoPackage createdPackage = muranoPackageApi.create(createPackageOptions, file);

         Category category = categoryApi.get(createdCategory.getId());
         assertThat(category).isNotNull();
         assertThat(category.getPackageCount()).isEqualTo(1);
         assertThat(category.getPackages()).isNotNull();
         assertThat(category.getPackages().size()).isEqualTo(1);
         assertThat(category.getPackages().get(0).getId()).isEqualTo(createdPackage.getId());

         muranoPackageApi.delete(createdPackage.getId());
         categoryApi.delete(category.getId());

      }
   }

   public void testGetCategoryNoPackages() {
      for (String region : api.getConfiguredRegions()) {
         CategoryApi categoryApi = api.getCategoryApi(region);
         Category createdCategory = categoryApi.create(CATEGORY_NAME);
         Category category = categoryApi.get(createdCategory.getId());
         assertThat(category).isNotNull();
         assertThat(category.getPackageCount()).isEqualTo(0);
         assertThat(category.getPackages()).isNotNull();
         assertThat(category.getPackages()).isEmpty();
         categoryApi.delete(category.getId());
      }
   }

   public void testDeleteCategory() {
      for (String region : api.getConfiguredRegions()) {
         CategoryApi categoryApi = api.getCategoryApi(region);
         Category category = categoryApi.create(CATEGORY_NAME);
         ImmutableList<Category> categories = categoryApi.list().toList();
         assertThat(categories).isNotNull();
         int listSize = categories.size();
         categoryApi.delete(category.getId());
         assertThat(categoryApi.list().toList().size()).isEqualTo(listSize - 1);
         assertThat(!categoryApi.list().toList().contains(category));
      }
   }


}
