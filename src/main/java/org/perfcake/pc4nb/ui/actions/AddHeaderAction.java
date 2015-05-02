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
package org.perfcake.pc4nb.ui.actions;

import org.openide.WizardDescriptor;
import org.perfcake.model.Header;
import org.perfcake.pc4nb.core.model.HeaderModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.wizards.HeaderWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.MessageVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddHeaderAction extends AbstractPC4NBAction {

    public AddHeaderAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }


    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        HeaderWizardPanel headerWizardPanel = new HeaderWizardPanel();
        
        return headerWizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        MessageVisualPanel sourceMessagePanel = (MessageVisualPanel) getSource();

        String name = (String) wiz.getProperty("header-name");
        String value = (String) wiz.getProperty("header-value");
        
        HeaderModel headerModel = new HeaderModel(new Header());
        headerModel.setName(name);
        headerModel.setValue(value);
        
        ModelMap.getDefault().addEntry(headerModel.getHeader(), headerModel);
        
        sourceMessagePanel.getHeadersTableModel().addRow(headerModel.getHeader());
    }
}
