package eureka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description listener
 * @Author zhao tailin
 * @Date 2020/7/30
 * @Version 1.0.0
 */
@Configuration
public class SpringEventListener {

    @Bean
    public ApplicationListener<ApplicationStartedEvent> zuulDiscoveryRefreshRoutesListener() {
        return new ContextRefreshedEventRefreshListener();
    }

    private static class ContextRefreshedEventRefreshListener implements ApplicationListener<ApplicationStartedEvent> {
        private final Logger log = LoggerFactory.getLogger(SpringEventListener.class);

        public static ApplicationContext applicationContext=null;

        @Override
        public void onApplicationEvent(ApplicationStartedEvent event) {
            applicationContext=event.getApplicationContext();
            log.info("onApplicationEvent>>>>>> "+event.getSource());
            initDistributedTransaction();
        }

        private void initDistributedTransaction() {
            //1、获取到需要分布式的bean
            DistributedTransaction distributedTransaction=getBean("serviceA");
            //2、获取到需要对应的分布式方法
            String functions=distributedTransaction.registeredTransaction();
            Class<?> serviceA=getBean("serviceA").getClass();
            Method[] methods=serviceA.getMethods();
            try {
                for (Method m : methods) {
                    if (m.getName().equals(functions)) {
                        serviceA.getDeclaredMethod(m.getName(), m.getParameterTypes()).invoke(distributedTransaction, 98, 2);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

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

    }

}
