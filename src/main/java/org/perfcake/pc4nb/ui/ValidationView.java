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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.perfcake.pc4nb.core.model.ValidationModel;
import org.perfcake.pc4nb.core.model.ValidatorModel;
import org.perfcake.pc4nb.wizards.ValidatorWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class ValidationView extends TopLevelView implements ActionListener {

    private JMenuItem addComponent = new JMenuItem("Add new validator");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ValidationView.ValidatorTransferHandler();

    public ValidationView() {
        super("Validation");
        addComponent.addActionListener(this);
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        this.setTransferHandler(transferHandler);
    }

    public void refreshView(ValidationModel model) {
        this.removeAll();

        for (int i = 0; i < model.getValidation().getValidator().size(); i++) {
            SecondLevelView newValidatorView = new SecondLevelView(model.getValidation().getValidator().get(i).getClazz());
            this.add(newValidatorView);
        }

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new ValidatorWizardPanel());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Add Validator");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            String text = (String) wiz.getProperty("validator-type");

        }
    }

    private final class ValidatorTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(ValidatorModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferSupport support) {
            try {
                ValidatorModel model = (ValidatorModel) support.getTransferable().getTransferData(ValidatorModel.DATA_FLAVOR);
                ((ValidationModel) getModel()).addValidator(model.getValidator());

                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
}
