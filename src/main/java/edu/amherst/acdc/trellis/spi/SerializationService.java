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
import java.io.OutputStream;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFSyntax;
import org.apache.commons.rdf.api.Triple;

/**
 * The SerializationService defines methods for reading and writing RDF streams
 * to/from a concrete RDF 1.1 syntax.
 *
 * @author acoburn
 */
public interface SerializationService {

    /**
     * A method for setting a namespace service
     * @param service the service
     */
    void setNamespaceService(NamespaceService service);

    /**
     * A method for unbinding a namespace service
     * @param service the service
     */
    void unsetNamespaceService(NamespaceService service);

    /**
     * Serialize the triple stream as RDF
     * @param triples the stream of triples
     * @param output the output stream
     * @param syntax the output format
     */
    void write(Stream<Triple> triples, OutputStream output, RDFSyntax syntax);

    /**
     * Serialize the triple stream as RDF
     * @param triples the stream of triples
     * @param output the output stream
     * @param syntax the output format
     * @param profile additional profile information used for output
     */
    void write(Stream<Triple> triples, OutputStream output, RDFSyntax syntax, IRI profile);

    /**
     * Read an input stream into a graph
     * @param graph the graph
     * @param input the input stream
     * @param syntax the RDF syntax
     */
    void read(Graph graph, InputStream input, RDFSyntax syntax);
}
