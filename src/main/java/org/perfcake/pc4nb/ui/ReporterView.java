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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.ui.actions.DeleteReportersAction;
import org.perfcake.pc4nb.ui.actions.EditReporterAction;

public class ReporterView extends SecondLevelView {
    private JMenuItem editComponent = new JMenuItem("Edit reporter");
    private JMenuItem deleteComponent = new JMenuItem("Delete reporter");
    private JPopupMenu menu = new JPopupMenu();

    public ReporterView(int x, int y, String header) {
        super(x, y, header);
        setColor(Color.RED);

        editComponent.addActionListener(new EditReporterListener());
        menu.add(editComponent);

        deleteComponent.addActionListener(new DeleteReporterListener());
        menu.add(deleteComponent);

        this.setComponentPopupMenu(menu);
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        ReporterModel reporterModel = (ReporterModel) getModel();
        setHeader(reporterModel.getReporter().getClazz());
    }
    
    private class EditReporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EditReporterAction action = new EditReporterAction((ReporterModel) getModel());
            action.execute();
        }
    }

    private class DeleteReporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ReportingView reportingView = (ReportingView) getParent();
            Reporter reporter = ((ReporterModel) getModel()).getReporter();

            if (reportingView != null) {
                DeleteReportersAction action = new DeleteReportersAction(reportingView.getModel(), reporter);
                action.execute();
            }
        }
    }
}
