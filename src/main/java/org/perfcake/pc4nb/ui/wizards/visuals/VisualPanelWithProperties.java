/*
 * Copyright (c) 2015 Andrej Halaj
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

package org.perfcake.pc4nb.ui.wizards.visuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.ui.AbstractPC4NBView;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;

public abstract class VisualPanelWithProperties extends AbstractPC4NBView {

    private PropertiesTableModel propertiesTableModel;
    private Map<String, PropertiesTableModel> componentPropertiesMap;

    public VisualPanelWithProperties() {
        this.componentPropertiesMap = new HashMap<>();

        setPropertiesTableModel(new PropertiesTableModel());
    }

    public void listProperties(String clazz) throws ClassNotFoundException, NoSuchFieldException {
        setPropertiesTableModel(getComponentPropertiesModelMap().get(clazz));
        getPropertiesTable().setModel(getPropertiesTableModel());
    }

    public final void putToComponentPropertiesMap(String component, List<PropertyModel> properties) throws ClassNotFoundException {
        PropertiesTableModel tableModel;

        if (componentPropertiesMap.containsKey(component)) {
            tableModel = componentPropertiesMap.get(component);

            int index = -1;
            
            for (PropertyModel property : properties) {
                tableModel.updateRow(++index, property);

            }
        } else {
            tableModel = new PropertiesTableModel();

            for (PropertyModel property : properties) {
                tableModel.addRow(property);
            }
        }

        componentPropertiesMap.put(component, tableModel);
    }

    public final List<PropertyModel> getPropertiesFor(String clazz) {
        PropertiesTableModel tableModel = componentPropertiesMap.get(clazz);

        if (tableModel != null) {
            return tableModel.getProperties();
        } else {
            return new ArrayList<>();
        }
    }

    public final List<PropertyModel> getProperties() {
        return getPropertiesTableModel().getProperties();
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
