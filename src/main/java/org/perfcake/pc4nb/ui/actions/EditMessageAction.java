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
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.controller.MessagesController;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.MessagesModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.tableModel.HeadersTableModel;
import org.perfcake.pc4nb.wizards.MessageWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.MessagesVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditMessageAction extends AbstractPC4NBAction {

    MessagesVisualPanel sourcePanel;
    MessagesController controller;
    MessagesModel messagesModel;
    MessageModel messageModel;

    public EditMessageAction(AbstractPC4NBVisualPanel source) {
        super(source);
        sourcePanel = (MessagesVisualPanel) getSource();
        controller = (MessagesController) sourcePanel.getController();
        messagesModel = (MessagesModel) controller.getModel();
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        MessageWizardPanel messageWizardPanel = new MessageWizardPanel();

        int selectedMessage = sourcePanel.getMessagesTable().getSelectedRow();

        if (selectedMessage > -1) {
            Message message = messagesModel.getMessages().getMessage().get(selectedMessage);
            messageModel = (MessageModel) ModelMap.getDefault().getPC4NBModelFor(message);

           /* messageWizardPanel.getComponent().getUriTexField().setText(message.getUri());
            messageWizardPanel.getComponent().getMultiplicitySpinner().setValue(Integer.parseInt(message.getMultiplicity()));

            Properties properties = new Properties();

            for (Property property : message.getProperty()) {
                properties.put(property.getName(), property.getValue());
            }

            HeadersTableModel newHeadersTableModel = new HeadersTableModel();

            for (Header header : message.getHeader()) {
                newHeadersTableModel.addRow(header);
            }

            try {
                messageWizardPanel.getComponent().putToComponentPropertiesMap("Message", properties);
                messageWizardPanel.getComponent().listProperties("Message");

            } catch (ClassNotFoundException | NoSuchFieldException ex) {
                Exceptions.printStackTrace(ex);
            }

            messageWizardPanel.getComponent().setHeadersTableModel(newHeadersTableModel);
            messageWizardPanel.getComponent().getHeadersTable().setModel(messageWizardPanel.getComponent().getHeadersTableModel());*/
        }

        return messageWizardPanel;
    }

    @Override

    public void doAction(WizardDescriptor wiz) {
        messageModel.setUri(wiz.getProperty("message-uri").toString());
        messageModel.setMultiplicity(wiz.getProperty("message-multiplicity").toString());

        Properties properties = (Properties) wiz.getProperty("message-properties");
        Properties defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(org.perfcake.message.Message.class);

        messageModel.getProperty().clear();
        
        for (Object propertyName : properties.keySet()) {

            if (!properties.get(propertyName).equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName.toString());

                newProperty.setValue(properties.get(propertyName).toString());
                messageModel.addProperty(newProperty);
            }
        }
        
        messageModel.getMessage().getHeader().clear();
        
        List<Header> headers = (List<Header>) wiz.getProperty("message-headers");

        for (Header header : headers) {
            messageModel.addHeader(header);
        }
    }
}
