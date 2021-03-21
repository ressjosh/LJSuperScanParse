package my_project.view;

import my_project.control.ViewControll;

import javax.swing.*;

public class Anleitung {
    private JPanel mainPanel;
    private JLabel anleitungsFeld;
    private ViewControll vC;
    private JFrame frame;

    public Anleitung(ViewControll vC){
        this.vC = vC;
        frame = new JFrame("Anleitung");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        frame.setBounds(40,40,1250,400);
        anleitungsFeld.setText("<html><body><br/>Es gibt folgende Befehle:      <br/><br/>vor()<br/>linksUm()<br/>" +
                "                rechtsUm()<br/>pflanzen()<br/>ernten()<br/><br/>" +
                "                Am Anfang muss 'start' stehen.<br/>Dann können Parameter folgen. Diese werden mit 'int' eingeleitet. Danach folgt der Name und dann der Wert.<br/>" +
                "                Dann kommen die Methoden. Es muss erst 'functions' stehen. In der Zeile darauf folgt das Wort 'methode' und der Name der Methode. In den Zeilen darauf folgen die Befehle, die Teil der Methode sein sollen. " +
                "                <br/> ES DÜRFEN KEINE SCHLEIFEN IN DER METHODE SEIN! Die Methode wird mit einem '#' beendet. Um eine Methode auftzrufen nutzt man das Wort 'process' und dann der Name der Methode.<br/>" +
                "                Um mit den Parametern zu agieren nutzt man die Wörter 'add' und 'sub'. Man schreibt das Wort 'add' und den Namen um zu addieren und 'sub'und den Namen des Paarmters um zu subtrahieren." +
                "                <br/>Nach den möglichen Methoden folgt ein 'befehle'. Darunter kommen alle Befehl, die ausgeführt werden sollen." +
                "                Ganz am Ende kommt ein 'ende'." +
                "                <br/><br/>Man kann nicht nur einfache Befehle benutzen, sondern auch Schleifen und Verzweigungen." +
                "                <br/>Verzweigungen werden mit einem 'wenn' eingeleitet. Darauf folgt die Bedingung und dann in der Zeile darunter die Befehle. Das Ganze wird wie die Methode mit einem '#' beendet." +
                "                <br/>Es funktionieren auch Verzeigungen in Verzeigungen." +
                "                <br/>Schleifen werden mit einem 'solange' eingeleitet. Darauf folgt die Bedingung. In der nächsten Zeile kommen dann die Befehle. Das Ganze wird mit einem '#' beendet." +
                "                <br/>Es funktionieren auch Schleifen in Schleifen. <br/><br/><br/></body></html>");
    }

    public void setVisible(boolean b){
        frame.setVisible(b);
    }
}
