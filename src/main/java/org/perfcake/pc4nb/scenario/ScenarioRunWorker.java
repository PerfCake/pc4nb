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

package org.perfcake.pc4nb.scenario;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.SwingWorker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.perfcake.PerfCakeException;

/**
 *
 * @author Andrej Halaj
 */
public class ScenarioRunWorker extends SwingWorker<Void, String> {
    private static final Logger log = LogManager.getLogger(ScenarioRunWorker.class.getName());

    private ScenarioManager manager;
    private String scenarioPath;
    private PrintStream standardOut;
    private InputOutput outputWindow;
    private PCScenarioDataObject context;

    public ScenarioRunWorker(String scenarioPath, PCScenarioDataObject context) {
        super();
        try {
            this.context = context;
            manager = new ScenarioManager();
            this.scenarioPath = scenarioPath;

            outputWindow = IOProvider.getDefault().getIO("Scenario Output", false);
            outputWindow.getOut().reset();
            outputWindow.select();
        } catch (IOException ex) {
            String message = "Couldn't clear output window.";
            log.error(message, ex);
            System.err.println(message);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            redirectOutput();
            context.setRunning(true);
            System.out.println("PerfCake scenario " + context.getScenarioName() + " has started.");
            manager.runScenario(scenarioPath);
        } catch (ScenarioException | PerfCakeException ex) {
            String message = "Cannot run scenario";
            log.error(message, ex);
            ex.printStackTrace(System.out);
            System.err.println(message);
        }
        return null;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            System.out.println("PerfCake scenario finished.");
        }
        
        context.setRunning(false);
        System.setOut(standardOut);
    }

    private void redirectOutput() {
        standardOut = System.out;

        OutputStream os = new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                outputWindow.getOut().print(String.valueOf((char) i));
            }

            @Override
            public void write(byte[] bytes) throws IOException {
                outputWindow.getOut().print(new String(bytes));
            }

            @Override
            public void write(byte[] bytes, int off, int len) throws IOException {
                outputWindow.getOut().print(new String(bytes, off, len));
            }
        };

        System.setOut(new PrintStream(os, true));
    }
}
