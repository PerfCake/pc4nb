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
package org.perfcake.pc4nb.ui.wizards;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.perfcake.pc4nb.ui.wizards.visuals.SenderVisualPanel;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SenderWizardPanel implements WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {
    WizardDescriptor wizardDescriptor;
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private SenderVisualPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public SenderVisualPanel getComponent() {
        if (component == null) {
            component = new SenderVisualPanel();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        wizardDescriptor = wiz;
        getComponent().addPropertyChangeListener(this);
        
        String target = getComponent().getTargetTextField().getText();
        
        if (notNullNotEmpty(target)) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, null);
        } else {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, "Warning: Target is empty.");
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wizardDescriptor.putProperty("sender-type", getComponent().getSenderSelection().getSelectedItem());
        wizardDescriptor.putProperty("sender-target", getComponent().getTargetTextField().getText());
        wizardDescriptor.putProperty("sender-properties", getComponent().getProperties());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String target = getComponent().getTargetTextField().getText();
        
        if (notNullNotEmpty(target)) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, null);
        } else {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, "Warning: Target is empty.");
        }
    }
    
    private boolean notNullNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
