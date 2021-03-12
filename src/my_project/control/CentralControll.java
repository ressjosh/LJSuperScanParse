package my_project.control;

import my_project.model.CodeScanner;

public class CentralControll {

    private CodeScanner scanner;


    public CentralControll() {
        scanner = new CodeScanner();
    }


    public void executeCode(String code){
        scanner.ankommendesStringAbarbeiten(code);
    }
}
