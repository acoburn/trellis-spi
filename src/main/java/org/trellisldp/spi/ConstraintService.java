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

import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.trellisldp.vocabulary.LDP;

/**
 * The ConstraintService defines rules that constrain RDF triples
 * on a graph for a particular resource type.
 *
 * @author acoburn
 */
public interface ConstraintService {

    /**
     * Check a graph against an LDP interaction model
     * @param interactionModel the interaction model
     * @param domain the domain of the resource
     * @param graph the graph
     * @return any constraint on the graph
     */
    Optional<IRI> constrainedBy(IRI interactionModel, String domain, Graph graph);

    /**
     * Get all of the LDP resource (super) types for the given LDP interaction model
     * @param interactionModel the interaction model
     * @return a stream of types
     */
    static Stream<IRI> ldpResourceTypes(final IRI interactionModel) {
        return of(interactionModel).filter(type -> RDFUtils.superClassOf.containsKey(type) || LDP.Resource.equals(type))
            .flatMap(type -> concat(ldpResourceTypes(RDFUtils.superClassOf.get(type)), of(type)));
    }
}
