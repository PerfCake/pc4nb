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

package org.perfcake.pc4nb.ui;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.model.ModelMap;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.model.ReportingModel;
import org.perfcake.pc4nb.ui.actions.AddReporterAction;

public final class ReportingView extends PC4NBView {
    private JMenuItem addComponent = new JMenuItem("Add new reporter");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new ReporterTansferHandler();

    public ReportingView() {
        setHeader("Reporting");
        
        setDefaultBorder(new LineBorder(Color.RED, 1, true));
        setBorder(getDefaultBorder());

        addComponent.addActionListener(new AddReporterListener());
        menu.add(addComponent);
        this.setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        drawChildren();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ReportingModel.PROPERTY_REPORTERS)) {
            drawChildren();
        }
    }

    public void drawChildren() {
        removeAll();

        ReportingModel model = (ReportingModel) getModel();

        if (model != null && model.getReporting() != null) {
            for (Reporter reporter : model.getReporting().getReporter()) {
                add(new ReporterView(ModelMap.getDefault().getPC4NBModelFor(reporter)));
            }
        }

        revalidate();
        repaint();
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

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }

    private class AddReporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddReporterAction action = new AddReporterAction(getModel());
            action.execute();
        }
    }
}
