/*
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openide.util.Exceptions;
import org.perfcake.message.Message;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.message.sender.MessageSender;
import org.perfcake.validation.MessageValidator;
import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.generator.MessageGenerator;
import org.perfcake.pc4nb.model.PropertyModel;
import org.perfcake.util.properties.MandatoryProperty;

/**
 *
 * @author Andrej Halaj
 */
public class ComponentPropertiesScanner {
    private static final Logger logger = LogManager.getLogger(ComponentPropertiesScanner.class);
    
    private Set<Field> allComponentFields;
    private Set<Method> allComponentMethods;
    private Set<String> allComponentFieldNames;
    private Set<String> mandatoryPropertiesNames;
    private Object componentObject;
    private Class baseClass = null;

    public List<PropertyModel> getPropertiesOfComponent(Class component) {
        getBaseClass(component);
        allComponentMethods = getAllMethodsOfComponent(component);
        allComponentFields = getAllFieldsOfComponent(component);
        allComponentFieldNames = getAllFieldNamesOfComponent();
        mandatoryPropertiesNames = getMandatoryPropertiesNames();
        componentObject = null;

        try {
            componentObject = component.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.log(Level.ERROR, ex.getLocalizedMessage());
        }

        Properties properties = getDefaultValuesOfFields();
        
        if (MessageGenerator.class.isAssignableFrom(component)) {
            properties.remove("threads");
        }
        
        if (MessageSender.class.isAssignableFrom(component)) {
            properties.remove("target");
        }
        
        List<PropertyModel> extendedProperties = new ArrayList<>();
        
        for (Iterator<Object> it = properties.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            String value = (String) properties.get(name);
            PropertyModel newProp = new PropertyModel(name, value, mandatoryPropertiesNames.contains(name));
            
            extendedProperties.add(newProp);
        }

        return extendedProperties;
    }
    
    private void getBaseClass(Class component) {
        if (Reporter.class.isAssignableFrom(component)) {
            baseClass = Reporter.class;
        } else if (MessageValidator.class.isAssignableFrom(component)) {
            baseClass = MessageValidator.class;
        } else if (MessageGenerator.class.isAssignableFrom(component)) {
            baseClass = AbstractMessageGenerator.class;
        } else if (MessageSender.class.isAssignableFrom(component)) {
            baseClass = MessageSender.class;
        } else if (Destination.class.isAssignableFrom(component)) {
            baseClass = Destination.class;
        } else if (Message.class.isAssignableFrom(component)) {
            baseClass = Message.class;
        } else {
            baseClass = null;
            logger.log(Level.WARN, "ComponentPropertiesScanner has been used for " + component.getSimpleName() + ",but it only supports PerfCake.");
        }
    }
    
    private Set<String> getMandatoryPropertiesNames() {
        Set<String> mandatoryProperties = new HashSet<>();
        
        for (Field field : allComponentFields) {
            if (field.isAnnotationPresent(MandatoryProperty.class)) {
                mandatoryProperties.add(field.getName());
            }
        }
        
        return mandatoryProperties;
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

    private Set<String> getAllFieldNamesOfComponent() {
        Set<String> componentFieldNames = new HashSet<>();

        for (Field componentField : allComponentFields) {
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
                        logger.log(Level.ERROR, ex.getLocalizedMessage());
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
