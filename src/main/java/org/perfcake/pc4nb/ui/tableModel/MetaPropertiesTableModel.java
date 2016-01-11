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

package org.perfcake.pc4nb.ui.tableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.perfcake.model.Property;

public class MetaPropertiesTableModel extends AbstractTableModel {
    List<Property> properties = new ArrayList<>();

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

    public void addRow() {
        Property newProperty = new Property();
        properties.add(newProperty);
        fireTableRowsInserted(properties.size(), properties.size());
    }

    public void removeRow(int rowNum) {
        properties.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }

    @Override
    public int getRowCount() {
        return properties.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
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
            case 0:
                property.setName((String) value);
                break;
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
            case 0:
            case 1:
                return true;
            default:
                return false;
        }
    }
    
    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }
}
