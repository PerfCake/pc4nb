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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.ui.actions.EditSenderAction;

import static org.perfcake.pc4nb.model.SenderModel.PROPERTY_CLASS;
import static org.perfcake.pc4nb.model.SenderModel.PROPERTY_PROPERTIES;
import static org.perfcake.pc4nb.model.SenderModel.PROPERTY_TARGET;

public class SenderView extends PC4NBView {
    private JLabel targetLabel = new JLabel("null");
    private JMenuItem editComponent = new JMenuItem("Edit sender");
    private JPopupMenu menu = new JPopupMenu();
    TransferHandler transferHandler = new SenderTransferHandler();

    public SenderView() {
        setHeader("Sender");
        
        setDefaultBorder(new LineBorder(Color.BLUE, 1, true));
        setBorder(getDefaultBorder());

        editComponent.addActionListener(new EditSenderListener());

        menu.add(editComponent);
        setComponentPopupMenu(menu);
        
        setTransferHandler(transferHandler);
        addMouseListener(new SenderMouseListener());
        
        add(targetLabel);
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
        setTargetLabel(resolveAndGetTarget());
    }
    
    private void setTargetLabel(String newLabel) {
        targetLabel.setText("Target: " + newLabel);
    }
    
    private String resolveAndGetHeader() {
        SenderModel senderModel = (SenderModel) getModel();
        
        return senderModel.getSender().getClazz();
    }
    
    private String resolveAndGetTarget() {
        SenderModel senderModel = (SenderModel) getModel();
        String target = senderModel.getTarget();
        
        return target;
    }
    
    private class SenderMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class EditSenderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private void runEditWizard() {
        EditSenderAction action = new EditSenderAction((SenderModel) getModel());
        action.execute();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) { 
            case PROPERTY_CLASS:
                setHeader(resolveAndGetHeader());
                break;
            case PROPERTY_TARGET:
                setTargetLabel(resolveAndGetTarget());
                break;
            case PROPERTY_PROPERTIES:
            default:
                break;
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
                SenderModel oldModel = (SenderModel) getModel();
                SenderModel model = (SenderModel) support.getTransferable().getTransferData(SenderModel.DATA_FLAVOR);
                
                model.setTarget(oldModel.getTarget()); // keep old target setting
                
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
