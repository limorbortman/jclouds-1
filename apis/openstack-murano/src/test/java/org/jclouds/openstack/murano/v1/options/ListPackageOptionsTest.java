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
package org.jclouds.openstack.murano.v1.options;

import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jclouds.openstack.murano.v1.options.ListPackagesOptions.Builder.category;
import static org.jclouds.openstack.murano.v1.options.ListPackagesOptions.Builder.limit;
import static org.jclouds.openstack.murano.v1.options.ListPackagesOptions.Builder.marker;
import static org.jclouds.openstack.murano.v1.options.ListPackagesOptions.Builder.orderBy;


/**
 * Tests behavior of {@code ListPackageOptionsTest}
 */
@Test(groups = "unit")
public class ListPackageOptionsTest {

   public void testLimit() {
      ListPackagesOptions options = new ListPackagesOptions().limit(42);
      assertThat(options.buildQueryParameters().get("limit")).isEqualTo(ImmutableSet.of("42"));
   }

   public void testLimitStatic() {
      ListPackagesOptions options = limit(42);
      assertThat(options.buildQueryParameters().get("limit")).isEqualTo(ImmutableSet.of("42"));
   }

   public void testMarker() {
      ListPackagesOptions options = new ListPackagesOptions().marker("deploy");
      assertThat(options.buildQueryParameters().get("marker")).isEqualTo(ImmutableSet.of("deploy"));
   }

   public void testMarkerStatic() {
      ListPackagesOptions options = marker("deploy");
      assertThat(options.buildQueryParameters().get("marker")).isEqualTo(ImmutableSet.of("deploy"));
   }

   public void testCategory() {
      ListPackagesOptions options = new ListPackagesOptions().category("testCategory");
      assertThat(options.buildQueryParameters().get("category"))
            .isEqualTo(ImmutableSet.of("testCategory"));
   }

   public void testOwned() {
      ListPackagesOptions options = new ListPackagesOptions().owned(true);
      assertThat(options.buildQueryParameters().get("owned"))
              .isEqualTo(ImmutableSet.of(String.valueOf(true)));
   }

   public void testCatalog() {
      ListPackagesOptions options = new ListPackagesOptions().catalog(true);
      assertThat(options.buildQueryParameters().get("catalog"))
              .isEqualTo(ImmutableSet.of(String.valueOf(true)));
   }

   public void testStatusStatic() {
      ListPackagesOptions options = category("testCategory");
      assertThat(options.buildQueryParameters().get("category"))
            .isEqualTo(ImmutableSet.of("testCategory"));
   }

   public void testOrderBy() {
      ListPackagesOptions options = new ListPackagesOptions().orderBy(ListPackagesOptions.OrderBy.CREATED);
      assertThat(options.buildQueryParameters().get("order_by")).isEqualTo(ImmutableSet.of(ListPackagesOptions.OrderBy.CREATED.toString()));
   }

   public void testOrderByFromValue() {
      ListPackagesOptions options = new ListPackagesOptions().orderBy(ListPackagesOptions.OrderBy.fromValue("created"));
      assertThat(options.buildQueryParameters().get("order_by")).isEqualTo(ImmutableSet.of(ListPackagesOptions.OrderBy.CREATED.toString()));
   }

   public void testOrderByStatic() {
      ListPackagesOptions options = orderBy(ListPackagesOptions.OrderBy.CREATED);
      assertThat(options.buildQueryParameters().get("order_by")).isEqualTo(ImmutableSet.of(ListPackagesOptions.OrderBy.CREATED.toString()));
   }


}
