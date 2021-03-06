/*
 * Copyright (c) 2015 Andrej Halaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.util.Exceptions;
import org.perfcake.message.sender.MessageSender;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;

public final class SenderVisualPanel extends VisualPanelWithProperties implements DocumentListener {
    public static final String SENDER_PACKAGE = "org.perfcake.message.sender";

    /**
     * Creates new form ScenarioVisualPanel2
     */
    public SenderVisualPanel() {
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends MessageSender>> subTypes = scanner.findComponentsOfType(MessageSender.class, SENDER_PACKAGE);

        Set<String> components = new HashSet<>();
        
        for (Class<? extends MessageSender> sender : subTypes) {
            components.add(sender.getSimpleName());
        }
        
        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(SENDER_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        initComponents();
        setModel(new SenderModel(new Scenario.Sender()));
        
        try {
            listProperties((String) senderSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
        
        propertiesTable.setDefaultRenderer(String.class, new PropertiesTableCellRenderer());
        
        targetTextField.getDocument().addDocumentListener(this);
    }

    @Override
    public String getName() {
        return "Sender";
    }

    public JComboBox getSenderSelection() {
        return senderSelection;
    }
    
    public JTextField getTargetTextField() {
        return targetTextField;
    }
    
    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        SenderModel senderModel = (SenderModel) model;
        
        String target = senderModel.getTarget();
        targetTextField.setText(target);
        
        String senderClazz = senderModel.getSender().getClazz();
        List<PropertyModel> properties = new ArrayList<>(getPropertiesFor(senderClazz));
        
        for (Property property : senderModel.getSender().getProperty()) {
            for (PropertyModel defaultProperty : properties) {
                if (defaultProperty.getName().equals(property.getName())) {
                    defaultProperty.setValue(property.getValue());
                }
            }
        }

        try {
            if (senderClazz != null) {
                getSenderSelection().setSelectedItem(senderClazz);
                putToComponentPropertiesMap(senderClazz, properties);
                listProperties(senderClazz);
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

        senderSelection = new javax.swing.JComboBox();
        senderTypeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        propertiesLabel = new javax.swing.JLabel();
        targetLabel = new javax.swing.JLabel();
        targetTextField = new javax.swing.JTextField();

        Set<String> componentNames = getComponentPropertiesModelMap().keySet();
        String[] componentNamesArray = new String[componentNames.size()];
        componentNames.toArray(componentNamesArray);
        senderSelection.setModel(new DefaultComboBoxModel(componentNamesArray));

        senderSelection.addItemListener (new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) senderSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    // blah
                }
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(senderTypeLabel, org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.senderTypeLabel.text")); // NOI18N

        propertiesTable.setModel(getPropertiesTableModel());
        jScrollPane1.setViewportView(propertiesTable);

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.propertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(targetLabel, org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.targetLabel.text")); // NOI18N

        targetTextField.setText(org.openide.util.NbBundle.getMessage(SenderVisualPanel.class, "SenderVisualPanel.targetTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(targetLabel)
                    .addComponent(propertiesLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(senderTypeLabel)
                    .addComponent(senderSelection, 0, 458, Short.MAX_VALUE)
                    .addComponent(targetTextField))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(senderTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(senderSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(targetLabel)
                .addGap(18, 18, 18)
                .addComponent(targetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(propertiesLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JComboBox senderSelection;
    private javax.swing.JLabel senderTypeLabel;
    private javax.swing.JLabel targetLabel;
    private javax.swing.JTextField targetTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == targetTextField.getDocument()) {
            firePropertyChange("prop-target", 0, 1);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == targetTextField.getDocument()) {
            firePropertyChange("prop-target", 0, 1);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == targetTextField.getDocument()) {
            firePropertyChange("prop-target", 0, 1);
        }
    }
}
