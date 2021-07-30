package eureka.config.distributed;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/29
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
