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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertiesModel;
import org.perfcake.pc4nb.ui.actions.EditScenarioPropertiesAction;

public final class PropertiesView extends PC4NBView {
    private JMenuItem editProperties = new JMenuItem("Edit Properties");
    private JPopupMenu menu = new JPopupMenu();

    public PropertiesView() {
        setDefaultBorder(new LineBorder(Color.GRAY, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Properties");

        editProperties.addActionListener(new EditScenarioPropertiesListener());

        menu.add(editProperties);
        this.setComponentPopupMenu(menu);
    }
    
    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        drawChildren();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertiesModel.PROPERTY_PROPERTIES)) {
            drawChildren();
        }
    }

    public void drawChildren() {
        removeAll();

        PropertiesModel model = (PropertiesModel) getModel();

        if (model != null && model.getProperties() != null) {
            for (Property property : model.getProperty()) {
                add(new PropertyView(ModelMap.getDefault().getPC4NBModelFor(property)));
            }
        }
        
        revalidate();
        repaint();
    }

    private class EditScenarioPropertiesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditScenarioPropertiesAction action = new EditScenarioPropertiesAction((PropertiesModel) getModel());
            action.execute();
        }
    }
}
