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
package org.perfcake.pc4nb.wizards.visuals;

import java.beans.PropertyChangeEvent;
import javax.swing.JTextField;
import org.perfcake.model.Header;
import org.perfcake.pc4nb.core.model.HeaderModel;
import org.perfcake.pc4nb.ui3.AbstractPC4NBView;

public final class HeaderVisualPanel extends AbstractPC4NBView {

    /**
     * Creates new form HeaderVisualPanel1
     */
    public HeaderVisualPanel() {
        setModel(new HeaderModel(new Header()));
        initComponents();
    }

    @Override
    public String getName() {
        return "Header wizard";
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getValueTextField() {
        return valueTextField;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        valueLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        valueTextField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(HeaderVisualPanel.class, "HeaderVisualPanel.nameLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(valueLabel, org.openide.util.NbBundle.getMessage(HeaderVisualPanel.class, "HeaderVisualPanel.valueLabel.text")); // NOI18N

        nameTextField.setText(org.openide.util.NbBundle.getMessage(HeaderVisualPanel.class, "HeaderVisualPanel.nameTextField.text")); // NOI18N

        valueTextField.setText(org.openide.util.NbBundle.getMessage(HeaderVisualPanel.class, "HeaderVisualPanel.valueTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(valueLabel))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameTextField)
                    .addComponent(valueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueLabel)
                    .addComponent(valueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JTextField valueTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof HeaderModel) {
            HeaderModel model = (HeaderModel) evt.getSource();
            nameTextField.setText(model.getHeader().getName());
            valueTextField.setText(model.getHeader().getValue());
        }
    }
}
