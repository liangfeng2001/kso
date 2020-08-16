package com.ekek.tftcooker.database;

import android.util.Log;

//import com.hoob.xlw.ovenpanel.CataTFTHobApplication;
import com.ekek.tftcooker.TFTCookerApplication;
//import com.hoob.xlw.ovenpanel.entity.OvenData;
import com.ekek.tftcooker.entity.TFTDataModel;
//import com.hoob.xlw.ovenpanel.greendao.OvenDataDao;
import com.ekek.tftcooker.entity.TFTDataModelDao;

//import com.hoob.xlw.ovenpanel.utils.LogUtil;
import com.ekek.tftcooker.utils.LogUtil;

import java.util.List;

public class DatabaseHelper {
    public static void SaveCookersData(Long id, int CookerModel_a, int CookerFireGear_a,
                                       int CookerTemperature_a, int CookerModel_b, int CookerFireGear_b,
                                       int CookerTemperature_b, int CookerModel_c, int CookerFireGear_c,
                                       int CookerTemperature_c, int CookerModel_d, int CookerFireGear_d,
                                       int CookerTemperature_d, int CookerModel_e, int CookerFireGear_e,
                                       int CookerTemperature_e, int CookerModel_f, int CookerFireGear_f,
                                       int CookerTemperature_f, int FanGear, int LightGear_blue,
                                       int LightGear_orange, int reserved_1, int reserved_2) {
        TFTDataModelDao dao = TFTCookerApplication.getDaoSession().getTFTDataModelDao();
        List<TFTDataModel> datas = dao.loadAll();   // 查询所有
        if (datas.size() == 0) {
            TFTDataModel data = new TFTDataModel(id, CookerModel_a,  CookerFireGear_a,
             CookerTemperature_a,  CookerModel_b,  CookerFireGear_b,
             CookerTemperature_b,  CookerModel_c,  CookerFireGear_c,
             CookerTemperature_c,  CookerModel_d,  CookerFireGear_d,
             CookerTemperature_d,  CookerModel_e,  CookerFireGear_e,
             CookerTemperature_e,  CookerModel_f,  CookerFireGear_f,
             CookerTemperature_f,  FanGear,  LightGear_blue,
             LightGear_orange,  reserved_1,  reserved_2);
            LogUtil .d("insert new data done!! the sizeofdtata is zero !!") ;
            dao.insertOrReplace(data);
        } else {
            if(id<datas.size()){
                TFTDataModel data = datas.get(new Long(id).intValue());
                if(data!=null ){                   // 原来有记录，更新
                    data.setId(id);
                    data.setCookerModel_a(CookerModel_a);
                    data.setCookerFireGear_a(CookerFireGear_a);
                    data.setCookerTemperature_a(CookerTemperature_a);
                    data.setCookerModel_b(CookerModel_b);
                    data.setCookerFireGear_b(CookerFireGear_b);
                    data.setCookerTemperature_b(CookerTemperature_b);
                    data.setCookerModel_c(CookerModel_c);
                    data.setCookerFireGear_c(CookerFireGear_c);
                    data.setCookerTemperature_c(CookerTemperature_c);
                    data.setCookerModel_d(CookerModel_d);
                    data.setCookerFireGear_d(CookerFireGear_d);
                    data.setCookerTemperature_d(CookerTemperature_d);
                    data.setCookerModel_e(CookerModel_e);
                    data.setCookerFireGear_e(CookerFireGear_e);
                    data.setCookerTemperature_e(CookerTemperature_e);
                    data.setCookerModel_f(CookerModel_f);
                    data.setCookerFireGear_f(CookerFireGear_f);
                    data.setCookerTemperature_f(CookerTemperature_f);
                    data.setFanGear(FanGear);
                    data .setLightGear_blue(LightGear_blue);
                    data.setLightGear_orange(LightGear_orange);
                    data.setReserved_1(reserved_1);
                    data.setReserved_2(reserved_2);
                    dao.update(data) ;
                }
            }
           else {                        // 原来无记录，插入
                TFTDataModel data1 = new TFTDataModel(id, CookerModel_a,  CookerFireGear_a,
                        CookerTemperature_a,  CookerModel_b,  CookerFireGear_b,
                        CookerTemperature_b,  CookerModel_c,  CookerFireGear_c,
                        CookerTemperature_c,  CookerModel_d,  CookerFireGear_d,
                        CookerTemperature_d,  CookerModel_e,  CookerFireGear_e,
                        CookerTemperature_e,  CookerModel_f,  CookerFireGear_f,
                        CookerTemperature_f,  FanGear,  LightGear_blue,
                        LightGear_orange,  reserved_1,  reserved_2);
                dao.insert(data1);
                Log.d("ovendata=","insert new data done!!!!!!!!!!!!!!") ;
            }
        }


    }

    public static void ResetCookersData() {
        TFTDataModelDao dao = TFTCookerApplication.getDaoSession().getTFTDataModelDao();
        List<TFTDataModel> datas = dao.loadAll();   // 查询所有
        if (datas.size() != 0) {
            dao.deleteAll();
        }
        SaveCookersData(0L,
                1, 0, 0,
                1,0,0,
                1,0,0,
                1,0,0,
                1,0,0,
                1,0,0,
                0,0,0,0,0);
    }
    public static List<TFTDataModel> GetCookersData() {
        TFTDataModelDao dao = TFTCookerApplication.getDaoSession().getTFTDataModelDao();
        List<TFTDataModel> datas =  dao.loadAll();
        return datas;
    }
}
