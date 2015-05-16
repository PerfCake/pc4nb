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
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import static org.perfcake.pc4nb.ui.SizeConstraints.PERFCAKE_RECTANGLE_HEIGHT;

/**
 *
 * @author Andrej Halaj
 */
public class PC4NBView extends AbstractPC4NBView implements PropertyChangeListener {
    public static final double CORNER_ARC = 10;
    public static final int TOP_INDENT = 50;

    private String header = "";
    private Border defaultBorder = new LineBorder(Color.BLACK, 1, true);
    private Border selectedBorder = new LineBorder(Color.BLACK, 2, true);

    public PC4NBView(int x, int y, int width) {
        this.setBounds(x, y, width, PERFCAKE_RECTANGLE_HEIGHT);
        setFocusable(true);
        setBorder(defaultBorder);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    requestFocusInWindow();
                }
            }
        });

        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                setBorder(selectedBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(defaultBorder);
            }
        });
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        if (header != null) {
            this.header = header;
        }
    }

    public void setX(int x) {
        this.setBounds(x, (int) getLocation().getY(), (int) getSize().getWidth(), PERFCAKE_RECTANGLE_HEIGHT);
    }

    public void setY(int y) {
        this.setBounds((int) getLocation().getX(), y, (int) getSize().getWidth(), PERFCAKE_RECTANGLE_HEIGHT);
    }

    public void setWidth(int width) {
        this.setBounds((int) getLocation().getX(), (int) getLocation().getY(), width, (int) getSize().getHeight());
    }

    public void setHeight(int height) {
        this.setBounds((int) getLocation().getX(), (int) getLocation().getY(), (int) getSize().getWidth(), height);
    }

    public void recomputeHeightAndRedraw() {
        //this.setBounds((int) getLocation().getX(), (int) getLocation().getY(), (int) getSize().getWidth(), PERFCAKE_RECTANGLE_HEIGHT);
    }

    public void printHeader(Graphics2D graphics, String text) {
        if (graphics.getFontMetrics() != null) {
            int stringLen = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
            double start = getWidth() / 2 - stringLen / 2;
            graphics.drawString(text, (float) start, 20.0f);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        return;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2d = (Graphics2D) g;

        printHeader(g2d, getHeader());
    }
}
