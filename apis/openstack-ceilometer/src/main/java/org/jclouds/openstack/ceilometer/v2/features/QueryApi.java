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
package org.jclouds.openstack.ceilometer.v2.features;

import org.jclouds.Fallbacks.EmptyListOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.ceilometer.v2.domain.Sample;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Provides access to Meters features.
 *
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v2/query/")
public interface QueryApi {

   @Path("samples")
   @Named("resource:samples")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Payload("{}")
   @Fallback(EmptyListOnNotFoundOr404.class)
   List<Sample> listSamples();

   @Path("samples")
   @Named("resource:samples")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Fallback(EmptyListOnNotFoundOr404.class)
   @Payload("%7B\"limit\":\"{limit}\"%7D")
   List<Sample> listSamples(@Nullable @PayloadParam("limit") int limit);

}
