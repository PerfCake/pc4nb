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
package org.perfcake.pc4nb.ui.palette;

import java.util.Set;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.perfcake.message.Message;
import org.perfcake.message.generator.MessageGenerator;
import org.perfcake.message.sender.MessageSender;
import org.perfcake.pc4nb.model.MessageModel;
import org.perfcake.pc4nb.model.ReporterModel;
import org.perfcake.pc4nb.model.ValidatorModel;
import org.perfcake.pc4nb.reflect.ComponentScanner;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.validation.MessageValidator;
import org.perfcake.model.*;
import org.perfcake.pc4nb.model.DestinationModel;
import org.perfcake.pc4nb.model.GeneratorModel;
import org.perfcake.pc4nb.model.SenderModel;

/**
 *
 * @author Andrej Halaj
 */
class PerfCakeComponentNodeContainer extends Children.Keys<String> {

    private String category = new String();

    public PerfCakeComponentNodeContainer(String category) {
        this.category = category;
    }

    @Override
    protected void addNotify() {
        setKeys(new String[]{category});
    }

    @Override
    protected Node[] createNodes(String category) {
        Node[] components;
        ComponentScanner scanner = new ComponentScanner();

        if (category.equalsIgnoreCase("Reporting")) {
            Set<Class<? extends Reporter>> subTypes = scanner.findComponentsOfType(Reporter.class, "org.perfcake.reporting.reporters");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends Reporter> subType : subTypes) {
                Scenario.Reporting.Reporter reporter = new Scenario.Reporting.Reporter();
                reporter.setClazz(subType.getSimpleName());
                ReporterModel newModel = new ReporterModel(reporter);
                components[i] = new PerfCakeReporterNode(newModel);
                i++;
            }
        } else if (category.equalsIgnoreCase("Validation")) {
            Set<Class<? extends MessageValidator>> subTypes = scanner.findComponentsOfType(MessageValidator.class, "org.perfcake.validation");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends MessageValidator> subType : subTypes) {
                Scenario.Validation.Validator validator = new Scenario.Validation.Validator();
                validator.setClazz(subType.getSimpleName());
                ValidatorModel newModel = new ValidatorModel(validator);
                components[i] = new PerfCakeValidatorNode(newModel);
                i++;
            }
        } else if (category.equalsIgnoreCase("Messages")) {
            Set<Class<? extends Message>> subTypes = scanner.findComponentsOfType(Message.class, "org.perfcake.message");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends Message> subType : subTypes) {
                Scenario.Messages.Message message = new Scenario.Messages.Message();
                MessageModel newModel = new MessageModel(message);
                components[i] = new PerfCakeMessageNode(newModel);
                i++;
            }
        } else if (category.equalsIgnoreCase("Destinations")) {
            Set<Class<? extends Destination>> subTypes = scanner.findComponentsOfType(Destination.class, "org.perfcake.reporting.destinations");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends Destination> subType : subTypes) {
                Scenario.Reporting.Reporter.Destination destination = new Scenario.Reporting.Reporter.Destination();
                destination.setClazz(subType.getSimpleName());
                DestinationModel newModel = new DestinationModel(destination);
                components[i] = new PerfCakeDestinationNode(newModel);
                i++;
            }
        } else if (category.equalsIgnoreCase("Generators")) {
            Set<Class<? extends MessageGenerator>> subTypes = scanner.findComponentsOfType(MessageGenerator.class, "org.perfcake.message.generator");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends MessageGenerator> subType : subTypes) {
                Scenario.Generator generator = new Scenario.Generator();
                generator.setClazz(subType.getSimpleName());
                GeneratorModel newModel = new GeneratorModel(generator);
                components[i] = new PerfCakeGeneratorNode(newModel);
                i++;
            }
        } else if (category.equalsIgnoreCase("Senders")) {
            Set<Class<? extends MessageSender>> subTypes = scanner.findComponentsOfType(MessageSender.class, "org.perfcake.message.sender");
            components = new Node[subTypes.size()];
            int i = 0;
            for (Class<? extends MessageSender> subType : subTypes) {
                Scenario.Sender sender = new Scenario.Sender();
                sender.setClazz(subType.getSimpleName());
                SenderModel newModel = new SenderModel(sender);
                components[i] = new PerfCakeSenderNode(newModel);
                i++;
            }
        } else {
            components = new Node[]{};
            // TODO: throw exception and log
        }

        return components;
    }

}
