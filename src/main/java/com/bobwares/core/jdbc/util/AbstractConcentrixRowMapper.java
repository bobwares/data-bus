package com.bobwares.core.jdbc.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.maritz.core.encryption.Cryptographer;
import com.maritz.core.encryption.DataEncryptionException;
import com.maritz.core.jdbc.builder.Table;
import com.maritz.core.util.DateUtils;
import com.maritz.core.util.ObjectUtils;


public abstract class AbstractConcentrixRowMapper<T> implements RowMapper<T> {
	final Logger logger = LoggerFactory.getLogger(getClass());

	protected Cryptographer cryptographer;
	protected Table table;
	protected Collection<String> encryptedColumns = new HashSet<>();
	protected Collection<String> crcColumns = new HashSet<>();
	protected ConversionService conversionService = ObjectUtils.getConversionService();


	// ***********************************************************
	// Constructors
	// ***********************************************************

	public AbstractConcentrixRowMapper() {
	}


	// ***********************************************************
	// Getters/Setters
	// ***********************************************************

	public AbstractConcentrixRowMapper<T> setTable(Table table) {
		this.table = table;
		if (table != null) {
			this.encryptedColumns.addAll(table.getEncryptedFieldNames());
			this.crcColumns.addAll(table.getCrcFieldNames());
		}
		return this;
	}

	public AbstractConcentrixRowMapper<T> setEncryptedColumns(Collection<String> encryptedColumns) {
		if (encryptedColumns != null) this.encryptedColumns.addAll(encryptedColumns);
		return this;
	}

	public AbstractConcentrixRowMapper<T> setCrcColumns(Collection<String> crcColumns) {
		if (crcColumns != null) this.crcColumns.addAll(crcColumns);
		return this;
	}

	public AbstractConcentrixRowMapper<T> setCryptographer(Cryptographer cryptographer) {
		this.cryptographer = cryptographer;
		return this;
	}

	public AbstractConcentrixRowMapper<T> build() {
		return this;
	}


	// ***********************************************************
	// RowMapper Interface
	// ***********************************************************

	@Override
	public abstract T mapRow(ResultSet rs, int rowNum) throws SQLException;


	// ***********************************************************
	// Support Methods
	// ***********************************************************

	@SuppressWarnings("unchecked")
	protected <E> E getColumnValue(ResultSet rs, int index, Class<E> resultType, boolean isEncrypted) throws SQLException {
		//get the data from the result set
		Object value = JdbcUtils.getResultSetValue(rs, index);
		if (value != null) {
			//decrypt the data if needed
			if (value instanceof String && isEncrypted) value = decrypt((String)value);

			//convert to the required type
			if (resultType != null) {
				if (resultType.equals(Date.class)) {
					long millis = getTimeInMillis(value);
					if (millis != 0) value = new Date(millis);
				}
				else if (resultType.equals(DateTime.class)) {
					long millis = getTimeInMillis(value);
					if (millis != 0) value = new DateTime(millis);
				}
				else if (resultType.equals(LocalDate.class)) {
					long millis = getTimeInMillis(value);
					if (millis != 0) value = new LocalDate(millis);
				}
				else if (resultType.equals(LocalTime.class)) {
					long millis = getTimeInMillis(value);
					if (millis != 0) value = new LocalTime(millis);
				}
				else {
					value = conversionService.convert(value, resultType);
				}
			}
		}
		return (E)value;
	}

	protected String decrypt(String encrypted) {
		String result = encrypted;

		Cryptographer cryptographer = this.cryptographer;
		if (cryptographer == null && table != null) cryptographer = table.getCryptographer();

		if (cryptographer != null) {
			try {
				result = cryptographer.decrypt(encrypted);
			}
			catch (Exception e) {
				throw new DataEncryptionException("Decryption failed", e);
			}
		}

		return result;
	}

	protected boolean isEncrypted(String resultName) {
		return encryptedColumns.contains(resultName);
	}

	protected long getTimeInMillis(Object value) {
		if (value instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp)value).getTime();
		}
		else if (value instanceof java.sql.Date) {
			return ((java.sql.Date)value).getTime();
		}
		else if (value instanceof java.sql.Time) {
			return ((java.sql.Time)value).getTime();
		}
		else if (value instanceof String) {
			Date date = DateUtils.parse((String)value);
			if (date != null) return date.getTime();
		}
		return 0;
	}
}
