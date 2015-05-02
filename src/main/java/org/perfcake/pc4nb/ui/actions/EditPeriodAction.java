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

import javax.swing.JTable;
import org.openide.WizardDescriptor;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.PeriodModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.tableModel.PeriodsTableModel;
import org.perfcake.pc4nb.wizards.PeriodWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.DestinationVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditPeriodAction extends AbstractPC4NBAction {
    private PeriodsTableModel periodsTableModel;
    int selectedRow = 0;
    DestinationVisualPanel sourcePanel;

    public EditPeriodAction(AbstractPC4NBVisualPanel source) {
        super(source);
        
        sourcePanel = (DestinationVisualPanel) getSource();
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        periodsTableModel = sourcePanel.getPeriodsTableModel();
        
        PeriodWizardPanel wizardPanel = new PeriodWizardPanel();

        JTable periodsTable = sourcePanel.getPeriodsTable();
        if (periodsTable.getSelectedRowCount() > -1) {
            selectedRow = periodsTable.getSelectedRow();
        }

        wizardPanel.getComponent().getPeriodSelection().setSelectedItem(periodsTableModel.getValueAt(selectedRow, 0));
        wizardPanel.getComponent().getPeriodValueSpinner().setValue((int) periodsTableModel.getValueAt(selectedRow, 1));

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        PeriodModel periodModel = new PeriodModel(new Period());
        periodModel.setType((String) wiz.getProperty("period-type"));
        periodModel.setValue((String) wiz.getProperty("period-value"));

        DestinationVisualPanel sourcePanel = (DestinationVisualPanel) getSource();
        sourcePanel.getPeriodsTableModel().updateRow(selectedRow, periodModel.getPeriod());
        ModelMap.getDefault().addEntry(periodModel.getPeriod(), periodModel);
    }
}
