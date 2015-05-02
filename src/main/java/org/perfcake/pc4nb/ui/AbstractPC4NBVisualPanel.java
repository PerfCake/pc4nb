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
package org.perfcake.pc4nb.ui;

import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.perfcake.pc4nb.core.model.PC4NBModel;

/**
 *
 * @author Andrej Halaj
 */
public abstract class AbstractPC4NBVisualPanel extends JPanel implements PropertyChangeListener {
    private PC4NBModel model;
    
    public PC4NBModel getModel() {
        return model;
    }
    
    public void setModel(PC4NBModel model) {
        this.model = model;
    }
}
