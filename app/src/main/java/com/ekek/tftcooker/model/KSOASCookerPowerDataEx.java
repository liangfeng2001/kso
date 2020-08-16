package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.model.AllCookerData;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.hardwaremodule.power.KSOASCookerPowerData;

public class KSOASCookerPowerDataEx extends KSOASCookerPowerData implements ICookerPowerDataEx {

    @Override
    public int updateCookerPower(AllCookerData data) {
        return super.updateCookerPower(data);
    }

    @Override
    public int checkCookerPower(int value, CookerData... datas) {
        return super.checkCookerPower(value, datas);
    }
}
