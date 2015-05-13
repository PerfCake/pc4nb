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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.wizards.PeriodWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddPeriodAction extends AbstractPC4NBAction {
    private PC4NBModel to;

    public AddPeriodAction(PC4NBModel to) {
        this.to = to;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        PeriodWizardPanel wizardPanel = new PeriodWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        Period period = new Period();
        period.setType((String) wiz.getProperty("period-type"));
        period.setValue((String) wiz.getProperty("period-value"));

        DestinationModel destinationModel = (DestinationModel) to;
        destinationModel.addPeriod(period);
    }
}
