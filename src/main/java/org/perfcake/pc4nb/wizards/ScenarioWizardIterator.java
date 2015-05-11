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
package org.perfcake.pc4nb.wizards;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.model.Scenario.Sender;
import org.perfcake.pc4nb.core.model.GeneratorModel;
import org.perfcake.pc4nb.core.model.MessagesModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.ReportingModel;
import org.perfcake.pc4nb.core.model.ScenarioModel;
import org.perfcake.pc4nb.core.model.SenderModel;
import org.perfcake.pc4nb.core.model.ValidationModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.scenario.ScenarioException;
import org.perfcake.pc4nb.scenario.ScenarioManager;
import org.perfcake.pc4nb.scenario.ScenarioManagerException;
import static org.perfcake.pc4nb.wizards.visuals.GeneratorVisualPanel.GENERATOR_PACKAGE;
import static org.perfcake.pc4nb.wizards.visuals.SenderVisualPanel.SENDER_PACKAGE;

@TemplateRegistration(folder = "PerfCake", displayName = "#ScenarioWizardIterator_displayName", iconBase = "org/perfcake/pc4nb/favicon.png", description = "scenario.html")
@Messages("ScenarioWizardIterator_displayName=Scenario")
public final class ScenarioWizardIterator implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {

    private static final Logger log = LogManager.getLogger(ScenarioWizardIterator.class.getName());

    private int index;
    private ScenarioModel scenarioModel;

    private WizardDescriptor wizard;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        if (panels == null) {
            panels = new ArrayList<>();
            panels.add(new ScenarioWizardPanel());
            panels.add(new GeneratorWizardPanel());
            panels.add(new SenderWizardPanel());
            panels.add(new ReportingWizardPanel());
            panels.add(new ValidationWizardPanel());
            panels.add(new MessagesWizardPanel());
            panels.add(new ScenarioPropertiesWizardPanel());
            String[] steps = createSteps();
            for (int i = 0; i < panels.size(); i++) {
                Component c = panels.get(i).getComponent();
                if (steps[i] == null) {
                    // Default step name to component name of panel. Mainly
                    // useful for getting the name of the target chooser to
                    // appear in the list of steps.
                    steps[i] = c.getName();
                }
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
        }
        return panels;
    }

    @Override
    public Set<?> instantiate() throws IOException {
        boolean finished = (wizard.getValue() == WizardDescriptor.FINISH_OPTION);

        if (finished) {
            ScenarioManager manager = new ScenarioManager();
            prepareScenario();
            
            ModelMap.getDefault().addEntry(scenarioModel.getScenario(), scenarioModel);

            Project project = (Project) wizard.getProperty("scenario-project");
            URI scenarioPath = Utilities.toURI(new File(project.getProjectDirectory().getPath() + File.separator + "scenarios"
                    + File.separator + wizard.getProperty("scenario-name") + ".xml"));

            OutputStream os = new FileOutputStream(Utilities.toFile(scenarioPath));

            try {
                manager.createXML(scenarioModel.getScenario(), os);
            } catch (ScenarioException | ScenarioManagerException ex) {
                log.warn(ex.getMessage(), ex.getCause());
            }
        }

        return Collections.emptySet();
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    private String[] createSteps() {
        String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
        assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
        String[] res = new String[(beforeSteps.length - 1) + panels.size()];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
            }
        }
        return res;
    }

    private void prepareScenario() {
        try {
            scenarioModel = new ScenarioModel(new Scenario());
            prepareGenerator();
            prepareSender();
            prepareReporting();
            prepareMessages();
            prepareValidation();

            ModelMap.getDefault().addEntry(scenarioModel.getScenario(), scenarioModel);
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    private void prepareGenerator() throws ClassNotFoundException {
        GeneratorModel generatorModel = new GeneratorModel(new Generator());
        List<Property> properties = (List<Property>) wizard.getProperty("generator-properties");

        generatorModel.setClazz((String) wizard.getProperty("generator-type"));

        ComponentPropertiesScanner propertiesScanner = new ComponentPropertiesScanner();
        Properties defaultProperties = propertiesScanner.getPropertiesOfComponent(Class.forName(GENERATOR_PACKAGE + "." + wizard.getProperty("generator-type")));

        Run generatorRun = new Run();
        generatorRun.setType((String) wizard.getProperty("generator-period"));

        for (Property property : properties) {
            switch (property.getName()) {
                case "monitoringPeriod":
                    generatorRun.setValue(property.getValue());
                    break;
                case "threads":
                    generatorModel.setThreads(property.getValue());
                    break;
                default:
                    if (!defaultProperties.get(property.getName()).equals(property.getValue())) {
                        generatorModel.addProperty(generatorModel.getProperty().size(), property);
                    }
                    break;
            }
        }

        generatorModel.setRun(generatorRun);
        ModelMap.getDefault().addEntry(generatorModel.getGenerator(), generatorModel);

        scenarioModel.setGenerator(generatorModel.getGenerator());
    }

    private void prepareSender() throws ClassNotFoundException {
        SenderModel senderModel = new SenderModel(new Sender());
        List<Property> properties = (List<Property>) wizard.getProperty("sender-properties");

        senderModel.setClazz((String) wizard.getProperty("sender-type"));

        ComponentPropertiesScanner propertiesScanner = new ComponentPropertiesScanner();
        Properties defaultProperties = propertiesScanner.getPropertiesOfComponent(Class.forName(SENDER_PACKAGE + "." + wizard.getProperty("sender-type")));

        for (Property property : properties) {
            if (!defaultProperties.get(property.getName()).equals(property.getValue())) {
                senderModel.addProperty(senderModel.getProperty().size(), property);
            }
        }

        ModelMap.getDefault().addEntry(senderModel.getSender(), senderModel);

        scenarioModel.setSender(senderModel.getSender());
    }

    private void prepareReporting() {
        ReportingModel reportingModel = (ReportingModel) wizard.getProperty("reporting-model");

        ModelMap.getDefault().addEntry(reportingModel.getReporting(), reportingModel);

        if (reportingModel.getReporting().getReporter().isEmpty()) {
            scenarioModel.setReporting(null);
        } else {
            scenarioModel.setReporting(reportingModel.getReporting());
        }
    }

    private void prepareMessages() {
        MessagesModel messagesModel = (MessagesModel) wizard.getProperty("messages-model");

        ModelMap.getDefault().addEntry(messagesModel.getMessages(), messagesModel);

        if (messagesModel.getMessages().getMessage().isEmpty()) {
            scenarioModel.setMessages(null);
        } else {
            scenarioModel.setMessages(messagesModel.getMessages());
        }
    }

    private void prepareValidation() {
        ValidationModel validationModel = (ValidationModel) wizard.getProperty("validation-model");

        ModelMap.getDefault().addEntry(validationModel.getValidation(), validationModel);

        if (validationModel.getValidation().getValidator().isEmpty()) {
            scenarioModel.setValidation(null);
        } else {
            scenarioModel.setValidation(validationModel.getValidation());
        }
    }
}
