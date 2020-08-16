package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataC extends CookerData {
    public CookerDataC(int mode) {
        super(CookerConstant.COOKER_ID_C_COOKDER, mode);
    }
    public CookerDataC() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
