package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.hardwaremodule.model.CookerData;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class CookerDataD extends CookerData {
    public CookerDataD(int mode) {
        super(CookerConstant.COOKER_ID_D_COOKDER, mode);
    }
    public CookerDataD() {
        this(TFTCookerConstant.COOKER_MODE_NORMAL);
    }
}
