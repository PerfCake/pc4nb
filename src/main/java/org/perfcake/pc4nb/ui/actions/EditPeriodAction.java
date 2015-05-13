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
import org.perfcake.pc4nb.model.PeriodModel;
import org.perfcake.pc4nb.ui.wizards.PeriodWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditPeriodAction extends AbstractPC4NBAction {
    private PeriodWizardPanel wizardPanel;
    private PeriodModel periodModel;

    public EditPeriodAction(PeriodModel periodModel) {
        this.periodModel = periodModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new PeriodWizardPanel();
        wizardPanel.getComponent().setModel(periodModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        periodModel.setType((String) wiz.getProperty("period-type"));
        periodModel.setValue((String) wiz.getProperty("period-value"));
    }
}
