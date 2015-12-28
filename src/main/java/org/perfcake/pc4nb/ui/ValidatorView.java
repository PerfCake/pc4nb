/*
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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import static org.perfcake.pc4nb.model.ValidatorModel.PROPERTY_CLASS;
import static org.perfcake.pc4nb.model.ValidatorModel.PROPERTY_ID;
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

    public ValidatorView(PC4NBModel model) {
        super(model);
        setHeader(resolveAndGetHeader());

        setDefaultBorder(new LineBorder(Color.MAGENTA, 1, true));
        setBorder(getDefaultBorder());

        editComponent.addActionListener(new EditValidatorListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteValidatorListener());
        menu.add(deleteComponent);

        this.setComponentPopupMenu(menu);
        addMouseListener(new ValidatorMouseAdapter());
        addKeyListener(new ValidatorKeyAdapter());
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
    }

    private String resolveAndGetHeader() {
        ValidatorModel validatorModel = (ValidatorModel) getModel();
        String validatorClazz = validatorModel.getValidator().getClazz();
        String validatorId = validatorModel.getValidator().getId();
        
        return "(" + validatorId + ") " + validatorClazz;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PROPERTY_CLASS:
            case PROPERTY_ID:
                setHeader(resolveAndGetHeader());
                revalidate();
                repaint();
        }
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
