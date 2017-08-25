package com.bobwares.databus.common.model.fieldtype;

import com.bobwares.databus.common.renderer.pdf.Alignment;

public interface FieldTypeDefinition {

	String getName();

    Alignment getAlignment();

    String format(String input);

}
