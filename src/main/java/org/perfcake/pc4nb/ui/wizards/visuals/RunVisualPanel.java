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
package org.perfcake.pc4nb.ui.wizards.visuals;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import org.perfcake.common.PeriodType;
import org.perfcake.model.Scenario.Run;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.RunModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;

public final class RunVisualPanel extends AbstractPC4NBView {

    /**
     * Creates new form EditRunVisualPanel1
     */
    public RunVisualPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Run";
    }

    public JComboBox getRunTypeComboBox() {
        return runTypeComboBox;
    }

    public JSpinner getRunValueSpinner() {
        return runValueSpinner;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        RunModel runModel = (RunModel) model;
        Run run = runModel.getRun();
        
        if (run != null) {
            runTypeComboBox.setSelectedItem(run.getType().toLowerCase());
            
            if (run.getValue() != null && !run.getValue().isEmpty()) {
                runValueSpinner.setValue(Integer.parseInt(run.getValue()));
            } else {
                runValueSpinner.setValue(1000);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        runTypeComboBox = new javax.swing.JComboBox();
        runValueSpinner = new javax.swing.JSpinner();
        runValueLabel = new javax.swing.JLabel();
        runTypeLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 440));

        List<PeriodType> periodTypes = Arrays.asList(PeriodType.values());
        List<String> periodTypeNamesList = new ArrayList<>();

        for (PeriodType periodType : periodTypes) {
            periodTypeNamesList.add(periodType.name().toLowerCase());
        }

        String[] periodTypeNames = new String[periodTypeNamesList.size()];
        periodTypeNamesList.toArray(periodTypeNames);
        runTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(periodTypeNames));

        org.openide.awt.Mnemonics.setLocalizedText(runValueLabel, org.openide.util.NbBundle.getMessage(RunVisualPanel.class, "RunVisualPanel.runValueLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(runTypeLabel, org.openide.util.NbBundle.getMessage(RunVisualPanel.class, "RunVisualPanel.runTypeLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(runTypeLabel)
                    .addComponent(runValueLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(runTypeComboBox, 0, 319, Short.MAX_VALUE)
                        .addComponent(runValueSpinner)))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(runTypeLabel)
                .addGap(18, 18, 18)
                .addComponent(runTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(runValueLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(runValueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox runTypeComboBox;
    private javax.swing.JLabel runTypeLabel;
    private javax.swing.JLabel runValueLabel;
    private javax.swing.JSpinner runValueSpinner;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
