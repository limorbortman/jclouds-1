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

package org.jclouds.openstack.neutron.v2_0.options;

import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.EtherType;

/**
 * @author Nick Livens
 */
public class CreateSecurityGroupRuleOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromCreateSecurityGroupRuleOptions(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String remoteSecurityGroupId;
      protected Integer portRangeMin;
      protected Integer portRangeMax;
      protected String remoteIpPrefix;
      protected EtherType etherType;

      /**
       * @see CreateSecurityGroupRuleOptions#getRemoteSecurityGroupId()
       */
      public T remoteSecurityGroupId(String remoteSecurityGroupId) {
         this.remoteSecurityGroupId = remoteSecurityGroupId;
         return self();
      }

      /**
       * @see CreateSecurityGroupRuleOptions#getPortRangeMin()
       */
      public T portRangeMin(Integer portRangeMin) {
         this.portRangeMin = portRangeMin;
         return self();
      }

      /**
       * @see CreateSecurityGroupRuleOptions#getPortRangeMax()
       */
      public T portRangeMax(Integer portRangeMax) {
         this.portRangeMax = portRangeMax;
         return self();
      }

      /**
       * @see CreateSecurityGroupRuleOptions#getRemoteIpPrefix()
       */
      public T remoteIpPrefix(String remoteIpPrefix) {
         this.remoteIpPrefix = remoteIpPrefix;
         return self();
      }

      /**
       * @see CreateSecurityGroupRuleOptions#getEtherType()
       */
      public T etherType(EtherType etherType) {
         this.etherType = etherType;
         return self();
      }

      public CreateSecurityGroupRuleOptions build() {
         return new CreateSecurityGroupRuleOptions(remoteSecurityGroupId, portRangeMin, portRangeMax, remoteIpPrefix, etherType);
      }

      public T fromCreateSecurityGroupRuleOptions(CreateSecurityGroupRuleOptions options) {
         return this.remoteSecurityGroupId(options.getRemoteSecurityGroupId())
               .portRangeMin(options.getPortRangeMin())
               .portRangeMax(options.getPortRangeMax())
               .remoteIpPrefix(options.getRemoteIpPrefix())
               .etherType(options.getEtherType());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   protected static class CreateSecurityGroupRuleRequest {
      protected String security_group_id;
      protected String remote_group_id;
      protected String direction;
      protected String protocol;
      protected Integer port_range_min;
      protected Integer port_range_max;
      protected String remote_ip_prefix;
      protected String ethertype;

      protected CreateSecurityGroupRuleRequest(String securityGroupId, String direction, String protocol) {
         this.security_group_id = securityGroupId;
         this.direction = direction;
         this.protocol = protocol;
      }
   }

   private final String remoteSecurityGroupId;
   private final Integer portRangeMin;
   private final Integer portRangeMax;
   private final String remoteIpPrefix;
   private final EtherType etherType;

   public CreateSecurityGroupRuleOptions(String remoteSecurityGroupId, Integer portRangeMin,
                                         Integer portRangeMax, String remoteIpPrefix, EtherType etherType) {
      this.remoteSecurityGroupId = remoteSecurityGroupId;
      this.portRangeMin = portRangeMin;
      this.portRangeMax = portRangeMax;
      this.remoteIpPrefix = remoteIpPrefix;
      this.etherType = etherType;
   }

   /**
    * @return The remote security group ID
    */
   public String getRemoteSecurityGroupId() {
      return remoteSecurityGroupId;
   }

   /**
    * @return The minimal port
    */
   public Integer getPortRangeMin() {
      return portRangeMin;
   }

   /**
    * @return The maximal port
    */
   public Integer getPortRangeMax() {
      return portRangeMax;
   }

   /**
    * @return The cidr of the IP addresses allowed
    */
   public String getRemoteIpPrefix() {
      return remoteIpPrefix;
   }

   /**
    * @return The ether type used by the rule
    */
   public EtherType getEtherType() {
      return etherType;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      CreateSecurityGroupRuleRequest createSecurityGroupRuleRequest =
            new CreateSecurityGroupRuleRequest(
                  checkNotNull(postParams.get("security_group_id"), "securityGroupId not present").toString(),
                  checkNotNull(postParams.get("direction"), "direction not present").toString(),
                  checkNotNull(postParams.get("protocol"), "protocol not present").toString()
            );

      if (remoteSecurityGroupId != null)
         createSecurityGroupRuleRequest.remote_group_id = remoteSecurityGroupId;
      if (portRangeMin != null)
         createSecurityGroupRuleRequest.port_range_min = portRangeMin;
      if (portRangeMax != null)
         createSecurityGroupRuleRequest.port_range_max = portRangeMax;
      if (remoteIpPrefix != null)
         createSecurityGroupRuleRequest.remote_ip_prefix = remoteIpPrefix;
      if (etherType != null)
         createSecurityGroupRuleRequest.ethertype = etherType.value();

      return bindToRequest(request, ImmutableMap.of("security_group_rule", createSecurityGroupRuleRequest));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }
}