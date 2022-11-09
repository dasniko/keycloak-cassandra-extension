/*
 * Copyright 2022 IT-Systemhaus der Bundesagentur fuer Arbeit 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.arbeitsagentur.opdt.keycloak.cassandra.cache;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.interceptor.InvocationContext;
import java.util.Arrays;

/**
 * Nötig, um die verschiedenen Aufrufe unterscheiden zu können, siehe https://vladmihalcea.com/spring-request-level-memoization/
 */
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class CacheInvocationContext {
   private final Class targetClass;
   private final String targetMethod;
   private final Object[] args;

   public static CacheInvocationContext create(InvocationContext invocationContext) {
      return new CacheInvocationContext(invocationContext.getTarget().getClass(), invocationContext.getMethod().getName(), invocationContext.getParameters());
   }

   @Override
   public String toString() {
      return String.format("%s.%s(%s)", targetClass.getName(), targetMethod, Arrays.toString(args));
   }
}