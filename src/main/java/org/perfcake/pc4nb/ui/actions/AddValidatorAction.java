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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.core.controller.ValidationController;
import org.perfcake.pc4nb.core.model.ModelMap;
import org.perfcake.pc4nb.core.model.ValidationModel;
import org.perfcake.pc4nb.core.model.ValidatorModel;
import org.perfcake.pc4nb.reflect.ComponentPropertiesScanner;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.wizards.ValidatorWizardPanel;
import org.perfcake.pc4nb.wizards.visuals.ValidationVisualPanel;
import static org.perfcake.pc4nb.wizards.visuals.ValidatorVisualPanel.VALIDATOR_PACKAGE;

/**
 *
 * @author Andrej Halaj
 */
public class AddValidatorAction extends AbstractPC4NBAction {

    public AddValidatorAction(AbstractPC4NBVisualPanel source) {
        super(source);
    }
    
    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        ValidatorWizardPanel wizardPanel = new ValidatorWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        ValidationVisualPanel sourcePanel = (ValidationVisualPanel) getSource();
        ValidatorModel validatorModel = new ValidatorModel(new Validator());
        validatorModel.setClazz((String) wiz.getProperty("validator-type"));
        validatorModel.setId((String) wiz.getProperty("validator-id"));

        List<Property> properties = (List<Property>) wiz.getProperty("reporter-properties");

        Properties defaultValues = new Properties();

        try {
            defaultValues = (new ComponentPropertiesScanner()).getPropertiesOfComponent(Class.forName(VALIDATOR_PACKAGE + "." + wiz.getProperty("reporter-clazz")));
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
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

        ValidationController validationController = (ValidationController) sourcePanel.getController();
        ValidationModel validationModel = (ValidationModel) validationController.getModel();
        validationModel.addValidator(validatorModel.getValidator());
        ModelMap.getDefault().addEntry(validatorModel.getValidator(), validatorModel);
    }
}
