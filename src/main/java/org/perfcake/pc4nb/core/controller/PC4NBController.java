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
package org.perfcake.pc4nb.core.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.core.model.PC4NBModel;

/**
 *
 * @author Andrej Halaj
 */
public abstract class PC4NBController implements PropertyChangeListener {
    private PC4NBModel model;
    private AbstractPC4NBVisualPanel wizardView;

    public PC4NBController(PC4NBModel model, AbstractPC4NBVisualPanel wizardView) {
        this.model = model;
        this.wizardView = wizardView;
        model.addPropertyChangeListener(this);
    }
    
    public PC4NBModel getModel() {
        return model;
    }

    public AbstractPC4NBVisualPanel getWizardView() {
        return wizardView;
    }
    
    @Override
    public abstract void propertyChange(PropertyChangeEvent evt);
}
