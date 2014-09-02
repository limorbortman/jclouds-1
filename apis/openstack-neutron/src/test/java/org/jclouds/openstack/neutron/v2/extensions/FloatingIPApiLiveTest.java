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

import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.NetworkType;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.RouterInterface;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.neutron.v2.internal.BaseNeutronApiLiveTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of FloatingIPApi
 */
@Test(groups = "live", testName = "FloatingIPApiLiveTest")
public class FloatingIPApiLiveTest extends BaseNeutronApiLiveTest {

   public void testCreateUpdateAndDeleteFloatingIp() {
      for (String zone : api.getConfiguredRegions()) {
         NetworkApi networkApi = api.getNetworkApi(zone);
         SubnetApi subnetApi = api.getSubnetApi(zone);
         PortApi portApi = api.getPortApi(zone);
         RouterApi routerApi = api.getRouterExtensionApi(zone).get();
         FloatingIPApi floatingIpApi = api.getFloatingIPExtensionApi(zone).get();

         // Creating external network and subnet
         Network externalNetwork = networkApi.create(
               Network.createOptions("jclouds-live-external-network").adminStateUp(true).external(true).networkType(NetworkType.LOCAL).build());
         assertNotNull(externalNetwork);
         assertNotNull(externalNetwork.getId());


         Subnet externalSubnet = subnetApi.create(
               Subnet.createOptions(externalNetwork.getId(), "172.16.0.0/24").ipVersion(4).name("jclouds-live-external-subnet").gatewayIp("172.16.0.1").enableDhcp(true).build());
         assertNotNull(externalSubnet);
         assertNotNull(externalSubnet.getId());

         // Creating router with the external network as gateway
         ExternalGatewayInfo externalGatewayInfo = ExternalGatewayInfo.builder().networkId(externalNetwork.getId()).build();
         Router router = routerApi.create(
               Router.createOptions().name("jclouds-live-rotuer").adminStateUp(true).externalGatewayInfo(externalGatewayInfo).build());
         assertNotNull(router);
         assertNotNull(router.getId());

         // Creating internal network and subnet
         Network internalNetwork = networkApi.create(
               Network.createOptions("jclouds=live-internal-network").adminStateUp(true).networkType(NetworkType.LOCAL).build());
         assertNotNull(internalNetwork);
         assertNotNull(internalNetwork.getId());

         Subnet internalSubnet = subnetApi.create(
               Subnet.createOptions(internalNetwork.getId(), "192.168.0.0/24").ipVersion(4).name("jclouds-live-internal-subnet").gatewayIp("192.168.0.1").enableDhcp(true).build());
         assertNotNull(internalSubnet);
         assertNotNull(internalSubnet.getId());

         // Creating interface between the internal network and the router
         RouterInterface internalNetworkRouterInterface = routerApi.addInterfaceForSubnet(router.getId(), internalSubnet.getId());
         assertNotNull(internalNetworkRouterInterface);

         // Creating port on the internal network
         Port port = portApi.create(
               Port.createOptions(internalNetwork.getId()).name("jclouds-live-port").adminStateUp(true).build());
         assertNotNull(port);
         assertNotNull(port.getId());

         // Allocating floating IP to this port
         FloatingIP floatingIp = floatingIpApi.create(
               FloatingIP.createOptions(externalNetwork.getId()).build());
         assertNotNull(floatingIp);
         assertNotNull(floatingIp.getId());

         List<FloatingIP> allFloatingIps = floatingIpApi.list().concat().toList();
         assertNotNull(allFloatingIps);
         assertTrue(allFloatingIps.size() > 0);
         for (FloatingIP listFloatingIp : allFloatingIps) {
            if (listFloatingIp.getId().equals(floatingIp.getId()))
               assertEquals(floatingIp, listFloatingIp);
         }

         // Attach the floating IP to an existing port
         FloatingIP updatedFloatingIp = floatingIpApi.update(floatingIp.getId(), FloatingIP.updateOptions().portId(port.getId()).build());
         assertNotNull(updatedFloatingIp);
         assertEquals(updatedFloatingIp.getPortId(), port.getId());

         // Detach the floating IP from the existing port
         updatedFloatingIp = floatingIpApi.update(floatingIp.getId(), FloatingIP.updateOptions().build());
         assertNotNull(updatedFloatingIp);
         assertNull(updatedFloatingIp.getPortId());

         // Clean up
         floatingIpApi.delete(floatingIp.getId());
         portApi.delete(port.getId());
         routerApi.removeInterfaceForSubnet(router.getId(), internalSubnet.getId());
         routerApi.delete(router.getId());
         networkApi.delete(internalNetwork.getId());
         networkApi.delete(externalNetwork.getId());
         assertFalse(floatingIpApi.delete("unknownId"));
      }
   }

}
