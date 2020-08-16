package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataF extends CookerData {
    public CookerDataF(int mode) {
        super(CookerConstant.COOKER_ID_F_COOKDER, mode);
    }
    public CookerDataF() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
