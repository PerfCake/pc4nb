package org.perfcake.pc4nb.model;

import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class ValidatorRefModel extends PC4NBModel {

	public static final String PROPERTY_ID = "validatorRef-id";
	
	private ValidatorRef validatorRef;

	public ValidatorRefModel(ValidatorRef validatorRef) {
		if (validatorRef == null){
			throw new IllegalArgumentException("ValidatorRef must not be null.");
		}
		this.validatorRef = validatorRef;
                ModelMap.getDefault().addEntry(validatorRef, this);
	}

	public ValidatorRef getValidatorRef() {
		return validatorRef;
	}
	
	public void setId(String id){
		String oldId = getValidatorRef().getId();
		getValidatorRef().setId(id);
		getListeners().firePropertyChange(PROPERTY_ID, oldId, id);
	}
}
