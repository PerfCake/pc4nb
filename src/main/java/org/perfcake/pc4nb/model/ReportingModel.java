/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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
package org.perfcake.pc4nb.model;

import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;

public class ReportingModel extends PC4NBModel {

    public static final String PROPERTY_PROPERTIES = "reporting-property";
    public static final String PROPERTY_REPORTERS = "reporting-reporter";

    private Reporting reporting;

    public ReportingModel(Reporting reporting) {
        this.reporting = reporting;
    }

    public Reporting getReporting() {
        return reporting;
    }

    public void addReporter(Reporter reporter) {
        if (getReporting() == null) {
            createReporting();
        }
        
        addReporter(getReporting().getReporter().size(), reporter);
    }

    public void addReporter(int index, Reporter reporter) {
        if (getReporting() == null) {
            createReporting();
        }
        
        getReporting().getReporter().add(index, reporter);
        ModelMap.getDefault().createModelAndAddEntry(reporter);
        getListeners().firePropertyChange(PROPERTY_REPORTERS, null, reporter);
    }

    public void removeReporter(Reporter reporter) {
        if (getReporting().getReporter().remove(reporter)) {
            getListeners().firePropertyChange(PROPERTY_REPORTERS, reporter, null);
        }
        
        if (reporting.getReporter().isEmpty() && reporting.getProperty().isEmpty()) {
            removeReporting();
        }
    }

    public void addProperty(Property Property) {
        addProperty(getReporting().getProperty().size(), Property);
    }

    public void addProperty(int index, Property property) {
        if (reporting == null) {
            createReporting();
        }
        
        getReporting().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getReporting().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }

        if (reporting.getReporter().isEmpty() && reporting.getProperty().isEmpty()) {
            removeReporting();
        }
    }

    private void createReporting() {
        this.reporting = new Reporting();
        ModelMap.getDefault().addEntry(reporting, this);
    }

    private void removeReporting() {
        ModelMap.getDefault().removeEntry(reporting);
        this.reporting = null;
    }

    public List<Property> getProperty() {
        return getReporting().getProperty();
    }
}
