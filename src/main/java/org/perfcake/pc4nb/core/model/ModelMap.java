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
package org.perfcake.pc4nb.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andrej Halaj
 */
public class ModelMap {
    private static ModelMap instance = new ModelMap();
    private Map<Object, PC4NBModel> modelMap = new HashMap<>();
    
    private ModelMap() {}
    
    public static ModelMap getDefault() {
        return instance;
    }
    
    public void addEntry(Object perfCakeModel, PC4NBModel pc4nbModel) {
        modelMap.put(perfCakeModel, pc4nbModel);
    }
    
    public void removeEntry(Object perfCakeModel) {
        modelMap.remove(perfCakeModel);
    }
    
    public PC4NBModel getPC4NBModelFor(Object perfCakeModel) {
        return modelMap.get(perfCakeModel);
    }
}
