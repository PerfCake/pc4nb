/*
 * Copyright 2015 Andrej Halaj.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.perfcake.pc4nb.scenario;

import java.io.OutputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.openide.util.Exceptions;
import org.perfcake.PerfCakeException;
import org.perfcake.pc4nb.core.model.ScenarioModel;
import org.perfcake.scenario.Scenario;
import org.perfcake.scenario.ScenarioLoader;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrej Halaj
 */
public class ScenarioManager {

    public static final String SCHEMA_PATH = "perfcake-scenario-4.0.xsd";

    private static final Logger log = Logger.getLogger(ScenarioManager.class.getName());

    public void createXML(org.perfcake.model.Scenario scenarioModel, OutputStream out) throws ScenarioException, ScenarioManagerException {
        if (scenarioModel == null) {
            String message = "scenario model is null. please use setModel() to set model.";
            log.warn(message);
            throw new ScenarioManagerException(message);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(org.perfcake.model.Scenario.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL schemaUrl = this.getClass().getResource(SCHEMA_PATH);

            Schema schema = schemaFactory.newSchema(schemaUrl);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(scenarioModel, out);
        } catch (JAXBException e) {
            String message = "JAXB error";
            log.error(message, e);
            throw new ScenarioException(message, e);
        } catch (SAXException e) {
            String message = "Cannot obtain schema definition.";
            log.error(message, e);
            throw new ScenarioException(message, e);
        }
    }
}
