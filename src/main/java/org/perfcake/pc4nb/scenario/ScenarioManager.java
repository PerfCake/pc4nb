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
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.perfcake.PerfCakeException;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.ScenarioModel;
import org.perfcake.scenario.ScenarioLoader;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrej Halaj
 */
public class ScenarioManager {
    public static final String SCHEMA_PATH = "perfcake-scenario-4.0.xsd";
    private static final Logger log = Logger.getLogger(ScenarioManager.class.getName());

    public void runScenario(URL scenarioURL) throws ScenarioException, PerfCakeException {

        if (scenarioURL == null) {
            log.error("URL to scenario is null");
            throw new IllegalArgumentException("URL to scenario is null.");
        }

        org.perfcake.scenario.Scenario scenario = ScenarioLoader.load(scenarioURL.toExternalForm());

        try {
            scenario.init();
            scenario.run();
        } catch (PerfCakeException e) {
            log.error("Error during scenario execution.", e);
            throw new ScenarioException("Error during scenario execution.", e);
        }

        try {
            scenario.close();
        } catch (PerfCakeException e) {
            log.error("Error during finishing scenario.", e);
            throw new ScenarioException("Error during finishing scenario.", e);
        }
    }

    public void stopScenario(org.perfcake.scenario.Scenario scenario) {
        if (scenario != null) {
            scenario.stop();
        } else {
            log.warn("Trying to stop null scenario");
        }
    }

    public void createXML(Scenario scenarioModel, OutputStream out) throws ScenarioException, ScenarioManagerException {
        if (scenarioModel == null) {
            String message = "scenario model is null. please use setModel() to set model.";
            log.warn(message);
            throw new ScenarioManagerException(message);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL schemaUrl = this.getClass().getResource(SCHEMA_PATH);

            Schema schema = schemaFactory.newSchema(schemaUrl);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(scenarioModel, out);
        } catch (JAXBException ex) {
            String message = "JAXB error";
            log.error(message, ex);
            throw new ScenarioException(message, ex);
        } catch (SAXException ex) {
            String message = "Cannot obtain schema definition.";
            log.error(message, ex);
            throw new ScenarioException(message, ex);
        }
    }

    public ScenarioModel createModel(URL scenarioURL) throws ScenarioException {
        Scenario scenario = new Scenario();

        if (scenarioURL == null) {
            log.error("URL to scenario is null");
            throw new IllegalArgumentException("URL to scenario is null.");
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL schemaUrl = this.getClass().getResource(SCHEMA_PATH);

            Schema schema = schemaFactory.newSchema(schemaUrl);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            unmarshaller.setSchema(schema);
            scenario = (Scenario) unmarshaller.unmarshal(scenarioURL);
        } catch (JAXBException ex) {
            String message = "JAXB error";
            log.error(message, ex);
            throw new ScenarioException(message, ex);
        } catch (SAXException ex) {
            String message = "Cannot obtain schema definition.";
            log.error(message, ex);
            throw new ScenarioException(message, ex);
        }

        ModelMap.getDefault().createModelAndAddEntry(scenario);

        return (ScenarioModel) ModelMap.getDefault().getPC4NBModelFor(scenario);
    }
}
