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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.core.model.DestinationModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.wizards.DestinationWizardPanel;
import static org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel.DESTINATION_PACKAGE;
import org.perfcake.pc4nb.wizards.visuals.ReporterVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddDestinationAction extends AbstractPC4NBAction {

    public AddDestinationAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        DestinationWizardPanel wizardPanel = new DestinationWizardPanel();
        
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        DestinationModel destinationModel = new DestinationModel(new Destination());
        destinationModel.setClazz((String) wiz.getProperty("destination-type"));
        destinationModel.setEnabled((boolean) wiz.getProperty("destination-enabled"));
        
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
                
                destinationModel.addProperty(newProperty);
            }
        }

        List<Period> periods = (List<Period>) wiz.getProperty("destination-periods");

        for (Destination.Period period : periods) {
            destinationModel.addPeriod(period);
        }
        
        ModelMap.getDefault().addEntry(destinationModel.getDestination(), destinationModel);
        
        ReporterVisualPanel sourcePanel = (ReporterVisualPanel) getSource();
        sourcePanel.getDestinationsTableModel().addRow(destinationModel.getDestination());
    }  
}
