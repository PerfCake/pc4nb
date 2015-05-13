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

package org.perfcake.pc4nb.model;

import org.perfcake.model.Scenario.Generator.Run;

public class RunModel extends PC4NBModel {
	public static final String PROPERTY_TYPE = "run-type";
	public static final String PROPERTY_VALUE = "run-value";
        
	private Run run;
	
	public RunModel(Run run){
		if (run == null){
			throw new IllegalArgumentException("Run must not be null");
		}
		this.run = run;
	}

	public Run getRun(){
		return run;
	}

	public void setType(String value) {
		String oldValue = getRun().getType();
		getRun().setType(value);
		getListeners().firePropertyChange(PROPERTY_TYPE, oldValue, value);
	}

	public void setValue(String value) {
		String oldValue = getRun().getValue();
		getRun().setValue(value);
		getListeners().firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}
}
