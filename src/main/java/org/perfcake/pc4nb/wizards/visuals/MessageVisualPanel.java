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
package org.perfcake.pc4nb.wizards.visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.openide.util.Exceptions;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.model.HeaderModel;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui3.actions.AddHeaderAction;
import org.perfcake.pc4nb.ui3.actions.DeleteHeaderAction;
import org.perfcake.pc4nb.ui3.actions.EditHeaderAction;
import org.perfcake.pc4nb.ui.tableModel.HeadersTableModel;

public final class MessageVisualPanel extends VisualPanelWithProperties {

    public static final String MESSAGE_PACKAGE = "org.perfcake.message";

    public MessageVisualPanel() {
        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        try {
            putToComponentPropertiesMap("Message", propertyScanner.getPropertiesOfComponent(Class.forName(MESSAGE_PACKAGE + ".Message")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        initComponents();
        setModel(new MessageModel(new Message()));

        addHeaderButton.addActionListener(new AddHeaderListener());
        editHeaderButton.addActionListener(new EditHeaderListener());
        deleteHeaderButton.addActionListener(new DeleteHeaderListener());

        try {
            listProperties("Message");
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "Message";
    }

    public HeadersTableModel getHeadersTableModel() {
        return headersTableModel;
    }

    public void setHeadersTableModel(HeadersTableModel headersTableModel) {
        this.headersTableModel = headersTableModel;
    }

    public JTable getHeadersTable() {
        return headersTable;
    }

    public void setHeadersTable(JTable headersTable) {
        this.headersTable = headersTable;
    }

    public JTextField getContentTextField() {
        return contentTextField;
    }

    public void setContentTextField(JTextField contentTextField) {
        this.contentTextField = contentTextField;
    }

    public JSpinner getMultiplicitySpinner() {
        return multiplicitySpinner;
    }

    public void setMultiplicitySpinner(JSpinner multiplicitySpinner) {
        this.multiplicitySpinner = multiplicitySpinner;
    }

    public JTextField getUriTexField() {
        return uriTextField;
    }

    public void setUriTexField(JTextField uriTexField) {
        this.uriTextField = uriTexField;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        Scenario.Messages.Message message = ((MessageModel) model).getMessage();

        String messageUri = message.getUri();
        uriTextField.setText(messageUri);
        
        int messageMultiplicity = 0; 
        if (message.getMultiplicity() != null) {
            messageMultiplicity = Integer.parseInt(message.getMultiplicity());
        }
        multiplicitySpinner.setValue(messageMultiplicity);
        
        String messageContent = message.getContent();
        contentTextField.setText(messageContent);
        
        Properties properties = new Properties();
        properties.putAll(getPropertiesFor("Message"));

        for (Property property : message.getProperty()) {
            properties.put(property.getName(), property.getValue());
        }

        try {
            putToComponentPropertiesMap("Message", properties);
            listProperties("Message");

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        for (Header header : message.getHeader()) {
            headersTableModel.addRow(header);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uriLabel = new javax.swing.JLabel();
        uriTextField = new javax.swing.JTextField();
        multiplicityLabel = new javax.swing.JLabel();
        multiplicitySpinner = new javax.swing.JSpinner();
        contentLabel = new javax.swing.JLabel();
        contentTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        headersTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        attachedValidatorsTable = new javax.swing.JTable();
        addHeaderButton = new javax.swing.JButton();
        deleteHeaderButton = new javax.swing.JButton();
        editHeaderButton = new javax.swing.JButton();
        headersLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();
        attachedValidatorsLabel = new javax.swing.JLabel();
        attachValidatorButton = new javax.swing.JButton();
        detachValidatorButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(uriLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.uriLabel.text")); // NOI18N

        uriTextField.setText(org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.uriTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(multiplicityLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.multiplicityLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(contentLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.contentLabel.text")); // NOI18N

        contentTextField.setText(org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.contentTextField.text")); // NOI18N

        headersTableModel = new HeadersTableModel();
        headersTable.setModel(headersTableModel);
        jScrollPane1.setViewportView(headersTable);

        propertiesTable.setModel(getPropertiesTableModel());
        jScrollPane2.setViewportView(propertiesTable);

        attachedValidatorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(attachedValidatorsTable);

        org.openide.awt.Mnemonics.setLocalizedText(addHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.addHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.deleteHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.editHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(headersLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.headersLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.propertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(attachedValidatorsLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.attachedValidatorsLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(attachValidatorButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.attachValidatorButton.text")); // NOI18N
        attachValidatorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachValidatorButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(detachValidatorButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.detachValidatorButton.text")); // NOI18N
        detachValidatorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detachValidatorButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(attachedValidatorsLabel)
                    .addComponent(propertiesLabel)
                    .addComponent(headersLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uriTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(multiplicityLabel)
                            .addComponent(contentLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(multiplicitySpinner)
                            .addComponent(contentTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addHeaderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteHeaderButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editHeaderButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(attachValidatorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(detachValidatorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uriTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(multiplicityLabel)
                    .addComponent(multiplicitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contentLabel)
                    .addComponent(contentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headersLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(addHeaderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editHeaderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteHeaderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(propertiesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(attachedValidatorsLabel)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(attachValidatorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(detachValidatorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void attachValidatorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachValidatorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_attachValidatorButtonActionPerformed

    private void detachValidatorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detachValidatorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detachValidatorButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addHeaderButton;
    private javax.swing.JButton attachValidatorButton;
    private javax.swing.JLabel attachedValidatorsLabel;
    private javax.swing.JTable attachedValidatorsTable;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JTextField contentTextField;
    private javax.swing.JButton deleteHeaderButton;
    private javax.swing.JButton detachValidatorButton;
    private javax.swing.JButton editHeaderButton;
    private javax.swing.JLabel headersLabel;
    private javax.swing.JTable headersTable;
    private HeadersTableModel headersTableModel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel multiplicityLabel;
    private javax.swing.JSpinner multiplicitySpinner;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JLabel uriLabel;
    private javax.swing.JTextField uriTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getSource() instanceof MessageModel) {
//            Message message = (Message) ((MessageModel) evt.getSource()).getMessage();
//            uriTexField.setText(message.getUri());
//            multiplicitySpinner.setValue(message.getMultiplicity());
//        }
    }

    private class AddHeaderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddHeaderAction action = new AddHeaderAction(getModel());
            action.execute();
        }
    }

    private class EditHeaderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = MessageVisualPanel.this.getHeadersTable().getSelectedRow();

            if (selectedRow != -1) {
                MessageModel model = (MessageModel) MessageVisualPanel.this.getModel();
                Header header = model.getMessage().getHeader().get(selectedRow);
                EditHeaderAction action = new EditHeaderAction((HeaderModel) ModelMap.getDefault().getPC4NBModelFor(header));
                action.execute();
            }
        }
    }

    private class DeleteHeaderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = MessageVisualPanel.this.getHeadersTable().getSelectedRows();
            MessageModel messageModel = (MessageModel) MessageVisualPanel.this.getModel();
            List<Header> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Header header = messageModel.getMessage().getHeader().get(selectedRows[i]);
                toRemove.add(header);
            }

            DeleteHeaderAction action = new DeleteHeaderAction(getModel(), toRemove);
            action.execute();
        }
    }
}
