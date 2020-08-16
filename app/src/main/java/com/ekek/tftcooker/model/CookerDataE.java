package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataE extends CookerData {
    public CookerDataE(int mode) {
        super(CookerConstant.COOKER_ID_E_COOKDER, mode);
    }
    public CookerDataE() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
