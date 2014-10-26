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
package org.jclouds.openstack.ceilometer.v2.options;

import com.google.common.collect.ImmutableSet;
import org.jclouds.http.options.BaseHttpRequestOptions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * QueryOptions used to control Ceilometer results.
 */
public class QueryOptions extends BaseHttpRequestOptions {

   public static enum OP {
      LT, LE, EQ, NE, GE, GT, UNRECOGNIZED;

      public String value() {
         return name().toLowerCase();
      }

      @Override
      public String toString() {
         return value();
      }

      public static OP fromValue(String op) {
         try {
            return valueOf(checkNotNull(op, "op").toUpperCase());
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public QueryOptions field(String field) {
      this.queryParameters.replaceValues("q.field", ImmutableSet.of(field));
      return this;
   }

   public QueryOptions op(OP op) {
      this.queryParameters.replaceValues("q.op", ImmutableSet.of(op.toString()));
      return this;
   }

   public QueryOptions type(String type) {
      this.queryParameters.replaceValues("q.type", ImmutableSet.of(type));
      return this;
   }

   public QueryOptions value(String value) {
      this.queryParameters.replaceValues("q.value", ImmutableSet.of(value));
      return this;
   }

   public static class Builder {

      /**
       * @see QueryOptions#field
       */
      public static QueryOptions field(String field) {
         return new QueryOptions().field(field);
      }

      /**
       * @see QueryOptions#op
       */
      public static QueryOptions op(OP op) {
         return new QueryOptions().op(op);
      }

      /**
       * @see QueryOptions#type
       */
      public static QueryOptions type(String type) {
         return new QueryOptions().field(type);
      }

      /**
       * @see QueryOptions#value
       */
      public static QueryOptions value(String value) {
         return new QueryOptions().field(value);
      }
   }


}
