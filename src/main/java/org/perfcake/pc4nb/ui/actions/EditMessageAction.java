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
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.wizards.MessageWizardPanel;
import static org.perfcake.pc4nb.ui.wizards.visuals.MessageVisualPanel.MESSAGE_PACKAGE;

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
            messageModel.setContent(content);
        } else {
            messageModel.setUri(uri);
        }

        List<Header> headers = (List<Header>) wiz.getProperty("message-headers");

        for (Header header : headers) {
            messageModel.addHeader(header);
        }
        
        List<ValidatorRef> validatorRefs = (List<ValidatorRef>) wiz.getProperty("message-validators");
        
        for (ValidatorRef validatorRef : validatorRefs) {
            
        }
    }
}
