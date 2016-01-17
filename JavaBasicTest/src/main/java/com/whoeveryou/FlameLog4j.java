package com.whoeveryou;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FlameLog4j {

	private static final Logger logger = LogManager.getLogger("Flame");
    
    public static void main(String[] args){
//       logger.trace("Hello, Trace!");
        logger.debug("Hello, Debug!");
//        logger.info("Hello, Info!");
//        logger.warn("Hello, Warn!");
        logger.error("Hello, Error!");
//        logger.fatal("Hello, Fatal!");
    }
}