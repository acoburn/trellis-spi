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

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.amherst.acdc.trellis.vocabulary.AS;
import org.apache.commons.rdf.api.IRI;

/**
 * A structure used for serializing an Event into an ActivityStream 2.0 JSON object
 *
 * @see <a href="https://www.w3.org/TR/activitystreams-core/">Activity Streams 2.0</a>
 *
 * @author acoburn
 */
class ActivityStreamMessage {

    static class ContextDate {
        @JsonProperty("@id")
        public final String id = "http://purl.org/dc/terms/date";

        @JsonProperty("@type")
        public final String type = "xsd:dateTime";
    }

    static class EventResource {
        @JsonProperty("id")
        public String id;

        @JsonProperty("type")
        public List<String> type;

        public EventResource(final String id, final List<String> type) {
            this.id = id;
            this.type = type;
        }
    }

    @JsonProperty("id")
    public String id;

    @JsonProperty("type")
    public List<String> type;

    @JsonProperty("inbox")
    public String inbox;

    @JsonProperty("actor")
    public List<String> actor;

    @JsonProperty("date")
    public Instant date;

    @JsonProperty("object")
    public EventResource object;

    @JsonProperty("@context")
    public List<Object> context;

    /**
     * Populate a ActivityStreamMessage from an Event
     * @param event The event
     * @return an ActivityStreamMessage
     */
    public static ActivityStreamMessage from(final Event event) {

        final ActivityStreamMessage msg = new ActivityStreamMessage();

        final List<Object> context = new ArrayList<>();
        final Map<String, Object> ctxAdditions = new HashMap<>();
        ctxAdditions.put("date", new ContextDate());

        context.add(AS.uri);
        context.add(ctxAdditions);

        msg.context = context;
        msg.id = event.getIdentifier().getIRIString();
        msg.type = event.getTypes().stream().map(IRI::getIRIString).collect(toList());
        msg.actor = event.getAgents().stream().map(IRI::getIRIString).collect(toList());
        msg.date = event.getCreated();

        event.getInbox().map(IRI::getIRIString).ifPresent(inbox -> {
            msg.inbox = inbox;
        });

        event.getTarget().map(IRI::getIRIString).ifPresent(target -> {
            msg.object = new EventResource(target,
                    event.getTargetTypes().stream().map(IRI::getIRIString).collect(toList()));
        });

        return msg;
    }
}
