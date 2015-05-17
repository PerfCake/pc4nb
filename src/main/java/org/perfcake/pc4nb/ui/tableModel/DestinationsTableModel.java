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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.pc4nb.model.ModelMap;

/**
 *
 * @author Andrej Halaj
 */
public class DestinationsTableModel extends AbstractTableModel {

    List<Destination> destinations = new ArrayList<>();

    @Override
    public int getRowCount() {
        return destinations.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public List<Destination> getDestinations() {
        return Collections.unmodifiableList(destinations);
    }

    public void addRow(Destination destination) {
        destinations.add(destination);
    }

    public void insertRow(int index, Destination destination) {
        destinations.add(index, (Destination) destination);
        fireTableRowsInserted(index, index);
    }

    public void updateRow(int index, Destination destination) {
        ModelMap.getDefault().removeEntry(destinations.get(index));
        destinations.set(index, destination);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        destinations.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Destination destination = destinations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return destination.isEnabled();
            case 1:
                return destination.getClazz();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Destination destination = destinations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                destination.setEnabled((Boolean) value);
                break;
            case 1:
                destination.setClazz((String) value);
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

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Enabled";
            case 1:
                return "Class";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
