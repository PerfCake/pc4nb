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
package org.perfcake.pc4nb.ui3.actions;

import java.util.List;
import java.util.Properties;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.core.model.ReporterModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.wizards.DestinationWizardPanel;
import static org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel.DESTINATION_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
public class AddDestinationAction extends AbstractPC4NBAction {
    private PC4NBModel to;

    public AddDestinationAction(PC4NBModel to) {
        this.to = to;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        DestinationWizardPanel wizardPanel = new DestinationWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        Destination destination = new Destination();
        destination.setClazz((String) wiz.getProperty("destination-type"));
        destination.setEnabled((boolean) wiz.getProperty("destination-enabled"));

        List<Property> properties = (List<Property>) wiz.getProperty("destination-properties");

        Properties defaultValues = new Properties();
        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(DESTINATION_PACKAGE + "." + wiz.getProperty("destination-type")));
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

                destination.getProperty().add(newProperty);
            }
        }

        List<Period> periods = (List<Period>) wiz.getProperty("destination-periods");

        for (Destination.Period period : periods) {
            destination.getPeriod().add(period);
        }

        ReporterModel reporterModel = (ReporterModel) to;
        reporterModel.addDestination(destination);
    }
}
