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
package org.perfcake.pc4nb.scenario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(
        category = "Build",
        id = "org.perfcake.pc4nb.scenario.StopScenario"
)
@ActionRegistration(
        iconBase = "org/perfcake/pc4nb/favicon.png",
        displayName = "#CTL_StopScenario"
)
@ActionReference(path = "Loaders/text/pcscenario+xml/Actions", position = 1600)
@Messages("CTL_StopScenario=Stop Scenario")
public final class StopScenario implements ActionListener {
    private final DataObject context;

    public StopScenario(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        PCScenarioDataObject scenarioDataObject = (PCScenarioDataObject) context;

        if (scenarioDataObject.isRunning()) {
            try {
                scenarioDataObject.getRun().cancel(true);

                InputOutput outputWindow = IOProvider.getDefault().getIO("Scenario Output", false);
                outputWindow.select();
                
                outputWindow.getOut().println("Scenario stopped.");
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                scenarioDataObject.setRunning(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "This scenario is not running.", "Scenario Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
