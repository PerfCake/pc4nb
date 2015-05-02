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
package org.perfcake.pc4nb.ui.palette;

import java.awt.datatransfer.Transferable;
import java.io.IOException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.perfcake.pc4nb.core.model.MessageModel;

/**
 *
 * @author Andrej Halaj
 */
public class PerfCakeMessageNode extends AbstractNode {
    private MessageModel model = null;
    
    public PerfCakeMessageNode(MessageModel model) {
        super(Children.LEAF);
        this.model = model;
        this.setDisplayName(model.getMessage().getUri());
    }
    
    @Override
    public Transferable drag() throws IOException {
        return model;
    }
}
