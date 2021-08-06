package com.cxytiandi.sharding.config.limiting;

import com.cxytiandi.sharding.config.limiting.domain.Funnel;
import com.cxytiandi.sharding.config.limiting.domain.FixedWindowLimiting;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 *
 *     谏逐客书

 *     臣闻吏议逐客，窈以为过矣。
 *
 *     昔穆公求士，西取由余于戎，东得百里奚于宛，迎蹇叔于宋，来丕豹、公孙支于晋。此五人者，不产于秦，而穆公用之，并国二十，遂霸西戎。
 *     孝公用商鞅之法，移风易俗，民以殷盛，国以富强，百姓乐用，诸侯亲服，获楚、魏之师，举地千里，至今治强。
 *     惠王用张仪之计，拔三川之地，西并巴蜀，北收上郡，南取汉中，包九夷，制鄢郢，东据成皋之险，割膏腴之壤，遂散六国之众，使之西面事秦，功施到今。
 *     昭王得范雎，废穰侯，逐华阳，强公室，杜私门，蚕食诸侯，使秦成帝业。
 *
 *     此四君者皆以客之功，由此观之，客何负于秦哉？向使四君却客而不内，疏士而不用，是使国无富利之实，而秦无强大之名也。
 *
 *     今陛下致昆山之玉，有随、和之宝，垂明月之珠，服太阿之剑，求纤离之马，建翠凤之旗，树灵鼍之鼓。
 *     此数宝者，秦不生一焉，而陛下说之，何也?
 *     必秦国之所生然后可，则是夜光之用，西蜀丹青不为采。
 *     所以饰后宫、充下陈、娱心意、说耳目者，必出于秦然后可，
 *     则是宛珠之簪、傅玑之珥、阿缟之衣、锦绣之饰，不进于前；
 *     而随俗雅化、佳冶窈窕赵女，不立于侧也。
 *     夫击瓮叩缶、弹筝搏髀，而歌呼鸣鸣，快耳目者，真秦之声也。
 *
 *     郑卫桑间、《昭虞》《武象》者，异国之乐也。今弃击瓮而就郑卫，退弹筝而取昭虞，若是者何也?快意当前，适观而已矣。今取人则不然。
 *     不问可否，不论曲直，非秦者去，为客者逐。然则是所重者，在乎色乐珠玉；而所轻者，在乎人民也。此非所以跨海内、制诸侯之术也。
 *     臣闻地广者粟多，国大者人众，兵强则士勇。是以泰山不让土壤，故能成其大；河海不择细流，故能就其深；王者不却众庶，故能明其德。
 *     是以地无四方，民无异国，四时充美，鬼神降福，此五帝三王之所以无敌也。今乃弃黔首以资敌国，却宾客以业诸候，使天下之士，退而不敢西问，裹足不入秦，此所谓藉寇兵而赍盗粮者也。
 *     夫物不产于秦，可宝者多；士不产于秦，而愿忠者众。
 *     今逐客以资敌国，损民以益仇，内自虚而外树怨于诸侯，求国无危，不可得也。
 *
 * @Author zhao tailin
 * @Date 2021/8/5
 * @Version 1.0.0
 */
@Component
public class LimitingManager {
    private ConcurrentHashMap<String, FixedWindowLimiting> limitingPool=new ConcurrentHashMap<String, FixedWindowLimiting>(100);


    public boolean isLimiting(String uri) {
        return limitingPool.containsKey(uri);
    }

    public FixedWindowLimiting addPoolLimitingUri(String uri, FixedWindowLimiting limitingDomain) {
        return limitingPool.putIfAbsent(uri, limitingDomain);
    }

    public FixedWindowLimiting removePoolLimitingUri(String uri) {
        return limitingPool.remove(uri);
    }

    public boolean getPoolAccessRights(String uri,String flag) {
        return limitingPool.get(uri).getAccessRights();
    }


    public void changePoolRequestsPerSecond(String uri,Long requestsPerSecond){
        limitingPool.get(uri).changeRequestsPerSecond(requestsPerSecond);
    }



    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Funnel>> limitingToken=
            new ConcurrentHashMap<String,  ConcurrentHashMap<Integer, Funnel>>(100);

    public void createlimitingToken(String uri,Integer numbers){
        ConcurrentHashMap<Integer, Funnel> map=new ConcurrentHashMap<>();
        for (int i=0;i<numbers;i++){
            map.put(Integer.valueOf(i),new Funnel(300L));

        }
        limitingToken.putIfAbsent(uri,map);
    }

    public Integer getTokenAccessRights(String uri,String flag,Integer numbers) {
        Long offset=System.currentTimeMillis()%numbers;
        boolean accessRights=limitingToken.get(uri).get(offset.intValue()).getAccessRights(flag);
        if (accessRights){
            return offset.intValue();
        }
        return -1;
    }
    public void comeBackTokenAccessRights(String uri,String offset,String flag) {
        limitingToken.get(uri).get(offset).comeBackAccessRights(flag);
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            System.out.println(System.currentTimeMillis()%4);
        }
    }
}
