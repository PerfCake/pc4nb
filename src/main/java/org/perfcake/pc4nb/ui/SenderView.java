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
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.ui.actions.EditSenderAction;

/**
 *
 * @author Andrej Halaj
 */
public class SenderView extends PC4NBView {
    private JMenuItem editComponent = new JMenuItem("Edit sender");
    private JPopupMenu menu = new JPopupMenu();
    TransferHandler transferHandler = new SenderTransferHandler();

    public SenderView(int x, int y, int width) {
        super(x, y, width);
        setDefaultBorder(new LineBorder(Color.GREEN, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Sender");

        editComponent.addActionListener(new EditSenderListener());

        menu.add(editComponent);
        this.setComponentPopupMenu(menu);
        
        setTransferHandler(transferHandler);
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        SenderModel senderModel = (SenderModel) model;
        setHeader(senderModel.getSender().getClazz());
    }
    
    private class EditSenderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditSenderAction action = new EditSenderAction((SenderModel) getModel());
            action.execute();
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SenderModel.PROPERTY_CLASS))  {
            SenderModel senderModel = (SenderModel) evt.getSource();
            setHeader(senderModel.getSender().getClazz());
        }
    }

    private final class SenderTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(SenderModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                SenderModel model = (SenderModel) support.getTransferable().getTransferData(SenderModel.DATA_FLAVOR);
                setModel(model);
                revalidate();
                repaint();

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}
