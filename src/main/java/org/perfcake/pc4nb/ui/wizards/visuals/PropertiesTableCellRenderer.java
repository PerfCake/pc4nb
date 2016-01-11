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

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;

public class PropertiesTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        PropertiesTableModel model = (PropertiesTableModel) table.getModel();
        PropertyModel property = model.getProperties().get(row);

        boolean isMandatory = property.isMandatory();

        if (column == 0) {    
            if (isMandatory) {
                cell.setForeground(Color.RED);
            } else {
                cell.setForeground(Color.BLACK);
            }
        } else if (column == 1) {
            if (property.isDefault()) {
                cell.setForeground(Color.GRAY);
            } else {
                cell.setForeground(Color.GREEN);
            }
        } else {
            cell.setForeground(Color.BLUE);
        }
        
        return cell;
    }

}
