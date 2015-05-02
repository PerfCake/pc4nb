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
package org.perfcake.pc4nb.ui.actions;

import java.util.List;
import java.util.Properties;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.pc4nb.core.controller.ReporterController;
import org.perfcake.pc4nb.core.controller.ReportingController;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.ReporterModel;
import org.perfcake.pc4nb.core.model.ReportingModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.SecondLevelView;
import org.perfcake.pc4nb.wizards.ReporterWizardPanel;
import static org.perfcake.pc4nb.wizards.visuals.ReporterVisualPanel.REPORTER_PACKAGE;
import org.perfcake.pc4nb.wizards.visuals.ReportingVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddReporterAction extends AbstractPC4NBAction {
    private ReporterWizardPanel wizardPanel;

    public AddReporterAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new ReporterWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        ReporterModel reporterModel = new ReporterModel(new Reporter());
        reporterModel.setClazz((String) wiz.getProperty("reporter-clazz"));
        reporterModel.setEnabled((boolean) wiz.getProperty("reporter-enabled"));

        List<Property> properties = (List<Property>) wiz.getProperty("reporter-properties");

        Properties defaultValues = new Properties();

        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(REPORTER_PACKAGE + "." + wiz.getProperty("reporter-clazz")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        for (Property property : properties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();

            if (!propertyValue.equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName);
                newProperty.setValue(propertyValue);

                reporterModel.addProperty(newProperty);
            }
        }

        List<Destination> destinations = (List<Destination>) wiz.getProperty("reporter-destinations");

        for (Destination destination : destinations) {
            reporterModel.addDestination(destination);
        }

        ReportingVisualPanel sourcePanel = (ReportingVisualPanel) getSource();
        ReporterController reporterController = new ReporterController(reporterModel,wizardPanel.getComponent() , new SecondLevelView(reporterModel.getClass().getSimpleName()));
        wizardPanel.getComponent().setController(reporterController);
        
        ReportingController reportingController = (ReportingController) sourcePanel.getController();
        ReportingModel reportingModel = (ReportingModel) reportingController.getModel();
        reportingModel.addReporter(reporterModel.getReporter());
        ModelMap.getDefault().addEntry(reporterModel.getReporter(), reporterModel);
    }
}
