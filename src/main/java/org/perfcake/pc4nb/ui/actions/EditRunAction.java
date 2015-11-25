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
import org.perfcake.pc4nb.model.RunModel;
import org.perfcake.pc4nb.ui.wizards.RunWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditRunAction extends AbstractPC4NBAction {
    private RunWizardPanel wizardPanel;
    private RunModel runModel;

    public EditRunAction(RunModel runModel) {
        this.runModel = runModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new RunWizardPanel();
        wizardPanel.getComponent().setModel(runModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        runModel.setType((String) wiz.getProperty("run-type"));
        runModel.setValue((String) wiz.getProperty("run-value"));
    }
}
