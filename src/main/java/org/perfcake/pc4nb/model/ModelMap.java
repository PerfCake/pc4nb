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
import org.perfcake.model.*;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Run;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.model.Scenario.Properties;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.model.Scenario.Sender;
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;

/**
 *
 * @author Andrej Halaj
 */
public class ModelMap {

    private static final ModelMap instance = new ModelMap();
    private final Map<Object, PC4NBModel> modelMap = new HashMap<>();
    private ValidationModel validationModel = new ValidationModel(null);

    private ModelMap() {
    }

    public static ModelMap getDefault() {
        return instance;
    }

    public ValidationModel getValidationModel() {
        return validationModel;
    }

    public void addEntry(Object perfCakeModel, PC4NBModel pc4nbModel) {
        if (pc4nbModel instanceof ValidationModel) {
            validationModel = (ValidationModel) pc4nbModel;
        }
        
        if (perfCakeModel != null) {
            modelMap.put(perfCakeModel, pc4nbModel);
        }
    }

    public void removeEntry(Object perfCakeModel) {
        modelMap.remove(perfCakeModel);
    }

    public PC4NBModel getPC4NBModelFor(Object perfCakeModel) {
        if (!modelMap.containsKey(perfCakeModel)) {
            createModelAndAddEntry(perfCakeModel);
        }

        return modelMap.get(perfCakeModel);
    }

    public void createModelAndAddEntry(Object perfCakeModel) {
        PC4NBModel model;

        if (perfCakeModel instanceof Reporter) {
            model = new ReporterModel((Reporter) perfCakeModel);
        } else if (perfCakeModel instanceof Validator) {
            model = new ValidatorModel((Validator) perfCakeModel);
        } else if (perfCakeModel instanceof Generator) {
            model = new GeneratorModel((Generator) perfCakeModel);
        } else if (perfCakeModel instanceof Sender) {
            model = new SenderModel((Sender) perfCakeModel);
        } else if (perfCakeModel instanceof Message) {
            model = new MessageModel((Message) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario.Properties) {
            model = new PropertiesModel((Properties) perfCakeModel);
        } else if (perfCakeModel instanceof Header) {
            model = new HeaderModel((Header) perfCakeModel);
        } else if (perfCakeModel instanceof Destination) {
            model = new DestinationModel((Destination) perfCakeModel);
        } else if (perfCakeModel instanceof Property) {
            model = new PropertyModel((Property) perfCakeModel);
        } else if (perfCakeModel instanceof Period) {
            model = new PeriodModel((Period) perfCakeModel);
        } else if (perfCakeModel instanceof Run) {
            model = new RunModel((Run) perfCakeModel);
        } else if (perfCakeModel instanceof ValidatorRef) {
            model = new ValidatorRefModel((ValidatorRef) perfCakeModel);
        } else if (perfCakeModel instanceof Scenario) {
            model = new ScenarioModel((Scenario) perfCakeModel);
        } else if (perfCakeModel instanceof Reporting) {
            model = new ReportingModel((Reporting) perfCakeModel);
        } else if (perfCakeModel instanceof Validation) {
            model = new ValidationModel((Validation) perfCakeModel);
        } else if (perfCakeModel instanceof Messages) {
            model = new MessagesModel((Messages) perfCakeModel);
        } else {
            model = null;
            throw new IllegalArgumentException("This object is not a PerfCake object");
        }

        addEntry(perfCakeModel, model);
    }
}
