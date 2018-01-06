package com.order.processor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	private final String propertyFileName = "orderprocessor-config.properties";
	private Properties prop;

	public PropertyUtil() {
		prop = initalizeProperties();
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static String getLanguageCode(String input) {
		Properties prop = new PropertyUtil().initalizeProperties();
		return prop.getProperty(input.toLowerCase());
	}

	private Properties initalizeProperties() {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();

			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFileName + "' not found in the classpath");
			}
			return prop;
		} catch (IOException e) {

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

}
