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

import org.perfcake.model.Property;

public final class PropertyModel extends PC4NBModel {

    public static final String PROPERTY_NAME = "property-name";
    public static final String PROPERTY_VALUE = "property-value";

    private Property property;

    public PropertyModel(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property must not be null");
        }
        this.property = property;
        ModelMap.getDefault().addEntry(property, this);
    }

    public Property getProperty() {
        return property;
    }

    public void setName(String name) {
        String oldName = getProperty().getName();
        getProperty().setName(name);
        getListeners().firePropertyChange(PROPERTY_NAME, oldName, name);
    }

    public void setValue(String value) {
        String oldValue = getProperty().getValue();
        getProperty().setValue(value);
        getListeners().firePropertyChange(PROPERTY_VALUE, oldValue, value);
    }
}
