package MahJavaLib;

import MahJavaLib.analysisData.AnalysisData;
import MahJavaLib.game.Game;
import MahJavaLib.player.Player;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TestGame {

    public Game game = null;
    public AnalysisData data = new AnalysisData();

    @BeforeMethod
    public void methodSetUp() {
    }

    @AfterMethod
    public void methodTearDown() {
        data.combineAnalysisData(game.getAnalysisData());
    }

    @AfterSuite
    public void suiteTearDown() {
        System.out.println("----------- RESULTS -----------");
        System.out.println("Win Data: ");
        data.printWinsData();
        System.out.println("Point Data: ");
        data.printPointsData();
        System.out.println("Max Score Data: ");
        data.printNumberOfMaxScorePlays();

        System.out.println("----------- RESULTING CALCULATIONS -----------");
        data.printDataCalculations(10);
    }

    @DataProvider
    private Object[][] allPlayerMatchUps() {
        return new Object[][]{
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED"),Player.MixedPlayer("MIXED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.EagerPlayer("EAGER")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ComposedPlayer("COMPOSED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.ConcealedPlayer("CONCEALED")},
                {Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED"),Player.MixedPlayer("MIXED")}};
    }

    
    @Test(dataProvider = "allPlayerMatchUps", invocationCount=10)
    public void testAllPlayerMatchUps(Player p1, Player p2, Player p3, Player p4) {
        List<Player> players = Arrays.asList(p1,p2,p3,p4);

        game = new Game(players);
        game.gameLoop();
    }

    @Test(invocationCount=10)
    public void testMain() {
        //String[] names = {"Leonardo", "Raphael", "Donatello", "Michelangelo"};
        String[] names = {"EAGER", "COMPOSED", "CONCEALED", "MIXED"};
        List<Player> players = Arrays.asList(
                Player.EagerPlayer(names[0]),
                Player.ComposedPlayer(names[1]),
                Player.ConcealedPlayer(names[2]),
                Player.MixedPlayer(names[3])
        );

        game = new Game(players);
        game.gameLoop();
    }

    @Test
    public void testAnalysisData() {
        AnalysisData analysisData = new AnalysisData();
        AnalysisData analysisData1 = new AnalysisData();

        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        List<String> entries = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL", "DRAW");

        for(String key : keys) {
            for(String entry : entries) {
                analysisData.getWinsOf(key).get(entry).add(1);
                analysisData.getPointsOf(key).get(entry).add(1);
            }
        }
        for(String key : keys) {
            for(String entry : entries) {
                analysisData1.getWinsOf(key).get(entry).add(1);
                analysisData1.getPointsOf(key).get(entry).add(1);
            }
        }

        analysisData.getNumberOfMaxScorePlays().get("EAGER").add(1);
        analysisData1.getNumberOfMaxScorePlays().get("MIXED").add(1);

        analysisData.combineAnalysisData(analysisData1);
        
        System.out.println("Win Data: ");
        analysisData.printWinsData();
        System.out.println("Point Data: ");
        analysisData.printPointsData();
        System.out.println("Max Score Data: ");
        analysisData.printNumberOfMaxScorePlays();
    }
}