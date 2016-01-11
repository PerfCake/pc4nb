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
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.ui.wizards.SenderWizardPanel;

public class EditSenderAction extends AbstractPC4NBAction {
    private SenderWizardPanel wizardPanel;
    private SenderModel senderModel;

    public EditSenderAction(SenderModel senderModel) {
        this.senderModel = senderModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new SenderWizardPanel();
        wizardPanel.getComponent().setModel(senderModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        senderModel.setClazz((String) wiz.getProperty("sender-type"));
        senderModel.setTarget((String) wiz.getProperty("sender-target"));
        
        List<PropertyModel> properties = (List<PropertyModel>) wiz.getProperty("sender-properties");

        List<Property> senderProperties = senderModel.getProperty();
            
        for (int i = senderProperties.size() - 1; i >= 0; i--) {
            senderModel.removeProperty(senderProperties.get(i));
        }
        
        for (PropertyModel propertyModel : properties) {
            if (!propertyModel.isDefault()) {
                senderModel.addProperty(propertyModel.getProperty());
            }
        }
    }
}
