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
import java.io.File;
import javax.swing.JOptionPane;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.perfcake.PerfCakeConst;

@ActionID(
        category = "Build",
        id = "org.perfcake.pc4nb.scenario.RunScenario"
)
@ActionRegistration(
        iconBase = "org/perfcake/pc4nb/favicon.png",
        displayName = "#CTL_RunScenario"
)
@ActionReference(path = "Loaders/text/pcscenario+xml/Actions", position = 1500, separatorBefore = 1450)
@Messages("CTL_RunScenario=Run Scenario")
public final class RunScenario implements ActionListener {
    private final DataObject context;

    public RunScenario(DataObject context) {
        this.context = context;

        Project project = FileOwnerQuery.getOwner(context.getPrimaryFile());
        String projectLocation = project.getProjectDirectory().getPath();

        System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, projectLocation + File.separator + "messages");
        System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, projectLocation + File.separator + "scenarios");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        PCScenarioDataObject scenarioDataObject = (PCScenarioDataObject) context;
        
        scenarioDataObject.createNewRun();

        if (!scenarioDataObject.isRunning()) {
            try {
                scenarioDataObject.getRun().execute();
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "This scenario is  already running.", "Scenario Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
