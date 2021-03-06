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

import java.beans.PropertyChangeEvent;
import java.util.List;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertiesModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import org.perfcake.pc4nb.ui.tableModel.MetaPropertiesTableModel;

public final class ScenarioPropertiesVisualPanel extends AbstractPC4NBView {

    public ScenarioPropertiesVisualPanel() {
        initComponents();
        setModel(new PropertiesModel(new Scenario.Properties()));
    }

    @Override
    public String getName() {
        return "Scenario Properties";
    }

    public List<Property> getProperties() {
        return propertiesTableModel.getProperties();
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        for (int i = propertiesTableModel.getRowCount() - 1; i >= 0; i--) {
            propertiesTableModel.removeRow(i);
        }
        
        PropertiesModel propertiesModel = (PropertiesModel) model;
        
        if (propertiesModel != null && propertiesModel.getProperty() != null) {
            for (Property property : propertiesModel.getProperty()) {
                propertiesTableModel.addRow();
                int row = propertiesTableModel.getRowCount() - 1;

                propertiesTableModel.setValueAt(property.getName(), row, 0);
                propertiesTableModel.setValueAt(property.getValue(), row, 1);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        scenarioPropertiesLabel = new javax.swing.JLabel();
        addPropertyButton = new javax.swing.JButton();
        deletePropertyButton = new javax.swing.JButton();

        propertiesTableModel = new MetaPropertiesTableModel();
        propertiesTable.setModel(propertiesTableModel);
        jScrollPane1.setViewportView(propertiesTable);

        org.openide.awt.Mnemonics.setLocalizedText(scenarioPropertiesLabel, org.openide.util.NbBundle.getMessage(ScenarioPropertiesVisualPanel.class, "ScenarioPropertiesVisualPanel.scenarioPropertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addPropertyButton, org.openide.util.NbBundle.getMessage(ScenarioPropertiesVisualPanel.class, "ScenarioPropertiesVisualPanel.addPropertyButton.text")); // NOI18N
        addPropertyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPropertyButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deletePropertyButton, org.openide.util.NbBundle.getMessage(ScenarioPropertiesVisualPanel.class, "ScenarioPropertiesVisualPanel.deletePropertyButton.text")); // NOI18N
        deletePropertyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePropertyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scenarioPropertiesLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addPropertyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deletePropertyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(scenarioPropertiesLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(addPropertyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(deletePropertyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addPropertyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPropertyButtonActionPerformed
        propertiesTableModel.addRow();
    }//GEN-LAST:event_addPropertyButtonActionPerformed

    private void deletePropertyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePropertyButtonActionPerformed
        int[] selectedRows = propertiesTable.getSelectedRows();
        
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            propertiesTableModel.removeRow(selectedRows[i]);
        }
    }//GEN-LAST:event_deletePropertyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPropertyButton;
    private javax.swing.JButton deletePropertyButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable propertiesTable;
    private MetaPropertiesTableModel propertiesTableModel;
    private javax.swing.JLabel scenarioPropertiesLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
