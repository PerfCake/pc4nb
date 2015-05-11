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
package org.perfcake.pc4nb.ui3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.ui3.actions.DeleteMessageAction;
import org.perfcake.pc4nb.ui3.actions.EditMessageAction;

/**
 *
 * @author Andrej Halaj
 */
public class MessageView extends SecondLevelView {
    private JMenuItem editComponent = new JMenuItem("Edit message");
    private JMenuItem deleteComponent = new JMenuItem("Delete message");
    private JPopupMenu menu = new JPopupMenu();

    public MessageView(int x, int y, String header) {
        super(x, y, header);
        setColor(Color.ORANGE);
        
        editComponent.addActionListener(new EditMessageListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteMessageListener());
        menu.add(deleteComponent);

        this.setComponentPopupMenu(menu);
    }
    
     @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        MessageModel messagemodel = (MessageModel) getModel();
        setHeader(messagemodel.getMessage().getUri());
    }

    private class EditMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditMessageAction action = new EditMessageAction((MessageModel) getModel());
            action.execute();
        }
    }

    private class DeleteMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MessagesView messagesView = (MessagesView) getParent();
            Message message = ((MessageModel) getModel()).getMessage();

            if (messagesView != null) {
                DeleteMessageAction action = new DeleteMessageAction(messagesView.getModel(), message);
                action.execute();
            }
        }
    }
}
