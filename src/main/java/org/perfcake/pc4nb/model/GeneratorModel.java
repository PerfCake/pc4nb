/*
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
import org.perfcake.model.Scenario.Generator;

public final class GeneratorModel extends PC4NBModel implements Transferable {
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(GeneratorModel.class, "generator");

    public final static String PROPERTY_CLASS = "generator-class";
    public final static String PROPERTY_THREADS = "generator-threads";
    public final static String PROPERTY_PROPERTY = "generator-property";

    private Generator generator;

    public GeneratorModel(Generator generator) {
        if (generator == null) {
            throw new IllegalArgumentException("Generator must not be null");
        }
        this.generator = generator;
        ModelMap.getDefault().addEntry(generator, this);
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setThreads(String value) {
        String oldValue = getGenerator().getThreads();
        getGenerator().setThreads(value);
        getListeners().firePropertyChange(PROPERTY_THREADS, oldValue, value);
    }

    public void setClazz(String value) {
        String oldValue = getGenerator().getClazz();
        getGenerator().setClazz(value);
        getListeners().firePropertyChange(PROPERTY_CLASS, oldValue, value);
    }

    public void addProperty(Property newProperty) {
        addProperty(getGenerator().getProperty().size(), newProperty);
    }

    public void addProperty(int index, Property property) {
        getGenerator().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTY, null, property);
    }

    public void removeProperty(Property property) {
        if (getGenerator().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTY, property, null);
        }
    }

    public List<Property> getProperty() {
        return getGenerator().getProperty();
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
