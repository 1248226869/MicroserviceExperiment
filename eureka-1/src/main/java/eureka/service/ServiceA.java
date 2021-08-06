package eureka.service;

import eureka.config.DistributedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/29
 * @Version 1.0.0
 */
@Service
public class ServiceA implements DistributedTransaction {
    private final Logger log = LoggerFactory.getLogger(ServiceA.class);

    @Override
    public String registeredTransaction() {
       log.info("registered Transaction");
        return "startTransaction";
    }

    public String startTransaction(int a,int b){
       log.info("a+b = "+(a+b));
        return "is okj"+(a+b);
    }
}
