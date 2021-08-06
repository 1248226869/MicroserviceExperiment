package com.cxytiandi.sharding.config.distributedTransaction;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/8/4
 * @Version 1.0.0
 */
public class DistributedTransaction {
    private Long distributedTransactionId;
    private String tryURL;
    private String confirmURL;
    private String cancelURL;
    private Long retriesNumber;
    private Boolean failLog;

    public DistributedTransaction(Long distributedTransactionId, String tryURL, String confirmURL, String cancelURL, Long retriesNumber, Boolean failLog) {
        this.distributedTransactionId=distributedTransactionId;
        this.tryURL=tryURL;
        this.confirmURL=confirmURL;
        this.cancelURL=cancelURL;
        this.retriesNumber=retriesNumber;
        this.failLog=failLog;
    }

    public Long getDistributedTransactionId() {
        return distributedTransactionId;
    }

    public String getTryURL() {
        return tryURL;
    }

    public String getConfirmURL() {
        return confirmURL;
    }

    public String getCancelURL() {
        return cancelURL;
    }

    public Long getRetriesNumber() {
        return retriesNumber;
    }

    public Boolean getFailLog() {
        return failLog;
    }
}
