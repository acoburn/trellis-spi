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

import static java.util.Arrays.asList;
import static org.trellisldp.vocabulary.RDF.type;
import static org.trellisldp.vocabulary.Trellis.PreferAudit;

import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import org.trellisldp.vocabulary.AS;
import org.trellisldp.vocabulary.PROV;
import org.trellisldp.vocabulary.XSD;

/**
 * The RDFUtils class provides a set of convenience methods related to
 * generating and processing RDF objects.
 *
 * @author acoburn
 */
public final class RDFUtils {

    private static RDF rdf = ServiceLoader.load(RDF.class).iterator().next();

    /**
     * Get the Commons RDF instance in use
     * @return the RDF instance
     */
    public static RDF getInstance() {
        return rdf;
    }

    /**
     * Create audit-related creation data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static Dataset auditCreation(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Create));
    }

    /**
     * Create audit-related deletion data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static Dataset auditDeletion(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Delete));
    }

    /**
     * Create audit-related update data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static Dataset auditUpdate(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Update));
    }

    private static Dataset auditData(final IRI subject, final Session session, final List<IRI> types) {
        final Dataset dataset = rdf.createDataset();
        final BlankNode bnode = rdf.createBlankNode();
        dataset.add(rdf.createQuad(PreferAudit, subject, PROV.wasGeneratedBy, bnode));
        types.forEach(t ->
            dataset.add(rdf.createQuad(PreferAudit, bnode, type, t)));
        dataset.add(rdf.createQuad(PreferAudit, bnode, PROV.wasAssociatedWith, session.getAgent()));
        dataset.add(rdf.createQuad(PreferAudit, bnode, PROV.startedAtTime,
                    rdf.createLiteral(session.getCreated().toString(), XSD.dateTime)));
        session.getDelegatedBy().ifPresent(delegate ->
                dataset.add(rdf.createQuad(PreferAudit, bnode, PROV.actedOnBehalfOf, delegate)));
        return dataset;
    }

    private RDFUtils() {
        // prevent instantiation
    }
}
