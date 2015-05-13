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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import org.perfcake.pc4nb.ui.actions.DeleteValidatorAction;
import org.perfcake.pc4nb.ui.actions.EditValidatorAction;

/**
 *
 * @author Andrej Halaj
 */
public class ValidatorView extends SecondLevelView {
    private JMenuItem editComponent = new JMenuItem("Edit validator");
    private JMenuItem deleteComponent = new JMenuItem("Delete validator");
    private JPopupMenu menu = new JPopupMenu();

    public ValidatorView(int x, int y, String header) {
        super(x, y, header);
        setColor(Color.MAGENTA);

        editComponent.addActionListener(new EditValidatorListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteValidatorListener());
        menu.add(deleteComponent);

        this.setComponentPopupMenu(menu);
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        ValidatorModel validatorModel = (ValidatorModel) getModel();
        setHeader(validatorModel.getValidator().getClazz());
    }

    private class EditValidatorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditValidatorAction action = new EditValidatorAction((ValidatorModel) getModel());
            action.execute();
        }
    }

    private class DeleteValidatorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ValidationView validationView = (ValidationView) getParent();
            Validator validator = ((ValidatorModel) getModel()).getValidator();

            if (validationView != null) {
                DeleteValidatorAction action = new DeleteValidatorAction(validationView.getModel(), validator);
                action.execute();
            }
        }
    }
}
