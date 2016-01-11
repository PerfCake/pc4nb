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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.ReporterModel;
import static org.perfcake.pc4nb.model.ReporterModel.PROPERTY_CLASS;
import static org.perfcake.pc4nb.model.ReporterModel.PROPERTY_ENABLED;
import org.perfcake.pc4nb.ui.actions.DeleteReportersAction;
import org.perfcake.pc4nb.ui.actions.EditReporterAction;

public final class ReporterView extends SecondLevelView {
    private JLabel enabledLabel = new JLabel("Enabled: ");
    private JRadioButton enabledRadio = new JRadioButton();
    private JMenuItem editComponent = new JMenuItem("Edit reporter");
    private JMenuItem deleteComponent = new JMenuItem("Delete reporter");
    private JMenuItem turnOnOff = new JMenuItem("Turn reporter on/off");
    private JPopupMenu menu = new JPopupMenu();
    private TransferHandler transferHandler = new DestinationTransferHandler();

    public ReporterView(PC4NBModel model) {
        super(model);
        setHeader(resolveAndGetHeader());

        setDefaultBorder(new LineBorder(Color.RED, 1, true));
        setBorder(getDefaultBorder());

        menu.add(editComponent);
        editComponent.addActionListener(new EditReporterListener());

        menu.add(deleteComponent);
        deleteComponent.addActionListener(new DeleteReporterListener());
        
        menu.add(turnOnOff);
        turnOnOff.addActionListener(new TurnOnOffListener());

        setComponentPopupMenu(menu);
        setTransferHandler(transferHandler);

        addKeyListener(new ReporterKeyAdapter());
        addMouseListener(new ReporterMouseListener());
        
        add(enabledLabel);
        add(enabledRadio);
        
        enabledRadio.addActionListener(new EnabledRadioActionListener());
        
        ReporterModel reporterModel = (ReporterModel) model;
        if (reporterModel.isEnabled()) {
            setReporterEnabled(true);
        } else {
            setReporterEnabled(false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PROPERTY_CLASS:
                setHeader(resolveAndGetHeader());
                break;
            case PROPERTY_ENABLED:
                setReporterEnabled((boolean) evt.getNewValue());
                break;
            default:
                break;
        }
        
        revalidate();
        repaint();
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
        setReporterEnabled(((ReporterModel) getModel()).isEnabled());
    }
    
    public void setReporterEnabled(boolean enabled) {
        if (enabledRadio == null) {
            enabledRadio = new JRadioButton();
        }
        
        enabledRadio.setSelected(enabled);
    }

    private String resolveAndGetHeader() {
        ReporterModel reporterModel = (ReporterModel) getModel();

        return reporterModel.getReporter().getClazz();
    }

    private class ReporterKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isFocusOwner() && e.getKeyCode() == KeyEvent.VK_DELETE) {
                runDeleteWizard();
            }
        }
    }

    private class ReporterMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class EditReporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private class DeleteReporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runDeleteWizard();
        }
    }
    
    private class TurnOnOffListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ReporterModel reporterModel = (ReporterModel) getModel();
            reporterModel.setEnabled(!reporterModel.isEnabled());
        }
    }
    
    private class EnabledRadioActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton enabledRadio = (JRadioButton) e.getSource();
            ReporterView view = (ReporterView) enabledRadio.getParent().getParent();
            ReporterModel model = (ReporterModel) view.getModel();
            
            model.setEnabled(!model.isEnabled());
        } 
    }

    private void runEditWizard() {
        EditReporterAction action = new EditReporterAction((ReporterModel) getModel());
        action.execute();
    }

    private void runDeleteWizard() {
        ReportingView reportingView = (ReportingView) getParent().getParent();
        Reporter reporter = ((ReporterModel) getModel()).getReporter();

        if (reportingView != null) {
            DeleteReportersAction action = new DeleteReportersAction(reportingView.getModel(), reporter);
            action.execute();
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
