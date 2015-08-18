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

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;

import javax.inject.Named;

import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.openstack.murano.v1.options.CreatePackageOptions;
import org.jclouds.openstack.murano.v1.options.ListPackagesOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.PartParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;

/*
/**
 * Provides access to the OpenStack Catalog (Murano) Packages API features.
 */

@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("v1/catalog/packages")
public interface MuranoPackageApi {


   @Named("package:list")
   @GET
   @SelectJson("packages")
   FluentIterable<MuranoPackage> list();

   @Named("package:list")
   @GET
   @SelectJson("packages")
   FluentIterable<MuranoPackage> list(ListPackagesOptions options);

   @Named("package:get")
   @GET
   @Path("/{package_id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   MuranoPackage get(@PathParam("package_id") String id);

   @Named("package:delete")
   @DELETE
   @Path("/{package_id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("package_id") String id);

   @Named("package:create")
   @POST
   MuranoPackage create(@Nullable @FormParam("JsonString") @ParamParser(CreatePackageOptions.class) CreatePackageOptions
                              createPackageOptions, @PartParam(name = "file") File path);

   @Named("package:download")
   @GET
   @Path("/{package_id}/download")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Consumes(MediaType.APPLICATION_OCTET_STREAM)
   InputStream downloadPackage(@PathParam("package_id") String id);

}
