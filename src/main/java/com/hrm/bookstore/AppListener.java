package com.hrm.bookstore;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@WebListener
@Log4j2
public class AppListener implements ServletContextListener {
    private static GenericApplicationContext context;

    public static GenericApplicationContext getContext() {
        return context;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContextEvent: INIT");
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContextEvent: DESTROY");
        if (context != null) {
            context.close();
        }
        log.info("Context destroyed");
    }
}
