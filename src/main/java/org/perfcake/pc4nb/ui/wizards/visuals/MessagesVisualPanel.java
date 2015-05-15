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
import javax.swing.JTable;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import org.perfcake.pc4nb.ui.actions.AddMessageAction;
import org.perfcake.pc4nb.ui.actions.DeleteMessageAction;
import org.perfcake.pc4nb.ui.actions.EditMessageAction;
import org.perfcake.pc4nb.ui.tableModel.MessagesTableModel;

public final class MessagesVisualPanel extends AbstractPC4NBView {

    public MessagesVisualPanel() {
        setModel(new MessagesModel(new Messages()));
        initComponents();

        addMessageButton.addActionListener(new AddMessageListener());
        editMessageButton.addActionListener(new EditMessageListener());
        deleteMessageButton.addActionListener(new DeleteMessageListener());
    }

    @Override
    public String getName() {
        return "Messages";
    }

    public MessagesTableModel getMessagesTableModel() {
        return messagesTableModel;
    }

    public void setMessagesTableModel(MessagesTableModel messagesTableModel) {
        this.messagesTableModel = messagesTableModel;
    }

    public JTable getMessagesTable() {
        return messagesTable;
    }

    public void setMessagesTable(JTable messagesTable) {
        this.messagesTable = messagesTable;
    }

    public List<Message> getSelectedComponents() {
        List<Message> selectedComponents = new ArrayList<>();
        int[] selectedRows = getMessagesTable().getSelectedRows();

        for (int i = 1; i < selectedRows.length; i++) {
            selectedComponents.add(((MessagesModel) getModel()).getMessages().getMessage().get(selectedRows[i]));
        }

        return selectedComponents;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messagesTable = new javax.swing.JTable();
        addMessageButton = new javax.swing.JButton();
        editMessageButton = new javax.swing.JButton();
        deleteMessageButton = new javax.swing.JButton();

        messagesTableModel = new MessagesTableModel();
        messagesTable.setModel(messagesTableModel);
        jScrollPane1.setViewportView(messagesTable);

        org.openide.awt.Mnemonics.setLocalizedText(addMessageButton, org.openide.util.NbBundle.getMessage(MessagesVisualPanel.class, "MessagesVisualPanel.addMessageButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editMessageButton, org.openide.util.NbBundle.getMessage(MessagesVisualPanel.class, "MessagesVisualPanel.editMessageButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteMessageButton, org.openide.util.NbBundle.getMessage(MessagesVisualPanel.class, "MessagesVisualPanel.deleteMessageButton.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMessageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editMessageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(addMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMessageButton;
    private javax.swing.JButton deleteMessageButton;
    private javax.swing.JButton editMessageButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable messagesTable;
    private MessagesTableModel messagesTableModel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MessagesModel.PROPERTY_MESSAGE)) {
            MessagesModel model = (MessagesModel) getModel();
            List<Message> messagesList = model.getMessages().getMessage();
            int targetIndex;

            if (evt.getNewValue() != null) {
                targetIndex = messagesList.indexOf(evt.getNewValue());
                messagesTableModel.insertRow(targetIndex, (Message) evt.getNewValue());
            } else if (evt.getOldValue() != null) {
                targetIndex = messagesList.indexOf(evt.getOldValue());
                messagesTableModel.removeRow(targetIndex);
            } else {
                // error
            }
        }
    }
    
    private class DeleteMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = MessagesVisualPanel.this.getMessagesTable().getSelectedRows();
            MessagesModel messagesModel = (MessagesModel) MessagesVisualPanel.this.getModel();
            List<Message> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Message message = messagesModel.getMessages().getMessage().get(selectedRows[i]);
                toRemove.add(message);
            }

            DeleteMessageAction action = new DeleteMessageAction(getModel(), toRemove);
            action.execute();
        }
    }

    private class EditMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = MessagesVisualPanel.this.getMessagesTable().getSelectedRow();

            if (selectedRow != -1) {
                MessagesModel model = (MessagesModel) MessagesVisualPanel.this.getModel();
                Message message = model.getMessages().getMessage().get(selectedRow);
                EditMessageAction action = new EditMessageAction((MessageModel) ModelMap.getDefault().getPC4NBModelFor(message));
                action.execute();
            }
        }
    }

    private class AddMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddMessageAction action = new AddMessageAction(getModel());
            action.execute();
        }
    }
}
