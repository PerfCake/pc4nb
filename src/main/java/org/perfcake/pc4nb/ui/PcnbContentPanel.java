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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Andrej Halaj
 */
public class PcnbContentPanel extends JPanel implements ActionListener {

    private JMenuItem addComponent = new JMenuItem("Add new component");
    private JPopupMenu menu = new JPopupMenu();

    public PcnbContentPanel(String title) {
        addComponent.addActionListener(this);
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        
        Border border = new TitledBorder(title);
        this.setBackground(Color.white);
        this.setBorder(border);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setPreferredSize(new Dimension(390, 300));
        this.setMinimumSize(new Dimension(390, 300));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
