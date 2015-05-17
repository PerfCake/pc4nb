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
import org.perfcake.model.Header;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.wizards.MessageWizardPanel;

public final class AddMessageAction extends AbstractPC4NBAction {
    private PC4NBModel to;

    public AddMessageAction(PC4NBModel to) {
        this.to = to;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        MessageWizardPanel messageWizardPanel = new MessageWizardPanel();

        return messageWizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        Message message = new Message();
        message.setMultiplicity(wiz.getProperty("message-multiplicity").toString());
        String uri = (String) wiz.getProperty("message-uri");
        String content = (String) wiz.getProperty("message-content");
        
        if (content != null && !content.isEmpty()) {
            message.setContent(content);
        } else {
            message.setUri(uri);
        }

        List<Header> headers = (List<Header>) wiz.getProperty("message-headers");

        for (Header header : headers) {
            message.getHeader().add(header);
        }
        
        List<ValidatorRef> validatorRefs = (List<ValidatorRef>) wiz.getProperty("message-validators");
        
        for (ValidatorRef validatorRef : validatorRefs) {
            message.getValidatorRef().add(validatorRef);
        }

        MessagesModel messagesModel = (MessagesModel) to;
        messagesModel.addMessage(message);
    }
}
