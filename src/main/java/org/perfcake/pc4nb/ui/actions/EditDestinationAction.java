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
package org.perfcake.pc4nb.ui.actions;

import java.util.List;
import org.openide.WizardDescriptor;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.ui.wizards.DestinationWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditDestinationAction extends AbstractPC4NBAction {
    private DestinationWizardPanel wizardPanel;
    private DestinationModel destinationModel;

    public EditDestinationAction(DestinationModel destinationModel) {
        this.destinationModel = destinationModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new DestinationWizardPanel();
        wizardPanel.getComponent().setModel(destinationModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        destinationModel.setClazz((String) wiz.getProperty("destination-type"));
        destinationModel.setEnabled((boolean) wiz.getProperty("destination-enabled"));

        List<PropertyModel> properties = (List<PropertyModel>) wiz.getProperty("destination-properties");
        
        List<Property> destinationProperties = destinationModel.getProperty();
            
        for (int i = destinationProperties.size() - 1; i >= 0; i--) {
            destinationModel.removeProperty(destinationProperties.get(i));
        }

        for (PropertyModel propertyModel : properties) {
            if (!propertyModel.isDefault()) {
                destinationModel.addProperty(propertyModel.getProperty());
            }
        }
    }
}
