package org.lizaalert.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextProvider implements ApplicationContextAware {

    private static ApplicationContext instance;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextProvider.instance = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return instance.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return instance.getBean(beanName);
    }
}
