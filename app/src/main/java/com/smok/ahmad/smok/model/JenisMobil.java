package com.smok.ahmad.smok.model;

/**
 * Created by Ahmad on 05/05/2016.
 */
public class JenisMobil {
private String merkMobil;
    private String jenisMobil;

    public JenisMobil(String merkMobil, String jenisMobil) {
        this.merkMobil = merkMobil;
        this.jenisMobil = jenisMobil;

    }

    public String getMerkMobil() {
        return merkMobil;
    }

    public void setMerkMobil(String merkMobil) {
        this.merkMobil = merkMobil;
    }

    public String getJenisMobil() {
        return jenisMobil;
    }

    public void setJenisMobil(String jenisMobil) {
        this.jenisMobil = jenisMobil;
    }
}
