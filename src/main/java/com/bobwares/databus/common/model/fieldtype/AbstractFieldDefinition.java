package com.bobwares.databus.common.model.fieldtype;

import com.bobwares.databus.common.model.Alignment;

public abstract class AbstractFieldDefinition implements FieldTypeDefinition {

	protected final String name;
	protected final Alignment alignment;

	public AbstractFieldDefinition(String name) {
		this.name = name;
		this.alignment = Alignment.LEFT;
	}

	public AbstractFieldDefinition(String name, Alignment alignment) {
		this.name = name;
		this.alignment = alignment;
	}

    @Override
	public String getName() {
    	return name;
	}

	@Override
	public Alignment getAlignment() {
		return alignment;
	}

    @Override
	public String format(String input) {
    	return input;
    }


}
