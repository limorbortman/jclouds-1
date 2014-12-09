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
import org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroupRules;
import org.jclouds.openstack.neutron.v2.fallbacks.EmptySecurityGroupRulesFallback;
import org.jclouds.openstack.neutron.v2.functions.ParseSecurityGroupRules;
import org.jclouds.openstack.neutron.v2.functions.SecurityGroupRulesToPagedIterable;
import org.jclouds.openstack.neutron.v2.options.ListSecurityGroupRuleOptions;
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
 * Provides asynchronous access to Security Group Rules operations on the openstack quantum API.
 *
 * @see <a href=
 *      "http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
@Beta
@Path("/security-group-rules")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface SecurityGroupRuleApi {

   /**
    * Returns the list of all security group rules currently defined in Neutron for the current tenant. The list provides the unique
    * identifier of each security group rule configured for the tenant
    *
    * @return the list of all security group rules references configured for the tenant.
    */
   @Named("securitygrouprule:list")
   @GET
   @Transform(SecurityGroupRulesToPagedIterable.class)
   @ResponseParser(ParseSecurityGroupRules.class)
   @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<SecurityGroupRule> list(ListSecurityGroupRuleOptions... options);

   /**
    * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/pagination.html">api doc</a>
    */
   @Named("securitygrouprule:list")
   @GET
   @ResponseParser(ParseSecurityGroupRules.class)
   @Fallback(EmptySecurityGroupRulesFallback.class)
   SecurityGroupRules list(PaginationOptions options);

   /**
    * Returns a security group rules collection that should contain a single security group rule with the id requested.
    *
    * @param id the id of the security group rule to return
    * @return security group rules collection or empty if not found
    */
   @Named("securitygrouprule:get")
   @GET
   @Path("/{id}")
   @SelectJson("security_group_rule")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Nullable
   SecurityGroupRule get(@PathParam("id") String id);

   /**
    * Create a new security group rule
    *
    * @param securityGroupRule Options for creating a security group rule
    * @return the newly created security group rule
    */
   @Named("securitygrouprule:create")
   @POST
   @SelectJson("security_group_rule")
   SecurityGroupRule create(@WrapWith("security_group_rule") SecurityGroupRule.CreateOptions securityGroupRule);

   /**
    * Deletes the specified security group rule
    *
    * @param id the id of the security group rule to delete
    * @return true if delete successful, false if not
    */
   @Named("securitygrouprule:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);
}
