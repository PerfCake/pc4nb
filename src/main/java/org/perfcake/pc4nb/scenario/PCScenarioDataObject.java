/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfcake.pc4nb.scenario;

import java.io.IOException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.perfcake.PerfCakeConst;

@Messages({
    "LBL_PCScenario_LOADER=Files of PCScenario"
})
@MIMEResolver.NamespaceRegistration(
        displayName = "#LBL_PCScenario_LOADER",
        mimeType = "text/pcscenario+xml",
        elementNS = {"urn:perfcake:scenario:" + PerfCakeConst.XSD_SCHEMA_VERSION}
)
@DataObject.Registration(
        mimeType = "text/pcscenario+xml",
        iconBase = "org/perfcake/pc4nb/favicon.png",
        displayName = "#LBL_PCScenario_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/pcscenario+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class PCScenarioDataObject extends MultiDataObject {
    private ScenarioRunWorker run;
    private boolean running = false;
    private String scenarioName = "";

    public PCScenarioDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/pcscenario+xml", true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_PCScenario_EDITOR",
            iconBase = "org/perfcake/pc4nb/favicon.png",
            mimeType = "text/pcscenario+xml",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "PCScenario",
            position = 2000
    )
    @Messages("LBL_PCScenario_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    public ScenarioRunWorker getRun() {
        return run;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void createNewRun() {
        if (!isRunning()) {
            String scenarioPath = this.getPrimaryFile().getPath();
            scenarioName = this.getPrimaryFile().getName();
            run = new ScenarioRunWorker(scenarioPath, this);
        }
    }
}
