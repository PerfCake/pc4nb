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
import org.perfcake.model.Header;
import org.perfcake.pc4nb.core.model.HeaderModel;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.tableModel.HeadersTableModel;
import org.perfcake.pc4nb.wizards.HeaderWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.MessageVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditHeaderAction extends AbstractPC4NBAction {
    int selectedRow = 0;
    MessageVisualPanel sourcePanel;

    public EditHeaderAction(AbstractPC4NBVisualPanel source) {
        super(source);
        
        sourcePanel = (MessageVisualPanel) getSource();
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        JTable headersTable = sourcePanel.getHeadersTable();
        HeadersTableModel headersTableModel = sourcePanel.getHeadersTableModel();
        HeaderWizardPanel headerWizardPanel = new HeaderWizardPanel();

        if (headersTable.getSelectedRowCount() > -1) {
            selectedRow = headersTable.getSelectedRow();
        }
        
        String headerName = (String) headersTableModel.getValueAt(selectedRow, 0);
        String headerValue = (String) headersTableModel.getValueAt(selectedRow, 1);
        
        headerWizardPanel.getComponent().getNameTextField().setText(headerName);
        headerWizardPanel.getComponent().getValueTextField().setText(headerValue);

        return headerWizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        HeaderModel newHeaderModel = new HeaderModel(new Header());
        newHeaderModel.setName((String) wiz.getProperty("header-name"));
        newHeaderModel.setValue((String) wiz.getProperty("header-value"));
        
        sourcePanel.getHeadersTableModel().updateRow(selectedRow, newHeaderModel.getHeader());
        ModelMap.getDefault().addEntry(newHeaderModel.getHeader(), newHeaderModel);
    }
}
