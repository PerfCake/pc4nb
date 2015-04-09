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
import java.awt.Label;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static org.perfcake.pc4nb.ui.LayoutSizeConstants.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui.LayoutSizeConstants.TOP_LEVEL_RECTANGLE_WIDHT;

/**
 *
 * @author Andrej Halaj
 */
public class ContentView extends JPanel {
    Border border;

    public ContentView() {
        border = new TitledBorder("content");
        this.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        
        this.setBackground(Color.WHITE);
        this.setBorder(border);
        
        this.setPreferredSize(new Dimension(TOP_LEVEL_RECTANGLE_WIDHT, PERFCAKE_RECTANGLE_HEIGHT));
//        this.setMinimumSize(new Dimension(300, 200));

    }

    public ContentView(String name) {
        this();
        border = new TitledBorder(name);
        this.setBorder(border);
    }
}
