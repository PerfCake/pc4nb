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
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.pc4nb.model.HeaderModel;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import org.perfcake.pc4nb.ui.actions.AddHeaderAction;
import org.perfcake.pc4nb.ui.actions.AddValidatorRefsAction;
import org.perfcake.pc4nb.ui.actions.DeleteHeaderAction;
import org.perfcake.pc4nb.ui.actions.DeleteValidatorRefsAction;
import org.perfcake.pc4nb.ui.actions.EditHeaderAction;
import org.perfcake.pc4nb.ui.tableModel.HeadersTableModel;
import org.perfcake.pc4nb.ui.tableModel.MetaPropertiesTableModel;
import org.perfcake.pc4nb.ui.tableModel.ValidatorRefsTableModel;

public final class MessageVisualPanel extends AbstractPC4NBView implements DocumentListener {
    public static final String MESSAGE_PACKAGE = "org.perfcake.message";

    public MessageVisualPanel() {
        initComponents();
        setModel(new MessageModel(new Message()));

        addHeaderButton.addActionListener(new AddHeaderListener());
        editHeaderButton.addActionListener(new EditHeaderListener());
        deleteHeaderButton.addActionListener(new DeleteHeaderListener());

        contentTextField.getDocument().addDocumentListener(this);
        uriTextField.getDocument().addDocumentListener(this);

        attachValidatorButton.addActionListener(new AddValidatorRefsListener());
        detachValidatorButton.addActionListener(new DeleteValidatorRefsListener());
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

    public List<Property> getProperties() {
        return propertiesModel.getProperties();
    }

    public ValidatorRefsTableModel getValidatorRefsTableModel() {
        return validatorRefsTableModel;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        Scenario.Messages.Message message = ((MessageModel) model).getMessage();

        String messageUri = message.getUri();
        uriTextField.setText(messageUri);

        int messageMultiplicity = 1;
        if (message.getMultiplicity() != null) {
            messageMultiplicity = Integer.parseInt(message.getMultiplicity());
        }
        multiplicitySpinner.setValue(messageMultiplicity);

        String messageContent = message.getContent();
        contentTextField.setText(messageContent);

        for (Property property : message.getProperty()) {
            int index = propertiesModel.getRowCount();
            propertiesModel.addRow();
            propertiesModel.setValueAt(property.getName(), index, 0);
            propertiesModel.setValueAt(property.getValue(), index, 1);
        }

        for (Header header : message.getHeader()) {
            headersTableModel.addRow(header);
        }
        
        for (ValidatorRef validatorRef : message.getValidatorRef()) {
            validatorRefsTableModel.addRow(validatorRef);
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
        addHeaderButton = new javax.swing.JButton();
        deleteHeaderButton = new javax.swing.JButton();
        editHeaderButton = new javax.swing.JButton();
        headersLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();
        attachedValidatorsLabel = new javax.swing.JLabel();
        attachValidatorButton = new javax.swing.JButton();
        detachValidatorButton = new javax.swing.JButton();
        addPropertyRow = new javax.swing.JButton();
        deleteProperties = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        validatorRefsTable = new javax.swing.JTable();

        org.openide.awt.Mnemonics.setLocalizedText(uriLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.uriLabel.text")); // NOI18N

        uriTextField.setText(org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.uriTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(multiplicityLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.multiplicityLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(contentLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.contentLabel.text")); // NOI18N

        contentTextField.setText(org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.contentTextField.text")); // NOI18N

        headersTableModel = new HeadersTableModel();
        headersTable.setModel(headersTableModel);
        jScrollPane1.setViewportView(headersTable);

        propertiesModel = new MetaPropertiesTableModel();
        propertiesTable.setModel(propertiesModel);
        jScrollPane2.setViewportView(propertiesTable);

        org.openide.awt.Mnemonics.setLocalizedText(addHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.addHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.deleteHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editHeaderButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.editHeaderButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(headersLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.headersLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.propertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(attachedValidatorsLabel, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.attachedValidatorsLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(attachValidatorButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.attachValidatorButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(detachValidatorButton, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.detachValidatorButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addPropertyRow, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.addPropertyRow.text")); // NOI18N
        addPropertyRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPropertyRowActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteProperties, org.openide.util.NbBundle.getMessage(MessageVisualPanel.class, "MessageVisualPanel.deleteProperties.text")); // NOI18N
        deleteProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePropertiesActionPerformed(evt);
            }
        });

        validatorRefsTableModel = new ValidatorRefsTableModel();
        validatorRefsTable.setModel(validatorRefsTableModel);
        jScrollPane4.setViewportView(validatorRefsTable);

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteHeaderButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editHeaderButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(attachValidatorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(detachValidatorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addPropertyRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addHeaderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteProperties, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(addPropertyRow, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteProperties, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(attachedValidatorsLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(attachValidatorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(detachValidatorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addPropertyRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPropertyRowActionPerformed
        propertiesModel.addRow();
    }//GEN-LAST:event_addPropertyRowActionPerformed

    private void deletePropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePropertiesActionPerformed
        int[] selectedRows = propertiesTable.getSelectedRows();

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            propertiesModel.removeRow(selectedRows[i]);
        }
    }//GEN-LAST:event_deletePropertiesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addHeaderButton;
    private javax.swing.JButton addPropertyRow;
    private javax.swing.JButton attachValidatorButton;
    private javax.swing.JLabel attachedValidatorsLabel;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JTextField contentTextField;
    private javax.swing.JButton deleteHeaderButton;
    private javax.swing.JButton deleteProperties;
    private javax.swing.JButton detachValidatorButton;
    private javax.swing.JButton editHeaderButton;
    private javax.swing.JLabel headersLabel;
    private javax.swing.JTable headersTable;
    private HeadersTableModel headersTableModel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel multiplicityLabel;
    private javax.swing.JSpinner multiplicitySpinner;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private MetaPropertiesTableModel propertiesModel;
    private javax.swing.JLabel uriLabel;
    private javax.swing.JTextField uriTextField;
    private javax.swing.JTable validatorRefsTable;
    private ValidatorRefsTableModel validatorRefsTableModel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MessageModel model = (MessageModel) getModel();
        int targetIndex;

        if (evt.getPropertyName().equals(MessageModel.PROPERTY_HEADERS)) {
            List<Header> headersList = model.getMessage().getHeader();
            
            if (evt.getNewValue() != null) {
                targetIndex = headersList.indexOf(evt.getNewValue());
                headersTableModel.insertRow(targetIndex, (Header) evt.getNewValue());
            } else if (evt.getOldValue() != null) {
                targetIndex = headersTableModel.getHeaders().indexOf(evt.getOldValue());
                headersTableModel.removeRow(targetIndex);
            } else {
                // error
            }
        } else if (evt.getPropertyName().equals(MessageModel.PROPERTY_VALIDATOR_REFS)) {
            List<ValidatorRef> validatorRefs = model.getMessage().getValidatorRef();
            
            if (evt.getNewValue() != null) {
                targetIndex = validatorRefs.indexOf(evt.getNewValue());
                validatorRefsTableModel.insertRow(targetIndex, (ValidatorRef) evt.getNewValue());
            } else if (evt.getOldValue() != null) {
                targetIndex = validatorRefsTableModel.getValidatorRefs().indexOf(evt.getOldValue());
                validatorRefsTableModel.removeRow(targetIndex);
            } else {
                // error
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == uriTextField.getDocument() || e.getDocument() == contentTextField.getDocument()) {
            firePropertyChange("prop-content-uri", 0, 1);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == uriTextField.getDocument() || e.getDocument() == contentTextField.getDocument()) {
            firePropertyChange("prop-content-uri", 0, 1);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == uriTextField.getDocument() || e.getDocument() == contentTextField.getDocument()) {
            firePropertyChange("prop-content-uri", 0, 1);
        }
    }

    private  class DeleteValidatorRefsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = MessageVisualPanel.this.validatorRefsTable.getSelectedRows();
            MessageModel messageModel = (MessageModel) MessageVisualPanel.this.getModel();
            List<ValidatorRef> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                ValidatorRef validatorRef = messageModel.getMessage().getValidatorRef().get(selectedRows[i]);
                toRemove.add(validatorRef);
            }

            DeleteValidatorRefsAction action = new DeleteValidatorRefsAction((MessageModel) getModel(), toRemove);
            action.execute();
        }
    }

    private class AddValidatorRefsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddValidatorRefsAction action = new AddValidatorRefsAction(ModelMap.getDefault().getValidationModel(), (MessageModel) getModel());
            action.execute();
        }
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
