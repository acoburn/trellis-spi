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

import java.time.Duration;
import java.util.Optional;

import edu.amherst.acdc.trellis.api.Resource;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

/**
 * The ResourceService provides methods for creating, retrieving and manipulating
 * repository resources.
 *
 * @author acoburn
 */
public interface ResourceService {

    /**
     * Find a resource at the given location
     * @param session the session
     * @param identifier the resource identifier
     * @return the resource
     */
    Optional<Resource> find(Session session, IRI identifier);

    /**
     * Test whether a resource exists at the identifier
     * @param session the session
     * @param identifier the resource identifier
     * @return whether the identified resource exists
     */
    Boolean exists(Session session, IRI identifier);

    /**
     * Create a new resource
     * @param session the session
     * @param identifier the Identifier for the new resource
     * @param type the type of the resource
     * @return the new resource
     */
    Resource create(Session session, IRI identifier, IRI type);

    /**
     * Update a resource
     * @param session the session
     * @param identifier the resource identifier
     * @param graph the new graph
     */
    void update(Session session, IRI identifier, Graph graph);

    /**
     * Delete a resource
     * @param session the session
     * @param identifier the resource identifier
     */
    void delete(Session session, IRI identifier);

    /**
     * Commit changes to durable storage
     * @param session the session
     */
    void commit(Session session);

    /**
     * Expire a session and drop any uncommitted changes
     * @param session the session
     */
    void expire(Session session);

    /**
     * Begin a new session
     * @return the session
     */
    Session begin();

    /**
     * Resume an existing session
     * @param identifier the identifier
     * @return the session, if it exists
     */
    Optional<Session> resume(IRI identifier);

    /**
     * Extend a session
     * @param session the session
     * @param duration the amount of time by which to extend the session
     * @return the new session
     */
    Optional<Session> extend(Session session, Duration duration);
}
