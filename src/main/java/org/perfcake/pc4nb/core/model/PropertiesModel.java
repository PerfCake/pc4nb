/*
 * PerfClispe
 * 
 * 
 * Copyright (c) 2014 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfcake.pc4nb.core.model;

import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Properties;

public class PropertiesModel extends PC4NBModel {


	public static final String PROPERTY_PROPERTIES = "properties-property";
	
	private Properties properties;

	public PropertiesModel(Properties properties) {
		if (properties == null){
			throw new IllegalArgumentException("Properties must not be null");
		}

		this.properties = properties;
	}

	
	/**
	 * This method should not be used for modifying properties (in a way getProperties().add()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Properties 
	 */
	public Properties getProperties() {
		return properties;
	}
	
	public void addProperty(Property Property){
		addProperty(getProperties().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getProperties().getProperty().add(index, property);
		getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getProperties().getProperty().remove(property)){
			getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getProperties().getProperty();
	}
}
