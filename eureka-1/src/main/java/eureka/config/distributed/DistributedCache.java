package eureka.config.distributed;

import org.springframework.stereotype.Controller;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/29
 * @Version 1.0.0
 */
@Controller
public class DistributedCache{

    //
    /**
     * 缓存方式：        初始化策略   更新策略    过期策略    一致性要求
     * guava            无          7           频繁
     * redis            api         all
     * local+redis      api         all
     * 更新策略
     *      主动：
     *          1直接db（最终一致性）
     *          2先cache后db（基本一致性）
     *          43先db后cache (基本一致性）
     *          5先delete cache 后db，再cache (基本一致性）
     *          6先delete cache 后db (基本一致性）
     *      被动：
     *          7cache自动过期后自己更新cache。 (最终一致性）
     * 一致性要求
     *  强一致性：
     *      db和cache通过事务更新
     *  基本一致性：
     *
     *  最终一致性：
     *
     *
     *
     *
     */


}
