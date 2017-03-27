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
     * @param identifier the resource identifier
     * @return the resource
     */
    Optional<Resource> get(IRI identifier);

    /**
     * Get a resource from the given location and time
     * @param identifier the resource identifier
     * @param time the time
     * @return the resource
     */
    Optional<Resource> get(IRI identifier, Instant time);

    /**
     * Put a resource into the repository
     * @param identifier the identifier for the new resource
     * @param dataset the dataset
     * @return whether the resource was added
     */
    Boolean put(IRI identifier, Dataset dataset);

    /**
     * Get the identifier for the structurally-logical container for the resource
     * @param identifier the identifier
     * @return an identifier for the structurally-logical container
     *
     * Note: The returned identifier is not guaranteed to exist
     */
    Optional<IRI> getContainer(IRI identifier);
}
