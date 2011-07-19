 /*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package org.apache.rat.anttasks;

import java.util.ArrayList;
import java.util.List;
import org.apache.rat.analysis.license.SimplePatternBasedLicense;

/**
 * Adapts {@link SimplePatternBasedLicense SimplePatternBasedLicense}
 * to Ant's method naming conventions so it becomes easy to write
 * substring based license matchers inside an Ant build file.
 */
public class SubstringLicenseMatcher extends SimplePatternBasedLicense {
    private List/*<String>*/ patterns = new ArrayList();

    public void addConfiguredPattern(Pattern p) {
        patterns.add(p.getSubstring());
    }

    // @Override
    public String[] getPatterns() {
        return (String[]) patterns.toArray(new String[0]);
    }

    /**
     * A simple wrapper around a substring.
     */
    public static class Pattern {
        private String substring;
        public void setSubstring(String s) { substring = s; }
        public String getSubstring() { return substring; }
    }
}
