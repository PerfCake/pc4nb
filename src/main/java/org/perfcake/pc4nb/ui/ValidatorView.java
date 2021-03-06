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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import static org.perfcake.pc4nb.model.ValidatorModel.PROPERTY_CLASS;
import static org.perfcake.pc4nb.model.ValidatorModel.PROPERTY_ID;
import org.perfcake.pc4nb.ui.actions.DeleteValidatorAction;
import org.perfcake.pc4nb.ui.actions.EditValidatorAction;

public class ValidatorView extends SecondLevelView {
    private JLabel idLabel = new JLabel("ID: ");
    private JLabel idString = new JLabel("");
    private JMenuItem editComponent = new JMenuItem("Edit validator");
    private JMenuItem deleteComponent = new JMenuItem("Delete validator");
    private JPopupMenu menu = new JPopupMenu();

    public ValidatorView(PC4NBModel model) {
        super(model);
        setHeader(resolveAndGetHeader());
        setValidatorId(((ValidatorModel) model).getValidator().getId());

        setDefaultBorder(new LineBorder(Color.MAGENTA, 1, true));
        setBorder(getDefaultBorder());

        editComponent.addActionListener(new EditValidatorListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteValidatorListener());
        menu.add(deleteComponent);
        
        add(idLabel);
        add(idString);

        this.setComponentPopupMenu(menu);
        addMouseListener(new ValidatorMouseAdapter());
        addKeyListener(new ValidatorKeyAdapter());
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
        setValidatorId(((ValidatorModel) model).getValidator().getId());
    }

    private String resolveAndGetHeader() {
        ValidatorModel validatorModel = (ValidatorModel) getModel();
        String validatorClazz = validatorModel.getValidator().getClazz();
        
        return validatorClazz;
    }
    
    private void setValidatorId(String id) {
        if (idString == null) {
            idString = new JLabel();
        }
        
        idString.setText(id);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PROPERTY_CLASS:
                setHeader(resolveAndGetHeader());
                break;
            case PROPERTY_ID:
                setValidatorId(((ValidatorModel) getModel()).getValidator().getId());
                break;
            default:
                break;
        }
        
        revalidate();
        repaint();
    }

    private class ValidatorKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isFocusOwner() && e.getKeyCode() == KeyEvent.VK_DELETE) {
                runDeleteWizard();
            }
        }
    }

    private class ValidatorMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class EditValidatorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private class DeleteValidatorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runDeleteWizard();
        }
    }

    private void runEditWizard() {
        EditValidatorAction action = new EditValidatorAction((ValidatorModel) getModel());
        action.execute();
    }

    private void runDeleteWizard() {
        ValidationView validationView = (ValidationView) getParent().getParent();
        Validator validator = ((ValidatorModel) getModel()).getValidator();

        if (validationView != null) {
            DeleteValidatorAction action = new DeleteValidatorAction(validationView.getModel(), validator);
            action.execute();
        }
    }
}
