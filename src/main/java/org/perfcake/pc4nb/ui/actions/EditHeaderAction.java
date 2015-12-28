/*
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
import org.perfcake.pc4nb.model.HeaderModel;
import org.perfcake.pc4nb.ui.wizards.HeaderWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditHeaderAction extends AbstractPC4NBAction {
    private HeaderWizardPanel wizardPanel;
    private HeaderModel headerModel;

    public EditHeaderAction(HeaderModel headerModel) {
        this.headerModel = headerModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new HeaderWizardPanel();
        wizardPanel.getComponent().setModel(headerModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        headerModel.setName((String) wiz.getProperty("header-name"));
        headerModel.setValue((String) wiz.getProperty("header-value"));
    }
}
