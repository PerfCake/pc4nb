/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
 * Modifications copyright (c) 2015 Andrej Halaj
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

package org.perfcake.pc4nb.model;

import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public final class ValidatorRefModel extends PC4NBModel {

    public static final String PROPERTY_ID = "validatorRef-id";

    private ValidatorRef validatorRef;

    public ValidatorRefModel(ValidatorRef validatorRef) {
        if (validatorRef == null) {
            throw new IllegalArgumentException("ValidatorRef must not be null.");
        }
        this.validatorRef = validatorRef;
        ModelMap.getDefault().addEntry(validatorRef, this);
    }

    public ValidatorRef getValidatorRef() {
        return validatorRef;
    }

    public void setId(String id) {
        String oldId = getValidatorRef().getId();
        getValidatorRef().setId(id);
        getListeners().firePropertyChange(PROPERTY_ID, oldId, id);
    }
}
