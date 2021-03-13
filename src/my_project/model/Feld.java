package my_project.model;

import KAGO_framework.model.GraphicalObject;

import java.awt.image.BufferedImage;

public class Feld extends GraphicalObject {

    private int baumAnzahl;
    private boolean baumdrauf;
    private BufferedImage[] images;
    private int aktuellesBild;

    public Feld() {
        baumdrauf = false;
        aktuellesBild = 0;
        images = new BufferedImage[4];
        setNewImage("assets/grass.jpg");
        images[0] = getMyImage();
        setNewImage("assets/grass01.png");
        images[2] = getMyImage();
        setNewImage("assets/grass02.png");
        images[1] = getMyImage();
        setNewImage("assets/grass03.png");
        images[3] = getMyImage();

    }

    public int getBaumAnzahl() {
        return baumAnzahl;
    }

    public void setBaumAnzahl(int baumAnzahl) {
        this.baumAnzahl = baumAnzahl;
        if(baumAnzahl == 0){
            baumdrauf = false;
            aktuellesBild = 0;
        }else if(baumAnzahl == 1){
            baumdrauf = true;
            aktuellesBild = 1;
        }else if(baumAnzahl == 2){
            baumdrauf = true;
            aktuellesBild = 2;
        }else if(baumAnzahl > 2){
            baumdrauf = true;
            aktuellesBild = 3;
        }
    }

    public void erhoeheBaumAnzahl(int anzahl) {
        baumAnzahl = baumAnzahl + anzahl;
        if(baumAnzahl == 0){
            baumdrauf = false;
            aktuellesBild = 0;
        }else if(baumAnzahl == 1){
            baumdrauf = true;
            aktuellesBild = 1;
        }else if(baumAnzahl == 2){
            baumdrauf = true;
            aktuellesBild = 2;
        }else if(baumAnzahl > 2){
            baumdrauf = true;
            aktuellesBild = 3;
        }
    }

    public boolean isBaumdrauf() {
        return baumdrauf;
    }

    public BufferedImage getImage(){
        return images[aktuellesBild];
    }
}
