package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataB extends CookerData {
    public CookerDataB(int mode) {
        super(CookerConstant.COOKER_ID_B_COOKDER, mode);
    }
    public CookerDataB() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
