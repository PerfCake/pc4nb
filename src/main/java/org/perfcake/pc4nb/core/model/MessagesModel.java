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

package org.perfcake.pc4nb.core.model;

import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;

public class MessagesModel extends PC4NBModel {

	public  static final String PROPERTY_MESSAGE = "messages-message";

	private Messages messages;

	public MessagesModel(Messages messages){

		this.messages = messages;
	}
	
	public Messages getMessages(){
		return messages;
	}
	
	public void addMessage(Message m){
		addMessage(getMessages().getMessage().size(), m);
	}
	public void addMessage(int index, Message m){
		getMessages().getMessage().add(index, m);
		getListeners().firePropertyChange(PROPERTY_MESSAGE, null, m);
	}
	
	public void removeMessage(Message m){
		if (getMessages().getMessage().remove(m)){
			getListeners().firePropertyChange(PROPERTY_MESSAGE, m, null);
		}
	}
}
