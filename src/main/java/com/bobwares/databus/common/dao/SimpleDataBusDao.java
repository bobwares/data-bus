package com.bobwares.databus.common.dao;

import com.bobwares.core.jdbc.dao.impl.AbstractDaoImpl;
import com.bobwares.core.jdbc.util.CamelCaseMapRowMapper;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.server.registry.service.FieldTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


public class SimpleDataBusDao extends AbstractDaoImpl implements DataBusDao {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private FieldTypeRegistry fieldTypeRegistry;

	@Inject
	public void setFieldTypeRegistry(FieldTypeRegistry fieldTypeRegistry) {
		this.fieldTypeRegistry = fieldTypeRegistry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingObject getRecords(ResourceConfiguration configuration, Map<String, String> filters, Integer pageNum, Integer pageSize, String sortBy, Boolean reverse) {
		return new PagingObject(pageNum, pageSize, (List<Map<String, Object>>)getSimpleJdbcCall()
			.withProcedureName(configuration.getStoredProcedure())
			.returningResultSet("results", new CamelCaseMapRowMapper())
			.execute(buildSqlParameterSource(configuration, filters, pageNum, pageSize, sortBy, reverse))
			.get("results")
		);
	}

	protected SimpleJdbcCall getSimpleJdbcCall() {
		return new SimpleJdbcCall(jdbcTemplate);
	}

	protected MapSqlParameterSource buildSqlParameterSource(ResourceConfiguration configuration, Map<String, String> filters, Integer pageNum, Integer pageSize, String sortBy, Boolean reverse) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource()
			.addValue("pageNumber", pageNum)
			.addValue("recordsPerPage", pageSize)
			.addValue("sortBy", sortBy)
			.addValue("sortDir", reverse ? "D" : "A")
		;

		if (configuration.getFilters() != null) {
			for (Field field : configuration.getFilters()) {
				//apply the default value if there is one
				if (field.getValue() != null) filters.put(field.getProperty(), field.getValue());

				fieldTypeRegistry.getEntry(field.getType()).addFilter(paramSource, filters, field.getProperty());
			}
		}

		if (configuration.isFilteringOnFieldsAllowed()) {
			for (Field field : configuration.getFields()) {
				fieldTypeRegistry.getEntry(field.getType()).addField(paramSource, filters, field.getProperty());
			}
		}
		return paramSource;
	}
}