package main.fr.epsi.gravarmor.model.callback;

import main.fr.epsi.gravarmor.model.Entity;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public interface ICoordinatesEntityListener {

    void handleEvent(HexaCoordinates coordinates, Entity entity);
}
