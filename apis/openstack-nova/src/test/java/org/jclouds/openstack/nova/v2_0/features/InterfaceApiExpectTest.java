package org.jclouds.openstack.nova.v2_0.features;

import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.internal.BaseNovaApiExpectTest;
import org.jclouds.openstack.nova.v2_0.options.AttachInterfaceOptions;
import org.jclouds.openstack.nova.v2_0.parse.ParseInterfaceAttachmentTest;
import org.testng.annotations.Test;

import java.net.URI;

import static org.testng.Assert.assertEquals;

/**
 *
 */
@Test(groups = "unit", testName = "InterfaceApiTest")
public class InterfaceApiExpectTest extends BaseNovaApiExpectTest {

   public void testAttachInterfaceResponseIs2xx() throws Exception {
      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/a01ea7e8107f48a3b9f04e12b01771b6/os-interface");
      InterfaceApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint)
                  .method("POST")
                  .payload(payloadFromStringWithContentType(
                        "{\"interfaceAttachment\":{\"net_id\":\"1017d1c5-963b-4ae3-b40f-2e8266287249\"}}", "application/json")).
                  build(),
            HttpResponse.builder().statusCode(200).message("HTTP/1.1 200").payload(payloadFromResource("/interface_attachment.json")).build()
      ).getInterfaceApiForZone("az-1.region-a.geo-1").get();

      InterfaceAttachment i = api.attachInterface("a01ea7e8107f48a3b9f04e12b01771b6", AttachInterfaceOptions.Builder.netId("1017d1c5-963b-4ae3-b40f-2e8266287249"));
      assertEquals(api.attachInterface("a01ea7e8107f48a3b9f04e12b01771b6", AttachInterfaceOptions.Builder.netId("1017d1c5-963b-4ae3-b40f-2e8266287249")).toString(),
            new ParseInterfaceAttachmentTest().expected().toString());
   }

}