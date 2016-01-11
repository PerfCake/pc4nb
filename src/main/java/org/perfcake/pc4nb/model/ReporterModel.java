/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
 * Modifications copyright (c) 2015 Andrej Halaj
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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;

public final class ReporterModel extends PC4NBModel implements Transferable {
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(ReporterModel.class, "reporter");

    public static final String PROPERTY_CLASS = "reporter-class";
    public static final String PROPERTY_DESTINATIONS = "reporter-destination";
    public static final String PROPERTY_PROPERTIES = "reporter-property";
    public static final String PROPERTY_ENABLED = "reporter-enabled";

    private Reporter reporter;

    public ReporterModel(Reporter reporter) {;
        if (reporter == null) {
            throw new IllegalArgumentException("Reporter must not be null");
        }
        this.reporter = reporter;
        ModelMap.getDefault().addEntry(reporter, this);
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setClazz(String clazz) {
        String oldClazz = getReporter().getClazz();
        getReporter().setClazz(clazz);
        getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
    }

    public void addDestination(Destination destination) {
        addDestination(getReporter().getDestination().size(), destination);
    }

    public void addDestination(int index, Destination destination) {
        getReporter().getDestination().add(index, destination);
        getListeners().firePropertyChange(PROPERTY_DESTINATIONS, null, destination);
    }

    public void removeDestination(Destination destination) {
        if (getReporter().getDestination().remove(destination)) {
            getListeners().firePropertyChange(PROPERTY_DESTINATIONS, destination, null);
        }
    }

    public void addProperty(Property Property) {
        addProperty(getReporter().getProperty().size(), Property);
    }

    public void addProperty(int index, Property property) {
        getReporter().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getReporter().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }
    }

    public List<Property> getProperty() {
        return getReporter().getProperty();
    }

    public void setEnabled(boolean enabled) {
        if (enabled != getReporter().isEnabled()) {
            getReporter().setEnabled(enabled);
            getListeners().firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
        }
    }

    public boolean isEnabled() {
        return getReporter().isEnabled();
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor == DATA_FLAVOR) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor == DATA_FLAVOR;
    }
}
