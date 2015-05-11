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
package org.perfcake.pc4nb.ui3;

import java.awt.Color;
import org.perfcake.pc4nb.core.model.PC4NBModel;
import org.perfcake.pc4nb.core.model.SenderModel;

/**
 *
 * @author Andrej Halaj
 */
public class SenderView extends PC4NBView {

    public SenderView(int x, int y, int width) {
        super(x, y, width);
        setColor(Color.BLUE);
        setHeader("Sender");
    }
    
    @Override
    public void setModel(PC4NBModel model) {
        super.setModel(model);
        
        SenderModel senderModel = (SenderModel) model;
        setHeader(senderModel.getSender().getClazz());
    }
}
