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
import java.util.List;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.PropertiesModel;
import static org.perfcake.pc4nb.ui.SizeConstraints.INSET;
import static org.perfcake.pc4nb.ui.SizeConstraints.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui.SizeConstraints.SECOND_LEVEL_RECTANGLE_WIDTH;

/**
 *
 * @author Andrej Halaj
 */
public class PropertiesView extends PC4NBView {

    public PropertiesView(int x, int y, int width) {
        super(x, y, width);
        setColor(Color.GRAY);
        setHeader("Properties");
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.GRAY);
        PropertiesModel model = (PropertiesModel) getModel();

        if (model != null && model.getProperties()!= null) {
            List<Property> properties = model.getProperties().getProperty();

            int currentX = INSET;
            int currentY = TOP_INDENT;
            
            this.removeAll();

            for (Property property : properties) {
                SecondLevelView newMessage = new SecondLevelView(currentX, currentY, property.getName());
                this.add(newMessage);

                if (currentX + 2 * (SECOND_LEVEL_RECTANGLE_WIDTH + INSET) > getWidth()) {
                    currentX = INSET;
                    currentY += PERFCAKE_RECTANGLE_HEIGHT + INSET;
                } else {
                    currentX += SECOND_LEVEL_RECTANGLE_WIDTH + INSET;
                }
            }
        }
    }

    @Override
    public void recomputeHeight() {
        super.recomputeHeight();
        PropertiesModel model = (PropertiesModel) getModel();

        if (model != null && model.getProperties()!= null) {
            List<Property> properties = model.getProperties().getProperty();
            int columns = (int) ((getWidth() - INSET) / (SECOND_LEVEL_RECTANGLE_WIDTH + INSET));
            this.setHeight((int) (TOP_INDENT + (Math.ceil((double) properties.size() / (double) columns) * (PERFCAKE_RECTANGLE_HEIGHT + INSET))));
        }
    }
}
