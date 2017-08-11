/*
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
package org.trellisldp.spi;

import static java.util.Collections.emptyMap;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.rdf.api.IRI;

import org.trellisldp.api.Binary;

/**
 * The BinaryService provides methods for retrieving, modifying and checking
 * the validity of binary content.
 *
 * @author acoburn
 */
public interface BinaryService {

    /**
     * A blob resolver, used by the BinaryService
     */
    interface Resolver {
        /**
         * @return the uri schemes supported by this resolver
         */
        List<String> getUriSchemes();

        /**
         * Get the content of the binary object
         * @param partition the partition to use
         * @param identifier the identifier
         * @return the content of the binary object
         */
        Optional<InputStream> getContent(String partition, IRI identifier);

        /**
         * Check whether the binary object exists
         * @param partition the partition to use
         * @param identifier the identifier
         * @return whether the binary object exists
         */
        Boolean exists(String partition, IRI identifier);

        /**
         * Set the content of the binary object
         * @param partition the partition to use
         * @param identifier the identifier
         * @param stream the stream
         */
        default void setContent(String partition, IRI identifier, InputStream stream) {
            setContent(partition, identifier, stream, emptyMap());
        }

        /**
         * Set the content of the binary object
         * @param partition the partition to use
         * @param identifier the identifier
         * @param stream the content
         * @param metadata any user metadata
         */
        void setContent(String partition, IRI identifier, InputStream stream, Map<String, String> metadata);

        /**
         * Test whether the resolver supports multipart uploads
         * @return true if the resolver supports multipart uploads; false otherwise
         */
        Boolean supportsMultipartUpload();

        /**
         * Initiate a multi-part upload
         * @param partition the partition
         * @param identifier the object identifier
         * @param mimeType the mimeType of the object
         * @return an upload session identifier
         */
        String initiateUpload(String partition, IRI identifier, String mimeType);

        /**
         * Upload a part
         * @param identifier the upload identifier
         * @param partNumber the part number
         * @param content the content to upload
         * @return a digest value returned for each part; this value is used later wich completeUpload()
         */
        String uploadPart(String identifier, Integer partNumber, InputStream content);

        /**
         * Complete a multi-part upload
         * @param identifier the upload identifier
         * @param partDigests digest values for each part
         * @return a Binary object
         */
        Binary completeUpload(String identifier, Map<Integer, String> partDigests);

        /**
         * Abort the upload for the given identifier
         * @param identifier the upload identifier
         */
        void abortUpload(String identifier);
    }

    /**
     * Get the content of the binary object
     * @param partition the partition to use
     * @param identifier an identifier used for locating the binary object
     * @return the content
     */
    default Optional<InputStream> getContent(String partition, IRI identifier) {
        return getResolver(identifier).flatMap(resolver -> resolver.getContent(partition, identifier));
    }

    /**
     * Test whether a binary object exists at the given URI
     * @param partition the partition to use
     * @param identifier the binary object identifier
     * @return whether the binary object exists
     */
    default Boolean exists(String partition, IRI identifier) {
        return getResolver(identifier).map(resolver -> resolver.exists(partition, identifier)).orElse(false);
    }

    /**
     * Set the content for a binary object
     * @param partition the partition to use
     * @param identifier the binary object identifier
     * @param stream the content
     */
    default void setContent(String partition, IRI identifier, InputStream stream) {
        getResolver(identifier).ifPresent(resolver -> resolver.setContent(partition, identifier, stream));
    }

    /**
     * Set the content for a binary object
     * @param partition the partition to use
     * @param identifier the binary object identifier
     * @param stream the content
     * @param metadata any user metadata
     */
    default void setContent(String partition, IRI identifier, InputStream stream, Map<String, String> metadata) {
        getResolver(identifier).ifPresent(resolver -> resolver.setContent(partition, identifier, stream, metadata));
    }

    /**
     * Calculate the digest for a binary object
     * @param partition the partition to use
     * @param identifier the identifier
     * @param algorithm the algorithm
     * @return the digest
     *
     * <p>Note: as per RFC 3230, the digest value is calculated over the entire resource,
     * not just the HTTP payload.</p>
     *
     */
    default Optional<String> calculateDigest(String partition, IRI identifier, String algorithm) {
        return getContent(partition, identifier).flatMap(stream -> hexDigest(algorithm, stream));
    }

    /**
     * Get a list of supported algorithms
     * @return the supported digest algorithms
     */
    Set<String> supportedAlgorithms();

    /**
     * Get the resolver for the given identifier
     * @param identifier the identifier
     * @return a binary object resolver
     */
    Optional<Resolver> getResolver(IRI identifier);

    /**
     * Get the default resolver for the given partition
     * @param partition the partition name
     * @return a binary object resolver
     */
    Optional<Resolver> getResolverForPartition(String partition);

    /**
     * Get the digest for an input stream
     * @param algorithm the algorithm to use
     * @param stream the input stream
     * @return the digest
     */
    Optional<String> hexDigest(String algorithm, InputStream stream);

    /**
     * An identifier supplier
     * @param partition the partition to use
     * @return a supplier of identifiers for new resources
     */
    Supplier<String> getIdentifierSupplier(String partition);
}
