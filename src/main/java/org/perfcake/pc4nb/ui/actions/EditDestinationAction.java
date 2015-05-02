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
import javax.swing.JTable;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.core.model.DestinationModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.tableModel.DestinationsTableModel;
import org.perfcake.pc4nb.wizards.DestinationWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel;
import static org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel.DESTINATION_PACKAGE;
import org.perfcake.pc4nb.wizards.visuals.ReporterVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditDestinationAction extends AbstractPC4NBAction {

    private DestinationsTableModel destinationsTableModel;
    int selectedRow = 0;
    ReporterVisualPanel sourcePanel;

    public EditDestinationAction(AbstractPC4NBVisualPanel source) {
        super(source);

        sourcePanel = (ReporterVisualPanel) getSource();
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        destinationsTableModel = sourcePanel.getDestinationsTableModel();
        Destination selectedDestination = destinationsTableModel.getDestinations().get(selectedRow);
        
        DestinationWizardPanel wizardPanel = new DestinationWizardPanel();

        JTable destinationsTable = sourcePanel.getDestinationsTable();
        if (destinationsTable.getSelectedRowCount() > -1) {
            selectedRow = destinationsTable.getSelectedRow();
        } else {
            // throw some exception that doesn't start the action
        }

        DestinationVisualPanel sourceVisualPanel = wizardPanel.getComponent();
        sourceVisualPanel.getDestinationSelection().setSelectedItem(selectedDestination.getClazz());
        sourceVisualPanel.getEnabledCheckBox().setSelected(selectedDestination.isEnabled());

        for (Period period : selectedDestination.getPeriod()) {
            sourceVisualPanel.getPeriodsTableModel().addRow(period);
        }
        
        Properties properties = new Properties();

        for (Property property : selectedDestination.getProperty()) {
            properties.put(property.getName(), property.getValue());
        }

        try {
            wizardPanel.getComponent().putToComponentPropertiesMap(selectedDestination.getClazz(), properties);
            wizardPanel.getComponent().listProperties(selectedDestination.getClazz());

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        DestinationModel destinationModel = new DestinationModel(new Destination());
        destinationModel.setClazz((String) wiz.getProperty("destination-type"));
        destinationModel.setEnabled((boolean) wiz.getProperty("destination-enabled"));
        
        Properties properties = (Properties) wiz.getProperty("destination-properties");
        
        Properties defaultValues = new Properties();
        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(DESTINATION_PACKAGE + "." + wiz.getProperty("destination-type")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        destinationModel.getProperty().clear();
        
        for (Object propertyName : properties.keySet()) {

            if (!properties.get(propertyName).equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName.toString());
                newProperty.setValue(properties.get(propertyName).toString());
                
                destinationModel.addProperty(newProperty);
            }
        }
        
        destinationModel.getDestination().getPeriod().clear();
        
        List<Period> periods = (List<Period>) wiz.getProperty("destination-periods");

        for (Period period : periods) {
            destinationModel.addPeriod(period);
        }
        
        ModelMap.getDefault().addEntry(destinationModel.getDestination(), destinationModel);

        ReporterVisualPanel sourcePanel = (ReporterVisualPanel) getSource();
        sourcePanel.getDestinationsTableModel().updateRow(selectedRow, destinationModel.getDestination());
    }

}
