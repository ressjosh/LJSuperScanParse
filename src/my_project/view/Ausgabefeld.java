package my_project.view;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.image.BufferedImage;

public class Ausgabefeld extends GraphicalObject {

    private BufferedImage myImage;
    private BufferedImage biber;

    public Ausgabefeld() {
        setNewImage("assets/grass.jpg");
        myImage = getMyImage();
        setNewImage("assets/biber.png");
        biber = getMyImage();
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawRectangle(9, 9, 401, 201);
        for(int i = 0; i <10; i++){
            for(int j = 0; j < 5; j++){
                drawTool.drawImage(myImage, 10+i*40, 10+j*40);
            }

        }
        drawTool.drawImage(biber, 10, 90);

    }
}
