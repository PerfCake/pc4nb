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
package org.perfcake.pc4nb.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.perfcake.pc4nb.wizards.visuals.MessageVisualPanel;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.perfcake.model.Header;

public class MessageWizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

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
            //component.setController(null);
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
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        MessageVisualPanel component = getComponent();
        wiz.putProperty("message-uri", component.getUriTexField().getText());
        wiz.putProperty("message-multiplicity", component.getMultiplicitySpinner().getValue());
        wiz.putProperty("message-properties", component.getProperties());
        
        List<Header> headers = new ArrayList<>();
        
        for (int i = 0; i < component.getHeadersTableModel().getRowCount(); i++) {
            Header header = new Header();
            header.setName((String) component.getHeadersTableModel().getValueAt(i, 0));
            header.setValue((String) component.getHeadersTableModel().getValueAt(i, 1));
            headers.add(header);
        }
        
        wiz.putProperty("message-headers", headers);
    }
}
