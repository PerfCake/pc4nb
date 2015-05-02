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
import java.beans.PropertyChangeEvent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.perfcake.pc4nb.core.model.DestinationModel;

/**
 *
 * @author Andrej Halaj
 */
public class ThirdLevelView extends AbstractPC4NBVisualPanel {

    Border border;
     
    public ThirdLevelView() {
        border = new TitledBorder("content");
        this.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        
        this.setBackground(Color.WHITE);
        this.setBorder(border);
        
        this.setPreferredSize(new Dimension(80, 35));
//        this.setMinimumSize(new Dimension(300, 200));

    }

    public ThirdLevelView(String name) {
        this();
        border = new TitledBorder(name);
        this.setBorder(border);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof DestinationModel) {
            DestinationModel model = (DestinationModel) evt.getSource();
            
            border = new TitledBorder(model.getDestination().getClazz());
        }
    }
}
