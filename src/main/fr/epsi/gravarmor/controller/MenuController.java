package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MenuController {

    private int logCounter;
    private TextArea logArea;

    public int numeroEquipe = 2;

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

        Button boutonDeplacer = new Button("Déplacement (F3)");
        boutonDeplacer.setPrefSize(140,50);

        Button boutonTirer = new Button("Tirer (F4)");
        boutonTirer.setPrefSize(140,50);

        logArea = new TextArea();
        logCounter = 0;

        boutonPasserLeTour.setOnAction(e -> {
            log("Fin du tour");
        });

        boutonAjouterUnPion.setOnAction(e -> {
            log("Pion ajouté");

        });

        logArea.setWrapText(false);
        logArea.setEditable(false);

        gridPane.add(textMainMenu,0,0);
        gridPane.add(boutonPasserLeTour,0,2);
        gridPane.add(boutonAjouterUnPion,0,3);
       // gridPane.add(boutonTirer,0,4);
        //gridPane.add(boutonDeplacer,1,4);
        gridPane.add(logArea,0,5);

        gridPane.setVgap(20);
        gridPane.setHgap(20);

    }

    public void log(String text) {

        logCounter++;

        logArea.appendText("\n" + logCounter + ". " + text + "\n");
    }
}
