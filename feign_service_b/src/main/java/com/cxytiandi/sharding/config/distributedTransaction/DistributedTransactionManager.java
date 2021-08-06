package com.cxytiandi.sharding.config.distributedTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/8/4
 * @Version 1.0.0
 */
@Component
public class DistributedTransactionManager {
    @Autowired
    private RestTemplate restTemplate;
    //创建事务，返回事务id
    public DistributedTransaction createDistributedTransaction(String tryURL, String confirmURL, String cancelURL, Long retriesNumber, Boolean failLog) {
        long distributedTransactionId=12L;
        return new DistributedTransaction(distributedTransactionId, tryURL, confirmURL, cancelURL, retriesNumber, failLog);
    }

    public boolean executeeDistributedTransaction(DistributedTransaction distributedTransaction){
        distributedTransaction.getCancelURL();
        Boolean tryResult=restTemplate.getForObject(distributedTransaction.getTryURL(), Boolean.class);
        if (tryResult){
            Boolean forObject=restTemplate.getForObject(distributedTransaction.getConfirmURL(), Boolean.class);
            if (forObject){
                return forObject;
            }
            Long retriesNumber=distributedTransaction.getRetriesNumber();
            while (retriesNumber>0){
                retriesNumber--;
                if (restTemplate.getForObject(distributedTransaction.getConfirmURL(), Boolean.class)){
                    return forObject;
                }
            }
        }else {

            restTemplate.getForObject(distributedTransaction.getConfirmURL(), Boolean.class);
        }
        return false;
    }


}
