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
package org.perfcake.pc4nb.ui.wizards.visuals;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import org.openide.util.Exceptions;
import org.perfcake.common.PeriodType;
import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.pc4nb.model.GeneratorModel;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.reflect.ComponentScanner;

public final class GeneratorVisualPanel extends VisualPanelWithProperties {
    public static final String GENERATOR_PACKAGE = "org.perfcake.message.generator";

    /**
     * Creates new form GeneratorVisualPanel1
     */
    public GeneratorVisualPanel() {
        ComponentScanner scanner = new ComponentScanner();
        Set<Class<? extends AbstractMessageGenerator>> subTypes = scanner.findComponentsOfType(AbstractMessageGenerator.class, GENERATOR_PACKAGE);

        Set<String> components = new HashSet<>();

        for (Class<? extends AbstractMessageGenerator> generator : subTypes) {
            components.add(generator.getSimpleName());
        }

        ComponentPropertiesScanner propertyScanner = new ComponentPropertiesScanner();

        for (String component : components) {
            try {
                putToComponentPropertiesMap(component, propertyScanner.getPropertiesOfComponent(Class.forName(GENERATOR_PACKAGE + "." + component)));
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        initComponents();
        setModel(new GeneratorModel(new Scenario.Generator()));

        try {
            listProperties((String) generatorSelection.getSelectedItem());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Class not found " + ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "Generator";
    }

    public JComboBox getGeneratorSelection() {
        return generatorSelection;
    }

    public JComboBox getPeriodTypeSelection() {
        return periodTypeSelection;
    }

    public JSpinner getPeriodValueSpinner() {
        return periodValueSpinner;
    }

    public JSpinner getThreadsSpinner() {
        return threadsSpinner;
    }

    @Override
    public JTable getPropertiesTable() {
        return propertiesTable;
    }

    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);

        GeneratorModel generatorModel = (GeneratorModel) model;
        String generatorClazz = generatorModel.getGenerator().getClazz();
        String generatorThreads = generatorModel.getGenerator().getThreads();

        if (generatorThreads != null && !generatorThreads.isEmpty()) {
            threadsSpinner.setValue(Integer.parseInt(generatorThreads));
        } else {
            threadsSpinner.setValue(1);
        }

        Properties properties = new Properties();
        properties.putAll(getPropertiesFor(generatorClazz));

        for (Property property : generatorModel.getProperty()) {
            properties.put(property.getName(), property.getValue());
        }

        try {
            if (generatorClazz != null) {
                getGeneratorSelection().setSelectedItem(generatorClazz);
                putToComponentPropertiesMap(generatorClazz, properties);
                listProperties(generatorClazz);
            }

        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        Run generatorRun = generatorModel.getGenerator().getRun();

        if (generatorRun != null) {
            periodTypeSelection.setSelectedItem(generatorRun.getType());

            if (generatorRun.getValue() != null && !generatorRun.getValue().isEmpty()) {
                periodValueSpinner.setValue(Integer.parseInt(generatorRun.getValue()));
            } else {
                periodValueSpinner.setValue(1000);
            }
        }

        for (Property property : generatorModel.getProperty()) {
            getPropertiesTableModel().addRow(property);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generatorSelection = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        periodTypeSelection = new javax.swing.JComboBox();
        generatorTypeLabel = new javax.swing.JLabel();
        periodTypeLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();
        periodValueLabel = new javax.swing.JLabel();
        periodValueSpinner = new javax.swing.JSpinner();
        threadsSpinner = new javax.swing.JSpinner();
        threadsLabel = new javax.swing.JLabel();

        Set<String> componentNames = getComponentPropertiesModelMap().keySet();
        String[] componentNamesArray = new String[componentNames.size()];
        componentNames.toArray(componentNamesArray);
        generatorSelection.setModel(new DefaultComboBoxModel(componentNamesArray));
        generatorSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    listProperties((String) generatorSelection.getSelectedItem());
                } catch (ClassNotFoundException | NoSuchFieldException ex) {
                    // blah
                }
            }
        });

        propertiesTable.setModel(getPropertiesTableModel());
        jScrollPane2.setViewportView(propertiesTable);

        List<PeriodType> periodTypes = Arrays.asList(PeriodType.values());
        List<String> periodTypeNamesList = new ArrayList<>();

        for (PeriodType periodType : periodTypes) {
            periodTypeNamesList.add(periodType.name());
        }

        String[] periodTypeNames = new String[periodTypeNamesList.size()];
        periodTypeNamesList.toArray(periodTypeNames);
        periodTypeSelection.setModel(new javax.swing.DefaultComboBoxModel(periodTypeNames));

        org.openide.awt.Mnemonics.setLocalizedText(generatorTypeLabel, org.openide.util.NbBundle.getMessage(GeneratorVisualPanel.class, "GeneratorVisualPanel.generatorTypeLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(periodTypeLabel, org.openide.util.NbBundle.getMessage(GeneratorVisualPanel.class, "GeneratorVisualPanel.periodTypeLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(propertiesLabel, org.openide.util.NbBundle.getMessage(GeneratorVisualPanel.class, "GeneratorVisualPanel.propertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(periodValueLabel, org.openide.util.NbBundle.getMessage(GeneratorVisualPanel.class, "GeneratorVisualPanel.periodValueLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(threadsLabel, org.openide.util.NbBundle.getMessage(GeneratorVisualPanel.class, "GeneratorVisualPanel.threadsLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(threadsLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(periodValueLabel)
                        .addComponent(propertiesLabel)
                        .addComponent(periodTypeLabel)
                        .addComponent(generatorTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(periodTypeSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generatorSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                        .addComponent(periodValueSpinner)
                        .addComponent(threadsSpinner)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(generatorTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generatorSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(threadsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(threadsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(periodTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(periodTypeSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(periodValueLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(periodValueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(propertiesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox generatorSelection;
    private javax.swing.JLabel generatorTypeLabel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel periodTypeLabel;
    private javax.swing.JComboBox periodTypeSelection;
    private javax.swing.JLabel periodValueLabel;
    private javax.swing.JSpinner periodValueSpinner;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JLabel threadsLabel;
    private javax.swing.JSpinner threadsSpinner;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        return;
    }
}