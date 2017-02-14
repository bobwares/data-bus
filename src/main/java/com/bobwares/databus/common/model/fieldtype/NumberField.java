package com.bobwares.databus.common.model.fieldtype;

import com.bobwares.databus.common.model.Alignment;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberField extends AbstractFieldDefinition {

	public NumberField(String name) {
		super(name, Alignment.RIGHT);
	}

	public NumberField(String name, Alignment alignment) {
		super(name, alignment);
	}

    @Override
	public String format(String input) {
    	return formatNumber(input, NumberFormat.getNumberInstance(Locale.ENGLISH));
    }



    protected String formatNumber(String value, NumberFormat numberFormat) {
        double dollarValue = Double.parseDouble(value);
        boolean negative = dollarValue < 0;
        if (negative) dollarValue = Math.abs(dollarValue);

        String result = numberFormat.format(dollarValue);

        return negative ? "(" + result + ")" : result;
    }
}
