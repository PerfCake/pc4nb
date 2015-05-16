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
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;

/**
 *
 * @author Andrej Halaj
 */
public class PropertyView extends SecondLevelView {
    private JMenuItem deleteComponent = new JMenuItem("Delete property");
    private JPopupMenu menu = new JPopupMenu();

    public PropertyView(int x, int y, String header) {
        super(x, y, header);
        setDefaultBorder(new LineBorder(Color.GRAY, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Property");
        menu.add(deleteComponent);

        deleteComponent.addActionListener(new DeletePropertyListener());

        setComponentPopupMenu(menu);
    }

    @Override
    public final void setModel(PC4NBModel model) {
        super.setModel(model);

        PropertyModel propertyModel = (PropertyModel) getModel();

        if (propertyModel != null && propertyModel.getProperty() != null) {
            setHeader(propertyModel.getProperty().getName());
        }
    }


    public class DeletePropertyListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }
}
