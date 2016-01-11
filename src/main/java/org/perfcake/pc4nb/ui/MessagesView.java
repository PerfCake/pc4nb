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

package org.perfcake.pc4nb.ui;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.MessagesModel;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.actions.AddMessageAction;

public final class MessagesView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new message");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new MessageTransferHandler();

    public MessagesView() {
        setHeader("Messages");
        
        setDefaultBorder(new LineBorder(Color.ORANGE, 1, true));
        setBorder(getDefaultBorder());

        addComponent.addActionListener(new AddMessageListener());

        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }
    
    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        drawChildren();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MessagesModel.PROPERTY_MESSAGE)) {
            drawChildren();
        }
    }

    public void drawChildren() {
        removeAll();

        MessagesModel model = (MessagesModel) getModel();

        if (model != null && model.getMessages() != null) {
            for (Message message : model.getMessages().getMessage()) {
                add(new MessageView(ModelMap.getDefault().getPC4NBModelFor(message)));
            }
        }
        
        revalidate();
        repaint();
    }

    private final class MessageTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(MessageModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                MessageModel model = (MessageModel) support.getTransferable().getTransferData(MessageModel.DATA_FLAVOR);
                model.setUri("Message");
                ((MessagesModel) getModel()).addMessage(model.getMessage());

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }

    private class AddMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddMessageAction action = new AddMessageAction(getModel());
            action.execute();
        }
    }
}
