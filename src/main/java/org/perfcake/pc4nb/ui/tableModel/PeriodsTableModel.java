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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.pc4nb.core.model.ModelMap;

/**
 *
 * @author Andrej Halaj
 */
public class PeriodsTableModel extends AbstractTableModel {
    List<Period> periods = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return periods.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    public List<Period> getPeriods() {
        return Collections.unmodifiableList(periods);
    }

    public void addRow(Period period) {
        int lastRow = periods.size();
        insertRow(lastRow, period);
    }

    public void insertRow(int index, Period period) {
        periods.add(index, (Period) period);
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, Period period) {
        ModelMap.getDefault().removeEntry(periods.get(index));
        periods.set(index, period);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        periods.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Period period = periods.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return period.getType();
            case 1:
                return period.getValue();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Type";
            case 1:
                return "Value";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
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
}
