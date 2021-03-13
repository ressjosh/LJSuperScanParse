package my_project.view;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.ViewControll;

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
        drawTool.drawRectangle(9, 9, 501, 251);
        drawTool.drawRectangle(8, 8, 503, 253);
        for(int i = 0; i <10; i++){
            for(int j = 0; j < 5; j++){
                drawTool.drawImage(vC.getFelder()[i][j].getImage(), 10+i*50, 10+j*50);
            }
        }
        drawTool.drawImage(vC.getBiber().myImage(), 10 + vC.getBiber().getWeite()*50, 10 + vC.getBiber().getHoehe()*50);

    }
}
