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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

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
public class AccessControlServiceTest {

    private static final RDF rdf = new SimpleRDF();

    private final IRI identifier = rdf.createIRI("trellis:repository/resource");

    @Mock
    private AccessControlService mockAccessControlService;

    @Mock
    private Session mockSession;

    @Before
    public void setUp() {
        doCallRealMethod().when(mockAccessControlService).canRead(any(), any());
        doCallRealMethod().when(mockAccessControlService).canWrite(any(), any());
        doCallRealMethod().when(mockAccessControlService).canAppend(any(), any());
        doCallRealMethod().when(mockAccessControlService).canControl(any(), any());
    }

    @Test
    public void testDefaultMethodsPermissive() {
        when(mockAccessControlService.anyMatch(any(), any(), any())).thenReturn(true);

        assertTrue(mockAccessControlService.canRead(mockSession, identifier));
        assertTrue(mockAccessControlService.canWrite(mockSession, identifier));
        assertTrue(mockAccessControlService.canAppend(mockSession, identifier));
        assertTrue(mockAccessControlService.canControl(mockSession, identifier));
    }

    @Test
    public void testDefaultMethodsNonPermissive() {
        when(mockAccessControlService.anyMatch(any(), any(), any())).thenReturn(false);

        assertFalse(mockAccessControlService.canRead(mockSession, identifier));
        assertFalse(mockAccessControlService.canWrite(mockSession, identifier));
        assertFalse(mockAccessControlService.canAppend(mockSession, identifier));
        assertFalse(mockAccessControlService.canControl(mockSession, identifier));
    }
}
