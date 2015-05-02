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

import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import org.perfcake.model.Scenario;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.ReporterModel;
import org.perfcake.pc4nb.core.model.ReportingModel;
import org.perfcake.pc4nb.ui.actions.AddReporterAction;

/**
 *
 * @author Andrej Halaj
 */
public class ReportingView extends TopLevelView {

    private JMenuItem addComponent = new JMenuItem("Add new reporter");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ReporterTansferHandler();

    public ReportingView() {
        super("Reporting");
        menu.add(addComponent);
        addComponent.addActionListener(new AddReporterAction(this));
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof ReportingModel) {
            ReportingModel model = (ReportingModel) evt.getSource();

            this.removeAll();

            for (Scenario.Reporting.Reporter reporter : model.getReporting().getReporter()) {
                this.add(new SecondLevelView(reporter.getClazz()));
            }
        }
    }

    private final class ReporterTansferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(ReporterModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                ReporterModel model = (ReporterModel) support.getTransferable().getTransferData(ReporterModel.DATA_FLAVOR);
                ((ReportingModel) getModel()).addReporter(model.getReporter());
                ModelMap.getDefault().addEntry(model.getReporter(), model);

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}
