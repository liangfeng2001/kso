package com.ekek.tftcooker.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CookerEvent {

    @Id(autoincrement = true)
    @Property(nameInDb = "ce_id")
    private Long id;
    @Property(nameInDb = "ce_name")
    private String name;
    @Property(nameInDb = "ce_description")
    private String description;
    @Property(nameInDb = "ce_param_la")
    private long paramLA;
    @Property(nameInDb = "ce_param_lb")
    private long paramLB;
    @Property(nameInDb = "ce_param_lc")
    private long paramLC;
    @Property(nameInDb = "ce_param_ld")
    private long paramLD;
    @Property(nameInDb = "ce_param_sa")
    private String paramSA;
    @Property(nameInDb = "ce_param_sb")
    private String paramSB;
    @Property(nameInDb = "ce_param_sc")
    private String paramSC;
    @Property(nameInDb = "ce_param_sd")
    private String paramSD;
    @Property(nameInDb = "ce_created_at")
    private long createdAt;
    @Generated(hash = 201425449)
    public CookerEvent(Long id, String name, String description, long paramLA,
            long paramLB, long paramLC, long paramLD, String paramSA,
            String paramSB, String paramSC, String paramSD, long createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.paramLA = paramLA;
        this.paramLB = paramLB;
        this.paramLC = paramLC;
        this.paramLD = paramLD;
        this.paramSA = paramSA;
        this.paramSB = paramSB;
        this.paramSC = paramSC;
        this.paramSD = paramSD;
        this.createdAt = createdAt;
    }
    @Generated(hash = 2093438792)
    public CookerEvent() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getParamLA() {
        return this.paramLA;
    }
    public void setParamLA(long paramLA) {
        this.paramLA = paramLA;
    }
    public long getParamLB() {
        return this.paramLB;
    }
    public void setParamLB(long paramLB) {
        this.paramLB = paramLB;
    }
    public long getParamLC() {
        return this.paramLC;
    }
    public void setParamLC(long paramLC) {
        this.paramLC = paramLC;
    }
    public long getParamLD() {
        return this.paramLD;
    }
    public void setParamLD(long paramLD) {
        this.paramLD = paramLD;
    }
    public String getParamSA() {
        return this.paramSA;
    }
    public void setParamSA(String paramSA) {
        this.paramSA = paramSA;
    }
    public String getParamSB() {
        return this.paramSB;
    }
    public void setParamSB(String paramSB) {
        this.paramSB = paramSB;
    }
    public String getParamSC() {
        return this.paramSC;
    }
    public void setParamSC(String paramSC) {
        this.paramSC = paramSC;
    }
    public String getParamSD() {
        return this.paramSD;
    }
    public void setParamSD(String paramSD) {
        this.paramSD = paramSD;
    }
    public long getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
