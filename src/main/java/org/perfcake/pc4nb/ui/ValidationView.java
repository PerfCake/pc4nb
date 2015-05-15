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
import java.awt.Graphics;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.ValidationModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import static org.perfcake.pc4nb.ui.SizeConstraints.INSET;
import static org.perfcake.pc4nb.ui.SizeConstraints.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui.SizeConstraints.SECOND_LEVEL_RECTANGLE_WIDTH;
import org.perfcake.pc4nb.ui.actions.AddValidatorAction;

/**
 *
 * @author Andrej Halaj
 */
public class ValidationView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new validator");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ValidatorTransferHandler();

    public ValidationView(int x, int y, int width) {
        super(x, y, width);
        setColor(Color.MAGENTA);
        setHeader("Validation");

        addComponent.addActionListener(new AddValidatorListener());

        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ValidationModel.PROPERTY_VALIDATORS)) {
            recomputeHeightAndRedraw();
        }
    }

    protected void drawChildren() {
        ValidationModel model = (ValidationModel) getModel();

        this.removeAll();

        if (model != null && model.getValidation() != null) {
            List<Validator> validators = model.getValidation().getValidator();

            int currentX = INSET;
            int currentY = TOP_INDENT;

            for (Validator validator : validators) {
                ValidatorView newValidator = new ValidatorView(currentX, currentY, validator.getClazz());
                newValidator.setModel(ModelMap.getDefault().getPC4NBModelFor(validator));
                this.add(newValidator);

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
    public void recomputeHeightAndRedraw() {
        super.recomputeHeightAndRedraw();
        ValidationModel model = (ValidationModel) getModel();

        if (model != null && model.getValidation() != null) {
            List<Validator> validators = model.getValidation().getValidator();
            int columns = (int) ((getWidth() - INSET) / (SECOND_LEVEL_RECTANGLE_WIDTH + INSET));
            this.setHeight((int) (TOP_INDENT + (Math.ceil((double) validators.size() / (double) columns) * (PERFCAKE_RECTANGLE_HEIGHT + INSET))));
        }

        drawChildren();
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
}
