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

package org.perfcake.pc4nb.ui.wizards;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.perfcake.pc4nb.ui.wizards.visuals.MessageVisualPanel;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;

public class MessageWizardPanel implements WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {
    private ChangeSupport listeners = new ChangeSupport(this);
    private WizardDescriptor wizardDescriptor = null;
    private boolean isValid;

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private MessageVisualPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public MessageVisualPanel getComponent() {
        if (component == null) {
            component = new MessageVisualPanel();
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
        return isValid;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.addChangeListener(l);
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        wizardDescriptor = wiz;
        getComponent().addPropertyChangeListener(this);
        String uri = getComponent().getUriTexField().getText();
        String content = getComponent().getContentTextField().getText();
        isValid = notNullNotEmpty(content) || notNullNotEmpty(uri);
    }
    
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        MessageVisualPanel component = getComponent();
        wizardDescriptor.putProperty("message-uri", component.getUriTexField().getText());
        wizardDescriptor.putProperty("message-content", component.getContentTextField().getText());
        wizardDescriptor.putProperty("message-multiplicity", component.getMultiplicitySpinner().getValue().toString());
        wizardDescriptor.putProperty("message-properties", component.getProperties());
        wizardDescriptor.putProperty("message-headers", component.getHeadersTableModel().getHeaders());
        wizardDescriptor.putProperty("message-validators", component.getValidatorRefsTableModel().getValidatorRefs());
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String uri = getComponent().getUriTexField().getText();
        String content = getComponent().getContentTextField().getText();
        if (notNullNotEmpty(content) && notNullNotEmpty(uri)) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, "Both Content and URI fields are filled. In this case URI is ignored and Content is used.");
            isValid = true;
        } else if (notNullNotEmpty(content) || notNullNotEmpty(uri)) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, null);
            isValid = true;
        } else {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, "Please fill in URI or Content field.");
            isValid = false;
        }
        
        listeners.fireChange();
    }

    private boolean notNullNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
