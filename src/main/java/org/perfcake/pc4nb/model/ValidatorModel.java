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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Validation.Validator;

public class ValidatorModel extends PC4NBModel implements Transferable {
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(ValidatorModel.class, "validator");

    public static final String PROPERTY_CLASS = "validator-class";
    public static final String PROPERTY_ID = "validator-id";
    public static final String PROPERTY_VALUE = "validator-value";
    public static final String PROPERTY_PROPERTIES = "validator-properties";

    private Validator validator;

    public ValidatorModel(Validator validator) {
        if (validator == null) {
            throw new IllegalArgumentException("Validator must not be null.");
        }
        this.validator = validator;
        ModelMap.getDefault().addEntry(validator, this);
    }

    public Validator getValidator() {
        return validator;
    }

    public void setClazz(String clazz) {
        String oldClazz = getValidator().getClazz();
        getValidator().setClazz(clazz);
        getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
    }

    public void setId(String id) {
        String oldId = getValidator().getId();
        getValidator().setId(id);
        getListeners().firePropertyChange(PROPERTY_ID, oldId, id);
    }

    public void addProperty(Property Property) {
        addProperty(getValidator().getProperty().size(), Property);
    }

    public void addProperty(int index, Property property) {
        getValidator().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getValidator().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }
    }

    public List<Property> getProperty() {
        return getValidator().getProperty();
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
