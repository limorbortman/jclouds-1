package org.jclouds.openstack.cinder.v1.features;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.jclouds.openstack.cinder.v1.domain.zonescoped.AvailabilityZone;
import org.jclouds.openstack.cinder.v1.extentions.AvailabilityZoneApi;
import org.jclouds.openstack.cinder.v1.internal.BaseCinderApiLiveTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

@Test(groups = "live", testName = "AvailabilityZoneApiLiveTest", singleThreaded = true)
public class AvailabilityZoneApiLiveTest extends BaseCinderApiLiveTest {

   private AvailabilityZoneApi availabilityZoneApi;

   public AvailabilityZoneApiLiveTest() {
      super();
      provider = "openstack-cinder";
   }

   @BeforeClass(groups = {"integration", "live"})
   public void setupContext() {
      super.setup();
      String zone = Iterables.getFirst(api.getConfiguredZones(), "regionOne");
      availabilityZoneApi = api.getAvailabilityZoneApi(zone);
   }

   public void testListAvailabilityZones() {
      ImmutableList<? extends AvailabilityZone> cinderZones = availabilityZoneApi.list().toList();

      assertTrue(cinderZones.size() > 0);
      for (AvailabilityZone zone : cinderZones) {
         assertTrue(!Strings.isNullOrEmpty(zone.getName()));
         assertTrue(zone.getZoneState().isAvailable());
      }

   }

}