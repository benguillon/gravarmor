package main.fr.epsi.gravarmor.model;

import java.util.ArrayList;
import java.util.List;

public class LandBox {

    private BoxType type;
    private List<Entity> entities;
    private boolean isSelected;

    public LandBox(BoxType type) {

        this.type = type;
        this.entities = new ArrayList<>();
        this.isSelected = false;
    }

    public LandBox(BoxType type, Entity entity) {

        this.type = type;
        this.entities = new ArrayList<>();
        this.entities.add(entity);
        this.isSelected = false;
    }

    public LandBox(BoxType type, List<Entity> entities) {

        this.type = type;
        this.entities = entities;
        this.isSelected = false;
    }

    public BoxType getType() {

        return type;
    }

    public List<Entity> getEntities() {

        return entities;
    }

    public boolean isSelected() {

        return isSelected;
    }

    public void isSelected(boolean isSelected) {

        this.isSelected = isSelected;
    }

    public String toString() {

        return "Type : " + type + ", Entities : " + entities;
    }
}
