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

import org.junit.Test;

/**
 * @author acoburn
 */
public class IdentifierServiceTest {

    @Test
    public void testConfig() {
        final Integer zero = 0;
        final IdentifierService.IdentifierConfiguration config = new IdentifierService.IdentifierConfiguration();
        assertEquals("", config.getPrefix());
        assertEquals(zero, config.getHierarchy());
        assertEquals(zero, config.getLength());
    }

    @Test
    public void testConfig2() {
        final Integer hierarchy = 4;
        final Integer length = 2;
        final String prefix = "foo:";
        final IdentifierService.IdentifierConfiguration config = new IdentifierService.IdentifierConfiguration(
                prefix, hierarchy, length);
        assertEquals(prefix, config.getPrefix());
        assertEquals(hierarchy, config.getHierarchy());
        assertEquals(length, config.getLength());
    }
}
