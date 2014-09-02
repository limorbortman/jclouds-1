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

package org.jclouds.openstack.nova.v2_0.options;


import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 *
 */
public class CreateFloatingIPOptions implements MapBinder {
   public static final CreateFloatingIPOptions NONE = new CreateFloatingIPOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String pool;


   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      Map<String, Object> data = Maps.newHashMap();
      data.putAll(postParams);
      if (pool != null)
         data.put("pool", pool);
      return jsonBinder.bindToRequest(request, ImmutableMap.copyOf(data));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("createFloatingIp is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateFloatingIPOptions)) return false;
      final CreateFloatingIPOptions other = CreateFloatingIPOptions.class.cast(object);
      return equal(pool, other.pool);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(pool);
   }

   protected Objects.ToStringHelper string() {
      return toStringHelper("").add("pool", pool);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public String getPool() {
      return pool;
   }

   public CreateFloatingIPOptions pool(String pool) {
      this.pool = pool;
      return this;
   }

   public static class Builder {
      /**
       * @see CreateFloatingIPOptions#getPool()
       */
      public static CreateFloatingIPOptions pool(String pool) {
         return new CreateFloatingIPOptions().pool(pool);
      }
   }

}