package eureka.config.distributed;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * 3PC 把 2PC 的准备阶段分为了准备阶段和预处理阶段，
 * 在第一阶段只是询问各个资源节点是否可以执行事务，
 * 而在第二阶段，所有的节点反馈可以执行事务，才开始执行事务操作，
 * 最后在第三阶段执行提交或回滚操作。
 * 并且在事务管理器和资源管理器中都引入了超时机制，
 * 如果在第三阶段，资源节点一直无法收到来自资源管理器的提交或回滚请求，它就会在超时之后，继续提交事务。
 * 所以 3PC 可以通过超时机制，避免管理器挂掉所造成的长时间阻塞问题，
 * 但其实这样还是无法解决在最后提交全局事务时，由于网络故障无法通知到一些节点的问题，特别是回滚通知，这样会导致事务等待超时从而默认提交。
 *
 * TCC 采用最终一致性的方式实现了一种柔性分布式事务，
 * 与 XA 规范实现的二阶事务不同的是，TCC 的实现是基于服务层实现的一种二阶事务提交。
 * TCC 分为三个阶段，即 Try、Confirm、Cancel 三个阶段.
 *
 * 以上执行只是保证 Try 阶段执行时成功或失败的提交和回滚操作，
 * 如果在 Confirm 和 Cancel 阶段出现异常情况，TCC 会不停地重试调用失败的 Confirm 或 Cancel 方法，直到成功为止。
 *
 * @Author zhao tailin
 * @Date 2020/7/29
 * @Version 1.0.0
 */
@Configuration
public class DistributedTransaction {


    // mode:mush success,if fail,reConsumer，最终一致性
    public String t1M(){
        String msg="";
        send(msg);
        // do some thing
        commitOrRollback(msg);
        return "ok";
    }


    public String t2(){
        String msg="";
        consumer(msg);
        // do other thing
        // success -> return
        // fail ->  send(msg);
        return "ok";
    }

    private void commitOrRollback(String msg) {
    }

    private void send(String msg) {
    }

    private void consumer(String msg) {
    }


    // mode:may success，if fail,rollback all。软一致性、基本一致性
    public void leader(){
        String msg="";
        consumer(msg);
        //rpc invoke tB
        // if success,return
        // if fail,rpc invoke tBMRrollback
    }



    public String tAM(){
        String msg="";
        send(msg);
        // do some thing
        commitOrRollback(msg);
        return "ok";
    }

    public void  tAMRrollback(){

    }


    public String tB(){
        String msg="";
        consumer(msg);
        // do other thing
        // success -> return
        // fail ->  send(msg);
        return "ok";
    }


    public void  tBMRrollback(){}



    @Bean
    public String registeredT (){
        return "o";
//        try {
//            Class<?> aClass=Class.forName("eureka.EurekaServiceApplication");
//            Method[] methods=aClass.getMethods();
//            for (Method m:methods){
//                System.out.println(m.getName());
//            }
//            Method f1=aClass.getDeclaredMethod("f1");
//            f1.invoke(f1.getClass());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
