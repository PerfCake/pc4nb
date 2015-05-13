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
package org.perfcake.pc4nb.model;

import java.util.HashMap;
import java.util.Map;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 *
 * @author Andrej Halaj
 */
public class ModelMapper {
    private Map<Object, PC4NBModel> modelMap = new HashMap<>();

    private ModelMapper() {
        
    }

    public void addEntry(Object perfCakeModel, PC4NBModel pc4nbModel) {
        if (perfCakeModel != null) {
            modelMap.put(perfCakeModel, pc4nbModel);
        }
    }

    public void removeEntry(Object perfCakeModel) {
        modelMap.remove(perfCakeModel);
    }

    public PC4NBModel getPC4NBModelFor(Object perfCakeModel) {
        if (!modelMap.containsKey(perfCakeModel)) {
            createModel(perfCakeModel);
        }

        return modelMap.get(perfCakeModel);
    }

    public void createModel(Object perfCakeModel) {
        PC4NBModel model;

        if (perfCakeModel instanceof Scenario.Reporting.Reporter) {
            model = new ReporterModel((Scenario.Reporting.Reporter) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Validation.Validator) {
            model = new ValidatorModel((Scenario.Validation.Validator) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Generator) {
            model = new GeneratorModel((Scenario.Generator) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Sender) {
            model = new SenderModel((Scenario.Sender) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Messages.Message) {
            model = new MessageModel((Scenario.Messages.Message) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Properties) {
            model = new PropertiesModel((Scenario.Properties) perfCakeModel);
        } else if (perfCakeModel instanceof Header) {
            model = new HeaderModel((Header) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Reporting.Reporter.Destination) {
            model = new DestinationModel((Scenario.Reporting.Reporter.Destination) perfCakeModel);
        } else if (perfCakeModel instanceof Property) {
            model = new PropertyModel((Property) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Reporting.Reporter.Destination.Period) {
            model = new PeriodModel((Scenario.Reporting.Reporter.Destination.Period) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Generator.Run) {
            model = new RunModel((Scenario.Generator.Run) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Messages.Message.ValidatorRef) {
            model = new ValidatorRefModel((Scenario.Messages.Message.ValidatorRef) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario) {
            model = new ScenarioModel((Scenario) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Reporting) {
            model = new ReportingModel((Scenario.Reporting) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Validation) {
            model = new ValidationModel((Scenario.Validation) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Messages) {
            model = new MessagesModel((Scenario.Messages) perfCakeModel);
        } else {
            model = null;
        }

        addEntry(perfCakeModel, model);
    }
}
