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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Andrej Halaj
 */
public class Pc4nbContentPanel extends JPanel implements ActionListener {
    
    private JMenuItem addComponent = new JMenuItem("Add new component");
    private JPopupMenu menu = new JPopupMenu();
    
    public Pc4nbContentPanel(String title) {
        addComponent.addActionListener(this);
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        Border border = new TitledBorder(title);
        this.setBackground(Color.white);
        this.setBorder(border);
        this.setLayout(new GridLayout(0, 2, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                Pc4nbContentPanel.this.add(new JButton("Hello"));
//            }
//        });
        this.add(new JButton("Hello"));
        this.revalidate();
        this.repaint();
    }  
}
