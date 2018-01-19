package test.fr.epsi.gravarmor.circleci;

import main.fr.epsi.gravarmor.model.BoxType;
import main.fr.epsi.gravarmor.model.LandBox;
import main.fr.epsi.gravarmor.model.Team;
import org.junit.Assert;

import java.util.ArrayList;

public class UnitTest {

    @org.junit.jupiter.api.Test
    public void testTeamName() {

        String teamName1 = "MaSuperTeam1";
        String teamName2 = "MaSuperTeam2";
        String teamName3 = "MaSuperTeam3";

        Team team1 = new Team(teamName1, new ArrayList<>());
        Team team2 = new Team(teamName2, new ArrayList<>());
        Team team3 = new Team(teamName3, new ArrayList<>());

        Assert.assertEquals(teamName1, team1.getName());
        Assert.assertEquals(teamName2, team2.getName());
        Assert.assertEquals(teamName3, team3.getName());

        Assert.assertNotEquals(team1.getName(), team2.getName());
        Assert.assertNotEquals(team2.getName(), team3.getName());
        Assert.assertNotEquals(team3.getName(), team1.getName());
    }

    @org.junit.jupiter.api.Test
    public void testBoxType() {

        BoxType boxType1 = BoxType.WATER;
        BoxType boxType2 = BoxType.HIDDEN;
        BoxType boxType3 = BoxType.QUICKSAND;

        LandBox landBox1 = new LandBox(boxType1);
        LandBox landBox2 = new LandBox(boxType2);
        LandBox landBox3 = new LandBox(boxType3);

        Assert.assertEquals(boxType1, landBox1.getType());
        Assert.assertEquals(boxType2, landBox2.getType());
        Assert.assertEquals(boxType3, landBox3.getType());

        Assert.assertNotEquals(landBox1.getType(), landBox2.getType());
        Assert.assertNotEquals(landBox2.getType(), landBox3.getType());
        Assert.assertNotEquals(landBox3.getType(), landBox1.getType());
    }
}