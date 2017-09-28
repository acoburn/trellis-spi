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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author acoburn
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    private static final RDF rdf = new SimpleRDF();

    @Mock
    private ResourceService mockResourceService;

    @Before
    public void setUp() {
        doCallRealMethod().when(mockResourceService).skolemize(any());
        doCallRealMethod().when(mockResourceService).unskolemize(any());
    }

    @Test
    public void testSkolemization() {
        final BlankNode bnode = rdf.createBlankNode("testing");
        final IRI iri = rdf.createIRI("trellis:bnode/testing");
        final IRI resource = rdf.createIRI("trellis:repository/resource");

        assertTrue(mockResourceService.skolemize(bnode) instanceof IRI);
        assertTrue(((IRI) mockResourceService.skolemize(bnode)).getIRIString().startsWith("trellis:bnode/"));
        assertTrue(mockResourceService.unskolemize(iri) instanceof BlankNode);
        assertEquals(mockResourceService.unskolemize(iri), mockResourceService.unskolemize(iri));

        assertFalse(mockResourceService.unskolemize(rdf.createLiteral("Test")) instanceof BlankNode);
        assertFalse(mockResourceService.unskolemize(resource) instanceof BlankNode);
        assertFalse(mockResourceService.skolemize(rdf.createLiteral("Test2")) instanceof IRI);
    }
}
