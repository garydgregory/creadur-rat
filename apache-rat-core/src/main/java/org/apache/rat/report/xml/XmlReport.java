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
package org.apache.rat.report.xml;

import java.io.IOException;

import org.apache.rat.api.Document;
import org.apache.rat.api.RatException;
import org.apache.rat.api.Reporter;
import org.apache.rat.document.IDocumentAnalyser;
import org.apache.rat.document.RatDocumentAnalysisException;
import org.apache.rat.report.RatReport;
import org.apache.rat.report.xml.writer.IXmlWriter;

class XmlReport implements RatReport {
   
    private final IDocumentAnalyser analyser;
    private final IXmlWriter writer;
    private final Reporter reporter;
    
    public XmlReport(final IXmlWriter writer, IDocumentAnalyser analyser, final Reporter reporter) {
        this.analyser = analyser;
        this.writer = writer;
        this.reporter = reporter;
    }

    public void startReport() throws RatException {
        try {
            writer.openElement("rat-report");
        } catch (IOException e) {
            throw new RatException("Cannot open start element", e);
        }
    }

    public void endReport() throws RatException {
        try {
            writer.closeDocument();
        } catch (IOException e) {
            throw new RatException("Cannot close last element", e);
        }
    }

    /**
     * Report on a document. This will result in the document being passed through
     * the document analyser with descriptive elements added to the analysis report.
     * 
     * @param document the document to report on.
     */
    public void report(Document subject) throws RatException {
        try {
            analyser.analyse(subject);
            reporter.report(subject);
        } catch (RatDocumentAnalysisException e) {
            throw new RatException("Analysis failed", e);
        }
    }    
}
