package com.bobwares.core.web.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

//import org.apache.commons.lang3.StringUtils;


public class HttpQueryParameters {

	protected static final Pattern PARAM_SPLITTER = Pattern.compile("&");
	protected static final Pattern NAME_VALUE_SPLITTER = Pattern.compile("=");

	protected Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();


	// ***********************************************************
	// Constructors
	// ***********************************************************

	public HttpQueryParameters() {
	}

	public HttpQueryParameters(HttpServletRequest request) {
		this(request.getQueryString());
	}

	public HttpQueryParameters(String queryStr) {
		parseQueryStr(queryStr);
	}

	public HttpQueryParameters(Map<String, String[]> paramMap) {
		if (paramMap != null) {
			for (Entry<String, String[]> entry : paramMap.entrySet()) {
				String[] value = entry.getValue();
				if (value != null) {
					setParameter(entry.getKey(), Arrays.asList(value));
				}
				else {
					setParameter(entry.getKey(), "");
				}
			}
		}
	}


	// ***********************************************************
	// API
	// ***********************************************************

	public boolean containsParameter(String name) { return parameters.containsKey(name); }

	public String getParameter(String name) {
		String result = null;

		String[] values = getParameterValues(name);
		if (values != null) result = values[0];

		return result;
	}

	public Boolean getBoolean(String name) {
		Boolean result = null;
		String s = getParameter(name);
		if (s != null) result = Boolean.valueOf(s);
		return result;
	}

	public Integer getInteger(String name) {
		Integer result = null;
		String s = getParameter(name);
		if (s != null) result = Integer.valueOf(s);
		return result;
	}

	public Long getLong(String name) {
		Long result = null;
		String s = getParameter(name);
		if (s != null) result = Long.valueOf(s);
		return result;
	}

	public Double getDouble(String name) {
		Double result = null;
		String s = getParameter(name);
		if (s != null) result = Double.valueOf(s);
		return result;
	}

	public Character getChar(String name) {
		Character result = null;
		String s = getParameter(name);
		if (s != null && s.length() > 0) result = Character.valueOf(s.charAt(0));
		return result;
	}

	public Byte getByte(String name) {
		Byte result = null;
		String s = getParameter(name);
		if (s != null) result = Byte.valueOf(s);
		return result;
	}

	public String[] getParameterValues(String name) { return parameters.get(name); }
	public Map<String, String[]> getParameterMap() { return parameters; }

	public HttpQueryParameters setParameter(String name, boolean value) { return setParameter(name, Boolean.toString(value)); }
	public HttpQueryParameters setParameter(String name, int value) { return setParameter(name, Integer.toString(value)); }
	public HttpQueryParameters setParameter(String name, long value) { return setParameter(name, Long.toString(value)); }
	public HttpQueryParameters setParameter(String name, double value) { return setParameter(name, Double.toString(value)); }
	public HttpQueryParameters setParameter(String name, char value) { return setParameter(name, Character.toString(value)); }
	public HttpQueryParameters setParameter(String name, byte value) { return setParameter(name, Byte.toString(value)); }
	public HttpQueryParameters setParameter(String name, Object... a) { if (name != null && a != null) setParameter(name, Arrays.asList(a)); return this; }

	public HttpQueryParameters setParameter(String name, Collection<? extends Object> values) {
		if (name != null) {
			String[] strValues = null;

			if (values != null && values.size() > 0) {
				//convert the collection into a string array
				strValues = new String[values.size()];
				int i = 0;
				for (Object obj : values) {
					strValues[i++] = (obj != null) ? obj.toString() : null;
				}
			}

			parameters.put(name, (strValues != null && strValues.length > 0) ? strValues : new String[] { "" } );
		}
		return this;
	}

	public HttpQueryParameters setParameter(String name, Object value) {
		if (name != null) {
			String strValue = (value != null) ? value.toString() : "";

			String[] values = getParameterValues(name);
			if (values == null) {
				parameters.put(name, new String[] { strValue });
			}
			else {
				List<String> newValues = new ArrayList<String>();
				newValues.addAll(Arrays.asList(values));
				newValues.add(strValue);
				parameters.put(name, newValues.toArray(new String[newValues.size()]));
			}
		}
		return this;
	}

	public String[] removeParameter(String name) {
		String[] result = null;
		if (name != null) result = parameters.remove(name);
		return result;
	}

	public void removeParameters(Collection<String> names) {
		if (names != null) {
			for (String name : names) {
				parameters.remove(name);
			}
		}
	}

	public HttpQueryParameters replaceParameters(Map<String, String[]> replacementMap) {
		Iterator<Entry<String,String[]>> mapIterator = parameters.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Entry<String,String[]> entry = mapIterator.next();
			String[] values = entry.getValue();
			if (values != null && values.length == 1) {
				String value = values[0];
				if (value != null) {
					if (value.startsWith("{") && value.endsWith("}")) {
						String replacementKey = value.substring(1, value.length() - 1);
						if (replacementMap.containsKey(replacementKey)) {
							String[] replacementValues = replacementMap.get(replacementKey);
							if (replacementValues != null) {
								if (replacementValues.length == 0) {
									entry.setValue(new String[]{""});
								}
								else {
									entry.setValue(replacementValues);
								}
							}
							else {
								mapIterator.remove();
							}
						}
						else {
							mapIterator.remove();
						}
					}
				}
			}
		}
		return this;
	}

	public HttpQueryParameters addParameters(HttpQueryParameters addParams) {
		if (addParams != null) {
			parameters.putAll(addParams.getParameterMap());
		}
		return this;
	}

	public HttpQueryParameters addParameters(String queryStr) {
		parseQueryStr(queryStr);
		return this;
	}

	public String toQueryString() {
		return buildQueryStr();
	}

	@Override
	public String toString() { return toQueryString(); }


	// ***********************************************************
	// Support Methods
	// ***********************************************************

	protected void parseQueryStr(String queryStr) {
		if (queryStr != null) {
			String[] params = PARAM_SPLITTER.split(queryStr);
	        for (String param : params) {
	        	String[] nameValue = NAME_VALUE_SPLITTER.split(param);
	        	if (nameValue.length == 0 || nameValue.length > 2) throw new IllegalArgumentException("bad parameter");

	        	try {
		            String name = URLDecoder.decode(nameValue[0], StandardCharsets.UTF_8.name());
		            String value = "";
		            if (nameValue.length == 2) value = URLDecoder.decode(nameValue[1], StandardCharsets.UTF_8.name());
		            setParameter(name, value);
	        	}
	        	catch (UnsupportedEncodingException e) {
	        		throw new IllegalArgumentException("UTF-8 decoding support is missing.");
	        	}
	        }
		}
	}

	protected String buildQueryStr() {
		try {
			StringBuilder sb = new StringBuilder();

			Set<Entry<String, String[]>> entries = parameters.entrySet();
			for (Entry<String, String[]> entry : entries) {
				String name = entry.getKey();

				String[] values = entry.getValue();
				if (values != null && values.length > 0) {
					for (String value : values) {
						if (value != null && value.length() > 0) {
							sb.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name())).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()));
						}
						else {
							sb.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name()));
						}
						sb.append("&");
					}
				}
				else {
					sb.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name())).append("&");
				}
			}
			if (sb.length() > 0) sb.setLength(sb.length() - 1);	//remove trailing &

			return sb.toString();
		}
		catch (UnsupportedEncodingException e) {
    		throw new IllegalArgumentException("UTF-8 encoding support is missing.");
    	}
	}


}
