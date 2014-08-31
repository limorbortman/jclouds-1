package org.jclouds.openstack.neutron.v2_0.extensions;

import org.jclouds.openstack.neutron.v2_0.domain.NuageNetPartition;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiLiveTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of FloatingIPApi
 */
@Test(groups = "live", testName = "NuageNetPartitionLiveTest", singleThreaded = true)
public class NuageNetPartitionLiveTest extends BaseNeutronApiLiveTest {

   public void testCreateListAndDelete() {
      for (String zone : api.getConfiguredZones()) {
         NuageNetPartitionApi nuageNetPartitionApi = api.getNuageNetPartitionApiExtensionForZone(zone).get();

         NuageNetPartition netPartition = nuageNetPartitionApi.create("net-partition-jclouds");
         assertNotNull(netPartition);
         assertNotNull(netPartition.getId());

         boolean netPartitionFoundInList = false;
         List<? extends NuageNetPartition> netPartitions = nuageNetPartitionApi.list().toList();
         for (NuageNetPartition listNetPartition : netPartitions) {
            if (listNetPartition.getName().equals(netPartition.getName())) {
               netPartitionFoundInList = true;
               break;
            }
         }

         assertTrue(netPartitionFoundInList);

         NuageNetPartition retrievedNetPartition = nuageNetPartitionApi.get(netPartition.getId());
         assertNotNull(retrievedNetPartition);

         assertTrue(nuageNetPartitionApi.delete(netPartition.getId()));
         assertNull(nuageNetPartitionApi.get(netPartition.getId()));
      }
   }

}