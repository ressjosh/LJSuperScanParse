package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.Biber;
import my_project.model.CodeScanner;
import my_project.model.Interpreter;
import my_project.view.Ausgabefeld;

public class CentralControll {

    private CodeScanner scanner;
    private Interpreter interpreter;
    private ViewController vC;
    private ViewControll viewControll;

    public CentralControll(ViewController vC) {
        this.vC = vC;
        viewControll = new ViewControll(this, vC);
        scanner = new CodeScanner(viewControll, this);
        interpreter = new Interpreter(this, viewControll);
    }


    public void executeCode(String code){
        if(scanner.ankommendesStringAbarbeiten(code)){
            interpreter.start(scanner);
        }else viewControll.showError();
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }
}
