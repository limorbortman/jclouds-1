package org.jclouds.openstack.nova.v2_0.parse;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jclouds.json.BaseSetParserTest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.v2_0.config.NovaParserModule;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIPPool;
import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * @author Inbar Stolberg
 */
@Test(groups = "unit", testName = "ParseFloatingIPListTest")
public class ParseFloatingIPPoolListTest extends BaseSetParserTest<FloatingIPPool> {

   @Override
   public String resource() {
      return "/floatingippool_list.json";
   }

   @Override
   @SelectJson("floating_ip_pools")
   @Consumes(MediaType.APPLICATION_JSON)
   public Set<FloatingIPPool> expected() {
      return ImmutableSet.of(FloatingIPPool.builder().name("VLAN867").build());
   }

   protected Injector injector() {
      return Guice.createInjector(new NovaParserModule(), new GsonModule());
   }
}