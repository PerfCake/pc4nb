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
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.pc4nb.core.model.ModelMap;

/**
 *
 * @author Andrej Halaj
 */
public class ReportersTableModel extends AbstractTableModel {
     private List<Reporter> reporters =  new ArrayList<>();

    @Override
    public int getRowCount() {
        return reporters .size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public void addRow(Reporter reporter) {
        int lastRow = reporters.size();
        insertRow(lastRow, reporter);
    }

    public void insertRow(int index, Reporter reporter) {
        reporters.add(index, reporter);
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, Reporter reporter) {
        ModelMap.getDefault().removeEntry(reporters.get(index));
        reporters.set(index, reporter);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        reporters.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reporter reporter = reporters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return reporter.isEnabled();
            case 1:
                return reporter.getClazz();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Enabled";
            case 1:
                return "Type";
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
