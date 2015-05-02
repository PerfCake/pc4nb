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
package org.perfcake.pc4nb.reflect;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author Andrej Halaj
 */
public class ComponentScanner {

    public <T> Set<Class<? extends T>> findComponentsOfType(Class<T> componentType, String packageName) {
        Set<Class<? extends T>> classes = new HashSet<>();
        Reflections reflections = new Reflections(packageName);

        Set<Class<? extends T>> allClasses = reflections.getSubTypesOf(componentType);

        for (Class<? extends T> componentClass : allClasses) {
            int componentClassModifiers = componentClass.getModifiers();
            if (!Modifier.isAbstract(componentClassModifiers) && !Modifier.isInterface(componentClassModifiers)) {
                classes.add(componentClass);
            }
        }
        
         if (!Modifier.isAbstract(componentType.getModifiers()) && !Modifier.isInterface(componentType.getModifiers())) {
             classes.add(componentType);
         }

        return classes;
    }
}
