package main.fr.epsi.gravarmor.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Entity> listEntity  = new ArrayList<Entity>();


    public Team(String name, List<Entity> listEntity) {
        this.name = name;
        this.listEntity = listEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getListEntity() {
        return listEntity;
    }

    public void setListEntity(List<Entity> listEntity) {
        this.listEntity = listEntity;
    }
}
