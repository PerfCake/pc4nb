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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import static org.perfcake.pc4nb.ui.SizeConstraints.TOP_LEVEL_RECTANGLE_WIDTH;

public class PC4NBView extends AbstractPC4NBView implements PropertyChangeListener, FocusListener {
    public static final int TOP_INDENT = 50;

    private Border defaultBorder = new LineBorder(Color.BLACK, 1, true);
    private Border selectedBorder = new LineBorder(Color.BLACK, 2, true);
    private JPanel headerPanel = new JPanel();
    private JPanel contentPanel = new JPanel();
    
    private JLabel header = new JLabel("Header");

    public PC4NBView() {
        setFocusable(true);
        setBorder(defaultBorder);

        setLayout(new BorderLayout());
        headerPanel.add(header);
        add(headerPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        add(contentPanel, BorderLayout.CENTER);

        addMouseListener(new SelectedMouseAdapter());
        addFocusListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = TOP_LEVEL_RECTANGLE_WIDTH;
        
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension size = super.getPreferredSize();
        size.width = TOP_LEVEL_RECTANGLE_WIDTH;
        
        return size;
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    @Override
    public Component add(Component comp) {
        return contentPanel.add(comp);
    }

    @Override
    public void removeAll() {
        contentPanel.removeAll();
    }

    public Border getDefaultBorder() {
        return defaultBorder;
    }

    public void setDefaultBorder(Border defaultBorder) {
        this.defaultBorder = defaultBorder;
    }

    public Border getSelectedBorder() {
        return selectedBorder;
    }

    public void setSelectedBorder(Border selectedBorder) {
        this.selectedBorder = selectedBorder;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        return;
    }

    @Override
    public void focusGained(FocusEvent e) {
        setBorder(selectedBorder);
    }

    @Override
    public void focusLost(FocusEvent e) {
        setBorder(defaultBorder);
    }

    private class SelectedMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) || SwingUtilities.isRightMouseButton(event)) {
                requestFocusInWindow();
            }
        }
    }
}
