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

import java.util.function.Supplier;

/**
 * The IdentifierService provides a mechanism for creating new identifiers.
 *
 * @author acoburn
 */
public interface IdentifierService {

    /**
     * A configuration object for use with an IdentiferService
     */
    public static class IdentifierConfiguration {

        private final String prefix;
        private final Integer hierarchy;
        private final Integer length;

        /**
         * Configuration for an identifier supplier
         */
        public IdentifierConfiguration() {
            this("", 0, 0);
        }

        /**
         * Configuration for an identifier supplier
         * @param prefix a prefix
         * @param hierarchy levels of hierarchy
         * @param length the length of any hierarchy segment
         */
        public IdentifierConfiguration(final String prefix, final Integer hierarchy, final Integer length) {
            this.prefix = prefix;
            this.hierarchy = hierarchy;
            this.length = length;
        }

        /**
         * Get the prefix
         * @return the prefix
         */
        public String getPrefix() {
            return prefix;
        }

        /**
         * Get the levels of hierarchy
         * @return the levels of hierarchy
         */
        public Integer getHierarchy() {
            return hierarchy;
        }

        /**
         * Get the length of each hierarchy segment
         * @return the length of each segment
         */
        public Integer getLength() {
            return length;
        }
    }

    /**
     * Get a Supplier that generates Strings with the provided prefix
     * @param prefix the prefix
     * @param hierarchy the levels of hierarchy to add
     * @param length the length of each level of hierarchy
     * @return a String Supplier
     */
    Supplier<String> getSupplier(String prefix, Integer hierarchy, Integer length);

    /**
     * Get a Supplier that generates Strings with the provided prefix
     * @param prefix the prefix
     * @return a String Supplier
     */
    Supplier<String> getSupplier(String prefix);

    /**
     * Get a Supplier that generates Strings with the provided prefix
     * @return a String Supplier
     */
    Supplier<String> getSupplier();
}
