package main.fr.epsi.gravarmor.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Unit> listEntity;


    public Team(String name, List<Unit> listEntity) {
        this.name = name;
        this.listEntity = listEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Unit> getListEntity() {
        return listEntity;
    }

    public void setListEntity(List<Unit> listEntity) {
        this.listEntity = listEntity;
    }

}
