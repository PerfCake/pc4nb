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
package org.perfcake.pc4nb.ui;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import org.perfcake.pc4nb.core.model.ReporterModel;
import static org.perfcake.pc4nb.ui.LayoutSizeConstants.PERFCAKE_RECTANGLE_HEIGHT;
import static org.perfcake.pc4nb.ui.LayoutSizeConstants.SECOND_LEVEL_RECTANGLE_WIDTH;

/**
 *
 * @author Andrej Halaj
 */
public class ReporterView extends JPanel {

    ReporterModel model;

    public ReporterView(ReporterModel model) {
        this.model = model;
        this.add(new JLabel(model.getName()));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.setMinimumSize(new Dimension(SECOND_LEVEL_RECTANGLE_WIDTH, PERFCAKE_RECTANGLE_HEIGHT));
        //this.setPreferredSize(new Dimension(SECOND_LEVEL_RECTANGLE_WIDTH, PERFCAKE_RECTANGLE_HEIGHT));
    }
}
