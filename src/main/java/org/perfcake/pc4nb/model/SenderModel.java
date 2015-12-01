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
import org.perfcake.model.Scenario.Sender;

public final class SenderModel extends PC4NBModel implements Transferable {
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(SenderModel.class, "sender");

    public static final String PROPERTY_CLASS = "sender-class";
    public static final String PROPERTY_TARGET = "sender-target";
    public static final String PROPERTY_PROPERTIES = "sender-property";

    private Sender sender;

    public SenderModel(Sender sender) {
        if (sender == null) {
            throw new IllegalArgumentException("Sender must not be null");
        }
        this.sender = sender;
        ModelMap.getDefault().addEntry(sender, this);
    }

    public Sender getSender() {
        return sender;
    }

    public void setClazz(String clazz) {
        String oldClazz = getSender().getClazz();
        getSender().setClazz(clazz);
        getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
    }
    
    public void setTarget(String target) {
        String oldTarget = getSender().getTarget();
        getSender().setTarget(target);
        getListeners().firePropertyChange(PROPERTY_TARGET, oldTarget, target);
    }
    
    public String getTarget() {
        return getSender().getTarget();
    }

    public void addProperty(Property Property) {
        addProperty(getSender().getProperty().size(), Property);
    }

    public void addProperty(int index, Property property) {
        getSender().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getSender().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }
    }

    public List<Property> getProperty() {
        return getSender().getProperty();
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
