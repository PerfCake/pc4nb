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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.pc4nb.core.model.MessageModel;
import org.perfcake.pc4nb.core.model.MessagesModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import static org.perfcake.pc4nb.ui3.SizeConstraints.INSET;
import static org.perfcake.pc4nb.ui3.SizeConstraints.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui3.SizeConstraints.SECOND_LEVEL_RECTANGLE_WIDTH;
import org.perfcake.pc4nb.ui3.actions.AddMessageAction;

/**
 *
 * @author Andrej Halaj
 */
public class MessagesView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new message");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new MessageTransferHandler();

    public MessagesView(int x, int y, int width) {
        super(x, y, width);
        setColor(Color.GREEN);
        setHeader("Messages");
        
        addComponent.addActionListener(new AddMessageListener());
        
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.ORANGE);
        MessagesModel model = (MessagesModel) getModel();

        if (model != null && model.getMessages() != null) {
            List<Message> messages = model.getMessages().getMessage();

            int currentX = INSET;
            int currentY = TOP_INDENT;
            
            this.removeAll();

            for (Message message : messages) {
                MessageView newMessage = new MessageView(currentX, currentY, message.getUri());
                newMessage.setModel(ModelMap.getDefault().getPC4NBModelFor(message));
                this.add(newMessage);

                if (currentX + 2 * (SECOND_LEVEL_RECTANGLE_WIDTH + INSET) > getWidth()) {
                    currentX = INSET;
                    currentY += PERFCAKE_RECTANGLE_HEIGHT + INSET;
                } else {
                    currentX += SECOND_LEVEL_RECTANGLE_WIDTH + INSET;
                }
            }
        }
    }

    @Override
    public void recomputeHeight() {
        super.recomputeHeight();
        MessagesModel model = (MessagesModel) getModel();

        if (model != null && model.getMessages() != null) {
            List<Scenario.Messages.Message> messages = model.getMessages().getMessage();
            int columns = (int) ((getWidth() - INSET) / (SECOND_LEVEL_RECTANGLE_WIDTH + INSET));
            this.setHeight((int) (TOP_INDENT + (Math.ceil((double) messages.size() / (double) columns) * (PERFCAKE_RECTANGLE_HEIGHT + INSET))));
        }
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
