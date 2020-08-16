package com.ekek.tftcooker.model;

import com.ekek.hardwaremodule.model.AllCookerData;

public class AllCookerDataEx extends AllCookerData {
    private static final String FMT_RAW_DATA = "" +
            "A %02x %02x %02x B %02x %02x %02x C %02x %02x %02x " +
            "D %02x %02x %02x E %02x %02x %02x F %02x %02x %02x " +
            "P %02x H %02x L %02x"
            ;
    public String toRawDataString() {
        return String.format(
                FMT_RAW_DATA,
                getaMode(), getaFireValue(), getaTempValue(),
                getbMode(), getbFireValue(), getbTempValue(),
                getcMode(), getcFireValue(), getcTempValue(),
                getdMode(), getdFireValue(), getdTempValue(),
                geteMode(), geteFireValue(), geteTempValue(),
                getfMode(), getfFireValue(), getfTempValue(),
                getBluetoothTempValue(), getHoodValue(), getLightValue()
        ).toUpperCase();
    }

    public AllCookerDataEx clone() {
        AllCookerDataEx r = new AllCookerDataEx();
        r.setaMode(getaMode());
        r.setbMode(getbMode());
        r.setcMode(getcMode());
        r.setdMode(getdMode());
        r.seteMode(geteMode());
        r.setfMode(getfMode());
        r.setaFireValue(getaFireValue());
        r.setbFireValue(getbFireValue());
        r.setcFireValue(getcFireValue());
        r.setdFireValue(getdFireValue());
        r.seteFireValue(geteFireValue());
        r.setfFireValue(getfFireValue());
        r.setaTempValue(getaTempValue());
        r.setbTempValue(getbTempValue());
        r.setcTempValue(getcTempValue());
        r.setdTempValue(getdTempValue());
        r.seteTempValue(geteTempValue());
        r.setfTempValue(getfTempValue());
        r.setLightValue(getLightValue());
        r.setHoodValue(getHoodValue());
        r.setBluetoothTempValue(getBluetoothTempValue());
        return r;
    }
    public void loadFrom(AllCookerDataEx value) {
        if (value == null) return;

        setaMode(value.getaMode());
        setbMode(value.getbMode());
        setcMode(value.getcMode());
        setdMode(value.getdMode());
        seteMode(value.geteMode());
        setfMode(value.getfMode());
        setaFireValue(value.getaFireValue());
        setbFireValue(value.getbFireValue());
        setcFireValue(value.getcFireValue());
        setdFireValue(value.getdFireValue());
        seteFireValue(value.geteFireValue());
        setfFireValue(value.getfFireValue());
        setaTempValue(value.getaTempValue());
        setbTempValue(value.getbTempValue());
        setcTempValue(value.getcTempValue());
        setdTempValue(value.getdTempValue());
        seteTempValue(value.geteTempValue());
        setfTempValue(value.getfTempValue());
        setLightValue(value.getLightValue());
        setHoodValue(value.getHoodValue());
        setBluetoothTempValue(value.getBluetoothTempValue());
    }
}
