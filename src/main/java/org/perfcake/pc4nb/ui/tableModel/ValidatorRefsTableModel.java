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
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.pc4nb.model.ModelMap;

public class ValidatorRefsTableModel extends AbstractTableModel {
    List<ValidatorRef> validatorRefs = new ArrayList<>();

    @Override
    public int getRowCount() {
        return validatorRefs.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }
    
    public void addRow(ValidatorRef validatorRef) {
        int lastRow = validatorRefs.size();
        insertRow(lastRow, validatorRef);
    }

    public void insertRow(int index, ValidatorRef validatorRef) {
        validatorRefs.add(index, validatorRef);
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, ValidatorRef validatorRef) {
        ModelMap.getDefault().removeEntry(validatorRefs.get(index));
        validatorRefs.set(index, validatorRef);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        validatorRefs.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }
    
    public List<ValidatorRef> getValidatorRefs() {
        return Collections.unmodifiableList(validatorRefs);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ValidatorRef validatorRef = validatorRefs.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return validatorRef.getId();
            default:
                return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
}
