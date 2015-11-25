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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Run;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.RunModel;
import static org.perfcake.pc4nb.model.RunModel.PROPERTY_TYPE;
import static org.perfcake.pc4nb.model.RunModel.PROPERTY_VALUE;
import org.perfcake.pc4nb.ui.actions.EditRunAction;

/**
 *
 * @author Andrej Halaj
 */
public class RunView extends PC4NBView {
    private JMenuItem editComponent = new JMenuItem("Edit run");
    private JPopupMenu menu = new JPopupMenu();

    public RunView() {
        setDefaultBorder(new LineBorder(Color.YELLOW, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Run");

        editComponent.addActionListener(new EditRunListener());

        menu.add(editComponent);
        this.setComponentPopupMenu(menu);

        addMouseListener(new RunMouseListener());
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
    }
    
    private String resolveAndGetHeader() {
        RunModel runModel = (RunModel) getModel();
        Run run = runModel.getRun();
        
        return run.getType().toLowerCase() + ": " + run.getValue().toLowerCase();
    }

    private class RunMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class EditRunListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private void runEditWizard() {
        EditRunAction action = new EditRunAction((RunModel) getModel());
        action.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PROPERTY_TYPE:
            case PROPERTY_VALUE:
                setHeader(resolveAndGetHeader());
                break;
            default:
                break;
        }
    }
}
