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

import static java.time.Instant.now;
import static java.util.Collections.singleton;
import static java.util.Optional.of;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.trellisldp.spi.ActivityStreamSerializer.serialize;
import static org.trellisldp.vocabulary.AS.Create;
import static org.trellisldp.vocabulary.LDP.Container;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.trellisldp.vocabulary.AS;
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

    @Before
    public void setUp() {
        when(mockEvent.getIdentifier()).thenReturn(rdf.createIRI("info:event/12345"));
        when(mockEvent.getAgents()).thenReturn(singleton(rdf.createIRI("info:user/test")));
        when(mockEvent.getTarget()).thenReturn(of(rdf.createIRI("trellis:repository/resource")));
        when(mockEvent.getTypes()).thenReturn(singleton(Create));
        when(mockEvent.getTargetTypes()).thenReturn(singleton(Container));
        when(mockEvent.getCreated()).thenReturn(now());
        when(mockEvent.getInbox()).thenReturn(of(rdf.createIRI("info:ldn/inbox")));
    }

    @Test
    public void testSerialization() {
        final Optional<String> json = serialize(mockEvent);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"inbox\":\"info:ldn/inbox\""));
    }

    @Test
    public void testSerializationStructure() throws Exception {
        final Optional<String> json = serialize(mockEvent);
        assertTrue(json.isPresent());

        final ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = mapper.readValue(json.get(), Map.class);
        assertTrue(map.containsKey("@context"));
        assertTrue(map.containsKey("id"));
        assertTrue(map.containsKey("type"));
        assertTrue(map.containsKey("inbox"));
        assertTrue(map.containsKey("actor"));
        assertTrue(map.containsKey("object"));

        final List types = (List) map.get("type");
        assertTrue(types.contains(Create.getIRIString()));

        assertTrue(AS.uri.contains((String) map.get("@context")));

        final List actor = (List) map.get("actor");
        assertTrue(actor.contains("info:user/test"));

        assertTrue(map.get("id").equals("info:event/12345"));
        assertTrue(map.get("inbox").equals("info:ldn/inbox"));
    }
}
