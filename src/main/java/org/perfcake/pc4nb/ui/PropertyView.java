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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.ui.actions.DeletePropertiesAction;

/**
 *
 * @author Andrej Halaj
 */
public class PropertyView extends SecondLevelView {
    private JMenuItem deleteComponent = new JMenuItem("Delete property");
    private JPopupMenu menu = new JPopupMenu();

    public PropertyView(PC4NBModel model) {
        super(model);
        setHeader(resolveAndGetHeader());

        setDefaultBorder(new LineBorder(Color.GRAY, 1, true));
        setBorder(getDefaultBorder());

        menu.add(deleteComponent);
        deleteComponent.addActionListener(new DeletePropertyListener());
        setComponentPopupMenu(menu);
        addKeyListener(new PropertyKeyAdapter());

    }

    @Override
    public final void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
    }

    private String resolveAndGetHeader() {
        PropertyModel propertyModel = (PropertyModel) getModel();

        return propertyModel.getProperty().getName();
    }

    private class PropertyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isFocusOwner() && e.getKeyCode() == KeyEvent.VK_DELETE) {
                runDeleteWizard();
            }
        }
    }

    private class DeletePropertyListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            runDeleteWizard();
        }

    }

    private void runDeleteWizard() {
        PropertiesView propertiesView = (PropertiesView) getParent().getParent();
        Property property = ((PropertyModel) getModel()).getProperty();

        if (propertiesView != null) {
            DeletePropertiesAction action = new DeletePropertiesAction(propertiesView.getModel(), property);
            action.execute();
        }
    }
}
