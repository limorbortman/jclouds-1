package org.jclouds.openstack.nova.v2_0.options;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 *
 */
public class AttachInterfaceOptions implements MapBinder {

   public static final AttachInterfaceOptions NONE = new AttachInterfaceOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String portState;
   protected Set<org.jclouds.openstack.nova.v2_0.domain.FixedIp> fixedIps;
   protected String portId;
   protected String netId;

   static class AttachInterfaceRequest {

      @Named("port_state")
      String portState;
      @Named("fixed_ips")
      Set<AttachInterfaceRequest.FixedIp> fixedIps;
      @Named("port_id")
      String portId;
      @Named("net_id")
      String netId;

      static class FixedIp {

         @Named("subnet_id")
         protected String subnetId;
         @Named("ip_address")
         protected String ipAddress;

         FixedIp(String subnetId, String ipAddress) {
            this.subnetId = subnetId;
            this.ipAddress = ipAddress;
         }
      }

   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      AttachInterfaceRequest attachInterfaceRequest = new AttachInterfaceRequest();
      if (portState != null)
         attachInterfaceRequest.portState = portState;
      if (fixedIps != null && !fixedIps.isEmpty()) {
         attachInterfaceRequest.fixedIps = Sets.newLinkedHashSet();
         for (org.jclouds.openstack.nova.v2_0.domain.FixedIp fixedIp : fixedIps) {
            attachInterfaceRequest.fixedIps.add(new AttachInterfaceRequest.FixedIp(fixedIp.getSubnetId(), fixedIp.getIpAddress()));
         }
      }
      if (portId != null)
         attachInterfaceRequest.portId = portId;
      if (netId != null)
         attachInterfaceRequest.netId = netId;

      return jsonBinder.bindToRequest(request, ImmutableMap.of("interfaceAttachment", attachInterfaceRequest));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("AttachInterface is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof AttachInterfaceOptions)) return false;
      final AttachInterfaceOptions other = AttachInterfaceOptions.class.cast(object);
      return equal(portState, other.portState) && equal(fixedIps, other.fixedIps) && equal(portId, other.portId)
            && equal(netId, other.netId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(portState, fixedIps, portId, netId);
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("").add("portState", portState).add("fixedIps", fixedIps)
            .add("portId", portId).add("netId", netId);
   }

   @Override
   public String toString() {
      return string().toString();
   }


   /**
    * @param portState - The status of the port
    */
   public AttachInterfaceOptions portState(String portState) {
      this.portState = portState;
      return this;
   }

   /**
    * @param fixedIp - the fixed ip
    */
   public AttachInterfaceOptions fixedIps(Collection<org.jclouds.openstack.nova.v2_0.domain.FixedIp> fixedIp) {
      this.fixedIps = ImmutableSet.copyOf(fixedIp);
      return this;
   }

   /**
    * @param portId - uuid of the port
    */
   public AttachInterfaceOptions portId(String portId) {
      this.portId = portId;
      return this;
   }

   /**
    * @param netId - uuid of the network
    */
   public AttachInterfaceOptions netId(String netId) {
      this.netId = netId;
      return this;
   }

   public String getPortState() {
      return portState;
   }

   public Set<org.jclouds.openstack.nova.v2_0.domain.FixedIp> getFixedIps() {
      return fixedIps;
   }

   public String getPortId() {
      return portId;
   }

   public String getNetId() {
      return netId;
   }

   public static class Builder {

      /**
       * @see AttachInterfaceOptions#getPortState()()
       */
      public static AttachInterfaceOptions portState(String portState) {
         return new AttachInterfaceOptions().portState(portState);
      }

      /**
       * @see AttachInterfaceOptions#getFixedIps()
       */
      public static AttachInterfaceOptions fixIps(Set<org.jclouds.openstack.nova.v2_0.domain.FixedIp> fixedIps) {
         return new AttachInterfaceOptions().fixedIps(fixedIps);
      }

      /**
       * @see AttachInterfaceOptions#getPortId()
       */
      public static AttachInterfaceOptions getPortId(String portId) {
         return new AttachInterfaceOptions().portId(portId);
      }

      /**
       * @see AttachInterfaceOptions#getNetId()
       */
      public static AttachInterfaceOptions netId(String netId) {
         return new AttachInterfaceOptions().netId(netId);
      }
   }
}