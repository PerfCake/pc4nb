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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfcake.pc4nb.core.model.ModelMap;

/**
 *
 * @author Andrej Halaj
 */
public class ValidatorsTableModel extends AbstractTableModel {
    private List<Validator> validators =  new ArrayList<>();

    @Override
    public int getRowCount() {
        return validators.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public void addRow(Validator validator) {
        int lastRow = validators.size();
        insertRow(lastRow, validator);
    }

    public void insertRow(int index, Validator validator) {
        validators.add(index, validator);
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, Validator validator) {
        ModelMap.getDefault().removeEntry(validators.get(index));
        validators.set(index, validator);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        validators.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Validator validator = validators.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return validator.getId();
            case 1:
                return validator.getClazz();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
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
                return Integer.class;
            case 1:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
