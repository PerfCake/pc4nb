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
package org.perfcake.pc4nb.ui3.actions;

import java.util.List;
import java.util.Properties;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.core.model.ValidatorModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.wizards.ValidatorWizardPanel;
import static org.perfcake.pc4nb.wizards.visuals.ValidatorVisualPanel.VALIDATOR_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
public class EditValidatorAction extends AbstractPC4NBAction {
    private ValidatorWizardPanel wizardPanel;
    private ValidatorModel validatorModel;

    public EditValidatorAction(ValidatorModel validatorModel) {
        this.validatorModel = validatorModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new ValidatorWizardPanel();
        wizardPanel.getComponent().setModel(validatorModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        validatorModel.setClazz((String) wiz.getProperty("validator-type"));
        validatorModel.setId((String) wiz.getProperty("validator-id"));

        List<Property> properties = (List<Property>) wiz.getProperty("validator-properties");

        Properties defaultValues = new Properties();

        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(VALIDATOR_PACKAGE + "." + wiz.getProperty("validator-type")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        List<Property> validatorProperties = validatorModel.getProperty();
        
        for (int i = validatorProperties.size() - 1; i >= 0; i--) {
            validatorModel.removeProperty(validatorProperties.get(i));
        }

        for (Property property : properties) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();

            if (!propertyValue.equals(defaultValues.get(propertyName))) {
                Property newProperty = new Property();
                newProperty.setName(propertyName);
                newProperty.setValue(propertyValue);

                validatorModel.addProperty(newProperty);
            }
        }
    }
}
