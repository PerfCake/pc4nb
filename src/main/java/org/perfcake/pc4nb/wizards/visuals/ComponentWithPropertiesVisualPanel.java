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
package org.perfcake.pc4nb.wizards.visuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JTable;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.ui.AbstractPC4NBVisualPanel;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;

/**
 *
 * @author Andrej Halaj
 */
public abstract class ComponentWithPropertiesVisualPanel extends AbstractPC4NBVisualPanel {
    private PropertiesTableModel propertiesTableModel;
    private Map<String, PropertiesTableModel> componentPropertiesMap;
    
    public ComponentWithPropertiesVisualPanel() {
        this.componentPropertiesMap = new HashMap<>();

        setPropertiesTableModel(new PropertiesTableModel());
    }

    public void listProperties(String clazz) throws ClassNotFoundException, NoSuchFieldException {
        setPropertiesTableModel(getComponentPropertiesModelMap().get(clazz));
        getPropertiesTable().setModel(getPropertiesTableModel());
    }
    
    public final void putToComponentPropertiesMap(String component, Properties properties) throws ClassNotFoundException {
        PropertiesTableModel tableModel = new PropertiesTableModel();
        
        for (Iterator<Object> it = properties.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            
            Property property = new Property();
            property.setName(name);
            property.setValue((String) properties.get(name));
            tableModel.addRow(property);
        }
        
        componentPropertiesMap.put(component, tableModel);
    }
    
    public final List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();  
        
        for (int i = 0; i < getPropertiesTableModel().getRowCount(); i++) {
            Property property = new Property();
            property.setName((String) getPropertiesTableModel().getValueAt(i, 0));
            property.setValue(getPropertiesTableModel().getValueAt(i, 1).toString());
            properties.add(property);
        }
        
        return properties;
    }

    public final PropertiesTableModel getPropertiesTableModel() {
        return propertiesTableModel;
    }

    public final void setPropertiesTableModel(PropertiesTableModel propertiesTableModel) {
        this.propertiesTableModel = propertiesTableModel;
    }
    
    public abstract JTable getPropertiesTable();

    public Map<String, PropertiesTableModel> getComponentPropertiesModelMap() {
        return componentPropertiesMap;
    }

    public void setComponentPropertiesModelMap(Map<String, PropertiesTableModel> componentPropertiesModelMap) {
        this.componentPropertiesMap = componentPropertiesModelMap;
    }
}
