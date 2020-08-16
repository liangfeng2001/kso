package com.ekek.tftcooker.model;

import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public class AnalysisSerialData {

    private static final int DATA_LEN = 27;

    public static final int ERR_E1 = 1001;
    public static final int ERR_E2 = 1002;
    public static final int ERR_E3 = 1003;
    public static final int ERR_E4 = 1004;
    public static final int ERR_E5 = 1005;
    public static final int ERR_F1 = 2001;
    public static final int ERR_F3 = 2003;
    public static final int ERR_F4 = 2004;

    private static final int ERR_F1_BIT = 0x01;
    private static final int ERR_E3_BIT = 0x02;
    private static final int ERR_E4_BIT = 0x04;
    private static final int ERR_E1_BIT = 0x10;
    private static final int ERR_E2_BIT = 0x08;
    private static final int ERR_F4_BIT = 0x20;
    private static final int ERR_F3_BIT = 0x40;
    private static final int ERR_E5_BIT = 0x80;

    public static final int COOKER_A_BIT = 0x01;
    public static final int COOKER_B_BIT = 0x02;
    public static final int COOKER_C_BIT = 0x04;
    public static final int COOKER_D_BIT = 0x08;
    public static final int COOKER_E_BIT = 0x10;
    public static final int COOKER_F_BIT = 0x20;

    private byte[] data;
    private boolean legalData;
    private boolean poweredOn;

    private byte powerSignal;

    private byte aInfo;
    private byte aError;
    private byte aPanBottomAD;
    private boolean aErrorE1;
    private boolean aErrorE2;
    private boolean aErrorE3;
    private boolean aErrorE4;
    private boolean aErrorE5;
    private boolean aErrorF1;
    private boolean aErrorF3;
    private boolean aErrorF4;

    private byte bInfo;
    private byte bError;
    private byte bPanBottomAD;
    private boolean bErrorE1;
    private boolean bErrorE2;
    private boolean bErrorE3;
    private boolean bErrorE4;
    private boolean bErrorE5;
    private boolean bErrorF1;
    private boolean bErrorF3;
    private boolean bErrorF4;

    private byte cInfo;
    private byte cError;
    private byte cPanBottomAD;
    private boolean cErrorE1;
    private boolean cErrorE2;
    private boolean cErrorE3;
    private boolean cErrorE4;
    private boolean cErrorE5;
    private boolean cErrorF1;
    private boolean cErrorF3;
    private boolean cErrorF4;

    private byte dInfo;
    private byte dError;
    private byte dPanBottomAD;
    private boolean dErrorE1;
    private boolean dErrorE2;
    private boolean dErrorE3;
    private boolean dErrorE4;
    private boolean dErrorE5;
    private boolean dErrorF1;
    private boolean dErrorF3;
    private boolean dErrorF4;

    private byte eInfo;
    private byte eError;
    private byte ePanBottomAD;
    private boolean eErrorE1;
    private boolean eErrorE2;
    private boolean eErrorE3;
    private boolean eErrorE4;
    private boolean eErrorE5;
    private boolean eErrorF1;
    private boolean eErrorF3;
    private boolean eErrorF4;

    private byte fInfo;
    private byte fError;
    private byte fPanBottomAD;
    private boolean fErrorE1;
    private boolean fErrorE2;
    private boolean fErrorE3;
    private boolean fErrorE4;
    private boolean fErrorE5;
    private boolean fErrorF1;
    private boolean fErrorF3;
    private boolean fErrorF4;

    private byte crcH;
    private byte crcL;
    private boolean panelOverTemperature = false;

    public AnalysisSerialData(byte[] data) {
        setData(data);
    }
    public AnalysisSerialData() {
    }

    public void setData(byte[] data) {
        this.data = data;
        processHardwareData();
    }

    public void processHardwareData() {
        legalData = isValidData();
        if (!legalData) {
            return;
        }

        powerSignal = data[4];
        if ((int)data[4] == TFTCookerConstant.PROTOCOL_STATE_POWER_SWITCH_ON) {
            poweredOn = true;
        } else if ((int)data[4] == TFTCookerConstant.PROTOCOL_STATE_POWER_SWITCH_OFF) {
            poweredOn = false;
        } else {
            poweredOn = false;
        }

        handleCookerA();
        handleCookerB();
        handleCookerC();
        handleCookerD();
        handleCookerE();
        handleCookerF();

        crcH = data[23];
        crcL = data[24];

        panelOverTemperature = (aInfo & 0x20) == 0x20;
    }

    private boolean isValidData() {
        if (data.length < DATA_LEN) {
            return false;
        }

        return (data[0] == TFTCookerConstant.PROTOCOL_RECEIVE_HEAD_FIRST_BYTE)
                && (data[1] == TFTCookerConstant.PROTOCOL_RECEIVE_HEAD_SECOND_BYTE)
                && (data[2] == TFTCookerConstant.PROTOCOL_RECEIVE_HEAD_THIRD_BYTE)
                && (data[3] == TFTCookerConstant.PROTOCOL_RECEIVE_HEAD_FOURTH_BYTE)
                && (data[DATA_LEN - 1] == TFTCookerConstant.PROTOCOL_RECEIVE_END_LAST_BYTE)
                && (data[DATA_LEN - 2] == TFTCookerConstant.PROTOCOL_RECEIVE_END_SECOND_LAST_BYTE);
    }

    public boolean checkError(int checkBits, int error) {
        switch (error) {
            case ERR_E1:
                return (checkCookerA(checkBits) && aErrorE1)
                        || (checkCookerB(checkBits) && bErrorE1)
                        || (checkCookerC(checkBits) && cErrorE1)
                        || (checkCookerD(checkBits) && dErrorE1)
                        || (checkCookerE(checkBits) && eErrorE1)
                        || (checkCookerF(checkBits) && fErrorE1);
            case ERR_E2:
                return (checkCookerA(checkBits) && aErrorE2)
                        || (checkCookerB(checkBits) && bErrorE2)
                        || (checkCookerC(checkBits) && cErrorE2)
                        || (checkCookerD(checkBits) && dErrorE2)
                        || (checkCookerE(checkBits) && eErrorE2)
                        || (checkCookerF(checkBits) && fErrorE2);
            case ERR_E3:
                return (checkCookerA(checkBits) && aErrorE3)
                        || (checkCookerB(checkBits) && bErrorE3)
                        || (checkCookerC(checkBits) && cErrorE3)
                        || (checkCookerD(checkBits) && dErrorE3)
                        || (checkCookerE(checkBits) && eErrorE3)
                        || (checkCookerF(checkBits) && fErrorE3);
            case ERR_E4:
                return (checkCookerA(checkBits) && aErrorE4)
                        || (checkCookerB(checkBits) && bErrorE4)
                        || (checkCookerC(checkBits) && cErrorE4)
                        || (checkCookerD(checkBits) && dErrorE4)
                        || (checkCookerE(checkBits) && eErrorE4)
                        || (checkCookerF(checkBits) && fErrorE4);
            case ERR_E5:
                return (checkCookerA(checkBits) && aErrorE5)
                        || (checkCookerB(checkBits) && bErrorE5)
                        || (checkCookerC(checkBits) && cErrorE5)
                        || (checkCookerD(checkBits) && dErrorE5)
                        || (checkCookerE(checkBits) && eErrorE5)
                        || (checkCookerF(checkBits) && fErrorE5);
            case ERR_F1:
                return (checkCookerA(checkBits) && aErrorF1)
                        || (checkCookerB(checkBits) && bErrorF1)
                        || (checkCookerC(checkBits) && cErrorF1)
                        || (checkCookerD(checkBits) && dErrorF1)
                        || (checkCookerE(checkBits) && eErrorF1)
                        || (checkCookerF(checkBits) && fErrorF1);
            case ERR_F3:
                return (checkCookerA(checkBits) && aErrorF3)
                        || (checkCookerB(checkBits) && bErrorF3)
                        || (checkCookerC(checkBits) && cErrorF3)
                        || (checkCookerD(checkBits) && dErrorF3)
                        || (checkCookerE(checkBits) && eErrorF3)
                        || (checkCookerF(checkBits) && fErrorF3);
            case ERR_F4:
                return (checkCookerA(checkBits) && aErrorF4)
                        || (checkCookerB(checkBits) && bErrorF4)
                        || (checkCookerC(checkBits) && cErrorF4)
                        || (checkCookerD(checkBits) && dErrorF4)
                        || (checkCookerE(checkBits) && eErrorF4)
                        || (checkCookerF(checkBits) && fErrorF4);
        }
        return false;
    }
    public String getErrorCookers(int checkBits, int error) {
        StringBuilder sb = new StringBuilder();
        if (checkCookerA(checkBits) && checkError(COOKER_A_BIT, error)) {
            sb.append("A");
        }
        if (checkCookerB(checkBits) && checkError(COOKER_B_BIT, error)) {
            sb.append("B");
        }
        if (checkCookerC(checkBits) && checkError(COOKER_C_BIT, error)) {
            sb.append("C");
        }
        if (checkCookerD(checkBits) && checkError(COOKER_D_BIT, error)) {
            sb.append("D");
        }
        if (checkCookerE(checkBits) && checkError(COOKER_E_BIT, error)) {
            sb.append("E");
        }
        if (checkCookerF(checkBits) && checkError(COOKER_F_BIT, error)) {
            sb.append("F");
        }
        return sb.toString();
    }
    public int getErrorCookerBits(int checkBits, int error) {
        int result = 0;
        if (checkCookerA(checkBits) && checkError(COOKER_A_BIT, error)) {
            result |= COOKER_A_BIT;
        }
        if (checkCookerB(checkBits) && checkError(COOKER_B_BIT, error)) {
            result |= COOKER_B_BIT;
        }
        if (checkCookerC(checkBits) && checkError(COOKER_C_BIT, error)) {
            result |= COOKER_C_BIT;
        }
        if (checkCookerD(checkBits) && checkError(COOKER_D_BIT, error)) {
            result |= COOKER_D_BIT;
        }
        if (checkCookerE(checkBits) && checkError(COOKER_E_BIT, error)) {
            result |= COOKER_E_BIT;
        }
        if (checkCookerF(checkBits) && checkError(COOKER_F_BIT, error)) {
            result |= COOKER_F_BIT;
        }
        return result;
    }
    public boolean isaError() {
        return isaErrorE1() || isaErrorE2() || isaErrorE3() || isaErrorE4() 
                || isaErrorE5() || isaErrorF1() || isaErrorF3() || isaErrorF4();
    }
    public boolean isbError() {
        return isbErrorE1() || isbErrorE2() || isbErrorE3() || isbErrorE4()
                || isbErrorE5() || isbErrorF1() || isbErrorF3() || isbErrorF4();
    }
    public boolean iscError() {
        return iscErrorE1() || iscErrorE2() || iscErrorE3() || iscErrorE4()
                || iscErrorE5() || iscErrorF1() || iscErrorF3() || iscErrorF4();
    }
    public boolean isdError() {
        return isdErrorE1() || isdErrorE2() || isdErrorE3() || isdErrorE4()
                || isdErrorE5() || isdErrorF1() || isdErrorF3() || isdErrorF4();
    }
    public boolean iseError() {
        return iseErrorE1() || iseErrorE2() || iseErrorE3() || iseErrorE4()
                || iseErrorE5() || iseErrorF1() || iseErrorF3() || iseErrorF4();
    }
    public boolean isfError() {
        return isfErrorE1() || isfErrorE2() || isfErrorE3() || isfErrorE4()
                || isfErrorE5() || isfErrorF1() || isfErrorF3() || isfErrorF4();
    }

    private void handleCookerA() {
        aInfo = data[5];
        aError = data[6];
        aPanBottomAD = data[7];
        aErrorE1 = ((aError & ERR_E1_BIT) == ERR_E1_BIT);
        aErrorE2 = ((aError & ERR_E2_BIT) == ERR_E2_BIT);
        aErrorE3 = ((aError & ERR_E3_BIT) == ERR_E3_BIT);
        aErrorE4 = ((aError & ERR_E4_BIT) == ERR_E4_BIT);
        aErrorE5 = ((aError & ERR_E5_BIT) == ERR_E5_BIT);
        aErrorF1 = ((aError & ERR_F1_BIT) == ERR_F1_BIT);
        aErrorF3 = ((aError & ERR_F3_BIT) == ERR_F3_BIT);
        aErrorF4 = ((aError & ERR_F4_BIT) == ERR_F4_BIT);
    }
    private void handleCookerB() {
        bInfo = data[8];
        bError = data[9];
        bPanBottomAD = data[10];
        bErrorE1 = ((bError & ERR_E1_BIT) == ERR_E1_BIT);
        bErrorE2 = ((bError & ERR_E2_BIT) == ERR_E2_BIT);
        bErrorE3 = ((bError & ERR_E3_BIT) == ERR_E3_BIT);
        bErrorE4 = ((bError & ERR_E4_BIT) == ERR_E4_BIT);
        bErrorE5 = ((bError & ERR_E5_BIT) == ERR_E5_BIT);
        bErrorF1 = ((bError & ERR_F1_BIT) == ERR_F1_BIT);
        bErrorF3 = ((bError & ERR_F3_BIT) == ERR_F3_BIT);
        bErrorF4 = ((bError & ERR_F4_BIT) == ERR_F4_BIT);
    }
    private void handleCookerC() {
        cInfo = data[11];
        cError = data[12];
        cPanBottomAD = data[13];
        cErrorE1 = ((cError & ERR_E1_BIT) == ERR_E1_BIT);
        cErrorE2 = ((cError & ERR_E2_BIT) == ERR_E2_BIT);
        cErrorE3 = ((cError & ERR_E3_BIT) == ERR_E3_BIT);
        cErrorE4 = ((cError & ERR_E4_BIT) == ERR_E4_BIT);
        cErrorE5 = ((cError & ERR_E5_BIT) == ERR_E5_BIT);
        cErrorF1 = ((cError & ERR_F1_BIT) == ERR_F1_BIT);
        cErrorF3 = ((cError & ERR_F3_BIT) == ERR_F3_BIT);
        cErrorF4 = ((cError & ERR_F4_BIT) == ERR_F4_BIT);
    }
    private void handleCookerD() {
        dInfo = data[14];
        dError = data[15];
        dPanBottomAD = data[16];
        dErrorE1 = ((dError & ERR_E1_BIT) == ERR_E1_BIT);
        dErrorE2 = ((dError & ERR_E2_BIT) == ERR_E2_BIT);
        dErrorE3 = ((dError & ERR_E3_BIT) == ERR_E3_BIT);
        dErrorE4 = ((dError & ERR_E4_BIT) == ERR_E4_BIT);
        dErrorE5 = ((dError & ERR_E5_BIT) == ERR_E5_BIT);
        dErrorF1 = ((dError & ERR_F1_BIT) == ERR_F1_BIT);
        dErrorF3 = ((dError & ERR_F3_BIT) == ERR_F3_BIT);
        dErrorF4 = ((dError & ERR_F4_BIT) == ERR_F4_BIT);
    }
    private void handleCookerE() {
        eInfo = data[17];
        eError = data[18];
        ePanBottomAD = data[19];
        eErrorE1 = ((eError & ERR_E1_BIT) == ERR_E1_BIT);
        eErrorE2 = ((eError & ERR_E2_BIT) == ERR_E2_BIT);
        eErrorE3 = ((eError & ERR_E3_BIT) == ERR_E3_BIT);
        eErrorE4 = ((eError & ERR_E4_BIT) == ERR_E4_BIT);
        eErrorE5 = ((eError & ERR_E5_BIT) == ERR_E5_BIT);
        eErrorF1 = ((eError & ERR_F1_BIT) == ERR_F1_BIT);
        eErrorF3 = ((eError & ERR_F3_BIT) == ERR_F3_BIT);
        eErrorF4 = ((eError & ERR_F4_BIT) == ERR_F4_BIT);
    }
    private void handleCookerF() {
        fInfo = data[20];
        fError = data[21];
        fPanBottomAD = data[22];
        fErrorE1 = ((fError & ERR_E1_BIT) == ERR_E1_BIT);
        fErrorE2 = ((fError & ERR_E2_BIT) == ERR_E2_BIT);
        fErrorE3 = ((fError & ERR_E3_BIT) == ERR_E3_BIT);
        fErrorE4 = ((fError & ERR_E4_BIT) == ERR_E4_BIT);
        fErrorE5 = ((fError & ERR_E5_BIT) == ERR_E5_BIT);
        fErrorF1 = ((fError & ERR_F1_BIT) == ERR_F1_BIT);
        fErrorF3 = ((fError & ERR_F3_BIT) == ERR_F3_BIT);
        fErrorF4 = ((fError & ERR_F4_BIT) == ERR_F4_BIT);
    }

    public static boolean checkCookerA(int checkBits) {
        return (checkBits & COOKER_A_BIT) == COOKER_A_BIT;
    }
    public static boolean checkCookerB(int checkBits) {
        return (checkBits & COOKER_B_BIT) == COOKER_B_BIT;
    }
    public static boolean checkCookerC(int checkBits) {
        return (checkBits & COOKER_C_BIT) == COOKER_C_BIT;
    }
    public static boolean checkCookerD(int checkBits) {
        return (checkBits & COOKER_D_BIT) == COOKER_D_BIT;
    }
    public static boolean checkCookerE(int checkBits) {
        return (checkBits & COOKER_E_BIT) == COOKER_E_BIT;
    }
    public static boolean checkCookerF(int checkBits) {
        return (checkBits & COOKER_F_BIT) == COOKER_F_BIT;
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }

    public byte getPowerSignal() {
        return powerSignal;
    }

    public byte getAInfo() {
        return aInfo;
    }
    public byte getAError() {
        return aError;
    }
    public byte getAPanBottomAD() {
        return aPanBottomAD;
    }
    public byte getBInfo() {
        return bInfo;
    }
    public byte getBError() {
        return bError;
    }
    public byte getBPanBottomAD() {
        return bPanBottomAD;
    }
    public byte getCInfo() {
        return cInfo;
    }
    public byte getCError() {
        return cError;
    }
    public byte getCPanBottomAD() {
        return cPanBottomAD;
    }
    public byte getDInfo() {
        return dInfo;
    }
    public byte getDError() {
        return dError;
    }
    public byte getDPanBottomAD() {
        return dPanBottomAD;
    }
    public byte getEInfo() {
        return eInfo;
    }
    public byte getEError() {
        return eError;
    }
    public byte getEPanBottomAD() {
        return ePanBottomAD;
    }
    public byte getFInfo() {
        return fInfo;
    }
    public byte getFError() {
        return fError;
    }
    public byte getFPanBottomAD() {
        return fPanBottomAD;
    }
    public byte getCrcH() {
        return crcH;
    }
    public byte getCrcL() {
        return crcL;
    }
    public boolean isPanelOverTemperature() {
        return !TFTCookerApplication.getInstance().isInDemonstrationMode() && panelOverTemperature;
    }
    public boolean isaErrorE1() {
        return aErrorE1;
    }
    public boolean isaErrorE2() {
        return aErrorE2;
    }
    public boolean isaErrorE3() {
        return aErrorE3;
    }
    public boolean isaErrorE4() {
        return aErrorE4;
    }
    public boolean isaErrorE5() {
        return aErrorE5;
    }
    public boolean isaErrorF1() {
        return aErrorF1;
    }
    public boolean isaErrorF3() {
        return aErrorF3;
    }
    public boolean isaErrorF4() {
        return aErrorF4;
    }
    public boolean isbErrorE1() {
        return bErrorE1;
    }
    public boolean isbErrorE2() {
        return bErrorE2;
    }
    public boolean isbErrorE3() {
        return bErrorE3;
    }
    public boolean isbErrorE4() {
        return bErrorE4;
    }
    public boolean isbErrorE5() {
        return bErrorE5;
    }
    public boolean isbErrorF1() {
        return bErrorF1;
    }
    public boolean isbErrorF3() {
        return bErrorF3;
    }
    public boolean isbErrorF4() {
        return bErrorF4;
    }
    public boolean iscErrorE1() {
        return cErrorE1;
    }
    public boolean iscErrorE2() {
        return cErrorE2;
    }
    public boolean iscErrorE3() {
        return cErrorE3;
    }
    public boolean iscErrorE4() {
        return cErrorE4;
    }
    public boolean iscErrorE5() {
        return cErrorE5;
    }
    public boolean iscErrorF1() {
        return cErrorF1;
    }
    public boolean iscErrorF3() {
        return cErrorF3;
    }
    public boolean iscErrorF4() {
        return cErrorF4;
    }
    public boolean isdErrorE1() {
        return dErrorE1;
    }
    public boolean isdErrorE2() {
        return dErrorE2;
    }
    public boolean isdErrorE3() {
        return dErrorE3;
    }
    public boolean isdErrorE4() {
        return dErrorE4;
    }
    public boolean isdErrorE5() {
        return dErrorE5;
    }
    public boolean isdErrorF1() {
        return dErrorF1;
    }
    public boolean isdErrorF3() {
        return dErrorF3;
    }
    public boolean isdErrorF4() {
        return dErrorF4;
    }
    public boolean iseErrorE1() {
        return eErrorE1;
    }
    public boolean iseErrorE2() {
        return eErrorE2;
    }
    public boolean iseErrorE3() {
        return eErrorE3;
    }
    public boolean iseErrorE4() {
        return eErrorE4;
    }
    public boolean iseErrorE5() {
        return eErrorE5;
    }
    public boolean iseErrorF1() {
        return eErrorF1;
    }
    public boolean iseErrorF3() {
        return eErrorF3;
    }
    public boolean iseErrorF4() {
        return eErrorF4;
    }
    public boolean isfErrorE1() {
        return fErrorE1;
    }
    public boolean isfErrorE2() {
        return fErrorE2;
    }
    public boolean isfErrorE3() {
        return fErrorE3;
    }
    public boolean isfErrorE4() {
        return fErrorE4;
    }
    public boolean isfErrorE5() {
        return fErrorE5;
    }
    public boolean isfErrorF1() {
        return fErrorF1;
    }
    public boolean isfErrorF3() {
        return fErrorF3;
    }
    public boolean isfErrorF4() {
        return fErrorF4;
    }
    public boolean isLegalData() {
        return legalData;
    }
}