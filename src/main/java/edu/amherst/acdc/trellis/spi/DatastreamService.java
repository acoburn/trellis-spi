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

import static java.util.Collections.emptyMap;

import edu.amherst.acdc.trellis.api.Datastream;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.rdf.api.IRI;

/**
 * The DatastreamService provides methods for retrieving, modifying and checking
 * the validity of binary content.
 *
 * @author acoburn
 */
public interface DatastreamService extends AutoCloseable {

    /**
     * A datastream resolver, used by the DatastreamService
     */
    interface Resolver {
        /**
         * @return the uri schemes supported by this resolver
         */
        List<String> getUriSchemes();

        /**
         * Get the content of the datastream
         * @param identifier the identifier
         * @return the content of the datastream
         */
        Optional<InputStream> getContent(IRI identifier);

        /**
         * Check whether the datastream exists
         * @param identifier the identifier
         * @return whether the datastream exists
         */
        Boolean exists(IRI identifier);

        /**
         * Set the content of the datastream
         * @param identifier the identifier
         * @param stream the stream
         */
        default void setContent(IRI identifier, InputStream stream) {
            setContent(identifier, stream, emptyMap());
        }

        /**
         * Set the content of the datastream
         * @param identifier the identifier
         * @param stream the content
         * @param metadata any user metadata
         */
        void setContent(IRI identifier, InputStream stream, Map<String, String> metadata);
    }

    /**
     * Set a list of resolvers
     * @param resolvers the resolvers
     */
    void setResolvers(List<Resolver> resolvers);

    /**
     * Bind a resolver to this service
     * @param resolver the resolver
     */
    void bind(Resolver resolver);

    /**
     * Unbind a resolver
     * @param resolver the resolver
     */
    void unbind(Resolver resolver);

    /**
     * Get the content of the datastream
     * @param identifier an identifier used for locating the datastream
     * @return the content
     */
    default Optional<InputStream> getContent(IRI identifier) {
        return getResolver(identifier).flatMap(resolver -> resolver.getContent(identifier));
    }

    /**
     * Test whether a datastream exists at the given URI
     * @param identifier the datastream identifier
     * @return whether the datastream exists
     */
    default Boolean exists(IRI identifier) {
        return getResolver(identifier).map(resolver -> resolver.exists(identifier)).orElse(false);
    }

    /**
     * Set the content for a datastream
     * @param identifier the datastream identifier
     * @param stream the content
     */
    default void setContent(IRI identifier, InputStream stream) {
        setContent(identifier, stream, emptyMap());
    }

    /**
     * Set the content for a datastream
     * @param identifier the datastream identifier
     * @param stream the content
     * @param metadata any user metadata
     */
    default void setContent(IRI identifier, InputStream stream, Map<String, String> metadata) {
        getResolver(identifier).ifPresent(resolver -> resolver.setContent(identifier, stream, metadata));
    }

    /**
     * Calculate the digest for a datastream
     * @param identifier the identifier
     * @param algorithm the algorithm
     * @return the digest
     *
     * <p>Note: as per RFC 3230, the digest value is calculated over the entire resource,
     * not just the HTTP payload.</p>
     *
     */
    default Optional<String> calculateDigest(IRI identifier, String algorithm) {
        return getContent(identifier).flatMap(stream -> hexDigest(algorithm, stream));
    }

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

    /**
     * Get the resolver for the given identifier
     * @param identifier the identifier
     * @return a datastream resolver
     */
    Optional<Resolver> getResolver(IRI identifier);

    /**
     * Get the digest for an input stream
     * @param algorithm the algorithm to use
     * @param stream the input stream
     * @return the digest
     */
    Optional<String> hexDigest(String algorithm, InputStream stream);
}
