package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataA extends CookerData {
    public CookerDataA(int mode) {
        super(CookerConstant.COOKER_ID_A_COOKDER, mode);
    }
    public CookerDataA() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
