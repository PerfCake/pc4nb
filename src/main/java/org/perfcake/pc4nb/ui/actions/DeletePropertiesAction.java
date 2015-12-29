/*
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

import java.util.ArrayList;
import java.util.List;
import org.openide.WizardDescriptor;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.PC4NBModel;
import org.perfcake.pc4nb.model.PropertiesModel;

/**
 *
 * @author Andrej Halaj
 */
public class DeletePropertiesAction extends AbstractPC4NBAction {
    private PC4NBModel from;
    private List<Property> toRemove;
    
    public DeletePropertiesAction(PC4NBModel from, List<Property> toRemove) {
        this.from = from;
        this.toRemove = toRemove;
    }
    
    public DeletePropertiesAction(PC4NBModel from, Property toRemove) {
        this.from = from;
        this.toRemove = new ArrayList<>();
        this.toRemove.add(toRemove);
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        return null;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        PropertiesModel propertiesModel = (PropertiesModel) from;

        for (Property property : toRemove) { 
            propertiesModel.removeProperty(property);
        }
    }
}
