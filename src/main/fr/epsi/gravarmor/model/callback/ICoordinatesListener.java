package main.fr.epsi.gravarmor.model.callback;

import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public interface ICoordinatesListener {

    public void handleEvent(HexaCoordinates coordinates);
}
