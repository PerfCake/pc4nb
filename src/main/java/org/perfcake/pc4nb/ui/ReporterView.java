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
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.model.ReportingModel;
import org.perfcake.pc4nb.ui.actions.DeleteReportersAction;
import org.perfcake.pc4nb.ui.actions.EditReporterAction;

public class ReporterView extends SecondLevelView {
    private JMenuItem editComponent = new JMenuItem("Edit reporter");
    private JMenuItem deleteComponent = new JMenuItem("Delete reporter");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new DestinationTransferHandler();

    public ReporterView(int x, int y, String header) {
        super(x, y, header);
        setColor(Color.RED);

        menu.add(editComponent);
        editComponent.addActionListener(new EditReporterListener());

        menu.add(deleteComponent);
        deleteComponent.addActionListener(new DeleteReporterListener());

        setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
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
    
    private final class DestinationTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(DestinationModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                DestinationModel model = (DestinationModel) support.getTransferable().getTransferData(DestinationModel.DATA_FLAVOR);
                ((ReporterModel) getModel()).addDestination(model.getDestination());

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}
