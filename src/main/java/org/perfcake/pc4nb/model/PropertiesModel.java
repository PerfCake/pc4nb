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

import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Properties;

public final class PropertiesModel extends PC4NBModel {

    public static final String PROPERTY_PROPERTIES = "properties-property";

    private Properties properties;

    public PropertiesModel(Properties properties) {
        this.properties = properties;

        if (properties != null) {
            ModelMap.getDefault().addEntry(properties, this);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        if (getProperties() == null) {
            createProperties();
        }
        
        getProperties().getProperty().add(property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void addProperty(int index, Property property) {
        if (getProperties() == null) {
            createProperties();
        }

        getProperties().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getProperties().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }

        if (getProperties().getProperty().isEmpty()) {
            removeProperties();
        }
    }

    public List<Property> getProperty() {
        if (getProperties() == null) {
            return null;
        } else {
            return getProperties().getProperty();
        }
    }

    private void createProperties() {
        this.properties = new Properties();
        ModelMap.getDefault().addEntry(properties, this);
    }

    private void removeProperties() {
        ModelMap.getDefault().removeEntry(properties);
        this.properties = null;
    }
}
