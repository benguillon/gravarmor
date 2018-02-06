package main.fr.epsi.gravarmor.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.awt.*;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MenuController {

    public String consoleOutpout ="" ;
    public int numeroEquipe =2;

    MenuController(ScrollPane scrollPane){

        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);

        Text textMainMenu = new Text("Menu du jeu");
        textMainMenu.setFont(new Font(23));
        textMainMenu.setFontSmoothingType(FontSmoothingType.GRAY);


            switch (numeroEquipe){
                case 1:{
                    Text textEquipePlay = new Text("Equipe Rouge");
                    textEquipePlay.setFont(new Font(17.5));
                    textEquipePlay.setFill(RED);
                    gridPane.add(textEquipePlay,0,1);
                    break;

                }
                case 2: {
                    Text textEquipePlay = new Text("Equipe Bleue");
                    textEquipePlay.setFont(new Font(17.5));
                    textEquipePlay.setFill(BLUE);
                    gridPane.add(textEquipePlay,0,1);
                    break;

                }
                default: System.err.println("ERROR Equipe");
        }

        Button boutonAjouterUnPion = new Button("Ajouter un pion (F1)");
        boutonAjouterUnPion.setPrefSize(140,50);

        Button boutonPasserLeTour = new Button("Passer le tour (F2)");
        boutonPasserLeTour.setPrefSize(140,50);


        RadioButton modeDeplacement = new RadioButton("Mode déplacement");
        RadioButton modeTire = new RadioButton("Mode tire");

        boutonPasserLeTour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                consoleOutpout="Fin du tour";
            }
        });

        boutonAjouterUnPion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                consoleOutpout="Pion ajouté";
            }
        });

        TextArea textConsole = new TextArea("\n"+consoleOutpout);
        textConsole.snappedBottomInset();
        textConsole.scrollTopProperty();

        gridPane.add(textMainMenu,0,0);
        gridPane.add(boutonPasserLeTour,0,2);
        gridPane.add(boutonAjouterUnPion,0,3);
        gridPane.add(textConsole,0,4);


        gridPane.setVgap(20);
        gridPane.setHgap(20);

    }



}
