package com.bobwares.core.jdbc.util;


public class CamelCaseMapRowMapper extends ConcentrixMapRowMapper {

    public CamelCaseMapRowMapper() {
        super();
    }

    public CamelCaseMapRowMapper(String prefix) {
        super();
        setPrefix(prefix);
    }

    public CamelCaseMapRowMapper(boolean capFirstChar) {
        super();
        setCapFirstChar(capFirstChar);
    }

}
