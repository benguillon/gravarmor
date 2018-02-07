package main.fr.epsi.gravarmor.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import main.fr.epsi.gravarmor.model.Entity;
import main.fr.epsi.gravarmor.model.coordinates.NumeroEquipe;
import main.fr.epsi.gravarmor.model.Unit;

import java.io.IOException;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MenuController {

    private int logCounter;
    private TextArea logArea;
    private AnchorPane entityDescriptionView;
    private Text textEquipePlay;


    MenuController(ScrollPane scrollPane) throws IOException {

        GridPane gridPane = new GridPane();
        Pane consolePane = new Pane();
        scrollPane.setContent(gridPane);

        Text textMainMenu = new Text("Menu du jeu");
        textMainMenu.setFont(new Font(23));
        textMainMenu.setFontSmoothingType(FontSmoothingType.GRAY);

        textEquipePlay = new Text();
        textEquipePlay.setFont(new Font(17.5));


        Button boutonPasserLeTour = new Button("Passer le tour (F2)");
        boutonPasserLeTour.setPrefSize(140,50);

        Button boutonDeplacer = new Button("Déplacement (F3)");
        boutonDeplacer.setPrefSize(140,50);

        Button boutonTirer = new Button("Tirer (F4)");
        boutonTirer.setPrefSize(140,50);

        logArea = new TextArea();
        logArea.setWrapText(false);
        logArea.setEditable(false);
        logCounter = 0;

        boutonPasserLeTour.setOnAction(e -> {
            log("Fin du tour");
        });


        FXMLLoader entityDescription = new FXMLLoader(getClass().getClassLoader().getResource("main/fr/epsi/gravarmor/view/fxml/entityDescription.fxml"));
        entityDescriptionView = entityDescription.load();

        gridPane.add(textEquipePlay, 0, 1);
        gridPane.add(textMainMenu,0,0);
        gridPane.add(boutonPasserLeTour,0,2);
        gridPane.add(boutonTirer,0,3);
        gridPane.add(boutonDeplacer,1,3);
        gridPane.add(logArea,0,5);
        gridPane.add(entityDescriptionView,0,6);

        gridPane.setVgap(20);
        gridPane.setHgap(20);

    }

    public void log(String text) {

        logCounter++;

        logArea.appendText("\n" + logCounter + ". " + text + "\n");
    }

    public void setEntityDescription(Entity entity) {

        if (entity == null) {
            ((Text) entityDescriptionView.lookup("#entityType")).setText("");
            ((Text) entityDescriptionView.lookup("#entityDescription")).setText("");
            return;
        }

        String title = entity.toString();
        if(entity.getTeam() != null) {
            title += " (" + entity.getTeam().getName() + ")";
        }

        String entityDescription = "";
        if(entity instanceof Unit) {
            entityDescription += "Points de vie : " + ((Unit) entity).getType().getDefenseValue() + "\n";
            entityDescription += "Points de mouvements : " + ((Unit) entity).getType().getMovementPoints() + "\n";
            entityDescription += "Points d'attaque : " + ((Unit) entity).getType().getAttackValue() + "\n";
            entityDescription += "Armes : " + ((Unit) entity).getType().getWeaponType() + "\n";
        }

        ((Text) entityDescriptionView.lookup("#entityType")).setText(title);
        ((Text) entityDescriptionView.lookup("#entityDescription")).setText(entityDescription);
    }

    private void setEquipe (NumeroEquipe numeroEquipe) {

        switch (numeroEquipe) {
            case EQUIPE_BLEU: {
                textEquipePlay.setText("Equipe LEAGUE");
                textEquipePlay.setFill(BLUE);
                break;
            }

            case EQUIPE_ROUGE: {
                textEquipePlay.setText("Equipe IMPERIAL ");
                textEquipePlay.setFill(RED);
                break;
            }
        }
    }
}
