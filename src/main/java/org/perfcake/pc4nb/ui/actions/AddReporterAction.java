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

import java.util.ArrayList;
import java.util.List;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.ReportingModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.wizards.ReporterWizardPanel;
import static org.perfcake.pc4nb.ui.wizards.visuals.ReporterVisualPanel.REPORTER_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
public class AddReporterAction extends AbstractPC4NBAction {
    private PC4NBModel to;
    
    public AddReporterAction(PC4NBModel to) {
        this.to = to;
    }
    
    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        ReporterWizardPanel wizardPanel = new ReporterWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        Reporter reporter = new Reporter();
        reporter.setClazz((String) wiz.getProperty("reporter-clazz"));
        reporter.setEnabled((boolean) wiz.getProperty("reporter-enabled"));

        List<PropertyModel> properties = (List<PropertyModel>) wiz.getProperty("reporter-properties");

        for (PropertyModel propertyModel : properties) {
            if (!propertyModel.isDefault()) {
                reporter.getProperty().add(propertyModel.getProperty());
            }
        }

        List<Destination> destinations = (List<Destination>) wiz.getProperty("reporter-destinations");

        for (Destination destination : destinations) {
            reporter.getDestination().add(destination);
        }

        ReportingModel reportingModel = (ReportingModel) to;
        reportingModel.addReporter(reporter);
    }
}
