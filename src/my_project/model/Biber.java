package my_project.model;

import java.awt.image.BufferedImage;

public class Biber {
    private int richtung;
    private int hoehe;
    private int weite;
    private BufferedImage[] biberBilder;

    public Biber() {
        richtung = 0;
        hoehe = 2;
        weite = 0;
    }

    public int getRichtung() {
        return richtung;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getWeite() {
        return weite;
    }

    public void setRichtung(int richtung) {
        this.richtung = richtung;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public void setWeite(int weite) {
        this.weite = weite;
    }
}
