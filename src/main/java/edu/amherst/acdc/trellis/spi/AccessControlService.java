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

import java.util.Optional;

import edu.amherst.acdc.trellis.api.Session;
import org.apache.commons.rdf.api.IRI;

/**
 * @author acoburn
 */
public interface AccessControlService {

    /**
     * Test whether the resource is readable
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can read the identified resource
     */
    Boolean canRead(final Session session, final IRI identifier);

    /**
     * Test whether the resource is writeable
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can write to identified resource
     */
    Boolean canWrite(final Session session, final IRI identifier);

    /**
     * Test whether the user can control the ACL for the given resource
     * @param session the user session
     * @param identifier the resource identifier
     * @return whether the user can control the ACL for the identified resource
     */
    Boolean canControl(final Session session, final IRI identifier);

    /**
     * Find the effective ACL for the given resource identifier
     * @param identifier the resource identifier
     * @return the ACL identifier
     */
    Optional<IRI> findAclFor(final IRI identifier);
}
