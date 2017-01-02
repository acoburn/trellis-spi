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

import java.io.InputStream;
import java.util.Optional;

import edu.amherst.acdc.trellis.api.Fixity;
import edu.amherst.acdc.trellis.api.Session;
import org.apache.commons.rdf.api.IRI;

/**
 * @author acoburn
 */
public interface DatastreamService {

    /**
     * Get the content of the datastream
     * @param identifier an identifier used for locating the datastream
     * @return the content
     */
    InputStream getContent(IRI identifier);

    /**
     * Test whether a datastream exists at the given URI
     * @param identifier the datastream identifier
     * @return whether the datastream exists
     */
    Boolean exists(IRI identifier);

    /**
     * Set the content for a datastream
     * @param identifier the identifier
     * @param stream the content
     * @param contentType the contentType
     */
    void setContent(IRI identifier, InputStream stream, String contentType);

    /**
     * Compute the fixity of a resource
     * @param identifier the datastream identifier
     * @param algorithm the algorithm to use
     * @return the digest
     */
    Optional<Fixity> computeDigest(IRI identifier, String algorithm);

    /**
     * Add the fixity computation to a resource
     * @param session the session
     * @param identifier the resource identifier
     * @param result the fixity result
     */
    void addFixity(Session session, IRI identifier, Fixity result);

}
