/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.glance.v1_0.binders;

import org.jclouds.http.HttpRequest;
import org.jclouds.io.Payload;
import org.jclouds.rest.Binder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class BindGlanceObjectToRequest implements Binder {

   @Inject
   public BindGlanceObjectToRequest() {
   }

   @SuppressWarnings("unchecked")
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      checkArgument(checkNotNull(input, "input") instanceof Payload, "this binder is only valid for Payload!");
      checkNotNull(request, "request");

      Payload object = (Payload) input;
      if (object.getContentMetadata().getContentType() == null)
         object.getContentMetadata().setContentType(MediaType.APPLICATION_OCTET_STREAM);

      if (object.getContentMetadata().getContentLength() == null
            || object.getContentMetadata().getContentLength() >= 2l * 1024 * 1024) {
         // Enable "chunked"/"streamed" data, where the size needn't be known in advance or size is simply too large.
         request = (R) request.toBuilder().replaceHeader("Transfer-Encoding", "chunked").build();
      }
      return request;
   }
}