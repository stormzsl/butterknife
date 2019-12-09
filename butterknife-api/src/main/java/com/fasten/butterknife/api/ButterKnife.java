package com.fasten.butterknife.api;

/**
 * 作者:created by storm on 2019-12-09
 */

public class ButterKnife {

    public static void bind(Object target){
        Class<?> clazz=target.getClass();
        String clazzName=clazz.getCanonicalName();//com.fasten.butterknife.MainActivity$$Binder
        String generateClazzName=clazzName+"_ViewBinding";
        try {
            Object generateObject=Class.forName(generateClazzName).newInstance();
            if(generateObject instanceof IBind){
                IBind bindInterface=(IBind) generateObject;
                bindInterface.bind(target);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
        }catch (InstantiationException e){
        }
    }
}
