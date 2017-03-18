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

import edu.amherst.acdc.trellis.api.Resource;

import java.time.Instant;
import java.util.Optional;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;

/**
 * The ResourceService provides methods for creating, retrieving and manipulating
 * repository resources.
 *
 * @author acoburn
 */
public interface ResourceService {

    /**
     * Set the event service
     * @param service the event service
     */
    default void setEventService(EventService service) {
        bind(service);
    }

    /**
     * Bind the event service
     * @param service the event service
     */
    void bind(EventService service);

    /**
     * Unbind the event service
     * @param service the event service
     */
    void unbind(EventService service);

    /**
     * Get a resource from the given location
     * @param session the session
     * @param identifier the resource identifier
     * @return the resource
     */
    Optional<Resource> get(Session session, IRI identifier);

    /**
     * Get a resource from the given location and time
     * @param session the session
     * @param identifier the resource identifier
     * @param time the time
     * @return the resource
     */
    Optional<Resource> get(Session session, IRI identifier, Instant time);

    /**
     * Test whether a resource exists at the identifier
     * @param session the session
     * @param identifier the resource identifier
     * @return whether the identified resource exists
     */
    default Boolean exists(Session session, IRI identifier) {
        return get(session, identifier).isPresent();
    }

    /**
     * Test whether a resource exists at the identifier at a given time
     * @param session the session
     * @param identifier the resource identifier
     * @param time the time
     * @return whether the identified resource exists
     */
    default Boolean exists(Session session, IRI identifier, Instant time) {
        return get(session, identifier, time).isPresent();
    }

    /**
     * Put a resource into the repository
     * @param session the session
     * @param identifier the Identifier for the new resource
     * @param dataset the dataset
     * @return whether the resource was added
     */
    Boolean put(Session session, IRI identifier, Dataset dataset);

    /**
     * Delete a resource
     * @param session the session
     * @param identifier the resource identifier
     * @return whether the resource was deleted
     */
    Boolean delete(Session session, IRI identifier);
}
