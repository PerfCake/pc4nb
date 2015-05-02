package org.perfcake.pc4nb.project;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ProjectFactory.class)
public class PerfCakeProjectFactory implements ProjectFactory {

    public static final String SCENARIOS_DIR = "scenarios";
    public static final String MESSAGES_DIR = "messages";

    //Specifies when a project is a project, i.e.,
    //if "customer.txt" is present in a folder:
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return containsScenariosDir(projectDirectory)
                && containsMessagesDir(projectDirectory)
                && containsPerfcakeScenario(projectDirectory);
    }

    //Specifies when the project will be opened, i.e., if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new PerfCakeProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        // leave unimplemented for the moment
    }

    private boolean containsPerfcakeScenario(FileObject projectDirectory) {
        List<FileObject> files = Arrays.asList(projectDirectory.getFileObject(SCENARIOS_DIR).getChildren());

        for (FileObject file : files) {
            if (file.getMIMEType().equals("text/pcscenario+xml")) {
                return true;
            }
        }

        return false;
    }

    private boolean containsScenariosDir(FileObject projectDirectory) {
        return projectDirectory.getFileObject(SCENARIOS_DIR) != null;
    }

    private boolean containsMessagesDir(FileObject projectDirectory) {
        return projectDirectory.getFileObject(MESSAGES_DIR) != null;
    }

}
