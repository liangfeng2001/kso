package com.ekek.tftcooker.database;

import android.content.Context;

import com.ekek.tftcooker.entity.CookerEvent;
import com.ekek.tftcooker.entity.CookerEventDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class CookerEventDBHelper {

    public static void addCookerEvent(Context context, CookerEvent value) {
        CookerEventDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getCookerEventDao();
        dao.insert(value);
    }
    public static void updateCookerEvent(Context context, CookerEvent value) {
        CookerEventDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getCookerEventDao();
        dao.update(value);
    }
    public static void delCookerEvent(Context context, CookerEvent value) {
        CookerEventDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getCookerEventDao();
        dao.delete(value);
    }
    public static void saveCookerEvent(Context context, CookerEvent value) {
        if (value.getId() == null) {
            addCookerEvent(context, value);
        } else {
            updateCookerEvent(context, value);
        }
    }
    public static List<CookerEvent> getCookerEvents(
            Context context,
            String eventName,
            int count) {

        CookerEventDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getCookerEventDao();
        return dao
                .queryBuilder()
                .where(CookerEventDao.Properties.Name.eq(eventName))
                .orderDesc(CookerEventDao.Properties.Id)
                .limit(count)
                .build()
                .list();
    }
}
