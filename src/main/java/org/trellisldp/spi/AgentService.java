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

import java.util.stream.Stream;

import org.apache.commons.rdf.api.IRI;

/**
 * This service provides some useful methods for handling user values.
 *
 * @author acoburn
 */
public interface AgentService {

    /**
     * Test whether the agent is an administrator
     * @param agent the agent identifier
     * @return whether the agent is an admin user
     */
    Boolean isAdmin(IRI agent);

    /**
     * Get the groups associated with this agent
     * @param agent the agent identifier
     * @return the groups associated with this user
     */
    Stream<IRI> getGroups(IRI agent);

    /**
     * Convert an agent String into an IRI
     * @param agent the agent as a string
     * @return the agent as an IRI
     */
    IRI asAgent(String agent);
}
