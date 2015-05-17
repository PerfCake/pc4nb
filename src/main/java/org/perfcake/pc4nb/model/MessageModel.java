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

import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class MessageModel extends PC4NBModel implements Transferable {
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(MessageModel.class, "message");
	
    public static final String PROPERTY_HEADERS = "message-header";
    public static final String PROPERTY_PROPERTIES = "message-property";
    public static final String PROPERTY_VALIDATOR_REFS = "message-validator-ref";
    public static final String PROPERTY_URI = "message-uri";
    public static final String PROPERTY_MULTIPLICITY = "message-multiplicity";
    public static final String PROPERTY_CONTENT = "message-content";

    private Message message;

    public MessageModel(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message must not be null.");
        }
        this.message = message;
        ModelMap.getDefault().addEntry(message, this);
    }

    public Message getMessage() {
        return message;
    }

    public void addProperty(Property Property) {
        addProperty(getMessage().getProperty().size(), Property);
    }

    public void addProperty(int index, Property property) {
        getMessage().getProperty().add(index, property);
        getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
    }

    public void removeProperty(Property property) {
        if (getMessage().getProperty().remove(property)) {
            getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
        }
    }

    public List<Property> getProperty() {
        return getMessage().getProperty();
    }

    public void addHeader(Header header) {
        addHeader(getMessage().getHeader().size(), header);
    }

    public void addHeader(int index, Header header) {
        getMessage().getHeader().add(index, header);
        getListeners().firePropertyChange(PROPERTY_HEADERS, null, header);
    }

    public void removeHeader(Header header) {
        if (getMessage().getHeader().remove(header)) {
            getListeners().firePropertyChange(PROPERTY_HEADERS, header, null);
        }
    }

    public void addValidatorRef(ValidatorRef ref) {
        addValidatorRef(getMessage().getValidatorRef().size(), ref);
    }

    public void addValidatorRef(int index, ValidatorRef ref) {
        getMessage().getValidatorRef().add(index, ref);
        getListeners().firePropertyChange(PROPERTY_VALIDATOR_REFS, null, ref);
    }

    public void removeValidatorRef(ValidatorRef ref) {
        if (getMessage().getValidatorRef().remove(ref)) {
            getListeners().firePropertyChange(PROPERTY_VALIDATOR_REFS, ref, null);
        }
    }

    public void setUri(String uri) {
        String oldUri = getMessage().getUri();
        getMessage().setUri(uri);
        getListeners().firePropertyChange(PROPERTY_URI, oldUri, uri);
    }

    public void setMultiplicity(String multiplicity) {
        String oldMultiplicity = getMessage().getMultiplicity();
        getMessage().setMultiplicity(multiplicity);
        getListeners().firePropertyChange(PROPERTY_MULTIPLICITY, oldMultiplicity, multiplicity);
    }

    public void setContent(String content) {
        String oldContent = getMessage().getContent();
        getMessage().setContent(content);
        getListeners().firePropertyChange(PROPERTY_CONTENT, oldContent, content);
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
