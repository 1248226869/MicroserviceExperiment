package eureka.service;

import eureka.config.DistributedTransaction;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/29
 * @Version 1.0.0
 */
@Service
public class ServiceA implements DistributedTransaction {
    @Override
    public String registeredTransaction() {
        System.out.println("registered Transaction");
        return "startTransaction";
    }

    public String startTransaction(int a,int b){
        System.out.println("a+b = "+(a+b));
        return "is okj"+(a+b);
    }
}
