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

import java.util.Optional;
import java.util.stream.Stream;

import edu.amherst.acdc.trellis.api.Resource;
import org.apache.commons.rdf.api.IRI;

/**
 * The AccessControlService provides methods for checking user access to given resources
 * as well as services for finding the applicable access control resource.
 *
 * @see <a href="https://www.w3.org/wiki/WebAccessControl">W3C WebAccessControl</a>
 * and <a href="https://github.com/solid/web-access-control-spec">Solid WebAC specification</a>
 *
 * @author acoburn
 */
public interface AccessControlService {

    /**
     * Add a resource service
     * @param service the service
     */
    void setResourceService(ResourceService service);

    /**
     * Remove a resource service
     * @param service the service
     */
    void unsetResourceService(ResourceService service);

    /**
     * Add an agent service
     * @param service the service
     */
    void setAgentService(AgentService service);

    /**
     * Remove an agent service
     * @param service the service
     */
    void unsetAgentService(AgentService service);

    /**
     * Test whether the resource is readable
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can read the identified resource
     */
    Boolean canRead(Session session, IRI identifier);

    /**
     * Test whether the resource is writeable
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can write to identified resource
     */
    Boolean canWrite(Session session, IRI identifier);

    /**
     * Test whether the user can control the ACL for the given resource
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can control the ACL for the identified resource
     */
    Boolean canControl(Session session, IRI identifier);

    /**
     * Test whether the user can append the given resource
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can append the identified resource
     */
    Boolean canAppend(Session session, IRI identifier);

    /**
     * Find the effective ACL for the given resource identifier
     * @param identifier the resource identifier
     * @return the ACL identifier
     */
    Optional<IRI> findAclFor(IRI identifier);

    /**
     * Find the first ancestor resource with an access control declaration
     * @param identifier the starting resource
     * @return the resource identifier
     */
    Optional<Resource> findAncestorWithAccessControl(IRI identifier);

    /**
     * Fetch the authorizations for the provided resource
     * @param identifier the resource containing acl:Authorization statements
     * @return a stream of Authorizations
     */
    Stream<Authorization> getAuthorizations(IRI identifier);
}
