package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.model.AllCookerData;
import com.ekek.hardwaremodule.model.CookerData;

public interface ICookerPowerDataEx {
    int updateCookerPower(AllCookerData data);
    int checkCookerPower(int value, CookerData... datas);
}
