package org.jclouds.openstack.nova.v2_0.extensions;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIPPool;
import org.jclouds.openstack.nova.v2_0.internal.BaseNovaApiLiveTest;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertTrue;

/**
 *
 */
@Test(groups = "live", testName = "FloatingIPPoolApiLiveTest")
public class FloatingIPPoolApiLiveTest extends BaseNovaApiLiveTest {

   @Test
   public void testListFloatingIPPools() throws Exception {
      for (String zoneId : api.getConfiguredZones()) {
         Optional<? extends FloatingIPPoolApi> apiOption = api.getFloatingIPPoolExtensionForZone(zoneId);
         if (!apiOption.isPresent())
            continue;
         FloatingIPPoolApi api = apiOption.get();
         FluentIterable<? extends FloatingIPPool> response = api.list();
         assertTrue(null != response);
         Set<? extends FloatingIPPool> set = response.toSet();
         assertTrue(response.size() >= 0);

      }
   }
}