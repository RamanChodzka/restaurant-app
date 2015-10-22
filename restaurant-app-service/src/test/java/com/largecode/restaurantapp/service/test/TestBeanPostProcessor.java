package com.largecode.restaurantapp.service.test;

import static org.mockito.Mockito.spy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class TestBeanPostProcessor implements BeanPostProcessor {
    
    private String packageName = "";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null && bean.getClass().getCanonicalName().startsWith(packageName)) {
            return spy(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
