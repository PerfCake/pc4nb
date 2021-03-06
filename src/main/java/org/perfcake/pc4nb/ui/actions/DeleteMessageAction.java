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

package org.perfcake.pc4nb.ui.actions;

import java.util.ArrayList;
import java.util.List;
import org.openide.WizardDescriptor;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.model.PC4NBModel;

public class DeleteMessageAction extends AbstractPC4NBAction {
    private PC4NBModel from;
    private List<Message> toRemove;
    
    public DeleteMessageAction(PC4NBModel from, List<Message> toRemove) {
        this.from = from;
        this.toRemove = toRemove;
    }
    
    public DeleteMessageAction(PC4NBModel from, Message toRemove) {
        this.from = from;
        this.toRemove = new ArrayList<>();
        this.toRemove.add(toRemove);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        return null;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        MessagesModel messagesModel = (MessagesModel) from;
        
        for (Message message : toRemove) {
            messagesModel.removeMessage(message);
        }
    }
}
