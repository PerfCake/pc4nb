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

import java.util.List;
import org.openide.WizardDescriptor;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.ui.wizards.ReporterWizardPanel;

/**
 *
 * @author Andrej Halaj
 */
public class EditReporterAction extends AbstractPC4NBAction {
    private ReporterWizardPanel wizardPanel;
    private ReporterModel reporterModel;

    public EditReporterAction(ReporterModel reporterModel) {
        this.reporterModel = reporterModel;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> getPanel() {
        wizardPanel = new ReporterWizardPanel();
        wizardPanel.getComponent().setModel(reporterModel);
        return wizardPanel;
    }

    @Override
    public void doAction(WizardDescriptor wiz) {
        reporterModel.setClazz((String) wiz.getProperty("reporter-clazz"));
        reporterModel.setEnabled((boolean) wiz.getProperty("reporter-enabled"));

        List<PropertyModel> properties = (List<PropertyModel>) wiz.getProperty("reporter-properties");
        
        List<Property> reporterProperties = reporterModel.getProperty();
            
        for (int i = reporterProperties.size() - 1; i >= 0; i--) {
            reporterModel.removeProperty(reporterProperties.get(i));
        }
        
        for (PropertyModel property : properties) {
            if (!property.isDefault()) {
                reporterModel.addProperty(property.getProperty());
            }
        }
    }
}
