package com.java.api.apache.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TIPs:
 * 1. 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL
 *
 * Created by kevintian on 2017/10/18.
 */
public class Log4j2Demo {

    public void rootLogging() {
        Logger rootLogger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        rootLogger.trace("trace level");
        rootLogger.debug("debug level");
        rootLogger.info("info level");
        rootLogger.warn("warn level");
        rootLogger.error("error level");
        rootLogger.fatal("fatal level");
    }

    public void myLogging() throws InterruptedException {
        Logger myLogger = LogManager.getLogger("mylog4j2");
        int i=0;
        while (i++<5000) {
            myLogger.trace("trace level");
            myLogger.debug("debug level");
            myLogger.info("info level");
            myLogger.warn("warn level");
            myLogger.error("error level");
            myLogger.fatal("fatal level");
        }
        Thread.sleep(1000 * 70); // sleep for 1 min
        myLogger.trace("trace level");
        myLogger.debug("debug level");
        myLogger.info("info level");
        myLogger.warn("warn level");
        myLogger.error("error level");
        myLogger.fatal("fatal level");
    }

    public void asyncLogging() {
        Logger asyncLogger = LogManager.getLogger("asynclog4j2");
        asyncLogger.trace("trace level");
        asyncLogger.debug("debug level");
        asyncLogger.info("info level");
        asyncLogger.warn("warn level");
        asyncLogger.error("error level");
        asyncLogger.fatal("fatal level");
    }

    public void mongoLogging() {
        Logger mongoLogger = LogManager.getLogger("mongolog");
        mongoLogger.trace("trace level");
        mongoLogger.debug("debug level");
        mongoLogger.info("info level");
        mongoLogger.warn("warn level");
        mongoLogger.error("error level");
        mongoLogger.fatal("fatal level");
    }
}
