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
package org.jclouds.openstack.neutron.v2.extensions;

import com.google.common.annotations.Beta;
import org.jclouds.Fallbacks;
import org.jclouds.collect.PagedIterable;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroups;
import org.jclouds.openstack.neutron.v2.fallbacks.EmptySecurityGroupsFallback;
import org.jclouds.openstack.neutron.v2.functions.ParseSecurityGroups;
import org.jclouds.openstack.neutron.v2.functions.SecurityGroupsToPagedIterable;
import org.jclouds.openstack.neutron.v2.options.ListSecurityGroupOptions;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.annotations.WrapWith;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * Provides synchronous access to Security Group operations on the openstack quantum API.
 * <p/>
 * Security groups and security group rules allows administrators and tenants the ability to specify the type of traffic and direction (ingress/egress)
 * that is allowed to pass through a port. A security group is a container for security group rules.
 *
 * @see <a href=
 *      "http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
@Beta
@Path("/v2.0/security-groups")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface SecurityGroupApi {

   /**
    * Returns the list of all security groups currently defined in Neutron for the current tenant. The list provides the unique
    * identifier of each security group configured for the tenant
    *
    * @return the list of all security groups references configured for the tenant.
    */
   @Named("securitygroup:list")
   @GET
   @Transform(SecurityGroupsToPagedIterable.class)
   @ResponseParser(ParseSecurityGroups.class)
   @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<SecurityGroup> list(ListSecurityGroupOptions... options);

   /**
    * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/pagination.html">api doc</a>
    */
   @Named("securitygroup:list")
   @GET
   @ResponseParser(ParseSecurityGroups.class)
   @Fallback(EmptySecurityGroupsFallback.class)
   SecurityGroups list(PaginationOptions options);

   /**
    * Returns a security group collection that should contain a single security group with the id requested.
    *
    * @param id the id of the security group to return
    * @return Security group collection or empty if not found
    */
   @Named("securitygroup:get")
   @GET
   @Path("/{id}")
   @SelectJson("security_group")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Nullable
   SecurityGroup get(@PathParam("id") String id);

   /**
    * Create a new security group
    *
    * @param securityGroup Options for creating a security group
    * @return the newly created security group
    */
   @Named("securitygroup:create")
   @POST
   @SelectJson("security_group")
   SecurityGroup create(@WrapWith("security_group") SecurityGroup.CreateOptions securityGroup);

   /**
    * Deletes the specified security group
    *
    * @param id the id of the security group to delete
    * @return true if delete successful, false if not
    */
   @Named("securitygroup:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);
}
