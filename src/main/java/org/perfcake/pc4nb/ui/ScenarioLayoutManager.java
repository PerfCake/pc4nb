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

import java.util.EnumMap;
import java.util.Map;
import static org.perfcake.pc4nb.ui.ComponentCategory.*;
import static org.perfcake.pc4nb.ui.SizeConstraints.INSET;
import static org.perfcake.pc4nb.ui.SizeConstraints.TOP_LEVEL_RECTANGLE_WIDHT;

/**
 *
 * @author Andrej Halaj
 */
public class ScenarioLayoutManager {
    private Map<ComponentCategory, PC4NBView> children = new EnumMap<>(ComponentCategory.class);

    private int width = TOP_LEVEL_RECTANGLE_WIDHT;

    public ScenarioLayoutManager() {
        
        children.put(GENERATOR, new GeneratorView(INSET, INSET, 0));
        children.put(SENDER, new SenderView(INSET, 0, 0));
        children.put(MESSAGES, new MessagesView(INSET, 0, 0));
        children.put(VALIDATION, new ValidationView(INSET, 0, 0));
        children.put(REPORTING, new ReportingView(INSET, 0, 0));
        children.put(PROPERTIES, new PropertiesView(INSET, 0, 0));
    }

    public PC4NBView getView(ComponentCategory category) {
        return children.get(category);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width >= TOP_LEVEL_RECTANGLE_WIDHT) {
            this.width = width;
        }

        recomputeChildren();
    }

    public void recomputeChildren() {
        PC4NBView generator = children.get(GENERATOR);
        PC4NBView sender = children.get(SENDER);
        PC4NBView messages = children.get(MESSAGES);
        PC4NBView validation = children.get(VALIDATION);
        PC4NBView reporting = children.get(REPORTING);
        PC4NBView properties = children.get(PROPERTIES);

        generator.setWidth(getWidth() - 2 * INSET);
        generator.setX(INSET);
        generator.setY(INSET);
        generator.recomputeHeightAndRedraw();

        sender.setWidth(generator.getWidth());
        sender.setY(generator.getY() + generator.getHeight() + INSET);
        sender.recomputeHeightAndRedraw();

        messages.setWidth((getWidth() - 3 * INSET) / 2);
        messages.setY(sender.getY() + sender.getHeight() + INSET);
        messages.recomputeHeightAndRedraw();
        
        validation.setWidth(messages.getWidth());
        validation.setY(messages.getY() + messages.getHeight() + INSET);
        validation.recomputeHeightAndRedraw();

        reporting.setWidth(messages.getWidth());
        reporting.setX(messages.getX() + messages.getWidth() + INSET);
        reporting.setY(messages.getY());
        reporting.recomputeHeightAndRedraw();
        
        int difference = reporting.getHeight() - (messages.getHeight() + validation.getHeight() + INSET);

        if (difference > 0) {
            validation.setHeight(validation.getHeight() + difference);
        } else {
            reporting.setHeight(reporting.getHeight() - difference);
        }

        properties.setWidth(generator.getWidth());
        properties.setY(validation.getY() + validation.getHeight() + INSET);
        properties.recomputeHeightAndRedraw();
    }
}
