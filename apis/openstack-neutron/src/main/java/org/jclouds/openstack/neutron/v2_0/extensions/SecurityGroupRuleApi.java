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

package org.jclouds.openstack.neutron.v2_0.extensions;

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.neutron.v2_0.domain.Reference;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule;
import org.jclouds.openstack.neutron.v2_0.options.CreateSecurityGroupRuleOptions;
import org.jclouds.openstack.neutron.v2_0.options.ListSecurityGroupRuleOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Provides synchronous access to Security Group Rules operations on the openstack quantum API.
 * <p/>
 * Security groups and security group rules allows administrators and tenants the ability to specify the type of traffic and direction (ingress/egress)
 * that is allowed to pass through a port. A security group is a container for security group rules.
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v2.0/security-group-rules")
public interface SecurityGroupRuleApi {

   /**
    * Returns the list of all security group rules currently defined in Quantum for the current tenant. The list provides the unique
    * identifier of each security group configured for the tenant
    *
    * @return the list of all security group rule references configured for the tenant.
    */
   @GET
   @SelectJson("security_group_rules")
   @QueryParams(keys = {"fields", "fields"}, values = {"id", "tenant_id"})
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends Reference> listReferences();

   /**
    * Returns all security group rules currently defined in Quantum for the current tenant.
    *
    * @return the list of all security group rules configured for the tenant
    */
   @GET
   @SelectJson("security_group_rules")
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends SecurityGroupRule> list(ListSecurityGroupRuleOptions... options);

   /**
    * Returns the specific security group rules.
    *
    * @param id the id of the security group to return
    * @return Security group rule or null if not found
    */
   @GET
   @SelectJson("security_group_rule")
   @Path("/{id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   SecurityGroupRule get(@PathParam("id") String id);

   /**
    * Create a new security group rule
    *
    * @return a reference of the newly-created security group rule
    */
   @POST
   @SelectJson("security_group_rule")
   @Produces(MediaType.APPLICATION_JSON)
   @WrapWith("security_group_rule")
   Reference create(@PayloadParam("security_group_id") String securityGroupId, @PayloadParam("direction") String direction,
                    @PayloadParam("protocol") String protocol, CreateSecurityGroupRuleOptions... options);

   /**
    * Deletes the specified security group rule
    *
    * @param id the id of the security group rule to delete
    * @return true if delete successful, false if not
    */
   @DELETE
   @Path("/{id}")
   Boolean delete(@PathParam("id") String id);
}