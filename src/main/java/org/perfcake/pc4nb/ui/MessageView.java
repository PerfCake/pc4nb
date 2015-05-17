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
package org.perfcake.pc4nb.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.actions.DeleteMessageAction;
import org.perfcake.pc4nb.ui.actions.EditMessageAction;

/**
 *
 * @author Andrej Halaj
 */
public class MessageView extends SecondLevelView {
    private JMenuItem editComponent = new JMenuItem("Edit message");
    private JMenuItem deleteComponent = new JMenuItem("Delete message");
    private JPopupMenu menu = new JPopupMenu();

    public MessageView(PC4NBModel model) {
        super(model);
        setHeader(resolveAndGetHeader());

        setDefaultBorder(new LineBorder(Color.ORANGE, 1, true));
        setBorder(getDefaultBorder());

        editComponent.addActionListener(new EditMessageListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteMessageListener());
        menu.add(deleteComponent);

        this.setComponentPopupMenu(menu);
        addMouseListener(new MessageMouseListener());
        addKeyListener(new MessageKeyAdapter());
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
    }

    private String resolveAndGetHeader() {
        MessageModel messageModel = (MessageModel) getModel();

        String uri = messageModel.getMessage().getUri();
        String content = messageModel.getMessage().getContent();

        if (content != null) {
            return content;
        } else {
            return uri;
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MessageModel.PROPERTY_CONTENT) || evt.getPropertyName().equals(MessageModel.PROPERTY_URI)) {
            setHeader(resolveAndGetHeader());
            revalidate();
            repaint();
        }
    }

    private class MessageMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class MessageKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isFocusOwner() && e.getKeyCode() == KeyEvent.VK_DELETE) {
                runDeleteWizard();
            }
        }
    }

    private class EditMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private class DeleteMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runDeleteWizard();
        }
    }

    private void runEditWizard() {
        EditMessageAction action = new EditMessageAction((MessageModel) getModel());
        action.execute();
    }

    private void runDeleteWizard() {
        MessagesView messagesView = (MessagesView) getParent().getParent();
        Message message = ((MessageModel) getModel()).getMessage();

        if (messagesView != null) {
            DeleteMessageAction action = new DeleteMessageAction(messagesView.getModel(), message);
            action.execute();
        }
    }
}
