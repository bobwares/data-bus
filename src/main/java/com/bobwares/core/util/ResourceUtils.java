package com.bobwares.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class ResourceUtils {
	static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    private static ClassLoader loader;
    static{
        // save the class loader used to load this class
        loader = ResourceUtils.class.getClassLoader();
    }

    public static URL getResourceUrl(String resourceName) {
    	return getResourceUrl(resourceName, false);
    }

    public static URL getResourceUrl(String resourceName, boolean checkWorkingDirectory) {
    	URL result = null;

    	try {
	        if (checkWorkingDirectory) {
	            File resource = new File(resourceName);
	            if (resource.exists()) result = resource.toURI().toURL();
	        }
	        if (result == null ) result = (loader == null ? ClassLoader.getSystemResource(resourceName) : loader.getResource(resourceName));
    	}
    	catch (Exception e) {
    		logger.debug("failed to load resource {}", resourceName, e);
    	}

	    return result;
    }

	public static InputStream getResourceStream(String resourceName) {
		return getResourceStream(resourceName, false);
	}

	public static InputStream getResourceStream(String resourceName, boolean checkWorkingDirectory) {
    	InputStream result = null;

    	// try to load via file i/o to bypass tomcat/jboss resource caching
    	// and jrun issues with paths on the file system and in jars
    	try {
			URL url = getResourceUrl(resourceName, checkWorkingDirectory);
			if (url != null) {
				File resource = new File(url.getFile());
				if (resource.exists()) result = new FileInputStream(resource);
			}

	    	if (result == null) {
				// not found on the file system
		        if (checkWorkingDirectory) {
		            try { result = new FileInputStream(resourceName); } catch( FileNotFoundException ignored) {}
		        }
	    	}

	    	//not found in the file system, so try the classpath
	    	if (result == null) result = (loader == null ? ClassLoader.getSystemResourceAsStream(resourceName) : loader.getResourceAsStream(resourceName));
		}
    	catch (Exception e) {
    		logger.debug("failed to load resource {}", resourceName, e);
    	}

    	return result;
    }

	public static String loadResourceAsString(String resourceName) throws IOException {
		return loadResourceAsString(resourceName, StandardCharsets.UTF_8);
	}

	public static String loadResourceAsString(String resourceName, Charset charset) throws IOException {
		byte[] data = loadResourceAsBytes(resourceName);
		return (data != null) ? new String(data, charset) : null;
	}

	public static byte[] loadResourceAsBytes(String resourceName) throws IOException {
		byte[] result = null;

		if (resourceName != null) {
			try (
				InputStream in = getResourceStream(resourceName, true);
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			) {
				if (in != null) {
					byte[] buffer = new byte [1024];
					int count;
					while ((count = in.read(buffer)) >= 0) {
						out.write(buffer, 0, count);
					}
					result = out.toByteArray();
				}
			}
		}

		return result;
	}

	public static void saveResource(String data, String resourceName) throws IOException {
		saveResource(data, resourceName, StandardCharsets.UTF_8);
	}

	public static void saveResource(String data, String resourceName, Charset charset) throws IOException {
		if (data != null) saveResource(data.getBytes(charset), resourceName);
	}

	public static void saveResource(byte[] data, String resourceName) throws IOException {
		if (data != null && resourceName != null) {
			try (OutputStream out = getResourceOutputStream(resourceName)) {
				out.write(data);
			}
		}
	}

	public static OutputStream getResourceOutputStream(String resourceName) throws IOException {
		return (resourceName != null) ? new FileOutputStream(resourceName) : null;
	}

	public static Map<String,Resource> findClasspathResources(String folder, String extension) throws IOException {
		Map<String,Resource> resourceMap = new HashMap<>();

		if (folder == null || folder.length() == 0) folder = "/";
		else if (!folder.endsWith("/")) folder += "/";

		boolean startsWithSlash = folder.startsWith("/");

    	String resourcePattern = "classpath*:" + folder +  "**/*";

    	if (extension != null) resourcePattern += extension.startsWith(".") ? extension : "." + extension;

    	PathMatchingResourcePatternResolver rr = new PathMatchingResourcePatternResolver();
    	Resource[] resources = rr.getResources(resourcePattern);
    	for (Resource resource : resources) {
    		String url = resource.getURL().toString();

    		int i = url.lastIndexOf(folder);
    		if (startsWithSlash) i++;

    		String resourceName = url.substring(i);

    		Resource classPathResource = resourceMap.get(resourceName);
    		if (classPathResource == null) {
    			classPathResource = new ClassPathResource(resourceName);
    			logger.debug("adding resource {} from {}", classPathResource, resource);
        		resourceMap.put(resourceName, classPathResource);
    		}
    	}

    	return resourceMap;
	}

}
