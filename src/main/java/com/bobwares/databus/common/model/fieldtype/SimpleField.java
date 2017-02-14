package com.bobwares.databus.common.model.fieldtype;


import com.bobwares.databus.common.model.Alignment;

public class SimpleField extends AbstractFieldDefinition {

	public SimpleField(String name) {
		super(name);
	}

	public SimpleField(String name, Alignment alignment) {
		super(name, alignment);
	}

}
