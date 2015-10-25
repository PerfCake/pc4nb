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
package org.perfcake.pc4nb.model;

import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;

public final class ValidationModel extends PC4NBModel {

    public static final String PROPERTY_VALIDATORS = "validation-validators";
    public static final String PROPERTY_ENABLED = "validation-enabled";
    public static final String PROPERTY_FAST_FORWARD = "validation-fast-forward";

    private Validation validation;

    public ValidationModel(Validation validation) {
        this.validation = validation;
        
        if (validation != null) {
            ModelMap.getDefault().addEntry(validation, this);
        }
    }

    public Validation getValidation() {
        return validation;
    }

    public void addValidator(Validator validator) {
        if (getValidation() == null) {
            createValidation();
        }
        
        getValidation().getValidator().add(validator);
        getListeners().firePropertyChange(PROPERTY_VALIDATORS, null, validator);
    }

    public void addValidator(int index, Validator validator) {
        if (getValidation() == null) {
            createValidation();
        }
        
        getValidation().getValidator().add(index, validator);
        getListeners().firePropertyChange(PROPERTY_VALIDATORS, null, validator);
    }

    public void removeValidator(Validator validator) {
        if (getValidation().getValidator().remove(validator)) {
            getListeners().firePropertyChange(PROPERTY_VALIDATORS, validator, null);
        }
        
        if (getValidation().getValidator().isEmpty()) {
            removeValidation();
        }
    }

    public void setEnabled(boolean enabled) {
        if (getValidation() == null) {
            createValidation();
        }
        
        boolean oldEnabled = getValidation().isEnabled();
        getValidation().setEnabled(enabled);
        getListeners().firePropertyChange(PROPERTY_ENABLED, oldEnabled, enabled);
    }

    public void setFastForward(boolean fastForward) {
        if (getValidation() == null) {
            createValidation();
        }
        
        boolean oldFastForward = getValidation().isFastForward();
        getValidation().setFastForward(fastForward);
        getListeners().firePropertyChange(PROPERTY_FAST_FORWARD, oldFastForward, fastForward);
    }

    private void createValidation() {
        this.validation = new Validation();
        ModelMap.getDefault().addEntry(validation, this);
    }

    private void removeValidation() {
        ModelMap.getDefault().removeEntry(validation);
        this.validation = null;
    }
}
