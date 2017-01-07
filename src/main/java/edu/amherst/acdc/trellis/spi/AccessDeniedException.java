/*
 * Copyright Amherst College
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
package edu.amherst.acdc.trellis.spi;

import edu.amherst.acdc.trellis.api.RuntimeRepositoryException;

/**
 * A class representing an unchecked access denied exception
 *
 * @author acoburn
 */
public class AccessDeniedException extends RuntimeRepositoryException {

    private static final long serialVersionUID = 8046489554418284258L;

    /**
     * Create a new AccessDeniedException
     */
    public AccessDeniedException() {
    }

    /**
     * Create a new AccessDeniedException with a custom message
     * @param message the message
     */
    public AccessDeniedException(final String message) {
        super(message);
    }

    /**
     * Create a new AccessDeniedException with a custom message and known cause
     * @param message the message
     * @param cause the cause
     */
    public AccessDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new AccessDeniedException with a known cause
     * @param cause the cause
     */
    public AccessDeniedException(final Throwable cause) {
        super(cause);
    }
}
