/*
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
import javax.swing.JTable;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PeriodModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;
import org.perfcake.pc4nb.ui.actions.AddPeriodAction;
import org.perfcake.pc4nb.ui.actions.DeletePeriodAction;
import org.perfcake.pc4nb.ui.actions.EditPeriodAction;
import org.perfcake.pc4nb.ui.tableModel.PeriodsTableModel;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;
import org.perfcake.reporting.destinations.Destination;

public final class DestinationVisualPanel extends VisualPanelWithProperties {

    public static final String DESTINATION_PACKAGE = "org.perfcake.reporting.destinations";

    public DestinationVisualPanel() {
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends Destination>> subTypes = scanner.findComponentsOfType(Destination.class, DESTINATION_PACKAGE);

        Set<String> components = new HashSet<>();

        for (Class<? extends Destination> reporter : subTypes) {
            components.add(reporter.getSimpleName());
        }

        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(DESTINATION_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        initComponents();
        setModel(new DestinationModel(new Scenario.Reporting.Reporter.Destination()));
        
        addPeriodButton.addActionListener(new AddPeriodListener());
        editPeriodButton.addActionListener(new EditPeriodListener());
        deletePeriodButton.addActionListener(new DeletePeriodListener());

        try {
            listProperties((String) destinationSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
        
        propertiesTable.setDefaultRenderer(String.class, new PropertiesTableCellRenderer());
    }

    @Override
    public String getName() {
        return "Destination";
    }

    public JComboBox getDestinationSelection() {
        return destinationSelection;
    }

    public JCheckBox getEnabledCheckBox() {
        return enabledCheckBox;
    }

    public PeriodsTableModel getPeriodsTableModel() {
        return periodsTableModel;
    }

    public JTable getPeriodsTable() {
        return periodsTable;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        Scenario.Reporting.Reporter.Destination destination = ((DestinationModel) model).getDestination();

        String destinationClazz = destination.getClazz();
        List<PropertyModel> properties = new ArrayList<>(getPropertiesFor(destinationClazz));
        
        for (Property property : destination.getProperty()) {
            for (PropertyModel defaultProperty : properties) {
                if (defaultProperty.getName().equals(property.getName())) {
                    defaultProperty.setValue(property.getValue());
                }
            }
        }
        
        try {
            if (destinationClazz != null) {
                destinationSelection.setSelectedItem(destinationClazz);
                putToComponentPropertiesMap(destinationClazz, properties);
                listProperties(destinationClazz);
            }

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        for (Period period : destination.getPeriod()) {
            periodsTableModel.addRow(period);
        }
        
        if (destination.isEnabled()) {
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

        destinationTypeLabel = new javax.swing.JLabel();
        destinationSelection = new javax.swing.JComboBox();
        enabledLabel = new javax.swing.JLabel();
        enabledCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        periodsTable = new javax.swing.JTable();
        addPeriodButton = new javax.swing.JButton();
        editPeriodButton = new javax.swing.JButton();
        deletePeriodButton = new javax.swing.JButton();
        periodsLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(destinationTypeLabel, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.destinationTypeLabel.text")); // NOI18N

        Set<String> componentNames = getComponentPropertiesModelMap().keySet();
        String[] componentNamesArray = new String[componentNames.size()];
        componentNames.toArray(componentNamesArray);
        destinationSelection.setModel(new DefaultComboBoxModel(componentNamesArray));
        destinationSelection.addItemListener (new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) destinationSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    System.err.println("Class not found " + ex.getMessage());
                }
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(enabledLabel, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.enabledLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enabledCheckBox, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.enabledCheckBox.text")); // NOI18N

        setPropertiesTableModel(new PropertiesTableModel());
        propertiesTable.setModel(getPropertiesTableModel());
        jScrollPane1.setViewportView(propertiesTable);

        periodsTableModel = new PeriodsTableModel();
        periodsTable.setModel(periodsTableModel);
        periodsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(periodsTable);

        org.openide.awt.Mnemonics.setLocalizedText(addPeriodButton, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.addPeriodButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editPeriodButton, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.editPeriodButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deletePeriodButton, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.deletePeriodButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(periodsLabel, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.periodsLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(DestinationVisualPanel.class, "DestinationVisualPanel.propertiesLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(propertiesLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(periodsLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(destinationTypeLabel)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(enabledLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(enabledCheckBox))
                                .addComponent(destinationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2)))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addPeriodButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editPeriodButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deletePeriodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(destinationTypeLabel)
                .addGap(18, 18, 18)
                .addComponent(destinationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enabledCheckBox)
                    .addComponent(enabledLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(periodsLabel)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addPeriodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editPeriodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deletePeriodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(propertiesLabel)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPeriodButton;
    private javax.swing.JButton deletePeriodButton;
    private javax.swing.JComboBox destinationSelection;
    private javax.swing.JLabel destinationTypeLabel;
    private javax.swing.JButton editPeriodButton;
    private javax.swing.JCheckBox enabledCheckBox;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel periodsLabel;
    private javax.swing.JTable periodsTable;
    private PeriodsTableModel periodsTableModel;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DestinationModel model = (DestinationModel) getModel();
        List<Period> periodsList = model.getDestination().getPeriod();
        int targetIndex;

        switch (evt.getPropertyName()) {
            case DestinationModel.PROPERTY_PERIOD:
                if (evt.getNewValue() != null) {
                    targetIndex = periodsList.indexOf(evt.getNewValue());
                    periodsTableModel.insertRow(targetIndex, (Period) evt.getNewValue());
                } else if (evt.getOldValue() != null) {
                    targetIndex = periodsTableModel.getPeriods().indexOf(evt.getOldValue());
                    periodsTableModel.removeRow(targetIndex);
                } else {
                    // error
                }
                break;
            case DestinationModel.PROPERTY_CLASS:
            case DestinationModel.PROPERTY_ENABLED:
                PeriodModel periodModel = (PeriodModel) evt.getSource();
                Period period = periodModel.getPeriod();
                targetIndex = periodsTableModel.getPeriods().indexOf(period);
                periodsTableModel.updateRow(targetIndex, period);
                break;
            default:
                break;
        }

    }
    
    private class AddPeriodListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddPeriodAction action = new AddPeriodAction(getModel());
            action.execute();
        }
    }

    private class EditPeriodListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = DestinationVisualPanel.this.getPeriodsTable().getSelectedRow();

            if (selectedRow != -1) {
                DestinationModel destinationModel = (DestinationModel) DestinationVisualPanel.this.getModel();
                Period period = destinationModel.getDestination().getPeriod().get(selectedRow);
                EditPeriodAction action = new EditPeriodAction((PeriodModel) ModelMap.getDefault().getPC4NBModelFor(period));
                action.execute();
            }
        }
    }

    private class DeletePeriodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = DestinationVisualPanel.this.getPeriodsTable().getSelectedRows();
            DestinationModel destinationModel = (DestinationModel) DestinationVisualPanel.this.getModel();
            List<Period> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Period period = destinationModel.getDestination().getPeriod().get(selectedRows[i]);
                toRemove.add(period);
            }

            DeletePeriodAction action = new DeletePeriodAction(getModel(), toRemove);
            action.execute();
        }
    }
}
