package com.fasten.butterknife.api;

import android.util.Log;

/**
 * 作者:created by storm on 2019-12-09
 */

public class ButterKnife {

    public static void bind(Object target){
        Class<?> clazz=target.getClass();
        String clazzName=clazz.getCanonicalName();//com.fasten.butterknife.MainActivity$$Binder
        String generateClazzName=clazzName+"$$Binder";
        try {
            Log.e("stormzsl","0000::"+generateClazzName);
            Object generateObject=Class.forName(generateClazzName).newInstance();
            if(generateObject instanceof IBindInterface){
                Log.e("stormzsl","1111");
                IBindInterface bindInterface=(IBindInterface) generateObject;
                bindInterface.bind(target);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            Log.e("stormzsl","22222::ClassNotFoundException:"+generateClazzName);
        }catch (IllegalAccessException e){
            Log.e("stormzsl","33333::IllegalAccessException:"+generateClazzName);
        }catch (InstantiationException e){
            Log.e("stormzsl","33333::InstantiationException:"+generateClazzName);
        }
    }
}
