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
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.MessagesModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.wizards.MessageWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.MessagesVisualPanel;

public final class AddMessageAction extends AbstractPC4NBAction {

    public AddMessageAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        MessageWizardPanel messageWizardPanel = new MessageWizardPanel();

        return messageWizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        MessageModel messageModel = new MessageModel(new Message());
        messageModel.setUri((String) wiz.getProperty("message-uri"));
        messageModel.setMultiplicity(wiz.getProperty("message-multiplicity").toString());

        List<Property> properties = (List<Property>) wiz.getProperty("message-properties");
        Properties defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(org.perfcake.message.Message.class);

        for (Property property : properties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();
            
            if (!propertyValue.equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName);
                newProperty.setValue(propertyValue);
                
                messageModel.addProperty(newProperty);
            }
        }
        
        List<Header> headers = (List<Header>) wiz.getProperty("message-headers");
        
        for (Header header : headers) {
            messageModel.addHeader(header);
        }
        
        MessagesVisualPanel sourcePanel = (MessagesVisualPanel) getSource();
        MessagesModel messagesModel = (MessagesModel) sourcePanel.getModel();
        messagesModel.addMessage(messageModel.getMessage());
        ModelMap.getDefault().addEntry(messageModel.getMessage(), messageModel);
    }
}
