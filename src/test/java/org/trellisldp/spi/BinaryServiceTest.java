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
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Map;

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
public class BinaryServiceTest {

    private static final RDF rdf = new SimpleRDF();

    private final IRI identifier = rdf.createIRI("trellis:repository/resource");
    private final IRI other = rdf.createIRI("trellis:repository/other");
    private final String checksum = "blahblahblah";

    @Mock
    private BinaryService mockBinaryService;

    @Mock
    private InputStream mockInputStream;

    @Mock
    private BinaryService.Resolver mockResolver;

    @Before
    public void setUp() {
        doCallRealMethod().when(mockBinaryService).getContent(any());
        doCallRealMethod().when(mockBinaryService).setContent(any(), any(), any());
        doCallRealMethod().when(mockBinaryService).setContent(any(), any());
        doCallRealMethod().when(mockBinaryService).exists(any());
        doCallRealMethod().when(mockBinaryService).calculateDigest(any(), any());
        doCallRealMethod().when(mockResolver).setContent(any(), any());
        when(mockResolver.getContent(any())).thenReturn(of(mockInputStream));
        when(mockResolver.exists(eq(identifier))).thenReturn(true);
        when(mockBinaryService.getResolver(any())).thenReturn(of(mockResolver));
        when(mockBinaryService.hexDigest(any(), any())).thenReturn(of(checksum));
        doNothing().when(mockResolver).setContent(any(), any(), any());
    }

    @Test
    public void testDefaultMethods() {
        final Map<String, String> data = emptyMap();
        assertEquals(of(mockResolver), mockBinaryService.getResolver(identifier));
        assertEquals(of(mockInputStream), mockBinaryService.getContent(identifier));
        assertTrue(mockBinaryService.exists(identifier));
        assertFalse(mockBinaryService.exists(other));
        mockBinaryService.setContent(identifier, mockInputStream, data);
        mockBinaryService.setContent(identifier, mockInputStream);
        assertTrue(mockBinaryService.getContent(other).isPresent());
        assertEquals(of(checksum), mockBinaryService.calculateDigest(other, "md5"));
        verify(mockResolver, times(2)).setContent(eq(identifier), eq(mockInputStream), eq(data));
    }
}
