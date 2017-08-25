package com.bobwares.core.jdbc.util;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.maritz.core.jdbc.builder.ColumnType;
import com.maritz.core.util.StringUtils;


public class ConcentrixMapRowMapper extends AbstractConcentrixRowMapper<Map<String ,Object>> {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Map<String, Class<?>> resultTypes;
	protected Map<String, Boolean> encryptedColumnMap = new HashMap<>();
	protected boolean camelCase = true;
	protected boolean capFirstChar = false;
	protected String prefix;
	protected Map<String, Class<?>> defaultTypes = new HashMap<>(32);
	protected Map<String, String> columnNameMap = new HashMap<>(32);

	
	// ***********************************************************
	// Constructors
	// ***********************************************************

	public ConcentrixMapRowMapper() {
		super();
	}
	
	
	// ***********************************************************
	// Getters/Setters
	// ***********************************************************
	
	public ConcentrixMapRowMapper setResultTypes(Map<String, Class<?>> resultTypes) {
		this.resultTypes = resultTypes;
		return this;
	}

	public ConcentrixMapRowMapper setCamelCase(boolean camelCase) {
		this.camelCase = camelCase;
		return this;
	}	

	public ConcentrixMapRowMapper setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public ConcentrixMapRowMapper setCapFirstChar(boolean capFirstChar) {
		//NOTE: this only applies when camel case is true
		this.capFirstChar = capFirstChar;
		return this;
	}		

	@Override
	public ConcentrixMapRowMapper build() {
		super.build();
		return this;
	}
		
	
	// ***********************************************************
	// RowMapper Interface Impl
	// ***********************************************************
	
	@Override
	public Map<String ,Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		Map<String, Object> result = new LinkedHashMap<String, Object>(columnCount);
		
		for (int index = 1; index <= columnCount; index++) {
			String columnName = JdbcUtils.lookupColumnName(rsmd, index);
			String resultName = getResultName(columnName);
			Class<?> resultType = getResultType(resultName, rsmd.getColumnType(index));
			boolean isEncrypted = getIsEncrypted(resultName, rsmd.getColumnTypeName(index));
			
			if (rowNum == 0 && logger.isDebugEnabled()) {
				logger.debug("Mapping column '" + columnName + "' to result '" + resultName + "' of type " + resultType.getName());
			}
			
			Object resultValue = getColumnValue(rs, index, resultType, isEncrypted);
			result.put(resultName, resultValue);
		}
		
		return result;
	}
	
	protected String getResultName(String columnName) {
		String key = columnName.toUpperCase();
		String result = columnNameMap.get(key);
		if (result == null) {
			if (prefix != null) {
				result = prefix + ((camelCase) ? StringUtils.convertDatabaseToCamelCase(columnName, true) : columnName);
			}
			else {
				result = (camelCase) ? StringUtils.convertDatabaseToCamelCase(columnName, capFirstChar) : columnName;
			}
			columnNameMap.put(key, result);
		}
		return result;
	}
	
	protected Class<?> getResultType(String resultName, int columnType) {
		Class<?> resultType = (resultTypes != null) ? resultTypes.get(resultName) : null;
		if (resultType == null) {
			resultType = defaultTypes.get(resultName);
			if (resultType == null) {
				resultType = ColumnType.valueOfJDBCType(columnType).getJavaObjectClass();
				defaultTypes.put(resultName, resultType);
			}
		}
		return resultType;
	}
	
	protected boolean getIsEncrypted(String resultName, String columnTypeName) {
		Boolean isEncrypted = encryptedColumnMap.get(resultName);
		if (isEncrypted == null) {
			if (isEncrypted(resultName)) {
				isEncrypted = Boolean.TRUE;
			}
			else if (columnTypeName.startsWith("TY_ENCRYPTED_")) {
				//NOTE: the ms sql jdbc driver does not return user type names only standard sql type names
				isEncrypted = Boolean.TRUE;
			}
			else {
				 isEncrypted = Boolean.FALSE;
			}
			encryptedColumnMap.put(resultName, isEncrypted);
		}
		return isEncrypted.booleanValue();
	}
}
