/*
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import org.perfcake.pc4nb.model.GeneratorModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.ui.actions.EditGeneratorAction;

import static org.perfcake.pc4nb.model.GeneratorModel.PROPERTY_CLASS;

/**
 *
 * @author Andrej Halaj
 */
public class GeneratorView extends PC4NBView {
    private JMenuItem editComponent = new JMenuItem("Edit generator");
    private JPopupMenu menu = new JPopupMenu();
    TransferHandler transferHandler = new GeneratorTransferHandler();

    public GeneratorView() {
        setDefaultBorder(new LineBorder(Color.GREEN, 1, true));
        setBorder(getDefaultBorder());
        setHeader("Generator");

        editComponent.addActionListener(new EditGeneratorListener());

        menu.add(editComponent);
        this.setComponentPopupMenu(menu);

        setTransferHandler(transferHandler);
        addMouseListener(new GeneratorMouseListener());
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        setHeader(resolveAndGetHeader());
    }
    
    private String resolveAndGetHeader() {
        GeneratorModel generatorModel = (GeneratorModel) getModel();
        
        return generatorModel.getGenerator().getClazz();
    }

    private class GeneratorMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                runEditWizard();
            }
        }
    }

    private class EditGeneratorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runEditWizard();
        }
    }

    private void runEditWizard() {
        EditGeneratorAction action = new EditGeneratorAction((GeneratorModel) getModel());
        action.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PROPERTY_CLASS:
                setHeader(resolveAndGetHeader());
                break;
            default:
                break;
        }
    }

    private final class GeneratorTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(GeneratorModel.DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                GeneratorModel oldModel = (GeneratorModel) getModel();
                GeneratorModel model = (GeneratorModel) support.getTransferable().getTransferData(GeneratorModel.DATA_FLAVOR);
                
                model.setThreads(oldModel.getGenerator().getThreads()); // keep old threads setting
                
                setModel(model);
                revalidate();
                repaint();

                return true;
            } catch (UnsupportedFlavorException | IOException ex) {
                return false;
            }
        }
    }
}
