/*
 * Copyright (c) 2015 Andrej Halaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfcake.pc4nb.ui.wizards.visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.model.ReportingModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import org.perfcake.pc4nb.ui.tableModel.ReportersTableModel;
import org.perfcake.pc4nb.ui.actions.AddReporterAction;
import org.perfcake.pc4nb.ui.actions.DeleteReportersAction;
import org.perfcake.pc4nb.ui.actions.EditReporterAction;

public final class ReportingVisualPanel extends AbstractPC4NBView {

    public ReportingVisualPanel() {
        setModel(new ReportingModel(new Scenario.Reporting()));

        for (Reporter reporter : ((ReportingModel) getModel()).getReporting().getReporter()) {
            ModelMap.getDefault().getPC4NBModelFor(reporter).addPropertyChangeListener(this);
        }

        ModelMap.getDefault().addEntry(((ReportingModel) getModel()).getReporting(), getModel());
        initComponents();

        addReporterButton.addActionListener(new AddReporterListener());
        editReporterButton.addActionListener(new EditReporterListener());
        deleteReporterButton.addActionListener(new DeleteReporterListener());
    }

    @Override
    public String getName() {
        return "Reporting";
    }

    public ReportersTableModel getReportersTableModel() {
        return reportersTableModel;
    }

    public JTable getReportersTable() {
        return reportersTable;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        reportersTable = new javax.swing.JTable();
        addReporterButton = new javax.swing.JButton();
        editReporterButton = new javax.swing.JButton();
        deleteReporterButton = new javax.swing.JButton();
        reportersLabel = new javax.swing.JLabel();

        reportersTableModel = new ReportersTableModel();
        reportersTable.setModel(reportersTableModel);
        jScrollPane2.setViewportView(reportersTable);

        org.openide.awt.Mnemonics.setLocalizedText(addReporterButton, org.openide.util.NbBundle.getMessage(ReportingVisualPanel.class, "ReportingVisualPanel.addReporterButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editReporterButton, org.openide.util.NbBundle.getMessage(ReportingVisualPanel.class, "ReportingVisualPanel.editReporterButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteReporterButton, org.openide.util.NbBundle.getMessage(ReportingVisualPanel.class, "ReportingVisualPanel.deleteReporterButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(reportersLabel, org.openide.util.NbBundle.getMessage(ReportingVisualPanel.class, "ReportingVisualPanel.reportersLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reportersLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addReporterButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editReporterButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteReporterButton, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                        .addGap(47, 47, 47))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(reportersLabel)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(addReporterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editReporterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteReporterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(256, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addReporterButton;
    private javax.swing.JButton deleteReporterButton;
    private javax.swing.JButton editReporterButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel reportersLabel;
    private javax.swing.JTable reportersTable;
    private ReportersTableModel reportersTableModel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ReportingModel model = (ReportingModel) getModel();
        List<Reporter> reportersList = model.getReporting().getReporter();
        int targetIndex;

        switch (evt.getPropertyName()) {
            case ReportingModel.PROPERTY_REPORTERS:
                if (evt.getNewValue() != null) {
                    targetIndex = reportersList.indexOf(evt.getNewValue());
                    reportersTableModel.insertRow(targetIndex, (Reporter) evt.getNewValue());
                } else if (evt.getOldValue() != null) {
                    targetIndex = reportersTableModel.getReporters().indexOf(evt.getOldValue());
                    reportersTableModel.removeRow(targetIndex);
                } else {
                    // error
                }
                break;
            case ReporterModel.PROPERTY_CLASS:
            case ReporterModel.PROPERTY_ENABLED:
                ReporterModel reporterModel = (ReporterModel) evt.getSource();
                Reporter reporter = reporterModel.getReporter();
                targetIndex = reportersTableModel.getReporters().indexOf(reporter);
                reportersTableModel.updateRow(targetIndex, reporter);
                break;
            default:
                break;
        }
    }

    private class AddReporterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddReporterAction action = new AddReporterAction(getModel());
            action.execute();
        }
    }

    private class EditReporterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ReportingVisualPanel.this.getReportersTable().getSelectedRow();

            if (selectedRow != -1) {
                ReportingModel reportingModel = (ReportingModel) ReportingVisualPanel.this.getModel();
                Reporter reporter = reportingModel.getReporting().getReporter().get(selectedRow);
                EditReporterAction action = new EditReporterAction((ReporterModel) ModelMap.getDefault().getPC4NBModelFor(reporter));
                action.execute();
            }
        }
    }

    private class DeleteReporterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedRows = ReportingVisualPanel.this.getReportersTable().getSelectedRows();
            ReportingModel reportingModel = (ReportingModel) ReportingVisualPanel.this.getModel();
            List<Reporter> toRemove = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Reporter reporter = reportingModel.getReporting().getReporter().get(selectedRows[i]);
                toRemove.add(reporter);
            }

            DeleteReportersAction action = new DeleteReportersAction((ReportingModel) getModel(), toRemove);
            action.execute();
        }
    }
}
