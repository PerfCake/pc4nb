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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ValidationModel;
import static org.perfcake.pc4nb.model.ValidationModel.PROPERTY_ENABLED;
import static org.perfcake.pc4nb.model.ValidationModel.PROPERTY_VALIDATORS;
import org.perfcake.pc4nb.model.ValidatorModel;
import org.perfcake.pc4nb.ui.actions.AddValidatorAction;

public final class ValidationView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new validator");
    private JMenuItem turnOnOff = new JMenuItem("Turn validation on/off");
    private JMenuItem fastForward = new JMenuItem("Turn fast forward on/off");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ValidatorTransferHandler();

    public ValidationView() {
        setDefaultBorder(new LineBorder(Color.MAGENTA, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Validation");

        addComponent.addActionListener(new AddValidatorListener());

        menu.add(addComponent);
        
        turnOnOff.addActionListener(new TurnOnOffListener());
        menu.add(turnOnOff);
        
        fastForward.addActionListener(new FastForwardListener());
        menu.add(fastForward);
        
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
        if (evt.getPropertyName().equals(ValidationModel.PROPERTY_VALIDATORS)) {
            drawChildren();
        }
    }

    public void drawChildren() {
        removeAll();

        ValidationModel model = (ValidationModel) getModel();

        if (model != null && model.getValidation() != null) {
            for (Validator validator : model.getValidation().getValidator()) {
                add(new ValidatorView(ModelMap.getDefault().getPC4NBModelFor(validator)));
            }
        }
        
        revalidate();
        repaint();
    }

    private final class ValidatorTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(ValidatorModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                ValidatorModel model = (ValidatorModel) support.getTransferable().getTransferData(ValidatorModel.DATA_FLAVOR);
                ((ValidationModel) getModel()).addValidator(model.getValidator());

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }

    private class AddValidatorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddValidatorAction action = new AddValidatorAction(getModel());
            action.execute();
        }
    }
    
    private class TurnOnOffListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ValidationModel validationModel = (ValidationModel) getModel();
            
            if (validationModel != null && validationModel.getValidation() != null) {
                validationModel.setEnabled(!validationModel.getValidation().isEnabled());
            }
        }
    }
    
    private class FastForwardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ValidationModel validationModel = (ValidationModel) getModel();
            
            if (validationModel != null && validationModel.getValidation() != null) {
                validationModel.setFastForward(!validationModel.getValidation().isFastForward());
            }
        }
    }
}
