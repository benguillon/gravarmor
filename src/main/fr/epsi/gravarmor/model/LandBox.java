package main.fr.epsi.gravarmor.model;

import java.util.ArrayList;
import java.util.List;

public class LandBox {

    private BoxType type;
    private List<Entity> entities;

    private boolean isGraphicallyHighlighted;

    public LandBox(BoxType type) {

        this.type = type;
        this.entities = new ArrayList<>();

        this.isGraphicallyHighlighted = false;
    }

    public LandBox(BoxType type, Entity entity) {

        this.type = type;
        this.entities = new ArrayList<>();
        this.entities.add(entity);

        this.isGraphicallyHighlighted = false;
    }

    public LandBox(BoxType type, List<Entity> entities) {

        this.type = type;
        this.entities = entities;

        this.isGraphicallyHighlighted = false;
    }

    public BoxType getType() {

        return type;
    }

    public List<Entity> getEntities() {

        return entities;
    }

    public boolean isGraphicallyHighlighted() {

        return isGraphicallyHighlighted;
    }

    public void isGraphicallyHighlighted(boolean isGraphicallyHighlighted) {

        this.isGraphicallyHighlighted = isGraphicallyHighlighted;
    }

    public String toString() {

        return "Type : " + type + ", Entities : " + entities;
    }
}
