package my_project.view;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.control.ViewControll;
import my_project.model.KrasseListe;

import java.awt.image.BufferedImage;

public class Ausgabefeld extends GraphicalObject {

    private BufferedImage myImage;
    private BufferedImage biber;
    private ViewControll vC;

    public Ausgabefeld(ViewControll vC) {
        this.vC = vC;
        setNewImage("assets/grass.jpg");
        myImage = getMyImage();
        setNewImage("assets/biber.png");
        biber = getMyImage();
    }

    @Override
    public void draw(DrawTool drawTool) {
        List<KrasseListe<Integer, String>.Token<Integer, String>> tmp = vC.gibParameter();
        tmp.toFirst();
        drawTool.formatText("Blöp", 3, 30);
        drawTool.setLineWidth(15);
        int temp = 40;
        while(tmp.hasAccess()){
            drawTool.drawText(1035, temp, tmp.getContent().getType() + " = " + tmp.getContent().getValue());
            tmp.next();
            temp += 40;
        }
        drawTool.drawRectangle(10, 10, 1000, 500);
        for(int i = 0; i <10; i++){
            for(int j = 0; j < 5; j++){
                drawTool.drawImage(vC.getFelder()[i][j].getImage(), 10+i*100, 10+j*100);
            }
        }
        drawTool.drawImage(vC.getBiber().myImage(), 10 + vC.getBiber().getWeite()*100, 10 + vC.getBiber().getHoehe()*100);

    }
}
