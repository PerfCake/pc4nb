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
import org.perfcake.model.Scenario.Reporting.Reporter;

/**
 *
 * @author Andrej Halaj
 */
public class ReportersTableModel extends AbstractTableModel {
    List<Reporter> reporters = new ArrayList<>();
    
     @Override
    public int getRowCount() {
        return reporters.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    public void addRow(Reporter reporter) {
        reporters.add(reporter);
    }

    public void insertRow(int index, Reporter reporter) {
        reporters.add(index, reporter);
        fireTableRowsInserted(index, index);
    }

    public void updateRow(int index, Reporter reporter) {
        setValueAt(reporter.isEnabled(), index, 0);
        setValueAt(reporter.getClazz(), index, 1);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        reporters.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reporter reporter = reporters.get(rowIndex);
        
        if (columnIndex == 0) {
            return reporter.isEnabled();
        } else if (columnIndex == 1) {
            return reporter.getClazz();
        } else {
            return null;
        }
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Reporter reporter = reporters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                reporter.setEnabled((Boolean) value);
                break;
            case 1:
                reporter.setClazz((String) value);
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
                return "Type";
            default:
                return "";
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
    
    public List<Reporter> getReporters() {
        return Collections.unmodifiableList(reporters);
    }
}
