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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrej Halaj
 */
public class ComponentPropertiesScanner {
    
    public <T> ComponentPropertiesScanner() {
    }
    
    public <T> Set<String> getPropertiesOfComponent(Class<T> component) throws NoSuchFieldException {
        Set<String> properties = getFieldNamesForComponent(component);
        
        return properties;
    }

    private <T> Set<String> getFieldNamesForComponent(Class<T> component) {
        Method[] allComponentMethods = component.getDeclaredMethods();
        Set<Method> allComponentMethodsSet = new HashSet<>(Arrays.asList(allComponentMethods));
        Set<String> componentFieldNames = new HashSet<>();
        
        for (Method componentMethod : allComponentMethodsSet) {
            String componentMethodName = componentMethod.getName();
            String fieldName;
            
            if (componentMethodName.startsWith("get")) {
                fieldName = componentMethodName.substring(3);
            } else if (componentMethodName.startsWith("is")) {
                fieldName = componentMethodName.substring(2);
            } else {
                continue;
            }
            
            componentFieldNames.add(fieldName); 
        }
        
        return componentFieldNames;
    }
}
