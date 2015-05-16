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

import java.awt.Color;
import javax.swing.border.LineBorder;
import static org.perfcake.pc4nb.ui.SizeConstraints.SECOND_LEVEL_RECTANGLE_WIDTH;

/**
 *
 * @author Andrej Halaj
 */
public class SecondLevelView extends PC4NBView {

    public SecondLevelView(int x, int y, String header) {
        super(x, y, SECOND_LEVEL_RECTANGLE_WIDTH);
        setDefaultBorder(new LineBorder(Color.PINK, 1, true));
        setBorder(getDefaultBorder());
        setHeader(header);
    }
}
