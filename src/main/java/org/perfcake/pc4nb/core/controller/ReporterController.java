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
package org.perfcake.pc4nb.core.controller;

import java.beans.PropertyChangeEvent;
import java.util.Map;
import org.openide.util.Exceptions;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import static org.perfcake.pc4nb.core.model.ReporterModel.PROPERTY_CLASS;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.ThirdLevelView;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;
import org.perfcake.pc4nb.wizards.visuals.ReporterVisualPanel;

/**
 *
 * @author Andrej Halaj
 */
public class ReporterController extends PC4NBController {
    AbstractPC4NBVisualPanel scenarioView = new ThirdLevelView();
    
    public ReporterController(PC4NBModel model, AbstractPC4NBVisualPanel wizardView, AbstractPC4NBVisualPanel scenarioView) {
        super(model, wizardView);
        this.scenarioView = scenarioView;
        this.scenarioView.setController(this);
        model.addPropertyChangeListener(this);
    }
    
    public AbstractPC4NBVisualPanel getScenarioView() {
        return scenarioView;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().startsWith(PROPERTY_CLASS)) {
            ReporterVisualPanel wizardView = (ReporterVisualPanel) getWizardView();
            
            Map<String, PropertiesTableModel> componentPropertiesModelMap = wizardView.getComponentPropertiesModelMap();
            for (String component : componentPropertiesModelMap.keySet()) {
                if (component.equals(getModel().getClass().getSimpleName())) {
                    try {
                        wizardView.getReporterSelection().setSelectedItem(component);
                        wizardView.listProperties(component); // necessary?
                        break;
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
    }
    
}
