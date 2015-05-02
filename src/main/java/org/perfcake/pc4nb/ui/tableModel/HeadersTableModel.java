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
import org.perfcake.model.Header;
import org.perfcake.pc4nb.core.model.HeaderModel;
import org.perfcake.pc4nb.core.model.ModelMap;

/**
 *
 * @author Andrej Halaj
 */
public class HeadersTableModel extends AbstractTableModel {

    private List<Header> headers = new ArrayList<>();

    @Override
    public int getRowCount() {
        return headers.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public void addRow(Header header) {
        int lastRow = headers.size();
        insertRow(lastRow, header);
    }

    public void insertRow(int index, Header header) {
        headers.add(index, (Header) header);
        fireTableRowsInserted(index, index);
    }

    public void updateRow(int index, Header header) {
        ModelMap.getDefault().removeEntry(headers.get(index));
        headers.set(index, header);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        headers.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Header header = headers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return header.getName();
            case 1:
                return header.getValue();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
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
