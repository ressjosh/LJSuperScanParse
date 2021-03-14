package my_project.model;

import KAGO_framework.model.GraphicalObject;

import java.awt.image.BufferedImage;

public class Biber extends GraphicalObject {
    private int richtung;
    private int hoehe;
    private int weite;
    private boolean AMfressen;
    private BufferedImage[] biberBilder;

    public Biber() {
        biberBilder = new BufferedImage[4];
        setNewImage("assets/biberRechts.png");
        biberBilder[0] = getMyImage();
        setNewImage("assets/biberRunter.png");
        biberBilder[1] = getMyImage();
        setNewImage("assets/biberLinks.png");
        biberBilder[2] = getMyImage();
        setNewImage("assets/biberHoch.png");
        biberBilder[3] = getMyImage();
        richtung = 0;
        hoehe = 2;
        weite = 0;
    }

    public boolean isAMfressen() {
        return AMfressen;
    }

    public void setAMfressen(boolean AMfressen) {
        this.AMfressen = AMfressen;
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

    public void setRichtung(int richtungsanderung) {
        richtung = richtung + richtungsanderung;
        if(richtung >3){
            richtung = 0;
        }
        if(richtung < 0){
            richtung = 3;
        }
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public void setWeite(int weite) {
        this.weite = weite;
    }

    public void removeRichtung(){
        richtung = 0;
    }

    public BufferedImage myImage(){
        return biberBilder[richtung];
    }
}
