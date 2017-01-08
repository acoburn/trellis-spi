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

import java.io.InputStream;

import edu.amherst.acdc.trellis.api.Datastream;
import org.apache.commons.rdf.api.IRI;

/**
 * The DatastreamService provides methods for retrieving, modifying and checking
 * the validity of binary content.
 *
 * @author acoburn
 */
public interface DatastreamService {

    /**
     * A datastream resolver, used by the DatastreamService
     */
    interface DatastreamResolver {
        /**
         * @return the uri scheme supported by this resolver
         */
        String getUriScheme();

        /**
         * Resolve the datastream
         * @param identifier the identifier
         * @return the content of the datastream
         */
        InputStream resolve(IRI identifier);

        /**
         * Whether this resolver supports the given storage partition
         * @param partition the partition
         * @return whether it is supported
         */
        Boolean supportsPartition(Datastream.StoragePartition partition);

        /**
         * Whether the given IRI is supported by this resolver
         * @param identifier the identifier
         * @return whether it is supported
         */
        Boolean supportsIRI(IRI identifier);

        /**
         * Given a resource identifier, generate a location for the datastream
         * @param resource the resource identifier
         * @return the datastream identifier
         */
        IRI generateIdentifier(IRI resource);
    }

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
     * @param identifier the datastream identifier
     * @param stream the content
     * @param contentType the contentType
     */
    void setContent(IRI identifier, InputStream stream, String contentType);

    /**
     * Generate an identifier for a new datastream resource
     * @param identifier the resource identifier
     * @return an identifier for the datastream
     */
    default IRI generateIdentifier(IRI identifier) {
        return generateIdentifier(identifier, null);
    }

    /**
     * Generate an identifier for a new datastream resource using a particular
     * partition hint.
     * @param identifier the resource identifier
     * @param partition the partition to use
     * @return the new identifier for the datastream
     */
    IRI generateIdentifier(IRI identifier, Datastream.StoragePartition partition);
}
