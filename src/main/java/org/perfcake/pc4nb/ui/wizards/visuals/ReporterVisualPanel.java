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
package org.perfcake.pc4nb.ui.wizards.visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;
import org.perfcake.pc4nb.ui.actions.AddDestinationAction;
import org.perfcake.pc4nb.ui.actions.DeleteDestinationAction;
import org.perfcake.pc4nb.ui.actions.EditDestinationAction;
import org.perfcake.pc4nb.ui.tableModel.DestinationsTableModel;
import org.perfcake.reporting.reporters.*;

public final class ReporterVisualPanel extends VisualPanelWithProperties {

    public static final String REPORTER_PACKAGE = "org.perfcake.reporting.reporters";

    public ReporterVisualPanel() {
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends Reporter>> subTypes = scanner.findComponentsOfType(Reporter.class, REPORTER_PACKAGE);

        Set<String> components = new HashSet<>();

        for (Class<? extends Reporter> reporter : subTypes) {
            components.add(reporter.getSimpleName());
        }

        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(REPORTER_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        initComponents();
        setModel(new ReporterModel(new Scenario.Reporting.Reporter()));

        for (Destination destination : ((ReporterModel) getModel()).getReporter().getDestination()) {
            ModelMap.getDefault().getPC4NBModelFor(destination).addPropertyChangeListener(this);
        }

        addDestinationButton.addActionListener(new AddDestinationListener());
        editDestinationButton.addActionListener(new EditDestinationListener());
        deleteDestinationButton.addActionListener(new DeleteDestinationListener());

        try {
            listProperties((String) reporterSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }

        propertiesTable.setDefaultRenderer(String.class, new PropertiesTableCellRenderer());
    }

    @Override
    public String getName() {
        return "Reporter Wizard";
    }

    public JComboBox getReporterSelection() {
        return reporterSelection;
    }

    public JScrollPane getjScrollPane1() {
        return propertiesTableScrollPane;
    }

    public JLabel getPropertiesLabel() {
        return propertiesLabel;
    }

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    public JTable getDestinationsTable() {
        return destinationsTable;
    }

    public DestinationsTableModel getDestinationsTableModel() {
        return destinationsTableModel;
    }

    public JCheckBox getEnabledCheckBox() {
        return enabledCheckBox;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        Scenario.Reporting.Reporter reporter = ((ReporterModel) model).getReporter();

        String reporterClazz = reporter.getClazz();
        List<PropertyModel> properties = new ArrayList<>(getPropertiesFor(reporterClazz));
        
        for (Property property : reporter.getProperty()) {
            for (PropertyModel defaultProperty : properties) {
                if (defaultProperty.getName().equals(property.getName())) {
                    defaultProperty.setValue(property.getValue());
                }
            }
        }

        try {
            if (reporterClazz != null) {
                getReporterSelection().setSelectedItem(reporterClazz);
                putToComponentPropertiesMap(reporterClazz, properties);
                listProperties(reporterClazz);
            }

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        for (Destination destination : reporter.getDestination()) {
            getDestinationsTableModel().addRow(destination);
        }

        if (reporter.isEnabled()) {
            enabledCheckBox.setSelected(true);
        } else {
            enabledCheckBox.setSelected(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reporterSelection = new javax.swing.JComboBox();
        reporterTypeLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();
        propertiesTableScrollPane = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        destinationsLabel = new javax.swing.JLabel();
        addDestinationButton = new javax.swing.JButton();
        deleteDestinationButton = new javax.swing.JButton();
        editDestinationButton = new javax.swing.JButton();
        enabledLabel = new javax.swing.JLabel();
        enabledCheckBox = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        destinationsTable = new javax.swing.JTable();

        Set<String> componentNames = getComponentPropertiesModelMap().keySet();
        String[] componentNamesArray = new String[componentNames.size()];
        componentNames.toArray(componentNamesArray);
        reporterSelection.setModel(new DefaultComboBoxModel(componentNamesArray));
        reporterSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) reporterSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    System.err.println("Class not found " + ex.getMessage());
                }
            }
        });
        reporterSelection.setName("reporterSelection"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(reporterTypeLabel, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.reporterTypeLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.propertiesLabel.text")); // NOI18N

        propertiesTableScrollPane.setPreferredSize(new java.awt.Dimension(475, 402));

        propertiesTable.setModel(getPropertiesTableModel());
        propertiesTable.getTableHeader().setReorderingAllowed(false);
        propertiesTableScrollPane.setViewportView(propertiesTable);

        org.openide.awt.Mnemonics.setLocalizedText(destinationsLabel, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.destinationsLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addDestinationButton, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.addDestinationButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteDestinationButton, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.deleteDestinationButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editDestinationButton, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.editDestinationButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enabledLabel, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.enabledLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enabledCheckBox, org.openide.util.NbBundle.getMessage(ReporterVisualPanel.class, "ReporterVisualPanel.enabledCheckBox.text")); // NOI18N

        destinationsTableModel = new DestinationsTableModel();
        destinationsTable.setModel(destinationsTableModel);
        jScrollPane2.setViewportView(destinationsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(destinationsLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(enabledLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(enabledCheckBox))
                            .addComponent(propertiesLabel)
                            .addComponent(reporterTypeLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(reporterSelection, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(propertiesTableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addDestinationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteDestinationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(editDestinationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(reporterTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reporterSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(enabledCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enabledLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(propertiesLabel)
                .addGap(12, 12, 12)
                .addComponent(propertiesTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(destinationsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addDestinationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDestinationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteDestinationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDestinationButton;
    private javax.swing.JButton deleteDestinationButton;
    private javax.swing.JLabel destinationsLabel;
    private javax.swing.JTable destinationsTable;
    private DestinationsTableModel destinationsTableModel;
    private javax.swing.JButton editDestinationButton;
    private javax.swing.JCheckBox enabledCheckBox;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JScrollPane propertiesTableScrollPane;
    private javax.swing.JComboBox reporterSelection;
    private javax.swing.JLabel reporterTypeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ReporterModel model = (ReporterModel) getModel();
        List<Destination> destinationsList = model.getReporter().getDestination();
        int targetIndex;

        switch (evt.getPropertyName()) {
            case ReporterModel.PROPERTY_DESTINATIONS:
                if (evt.getNewValue() != null) {
                    targetIndex = destinationsList.indexOf(evt.getNewValue());
                    destinationsTableModel.insertRow(targetIndex, (Destination) evt.getNewValue());
                } else if (evt.getOldValue() != null) {
                    targetIndex = destinationsTableModel.getDestinations().indexOf(evt.getOldValue());
                    destinationsTableModel.removeRow(targetIndex);
                } else {
                    // error
                }
                break;
            case DestinationModel.PROPERTY_CLASS:
            case DestinationModel.PROPERTY_ENABLED:
                DestinationModel destinationModel = (DestinationModel) evt.getSource();
                Destination destination = destinationModel.getDestination();
                targetIndex = destinationsTableModel.getDestinations().indexOf(destination);
                destinationsTableModel.updateRow(targetIndex, destination);
                break;
            default:
                break;
        }
    }

    private class AddDestinationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddDestinationAction action = new AddDestinationAction(getModel());
            action.execute();
        }
    }

    private class EditDestinationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ReporterVisualPanel.this.getDestinationsTable().getSelectedRow();

            if (selectedRow != -1) {
                ReporterModel reporterModel = (ReporterModel) ReporterVisualPanel.this.getModel();
                Destination destination = reporterModel.getReporter().getDestination().get(selectedRow);
                EditDestinationAction action = new EditDestinationAction((DestinationModel) ModelMap.getDefault().getPC4NBModelFor(destination));
                action.execute();
            }
        }
    }

    private class DeleteDestinationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = ReporterVisualPanel.this.getDestinationsTable().getSelectedRows();
            ReporterModel reporterModel = (ReporterModel) ReporterVisualPanel.this.getModel();
            List<Destination> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Destination destination = reporterModel.getReporter().getDestination().get(selectedRows[i]);
                toRemove.add(destination);
            }

            DeleteDestinationAction action = new DeleteDestinationAction(getModel(), toRemove);
            action.execute();
        }
    }
}
