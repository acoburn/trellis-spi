/*
 * Copyright 2016 Amherst College
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

import java.util.Optional;

import edu.amherst.acdc.trellis.api.Resource;
import org.apache.commons.rdf.api.IRI;

/**
 * @author acoburn
 */
public interface ResourceService {

    /**
     * Find a resource at the given location
     * @param identifier the resource identifier
     * @return the resource
     */
    Optional<Resource> find(final IRI identifier);

    /**
     * Test whether a resource exists at the identifier
     * @param identifier the resource identifier
     * @return whether the identified resource exists
     */
    Boolean exists(final IRI identifier);

    /**
     * Create a new resource
     * @param identifier the Identifier for the new resource
     * @param type the type of the resource
     * @param <T> the resource type
     * @return the new resource
     */
    <T> Optional<T> create(final IRI identifier, final Class<T> type);
}
