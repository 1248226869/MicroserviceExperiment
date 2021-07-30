package eureka.config;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/29
 * @Version 1.0.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * spring工具类，可以通过这个工具类获得bean
 * 同时实现了InitializingBean接口，可以在spring将bean初始化完毕后执行一些事情
 */
@Component("springBeanUtils")
public class SpringBeanUtils implements InitializingBean, ApplicationContextAware, DisposableBean {
    public static ApplicationContext applicationContext=null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        isInjected();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        isInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        isInjected();
        return applicationContext.getBeansOfType(type);
    }

    @Override
    public void destroy() throws Exception {
        SpringBeanUtils.applicationContext=null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext=applicationContext;
    }

    /**
     * 判断是否注入
     *
     * @return
     */
    public static void isInjected() {
        if (SpringBeanUtils.applicationContext == null) {
            throw new RuntimeException("springUtils applicationContext is not injected!");
        }
    }

    /**
     * spring框架加载完毕后的处理
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        createTransactionRegistered();
    }

    private void createTransactionRegistered() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DistributedTransaction distributedTransaction=getBean("serviceA");
        String functions=distributedTransaction.registeredTransaction();
        Class<?> serviceA=getBean("serviceA").getClass();
        Method[] methods=serviceA.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(functions)) {
                serviceA.getDeclaredMethod(m.getName(), m.getParameterTypes()).invoke(distributedTransaction, 98, 2);
            }
        }
    }
}
