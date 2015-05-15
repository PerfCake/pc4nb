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
import java.util.Properties;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.pc4nb.model.SenderModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.ui.wizards.GeneratorWizardPanel;
import org.perfcake.pc4nb.ui.wizards.SenderWizardPanel;
import static org.perfcake.pc4nb.ui.wizards.visuals.GeneratorVisualPanel.GENERATOR_PACKAGE;
import static org.perfcake.pc4nb.ui.wizards.visuals.SenderVisualPanel.SENDER_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
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
        
        List<Property> properties = (List<Property>) wiz.getProperty("sender-properties");

        Properties defaultValues = new Properties();

        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(SENDER_PACKAGE + "." + wiz.getProperty("sender-type")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        List<Property> senderProperties = senderModel.getProperty();
            
        for (int i = senderProperties.size() - 1; i >= 0; i--) {
            senderModel.removeProperty(senderProperties.get(i));
        }
        
        for (Property property : properties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();

            if (!propertyValue.equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName);
                newProperty.setValue(propertyValue);

                senderModel.addProperty(newProperty);
            }
        }
    }
}
