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
import org.openide.WizardDescriptor;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.ui.wizards.MessageWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditMessageAction extends AbstractPC4NBAction {

    private MessageWizardPanel wizardPanel;
    private MessageModel messageModel;

    public EditMessageAction(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new MessageWizardPanel();
        wizardPanel.getComponent().setModel(messageModel);
        return wizardPanel;
    }

    @Override

    public void doAction(WizardDescriptor wiz) {
        messageModel.setMultiplicity(wiz.getProperty("message-multiplicity").toString());
        String uri = (String) wiz.getProperty("message-uri");
        String content = (String) wiz.getProperty("message-content");

        if (content != null && !content.isEmpty()) {
            messageModel.setUri(null);
            messageModel.setContent(content);
        } else {
            messageModel.setContent(null);
            messageModel.setUri(uri);
        }
        
        
        List<Property> messageProperties = messageModel.getProperty();
            
        for (int i = messageProperties.size() - 1; i >= 0; i--) {
            messageProperties.remove(messageProperties.get(i));
        }
        
        List<Property> newProperties = (List<Property>) wiz.getProperty("message-properties");
        
        for (Property property : newProperties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();

            Property newProperty = new Property();
            newProperty.setName(propertyName);
            newProperty.setValue(propertyValue);

            messageModel.addProperty(newProperty);
        }
    }
}
