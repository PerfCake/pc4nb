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
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.ValidationModel;
import org.perfcake.pc4nb.model.ValidatorRefModel;
import org.perfcake.pc4nb.ui.wizards.ValidationWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class AddValidatorRefsAction extends AbstractPC4NBAction {
    ValidationModel validationModel;
    ValidationWizardPanel panel;
    MessageModel messageModel;
    
    public AddValidatorRefsAction(ValidationModel validationModel, MessageModel messageModel) {
        this.validationModel = validationModel;
        this.messageModel = messageModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        panel = new ValidationWizardPanel(true);
        panel.getComponent().setModel(validationModel);
        
        return panel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        int[] selectedRows = panel.getComponent().getValidatorsTable().getSelectedRows();
        List<Validator> validators = panel.getComponent().getValidatorsTableModel().getValidators();
        
        for (int i = 0; i < selectedRows.length; i++) {
            ValidatorRef validatorRef = new ValidatorRef();
            validatorRef.setId(validators.get(selectedRows[i]).getId());
            ValidatorRefModel validatorRefModel = new ValidatorRefModel(validatorRef);
            
            messageModel.addValidatorRef(validatorRefModel.getValidatorRef());
        }
    }
}
