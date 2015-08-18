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
package org.jclouds.openstack.murano.v1;

import org.jclouds.openstack.murano.v1.features.CategoryApi;
import org.jclouds.openstack.murano.v1.features.EnvironmentApi;
import org.jclouds.openstack.murano.v1.features.MuranoPackageApi;
import org.jclouds.location.Region;
import org.jclouds.location.functions.RegionToEndpoint;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.EndpointParam;

import java.io.Closeable;
import java.util.Set;

import com.google.inject.Provides;


public interface MuranoApi extends Closeable {


   @Provides
   @Region
   Set<String> getConfiguredRegions();


   /**
    * Provides access to Package features.
    */
   @Delegate
   MuranoPackageApi getPackageApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to Category features
    */
   @Delegate
   CategoryApi getCategoryApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to Environment features
    */
   @Delegate
   EnvironmentApi getEnvironmentApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

}

