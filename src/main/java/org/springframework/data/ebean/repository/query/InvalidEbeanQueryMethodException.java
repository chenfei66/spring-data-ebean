/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.ebean.repository.query;

/**
 * Signals that we encountered an invalid query method.
 *
 * @author Xuegui Yuan
 */
public class InvalidEbeanQueryMethodException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new {@link InvalidEbeanQueryMethodException} with the given message.
   *
   * @param message must not be {@literal null} or empty.
   */
  public InvalidEbeanQueryMethodException(String message) {
    super(message);
  }
}
