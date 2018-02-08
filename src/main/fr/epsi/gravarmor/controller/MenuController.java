package main.fr.epsi.gravarmor.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import main.fr.epsi.gravarmor.model.Entity;
import main.fr.epsi.gravarmor.model.Unit;
import main.fr.epsi.gravarmor.model.coordinates.NumeroEquipe;

import java.io.IOException;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MenuController {

    private int logCounter;
    private TextArea logArea;
    private AnchorPane entityDescriptionView;
    private Text textEquipePlay;

    private Button boutonPasserLeTour;
    private Button boutonChangerMode;

    MenuController(ScrollPane scrollPane) throws IOException {

        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);
        scrollPane.setTranslateX(10);
        scrollPane.setTranslateY(10);


        Text textMainMenu = new Text("Menu du jeu");
        textMainMenu.setFont(new Font(23));
        textMainMenu.setFontSmoothingType(FontSmoothingType.GRAY);

        textEquipePlay = new Text();
        textEquipePlay.setFont(new Font(17.5));


        boutonPasserLeTour = new Button("Terminer le tour ");
        boutonPasserLeTour.setPrefSize(140,50);

        boutonChangerMode = new Button("Passer en mode tire ");
        boutonChangerMode.setPrefSize(140,50);

        logArea = new TextArea();
        logArea.setWrapText(true);
        logArea.setEditable(false);
        logArea.setPrefSize(290,200);
        logCounter = 0;

        getBoutonPasserLeTour().setOnAction(e -> {
            log("Fin du tour");
        });


        FXMLLoader entityDescription = new FXMLLoader(getClass().getClassLoader().getResource("main/fr/epsi/gravarmor/view/fxml/entityDescription.fxml"));
        entityDescriptionView = entityDescription.load();

        gridPane.add(textEquipePlay, 0, 1);
        gridPane.add(textMainMenu,0,0);
        gridPane.add(boutonPasserLeTour,0,2);
        gridPane.add(boutonChangerMode,0,3);
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
            entityDescription += "Points de mouvements : " + ((Unit) entity).getMovementPoints() + "\n";
            entityDescription += "Points d'attaque : " + ((Unit) entity).getType().getAttackValue() + "\n";
            entityDescription += "Armes : " + ((Unit) entity).getType().getWeaponType() + "\n";
        }

        ((Text) entityDescriptionView.lookup("#entityType")).setText(title);
        ((Text) entityDescriptionView.lookup("#entityDescription")).setText(entityDescription);
    }

    public void setEquipe (NumeroEquipe numeroEquipe) {

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

    public Button getBoutonPasserLeTour() {
        return boutonPasserLeTour;
    }

    public Button getBoutonChangerMode() {
        return boutonChangerMode;
    }
}
