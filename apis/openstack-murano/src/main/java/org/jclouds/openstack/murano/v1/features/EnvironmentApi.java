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
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.murano.v1.domain.Deployment;
import org.jclouds.openstack.murano.v1.domain.Environment;
import org.jclouds.openstack.murano.v1.domain.Session;
import org.jclouds.openstack.murano.v1.options.AddApplicationOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("v1/environments")
public interface EnvironmentApi {

   String X_CONF_SESSION = "X-Configuration-Session";

   @Named("environments:list")
   @GET
   @SelectJson("environments")
   FluentIterable<Environment> list();

   @Named("environment:create")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   Environment create(@WrapWith("name") String name);

   @Named("environment:get")
   @GET
   @Path("/{id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   Environment get(@PathParam("id") String id);

   @Named("environment:delete_or_abandon")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id, @QueryParam("abandon") boolean abandon);

   @Named("environment:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);

   @Named("environment:session-create")
   @POST
   @Path("/{env_id}/configure")
   Session configure(@PathParam("env_id") String id);

   @Named("environment:session-delete")
   @DELETE
   @Path("/{env_id}/sessions/{session_id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean deleteSession(@PathParam("env_id") String id, @PathParam("session_id") String sessionId);

   @Named("environment:session-deploy")
   @POST
   @Path("/{env_id}/sessions/{session_id}/deploy")
   boolean deploy(@PathParam("env_id") String id, @PathParam("session_id") String sessionId);

   @Named("environment:session-get")
   @GET
   @Path("/{env_id}/sessions/{session_id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   Session getSession(@PathParam("env_id") String id, @PathParam("session_id") String sessionId);

   @Named("environment:get_deployments")
   @GET
   @SelectJson("deployments")
   @Path("/{env_id}/deployments")
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<Deployment> listDeployments(@PathParam("env_id") String id);

   @Named("environment:add_application")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/{env_id}/services")
   @Headers(keys = X_CONF_SESSION, values = "{x-configuraion-session}")
   @MapBinder(AddApplicationOptions.class)
   Object addApplication(@PathParam("env_id") String id, @PathParam("x-configuraion-session") String
         configurationSession, AddApplicationOptions addApplicationOptions);

   @Named("environment:list_applications")
   @GET
   @Path("/{env_id}/services")
   @Headers(keys = X_CONF_SESSION, values = "{x-configuraion-session}")
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<Object> listEnvironmentApplications(@PathParam("env_id") String id, @PathParam("x-configuraion-session") String configurationSession);

}
