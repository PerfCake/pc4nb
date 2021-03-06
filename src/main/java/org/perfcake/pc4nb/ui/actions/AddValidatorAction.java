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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.ValidationModel;
import org.perfcake.pc4nb.ui.wizards.ValidatorWizardPanel;

public class AddValidatorAction extends AbstractPC4NBAction {
    private PC4NBModel to;

    public AddValidatorAction(PC4NBModel to) {
        this.to = to;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        ValidatorWizardPanel wizardPanel = new ValidatorWizardPanel();

        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        Validator validator = new Validator();
        validator.setClazz((String) wiz.getProperty("validator-type"));
        validator.setId((String) wiz.getProperty("validator-id"));

        List<PropertyModel> properties = (List<PropertyModel>) wiz.getProperty("validator-properties");

        for (PropertyModel propertyModel : properties) {
            if (!propertyModel.isDefault()) {
                validator.getProperty().add(propertyModel.getProperty());
            }
        }

        ValidationModel validationModel = (ValidationModel) to;
        validationModel.addValidator(validator);
    }
}
