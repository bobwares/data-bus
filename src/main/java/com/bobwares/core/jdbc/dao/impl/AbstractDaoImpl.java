package com.bobwares.core.jdbc.dao.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.maritz.core.encryption.Cryptographer;
import com.maritz.core.encryption.DataEncryptionException;
import com.maritz.core.jdbc.builder.DaoInfo;
import com.maritz.core.jdbc.builder.JITBuilder;
import com.maritz.core.security.Security;
import com.maritz.core.util.StringUtils;


public abstract class AbstractDaoImpl {

	@Inject protected JITBuilder jitBuilder;
	@Inject protected Security security;
    @Inject @Named("databaseCryptographer") protected Cryptographer cryptographer;

    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected Map<String, DaoInfo> daoInfoCache = new ConcurrentHashMap<>();


    // See http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html#jdbc-JdbcTemplate-idioms
    @Inject
    public void setDataSource(@Named("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        //queryDslTemplate = new QueryDslJdbcTemplate(jdbcTemplate);
    }

    public <T> SqlParameterSource getSqlParameterSource(String tableName, T model) {
		return getDaoInfo(tableName).getSqlParameterSource(model, security.getUserName());
	}

	public SqlParameterSource getSqlParameterSource(String tableName, MapSqlParameterSource params) {
		return getDaoInfo(tableName).getSqlParameterSource(params);
	}

	public SqlParameterSource getSqlParameterSource(String tableName, Map<String, ?> params) {
		return getDaoInfo(tableName).getSqlParameterSource(params);
	}

	public <T> RowMapper<T> getBeanRowMapper(String tableName, Class<T> beanClass) {
		return getDaoInfo(tableName).getBeanRowMapper(beanClass);
	}

	public <T> RowMapper<T> getColumnRowMapper(String tableName, Class<T> resultClass) {
		return getDaoInfo(tableName).getColumnRowMapper(resultClass);
	}

	public RowMapper<Map<String, Object>> getMapRowMapper(String tableName) {
		return getDaoInfo(tableName).getMapRowMapper();
	}

    public String decrypt(String encrypted) {
        try {
            return cryptographer.decrypt(encrypted);
        }
        catch (Exception e) {
            throw new DataEncryptionException("Error decrypting column", e);
        }
    }

    public String encrypt(String plain) {
        try {
            return cryptographer.encrypt(plain);
        }
        catch (Exception e) {
            throw new DataEncryptionException("Error encrypting column", e);
        }
    }

    protected String makePageableQuery(String query, Pageable page, Map<String,String> columnMapping) {
		StringBuilder sb = new StringBuilder(256);

		//pageNum is 0 based
		int start = (page.getPageNumber() * page.getPageSize()) + 1;
		int end = start + page.getPageSize() - 1;

		sb.append("with cte as (select dt10.*,row_number() over (order by CURRENT_TIMESTAMP) as __RN__ from (");
		sb.append(makeSortableQuery(query, page.getSort(), columnMapping));
		sb.append(") as dt10 ) select cte.* from cte where cte.__RN__ between ").append(start).append(" and ").append(end);

		return sb.toString();
    }

    protected String makeCountQuery(String query) {
		StringBuilder sb = new StringBuilder(256);

		sb.append("select count(1) from (");

		//truncate the query at the order by by clause if there is one
		int orderByIndex = query.toLowerCase().indexOf("order by");
		if (orderByIndex > 0) query = query.substring(0, orderByIndex);

		sb.append(query);
		sb.append(") as dt10");

		return sb.toString();
    }

    protected String makeSortableQuery(String query, Sort sort, Map<String,String> columnMapping) {
		if (sort == null) return query;

		StringBuilder sb = new StringBuilder(256);
		boolean addOrderBy = true;
		for (Sort.Order order : sort) {
			if (addOrderBy) {
				sb.append(" ORDER BY ");
				addOrderBy = false;
			}

			String property = order.getProperty();
			String columnName = columnMapping.get(property);
			if (columnName == null) columnName = StringUtils.convertCamelCaseToDatabase(property);

			sb.append(columnName).append(order.isAscending() ? " ASC," : " DESC,");
		}
		if (sb.length() > 0) sb.setLength(sb.length() - 1); //remove trailing comma
		return sb.toString();
    }

    protected DaoInfo getDaoInfo(String tableName) {
		DaoInfo result = daoInfoCache.get(tableName);
		if (result == null) {
			result = new DaoInfo(jitBuilder.getTable(tableName));
			daoInfoCache.put(tableName, result);
		}
		return result;
	}

}
