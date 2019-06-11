package artifact.modules.common.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * baseService
 *
 * @author author
 */
@Component
public class BaseService implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BaseService.applicationContext == null) {
            BaseService.applicationContext = applicationContext;
        }
    }

    /**
     * 根據class获取指定类的bean
     *
     * @param clazz 类
     * @param <T>   泛型T
     * @return bean对象
     */
    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据class和name获取指定类的bean
     *
     * @param clazz 类
     * @param <T>   泛型T
     * @param name  bean名称
     * @return bean对象
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }
}
