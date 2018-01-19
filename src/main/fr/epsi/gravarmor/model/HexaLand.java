package main.fr.epsi.gravarmor.model;

import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public class HexaLand {

    private static final char MAP[][] = {
            { 'H', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'H' },
            { '0', 'F', 'F', 'S', 'W', 'S', 'M', 'M', 'S', 'F', 'W', 'W', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'W', 'S', 'F', 'M', '0' },
            { '0', 'Q', 'Q', 'F', 'F', 'S', 'F', 'Q', 'S', 'F', 'W', 'W', 'W', 'W', 'W', 'S', 'S', 'Q', 'S', 'S', 'S', 'S', 'M', 'S', '0' },
            { '0', 'Q', 'S', 'S', 'S', 'S', 'Q', 'M', 'Q', 'W', 'W', 'W', 'W', 'W', 'W', 'Q', 'Q', 'F', 'F', 'S', 'S', 'S', 'S', 'S', '0' },
            { '0', 'S', 'S', 'S', 'S', 'Q', 'S', 'S', 'S', 'S', 'W', 'W', 'W', 'S', 'S', 'Q', 'S', 'S', 'Q', 'S', 'S', 'S', 'M', 'S', '0' },
            { '0', 'Q', 'Q', 'S', 'S', 'Q', 'S', 'S', 'S', 'W', 'W', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'M', 'Q', 'Q', '0' },
            { '0', 'S', 'S', 'Q', 'S', 'S', 'S', 'S', 'S', 'W', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'F', 'Q', '0' },
            { '0', 'Q', 'S', 'S', 'S', 'S', 'F', 'S', 'S', 'F', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'F', 'S', 'S', 'F', 'Q', '0' },
            { '0', 'Q', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'F', 'W', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'F', 'Q', 'S', 'S', 'Q', 'S', '0' },
            { '0', 'S', 'Q', 'S', 'S', 'S', 'S', 'S', 'S', 'F', 'W', 'F', 'W', 'W', 'S', 'F', 'S', 'S', 'S', 'Q', 'Q', 'S', 'S', 'F', '0' },
            { '0', 'F', 'S', 'S', 'S', 'S', 'S', 'S', 'Q', 'S', 'W', 'W', 'W', 'W', 'W', 'Q', 'Q', 'Q', 'S', 'Q', 'S', 'S', 'Q', 'Q', '0' },
            { '1', 'F', 'Q', 'S', 'S', 'S', 'S', 'Q', 'S', 'W', 'W', 'W', 'W', 'W', 'F', 'S', 'S', 'Q', 'S', 'Q', 'Q', 'W', 'Q', 'S', '0' },
            { '0', 'S', 'S', 'W', 'S', 'W', 'S', 'S', 'S', 'S', 'W', 'W', 'W', 'W', 'Q', 'S', 'S', 'S', 'Q', 'W', 'W', 'W', 'W', 'S', '0' },
            { '0', 'Q', 'F', 'M', 'W', 'W', 'W', 'W', 'S', 'S', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'F', 'Q', 'F', 'Q', 'S', 'S', '0' },
            { '0', 'Q', 'Q', 'M', 'W', 'W', 'W', 'W', 'S', 'S', 'Q', 'S', 'S', 'S', 'Q', 'S', 'S', 'F', 'F', 'Q', 'Q', 'F', 'S', 'Q', '0' },
            { '0', 'S', 'F', 'M', 'W', 'W', 'W', 'S', 'Q', 'Q', 'W', 'S', 'S', 'S', 'S', 'Q', 'Q', 'F', 'F', 'Q', 'F', 'S', 'S', 'S', '0' },
            { '0', 'S', 'M', 'W', 'W', 'W', 'W', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'Q', 'S', 'Q', 'F', 'F', 'Q', 'S', 'S', 'Q', '0' },
            { '0', 'W', 'W', 'W', 'W', 'Q', 'S', 'S', 'S', 'S', 'S', 'S', 'Q', 'S', 'S', 'S', 'S', 'F', 'Q', 'Q', 'Q', 'S', 'S', 'S', '0' },
            { '0', 'W', 'W', 'W', 'W', 'S', 'Q', 'S', 'S', 'S', 'Q', 'F', 'Q', 'Q', 'S', 'S', 'S', 'M', 'M', 'F', 'Q', 'S', 'S', 'S', '0' },
            { '0', 'W', 'W', 'Q', 'Q', 'S', 'S', 'S', 'S', 'S', 'F', 'M', 'F', 'M', 'S', 'S', 'S', 'S', 'F', 'Q', 'S', 'S', 'S', 'S', '0' },
            { '0', 'Q', 'W', 'Q', 'Q', 'Q', 'S', 'S', 'S', 'Q', 'Q', 'Q', 'M', 'M', 'S', 'Q', 'S', 'Q', 'S', 'F', 'S', 'F', 'S', 'S', '0' },
            { '0', 'F', 'F', 'Q', 'F', 'F', 'S', 'S', 'S', 'Q', 'Q', 'M', 'Q', 'F', 'S', 'S', 'S', 'Q', 'Q', 'F', 'S', 'S', 'S', 'S', '0' },
            { '0', 'Q', 'F', 'F', 'M', 'S', 'S', 'Q', 'Q', 'Q', 'Q', 'M', 'Q', 'M', 'M', 'S', 'Q', 'W', 'F', 'S', 'S', 'S', 'S', 'M', '0' },
            { '0', 'Q', 'Q', 'Q', 'M', 'S', 'S', 'Q', 'S', 'Q', 'Q', 'M', 'F', 'F', 'S', 'S', 'S', 'W', 'W', 'W', 'S', 'F', 'F', 'M', '0' },
            { '0', '0', 'Q', '0', 'F', '0', 'S', '0', 'S', '0', 'M', '0', 'W', '0', 'Q', '0', 'S', '0', 'W', '0', 'W', '0', 'M', '0', '0' },
            { 'H', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', '0', 'H', 'H' }
    };

    private LandBox land[][];

    public HexaLand() {

        land = new LandBox[MAP.length][MAP[0].length];
        for(int y = 0; y < MAP.length; y++) {
            for(int x = 0; x < MAP[y].length; x++) {

                land[y][x] = new LandBox(BoxType.fromChar(MAP[y][x]));
            }
        }
    }

    public LandBox getBox(HexaCoordinates coordinates) {

        return land[coordinates.getPoint().getY()][coordinates.getPoint().getX()];
    }

    public int getHeight() {

        return MAP.length;
    }

    public int getWidth() {

        return MAP[0].length;
    }

    public boolean moveEntity(Entity entity, HexaCoordinates to){
        LandBox fromBox = getBox(entity.getCoordinates());
        LandBox toBox = getBox(to);

        if(fromBox.getEntities().remove(entity)) {
            toBox.getEntities().add(entity);
            entity.setCoordinates(to);
            return true;
        }

        return false;
    }
}
