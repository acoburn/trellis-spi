/*
 * Copyright 2016 Amherst College
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

import static java.time.Instant.now;
import static java.util.Collections.singleton;
import static java.util.Optional.of;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import edu.amherst.acdc.trellis.api.Event;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author acoburn
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityStreamSerializerTest {

    private static final RDF rdf = new SimpleRDF();

    @Mock
    private Event mockEvent;

    private ActivityStreamSerializer serializer;

    @Before
    public void setUp() {
        serializer = new ActivityStreamSerializer(mockEvent);
        when(mockEvent.getIdentifier()).thenReturn(rdf.createIRI("info:event/12345"));
        when(mockEvent.getAgents()).thenReturn(singleton(rdf.createIRI("info:user/test")));
        when(mockEvent.getTarget()).thenReturn(of(rdf.createIRI("info:trellis/resource")));
        when(mockEvent.getTypes()).thenReturn(singleton(rdf.createIRI("info:event/creation")));
        when(mockEvent.getTargetTypes()).thenReturn(singleton(rdf.createIRI("info:type/container")));
        when(mockEvent.getCreated()).thenReturn(now());
        when(mockEvent.getInbox()).thenReturn(of(rdf.createIRI("info:ldn/inbox")));
    }

    @Test
    public void testSerialization() {
        final Optional<String> json = serializer.serialize();
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"inbox\":\"info:ldn/inbox\""));
    }
}
