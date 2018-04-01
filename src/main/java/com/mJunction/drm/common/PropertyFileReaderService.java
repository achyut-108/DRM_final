package com.mJunction.drm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mJunction.drm.common.exception.PropertyFileNotFoundException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */

@Service
@Scope("singleton")
public class PropertyFileReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyFileReaderService.class);
    private Properties property = new Properties();

    public PropertyFileReaderService() throws PropertyFileNotFoundException {
        LOGGER.info("[default Constructor] : Going to create a singleton instance and load the property file!!");
            try {
                this.property.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            } catch (IOException e) {
                LOGGER.error("[default Constructor] : IOException :",e);
                throw new PropertyFileNotFoundException("config.properties not found!!");
            }
    }


    public Properties getProperty() {
        return property;
    }
}
