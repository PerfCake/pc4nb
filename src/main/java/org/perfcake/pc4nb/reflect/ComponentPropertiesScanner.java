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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.openide.util.Exceptions;
import org.perfcake.message.Message;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.message.sender.MessageSender;
import org.perfcake.validation.MessageValidator;
import org.perfcake.message.generator.AbstractMessageGenerator;

/**
 *
 * @author Andrej Halaj
 */
public class ComponentPropertiesScanner {

    Set<Method> allComponentMethods;
    Set<String> allComponentFieldNames;
    Object componentObject;
    Class baseClass = null;

    public Properties getPropertiesOfComponent(Class component) {
        baseClass = getBaseClass(component);
        allComponentMethods = getAllMethodsOfComponent(component);
        allComponentFieldNames = getAllFieldNamesOfComponent(component);
        componentObject = null;

        try {
            componentObject = component.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
            // log
        }

        Properties properties = getDefaultValuesOfFields();

        return properties;
    }
    
    private Class getBaseClass(Class component) {
        Class baseClass;
        
        if (Reporter.class.isAssignableFrom(component)) {
            baseClass = Reporter.class;
        } else if (MessageValidator.class.isAssignableFrom(component)) {
            baseClass = MessageValidator.class;
        } else if (AbstractMessageGenerator.class.isAssignableFrom(component)) {
            baseClass = AbstractMessageGenerator.class;
        } else if (MessageSender.class.isAssignableFrom(component)) {
            baseClass = MessageSender.class;
        } else if (Destination.class.isAssignableFrom(component)) {
            baseClass = Destination.class;
        } else if (Message.class.isAssignableFrom(component)) {
            baseClass = Message.class;
        } else {
            baseClass = null;
            // throw exception, log
        }
        
        return baseClass;
    }

    private Set<Method> getAllMethodsOfComponent(Class component) {
        Set<Method> allMethodsOfComponent = new HashSet<>(Arrays.asList(component.getDeclaredMethods()));

        Class superClass = component.getSuperclass();

        while (baseClass.isAssignableFrom(superClass)) {
            allMethodsOfComponent.addAll(getAllMethodsOfComponent(superClass));
            superClass = superClass.getSuperclass();
        }

        return allMethodsOfComponent;
    }

    private Set<String> getAllFieldNamesOfComponent(Class component) {
        Set<String> componentFieldNames = new HashSet<>();

        for (Field componentField : getAllFieldsOfComponent(component)) {
            componentFieldNames.add(componentField.getName());
        }

        return componentFieldNames;
    }

    private Properties getDefaultValuesOfFields() {
        Properties properties = new Properties();

        for (Method componentMethod : allComponentMethods) {
            String componentMethodName = componentMethod.getName();
            String fieldName;

            if (isFieldGetter(componentMethod)) { // kuknut sa na to s tym array
                if (componentMethodName.startsWith("get")) {
                    fieldName = Character.toLowerCase(componentMethodName.charAt(3)) + componentMethodName.substring(4);
                } else if (componentMethodName.startsWith("is")) {
                    fieldName = Character.toLowerCase(componentMethodName.charAt(2)) + componentMethodName.substring(3);
                } else {
                    continue;
                }

                if (allComponentFieldNames.contains(fieldName)) {
                    try {
                        properties.setProperty(fieldName, String.valueOf(componentMethod.invoke(componentObject)));
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }

        return properties;
    }

    private Set<Field> getAllFieldsOfComponent(Class component) {
        Set<Field> componentFields = new HashSet<>(Arrays.asList(component.getDeclaredFields()));

        Class superClass = component.getSuperclass();
        while (baseClass.isAssignableFrom(superClass)) {
            componentFields.addAll(getAllFieldsOfComponent(superClass));
            superClass = superClass.getSuperclass();
        }

        return componentFields;
    }
    
    private boolean isFieldGetter(Method componentMethod) {
        return isPublic(componentMethod) && takesNoParameters(componentMethod)
                && !componentMethod.getReturnType().isArray() && !componentMethod.getReturnType().equals(Properties.class);
    }
    
    private boolean isPublic(Method componentMethod) {
        return Modifier.isPublic(componentMethod.getModifiers());
    }
    
    private static boolean takesNoParameters(Method componentMethod) {
        return componentMethod.getParameterTypes().length == 0;
    }
}
