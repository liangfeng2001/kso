package com.ekek.tftcooker.entity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TFTDataModel {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "CookerModel_a")
    private int CookerModel_a;
    @Property(nameInDb = "CookerFireGear_a")
    private int CookerFireGear_a;
    @Property(nameInDb = "CookerTemperature_a")
    private int CookerTemperature_a;
    @Property(nameInDb = "CookerModel_b")
    private int CookerModel_b;
    @Property(nameInDb = "CookerFireGear_b")
    private int CookerFireGear_b;
    @Property(nameInDb = "CookerTemperature_b")
    private int CookerTemperature_b;
    @Property(nameInDb = "CookerModel_c")
    private int CookerModel_c;
    @Property(nameInDb = "CookerFireGear_c")
    private int CookerFireGear_c;
    @Property(nameInDb = "CookerTemperature_c")
    private int CookerTemperature_c;
    @Property(nameInDb = "CookerModel_d")
    private int CookerModel_d;
    @Property(nameInDb = "CookerFireGear_d")
    private int CookerFireGear_d;
    @Property(nameInDb = "CookerTemperature_d")
    private int CookerTemperature_d;
    @Property(nameInDb = "CookerModel_e")
    private int CookerModel_e;
    @Property(nameInDb = "CookerFireGear_e")
    private int CookerFireGear_e;
    @Property(nameInDb = "CookerTemperature_e")
    private int CookerTemperature_e;
    @Property(nameInDb = "CookerModel_f")
    private int CookerModel_f;
    @Property(nameInDb = "CookerFireGear_f")
    private int CookerFireGear_f;
    @Property(nameInDb = "CookerTemperature_f")
    private int CookerTemperature_f;

    @Property(nameInDb = "fanGear")
    private int FanGear;
    @Property(nameInDb = "LightGear_blue")
    private int LightGear_blue;
    @Property(nameInDb = "LightGear_orange")
    private int LightGear_orange;
    @Property(nameInDb = "reserved_1")
    private int reserved_1;
    @Property(nameInDb = "reserved_2")
    private int reserved_2;
    @Generated(hash = 1466555552)
    public TFTDataModel(Long id,
                        int CookerModel_a, int CookerFireGear_a, int CookerTemperature_a,
                        int CookerModel_b, int CookerFireGear_b, int CookerTemperature_b,
                        int CookerModel_c, int CookerFireGear_c, int CookerTemperature_c,
                        int CookerModel_d, int CookerFireGear_d, int CookerTemperature_d,
                        int CookerModel_e, int CookerFireGear_e, int CookerTemperature_e,
                        int CookerModel_f, int CookerFireGear_f, int CookerTemperature_f,
                        int FanGear, int LightGear_blue, int LightGear_orange,
                        int reserved_1, int reserved_2) {
        this.id = id;
        this.CookerModel_a = CookerModel_a;
        this.CookerFireGear_a = CookerFireGear_a;
        this.CookerTemperature_a = CookerTemperature_a;
        this.CookerModel_b = CookerModel_b;
        this.CookerFireGear_b = CookerFireGear_b;
        this.CookerTemperature_b = CookerTemperature_b;
        this.CookerModel_c = CookerModel_c;
        this.CookerFireGear_c = CookerFireGear_c;
        this.CookerTemperature_c = CookerTemperature_c;
        this.CookerModel_d = CookerModel_d;
        this.CookerFireGear_d = CookerFireGear_d;
        this.CookerTemperature_d = CookerTemperature_d;
        this.CookerModel_e = CookerModel_e;
        this.CookerFireGear_e = CookerFireGear_e;
        this.CookerTemperature_e = CookerTemperature_e;
        this.CookerModel_f = CookerModel_f;
        this.CookerFireGear_f = CookerFireGear_f;
        this.CookerTemperature_f = CookerTemperature_f;
        this.FanGear = FanGear;
        this.LightGear_blue = LightGear_blue;
        this.LightGear_orange = LightGear_orange;
        this.reserved_1 = reserved_1;
        this.reserved_2 = reserved_2;
    }
    @Generated(hash = 610600054)
    public TFTDataModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCookerModel_a() {
        return this.CookerModel_a;
    }
    public void setCookerModel_a(int CookerModel_a) {
        this.CookerModel_a = CookerModel_a;
    }
    public int getCookerFireGear_a() {
        return this.CookerFireGear_a;
    }
    public void setCookerFireGear_a(int CookerFireGear_a) {
        this.CookerFireGear_a = CookerFireGear_a;
    }
    public int getCookerTemperature_a() {
        return this.CookerTemperature_a;
    }
    public void setCookerTemperature_a(int CookerTemperature_a) {
        this.CookerTemperature_a = CookerTemperature_a;
    }
    public int getCookerModel_b() {
        return this.CookerModel_b;
    }
    public void setCookerModel_b(int CookerModel_b) {
        this.CookerModel_b = CookerModel_b;
    }
    public int getCookerFireGear_b() {
        return this.CookerFireGear_b;
    }
    public void setCookerFireGear_b(int CookerFireGear_b) {
        this.CookerFireGear_b = CookerFireGear_b;
    }
    public int getCookerTemperature_b() {
        return this.CookerTemperature_b;
    }
    public void setCookerTemperature_b(int CookerTemperature_b) {
        this.CookerTemperature_b = CookerTemperature_b;
    }
    public int getCookerModel_c() {
        return this.CookerModel_c;
    }
    public void setCookerModel_c(int CookerModel_c) {
        this.CookerModel_c = CookerModel_c;
    }
    public int getCookerFireGear_c() {
        return this.CookerFireGear_c;
    }
    public void setCookerFireGear_c(int CookerFireGear_c) {
        this.CookerFireGear_c = CookerFireGear_c;
    }
    public int getCookerTemperature_c() {
        return this.CookerTemperature_c;
    }
    public void setCookerTemperature_c(int CookerTemperature_c) {
        this.CookerTemperature_c = CookerTemperature_c;
    }
    public int getCookerModel_d() {
        return this.CookerModel_d;
    }
    public void setCookerModel_d(int CookerModel_d) {
        this.CookerModel_d = CookerModel_d;
    }
    public int getCookerFireGear_d() {
        return this.CookerFireGear_d;
    }
    public void setCookerFireGear_d(int CookerFireGear_d) {
        this.CookerFireGear_d = CookerFireGear_d;
    }
    public int getCookerTemperature_d() {
        return this.CookerTemperature_d;
    }
    public void setCookerTemperature_d(int CookerTemperature_d) {
        this.CookerTemperature_d = CookerTemperature_d;
    }
    public int getCookerModel_e() {
        return this.CookerModel_e;
    }
    public void setCookerModel_e(int CookerModel_e) {
        this.CookerModel_e = CookerModel_e;
    }
    public int getCookerFireGear_e() {
        return this.CookerFireGear_e;
    }
    public void setCookerFireGear_e(int CookerFireGear_e) {
        this.CookerFireGear_e = CookerFireGear_e;
    }
    public int getCookerTemperature_e() {
        return this.CookerTemperature_e;
    }
    public void setCookerTemperature_e(int CookerTemperature_e) {
        this.CookerTemperature_e = CookerTemperature_e;
    }
    public int getCookerModel_f() {
        return this.CookerModel_f;
    }
    public void setCookerModel_f(int CookerModel_f) {
        this.CookerModel_f = CookerModel_f;
    }
    public int getCookerFireGear_f() {
        return this.CookerFireGear_f;
    }
    public void setCookerFireGear_f(int CookerFireGear_f) {
        this.CookerFireGear_f = CookerFireGear_f;
    }
    public int getCookerTemperature_f() {
        return this.CookerTemperature_f;
    }
    public void setCookerTemperature_f(int CookerTemperature_f) {
        this.CookerTemperature_f = CookerTemperature_f;
    }
    public int getFanGear() {
        return this.FanGear;
    }
    public void setFanGear(int FanGear) {
        this.FanGear = FanGear;
    }
    public int getLightGear_blue() {
        return this.LightGear_blue;
    }
    public void setLightGear_blue(int LightGear_blue) {
        this.LightGear_blue = LightGear_blue;
    }
    public int getLightGear_orange() {
        return this.LightGear_orange;
    }
    public void setLightGear_orange(int LightGear_orange) {
        this.LightGear_orange = LightGear_orange;
    }
    public int getReserved_1() {
        return this.reserved_1;
    }
    public void setReserved_1(int reserved_1) {
        this.reserved_1 = reserved_1;
    }
    public int getReserved_2() {
        return this.reserved_2;
    }
    public void setReserved_2(int reserved_2) {
        this.reserved_2 = reserved_2;
    }

}
