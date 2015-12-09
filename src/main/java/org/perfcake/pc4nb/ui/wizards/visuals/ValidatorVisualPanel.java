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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;
import org.perfcake.validation.MessageValidator;

public final class ValidatorVisualPanel extends VisualPanelWithProperties {

    public static final String VALIDATOR_PACKAGE = "org.perfcake.validation";

    public ValidatorVisualPanel() {
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends MessageValidator>> subTypes = scanner.findComponentsOfType(MessageValidator.class, VALIDATOR_PACKAGE);

        Set<String> components = new HashSet<>();

        for (Class<? extends MessageValidator> validator : subTypes) {
            components.add(validator.getSimpleName());
        }

        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(VALIDATOR_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        initComponents();
        setModel(new ValidatorModel(new Scenario.Validation.Validator()));

        try {
            listProperties((String) validatorSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
        
        propertiesTable.setDefaultRenderer(String.class, new PropertiesTableCellRenderer());
    }

    @Override
    public String getName() {
        return "Add Validator";
    }

    public JComboBox getValidatorSelection() {
        return validatorSelection;
    }

    public JScrollPane getjScrollPane1() {
        return propertiesListScrollPane;
    }

    public JLabel getPropertiesLabel() {
        return propertiesLabel;
    }

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    public JScrollPane getPropertiesListScrollPane() {
        return propertiesListScrollPane;
    }

    public JLabel getValidatorTypeLabel() {
        return validatorTypeLabel;
    }

    public JTextField getValidatorIdTextField() {
        return validatorIdTextField;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        Validator validator = ((ValidatorModel) model).getValidator();
        
        String id = validator.getId();
        if (id != null) {
            getValidatorIdTextField().setText(validator.getId());
        }
        String validatorClazz = validator.getClazz();
        
        List<PropertyModel> properties = new ArrayList<>(getPropertiesFor(validatorClazz));
        
        for (Property property : validator.getProperty()) {
            for (PropertyModel defaultProperty : properties) {
                if (defaultProperty.getName().equals(property.getName())) {
                    defaultProperty.setValue(property.getValue());
                }
            }
        }

        try {
            if (validatorClazz != null) {
                getValidatorSelection().setSelectedItem(validatorClazz);
                putToComponentPropertiesMap(validatorClazz, properties);
                listProperties(validatorClazz);
            }

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        validatorSelection = new javax.swing.JComboBox();
        propertiesListScrollPane = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        validatorIdTextField = new javax.swing.JTextField();
        validatorTypeLabel = new javax.swing.JLabel();
        validatorIdLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();

        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends MessageValidator>> subTypes = scanner.findComponentsOfType(MessageValidator.class, "org.perfcake.validation");
        Set<String> validatorNames = new HashSet<>();

        for(Class<? extends MessageValidator> validator : subTypes) {
            validatorNames.add(validator.getSimpleName());
        }

        String[] validatorNamesArray = new String[validatorNames.size()];
        validatorNames.toArray(validatorNamesArray);
        validatorSelection.setModel(new DefaultComboBoxModel(validatorNamesArray));
        validatorSelection.setSelectedItem (validatorNamesArray[0]);

        validatorSelection.addItemListener (new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) validatorSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    System.err.println("Class not found " + ex.getMessage());
                }
            }
        });
        validatorSelection.setName("validatorSelection"); // NOI18N

        propertiesTable.setModel(getPropertiesTableModel());
        propertiesListScrollPane.setViewportView(propertiesTable);
        if (propertiesTable.getColumnModel().getColumnCount() > 0) {
            propertiesTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.propertiesTable.columnModel.title0_1")); // NOI18N
            propertiesTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.propertiesTable.columnModel.title1_1")); // NOI18N
        }

        validatorIdTextField.setText(org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.validatorIdTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(validatorTypeLabel, org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.validatorTypeLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(validatorIdLabel, org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.validatorIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(ValidatorVisualPanel.class, "ValidatorVisualPanel.propertiesLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(validatorTypeLabel)
                            .addComponent(validatorSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(validatorIdLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(25, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(propertiesLabel)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(validatorIdTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(propertiesListScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(validatorTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(validatorSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(validatorIdLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(validatorIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(propertiesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(propertiesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JScrollPane propertiesListScrollPane;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JLabel validatorIdLabel;
    private javax.swing.JTextField validatorIdTextField;
    private javax.swing.JComboBox validatorSelection;
    private javax.swing.JLabel validatorTypeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
