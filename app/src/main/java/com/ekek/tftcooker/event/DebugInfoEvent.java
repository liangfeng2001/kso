package com.ekek.tftcooker.event;

import com.ekek.tftcooker.model.AllCookerDataEx;
import com.ekek.tftcooker.model.AnalysisSerialData;

import java.util.Locale;

public class DebugInfoEvent {

    // Fields
    private boolean inDebugMode;
    private AnalysisSerialData dataReceived;
    private AllCookerDataEx allCookerDataEx;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Received: ");
        sb.append(getPowerSignal()).append(" ");
        sb.append(getDataReceivedA()).append(" ");
        sb.append(getDataReceivedB()).append(" ");
        sb.append(getDataReceivedC()).append(" ");
        sb.append(getDataReceivedD()).append(" ");
        sb.append(getDataReceivedE()).append(" ");
        sb.append(getDataReceivedF()).append(" ");
        sb.append("Sent: ");
        sb.append(getDebugInfoA()).append(" ");
        sb.append(getDebugInfoB()).append(" ");
        sb.append(getDebugInfoC()).append(" ");
        sb.append(getDebugInfoD()).append(" ");
        sb.append(getDebugInfoE()).append(" ");
        sb.append(getDebugInfoF()).append(" ");
        sb.append(getDebugInfoBluetooth()).append(" ");
        sb.append(getDebugInfoHood()).append(" ");
        sb.append(getDebugInfoLight()).append(" ");
        return sb.toString();
    }

    // Public functions
    public String getPowerSignal() {
        return String.format(
                Locale.ENGLISH,
                "POWER %02x",
                dataReceived.getPowerSignal()).toUpperCase();
    }
    public String getDataReceivedA() {
        return String.format(
                Locale.ENGLISH,
                "A %02x %02x %02x",
                dataReceived.getAInfo(),
                dataReceived.getAError(),
                dataReceived.getAPanBottomAD()).toUpperCase();

    }
    public String getDataReceivedB() {
        return String.format(
                Locale.ENGLISH,
                "B %02x %02x %02x",
                dataReceived.getBInfo(),
                dataReceived.getBError(),
                dataReceived.getBPanBottomAD()).toUpperCase();

    }
    public String getDataReceivedC() {
        return String.format(
                Locale.ENGLISH,
                "C %02x %02x %02x",
                dataReceived.getCInfo(),
                dataReceived.getCError(),
                dataReceived.getCPanBottomAD()).toUpperCase();

    }
    public String getDataReceivedD() {
        return String.format(
                Locale.ENGLISH,
                "D %02x %02x %02x",
                dataReceived.getDInfo(),
                dataReceived.getDError(),
                dataReceived.getDPanBottomAD()).toUpperCase();

    }
    public String getDataReceivedE() {
        return String.format(
                Locale.ENGLISH,
                "E %02x %02x %02x",
                dataReceived.getEInfo(),
                dataReceived.getEError(),
                dataReceived.getEPanBottomAD()).toUpperCase();

    }
    public String getDataReceivedF() {
        return String.format(
                Locale.ENGLISH,
                "F %02x %02x %02x",
                dataReceived.getFInfo(),
                dataReceived.getFError(),
                dataReceived.getFPanBottomAD()).toUpperCase();

    }
    public String getDebugInfoA() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "A %02x %02x %02x",
                allCookerDataEx.getaMode(),
                allCookerDataEx.getaFireValue(),
                allCookerDataEx.getaTempValue()).toUpperCase();

    }
    public String getDebugInfoB() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "B %02x %02x %02x",
                allCookerDataEx.getbMode(),
                allCookerDataEx.getbFireValue(),
                allCookerDataEx.getbTempValue()).toUpperCase();

    }
    public String getDebugInfoC() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "C %02x %02x %02x",
                allCookerDataEx.getcMode(),
                allCookerDataEx.getcFireValue(),
                allCookerDataEx.getcTempValue()).toUpperCase();

    }
    public String getDebugInfoD() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "D %02x %02x %02x",
                allCookerDataEx.getdMode(),
                allCookerDataEx.getdFireValue(),
                allCookerDataEx.getdTempValue()).toUpperCase();

    }
    public String getDebugInfoE() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "E %02x %02x %02x",
                allCookerDataEx.geteMode(),
                allCookerDataEx.geteFireValue(),
                allCookerDataEx.geteTempValue()).toUpperCase();

    }
    public String getDebugInfoF() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "F %02x %02x %02x",
                allCookerDataEx.getfMode(),
                allCookerDataEx.getfFireValue(),
                allCookerDataEx.getfTempValue()).toUpperCase();

    }
    public String getDebugInfoBluetooth() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "BT %02x",
                allCookerDataEx.getBluetoothTempValue()).toUpperCase();

    }
    public String getDebugInfoHood() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "Hood %02x",
                allCookerDataEx.getHoodValue()).toUpperCase();

    }
    public String getDebugInfoLight() {
        if (allCookerDataEx == null) {
            return "";
        }
        return String.format(
                Locale.ENGLISH,
                "Light %02x",
                allCookerDataEx.getLightValue()).toUpperCase();

    }

    // Properties
    public boolean isInDebugMode() {
        return inDebugMode;
    }
    public void setInDebugMode(boolean inDebugMode) {
        this.inDebugMode = inDebugMode;
    }
    public AnalysisSerialData getDataReceived() {
        return dataReceived;
    }
    public void setDataReceived(AnalysisSerialData dataReceived) {
        this.dataReceived = dataReceived;
    }
    public AllCookerDataEx getAllCookerDataEx() {
        return allCookerDataEx;
    }
    public void setAllCookerDataEx(AllCookerDataEx allCookerDataEx) {
        this.allCookerDataEx = allCookerDataEx;
    }
}
