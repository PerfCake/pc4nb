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

package org.perfcake.pc4nb.ui.actions;

import java.util.List;
import org.openide.WizardDescriptor;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.PropertiesModel;
import org.perfcake.pc4nb.ui.wizards.ScenarioPropertiesWizardPanel;

public class EditScenarioPropertiesAction extends AbstractPC4NBAction {
    private ScenarioPropertiesWizardPanel wizardPanel;
    private PropertiesModel propertiesModel;

    public EditScenarioPropertiesAction(PropertiesModel propertiesModel) {
        this.propertiesModel = propertiesModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new ScenarioPropertiesWizardPanel();
        wizardPanel.getComponent().setModel(propertiesModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        List<Property> oldProperties = propertiesModel.getProperty();

        if (oldProperties != null) {
            for (int i = oldProperties.size() - 1; i >= 0; i--) {
                propertiesModel.removeProperty(oldProperties.get(i));
            }
        }

        List<Property> properties = (List<Property>) wiz.getProperty("scenario-properties");

        for (Property property : properties) {
            propertiesModel.addProperty(property);
        }
    }
}
