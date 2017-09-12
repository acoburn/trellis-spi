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

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.trellisldp.api.Resource;

/**
 * The ResourceService provides methods for creating, retrieving and manipulating
 * repository resources.
 *
 * @author acoburn
 */
public interface ResourceService {

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

    /**
     * Compact (i.e. remove the history) of a resource
     * @param identifier the identifier
     * @param from a time after which a resource is to be compacted
     * @param until a time before which a resource is to be compacted
     * @return a stream of binary IRIs that can be safely purged
     */
    Stream<IRI> compact(IRI identifier, Instant from, Instant until);

    /**
     * Purge a resource from the repository
     * @param identifier the identifier
     * @return a stream of binary IRIs that can be safely purged
     */
    Stream<IRI> purge(IRI identifier);

    /**
     * Get a list of resources in the partition
     * @param partition the partition
     * @return a stream of RDF Triples, containing the resource and its LDP type
     */
    Stream<Triple> list(String partition);

    /**
     * Skolemize a blank node
     * @param term the RDF term
     * @return a skolemized node, if a blank node; otherwise the original term
     */
    RDFTerm skolemize(RDFTerm term);

    /**
     * Un-skolemize a blank node
     * @param term the RDF term
     * @return a blank node, if a previously-skolemized node; otherwise the original term
     */
    RDFTerm unskolemize(RDFTerm term);

    /**
     * Export the complete repository as a stream of Quads
     * @param partition the partition to export
     * @param graphNames the graph names to export
     * @return a stream of quads, where each named graph refers to the resource identifier
     */
    Stream<Quad> export(String partition, Collection<IRI> graphNames);

    /**
     * An identifier supplier
     * @return a supplier of identifiers for new resources
     */
    Supplier<String> getIdentifierSupplier();
}
