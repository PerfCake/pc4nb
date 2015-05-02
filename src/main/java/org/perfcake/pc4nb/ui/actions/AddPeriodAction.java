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
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.PeriodModel;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.wizards.PeriodWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddPeriodAction extends AbstractPC4NBAction {

    public AddPeriodAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        PeriodWizardPanel wizardPanel = new PeriodWizardPanel();
        
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        PeriodModel periodModel = new PeriodModel(new Period());
        periodModel.setType((String) wiz.getProperty("period-type"));
        periodModel.setValue((String) wiz.getProperty("period-value"));

        DestinationVisualPanel sourcePanel = (DestinationVisualPanel) getSource();
        sourcePanel.getPeriodsTableModel().addRow(periodModel.getPeriod());
        ModelMap.getDefault().addEntry(periodModel.getPeriod(), periodModel);
    }
}
