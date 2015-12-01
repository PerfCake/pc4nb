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
package org.perfcake.pc4nb.ui.tableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.perfcake.model.Property;

/**
 *
 * @author Andrej Halaj
 */
public class PropertiesTableModel extends AbstractTableModel {
    private List<Property> properties = new ArrayList<>();
    private List<Property> defaultValues = new ArrayList<>();

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Value";
            default:
                throw new IllegalArgumentException("column");
        }
    }

    public void addRow(Property property) {
        int lastRow = properties.size();
        insertRow(lastRow, property);
    }

    public void insertRow(int index, Property property) {
        properties.add(index, property);
        
        Property defaultValue = new Property();
        defaultValue.setName(property.getName());
        defaultValue.setValue(property.getValue());
        defaultValues.add(index, defaultValue);
        
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, Property property) {
        properties.set(index, property);
        fireTableRowsUpdated(index, index);
    }

    @Override
    public int getRowCount() {
        return properties.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public List<Property> getDefaultValues() {
        return Collections.unmodifiableList(defaultValues);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Property property = properties.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return property.getName();
            case 1:
                return property.getValue();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Property property = properties.get(rowIndex);
        switch (columnIndex) {
            case 1:
                property.setValue((String) value);
                break;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
                return true;
            case 0:
            default:
                return false;
        }
    }
}
