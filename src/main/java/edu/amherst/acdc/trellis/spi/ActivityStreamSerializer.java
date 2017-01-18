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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static edu.amherst.acdc.trellis.spi.ActivityStreamMessage.from;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author acoburn
 */
class ActivityStreamSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * Serialize the Event
     * @return the Event as a JSON string
     */
    static Optional<String> serialize(final Event event) {
        try {
            return of(MAPPER.writeValueAsString(from(event)));
        } catch (final JsonProcessingException ex) {
            return empty();
        }
    }

    private ActivityStreamSerializer() {
        // prevent instantiation
    }
}
