package my_project.control;

import my_project.model.Biber;
import my_project.model.CodeScanner;
import my_project.model.Interpreter;

public class CentralControll {

    private CodeScanner scanner;
    private Interpreter interpreter;

    public CentralControll() {
        scanner = new CodeScanner();
        interpreter = new Interpreter();
    }


    public void executeCode(String code){
        if(scanner.ankommendesStringAbarbeiten(code)) interpreter.start();
    }
}
