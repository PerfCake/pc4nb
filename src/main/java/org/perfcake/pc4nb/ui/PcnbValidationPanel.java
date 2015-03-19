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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.perfcake.pc4nb.wizards.AddReporterWizardPanel1;
import org.perfcake.pc4nb.wizards.AddValidatorWizardPanel1;

/**
 *
 * @author Andrej Halaj
 */
public class PcnbValidationPanel extends JPanel implements ActionListener {
    private JMenuItem addComponent = new JMenuItem("Add new validator");
    private JPopupMenu menu = new JPopupMenu();
    
    public PcnbValidationPanel() {
        addComponent.addActionListener(this);
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);

        Border border = new TitledBorder("Validation");
        this.setBackground(Color.white);
        this.setBorder(border);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setPreferredSize(new Dimension(390, 300));
        this.setMinimumSize(new Dimension(390, 300));
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new AddValidatorWizardPanel1());
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
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Add Validator");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            String text = (String) wiz.getProperty("validator-type");
            JButton newButton = new JButton(text);
            newButton.setMinimumSize(new Dimension(120, 45));
            newButton.setPreferredSize(new Dimension(120, 45));
            this.add(newButton);

            this.revalidate();
            this.repaint();
        }
    }
}