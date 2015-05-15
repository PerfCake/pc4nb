/*
 * PerfClispe
 * 
 * 
 * Copyright (c) 2014 Jakub Knetl
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
package org.perfcake.pc4nb.model;

import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;

public class MessagesModel extends PC4NBModel {

    public static final String PROPERTY_MESSAGE = "messages-message";

    private Messages messages;

    public MessagesModel(Messages messages) {
        this.messages = messages;
        
        if (messages != null) {
            ModelMap.getDefault().addEntry(messages, this);
        }
    }

    public Messages getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        if (getMessages() == null) {
            createMessages();
        }
        
        addMessage(getMessages().getMessage().size(), message);
    }

    public void addMessage(int index, Message message) {
        if (getMessages() == null) {
            createMessages();
        }
        
        getMessages().getMessage().add(index, message);
        getListeners().firePropertyChange(PROPERTY_MESSAGE, null, message);
    }

    public void removeMessage(Message message) {
        if (getMessages().getMessage().remove(message)) {
            getListeners().firePropertyChange(PROPERTY_MESSAGE, message, null);
        }
        
        if (getMessages().getMessage().isEmpty()) {
            removeMessages();
        }
    }
    
    private void createMessages() {
        this.messages = new Messages();
        ModelMap.getDefault().addEntry(messages, this);
    }

    private void removeMessages() {
        ModelMap.getDefault().removeEntry(messages);
        this.messages = null;
    }
}
