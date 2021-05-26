package MahJava;

import MahJava.analysisData.AnalysisData;
import MahJava.game.Game;
import MahJava.player.Player;
import org.testng.annotations.*;

import java.util.*;

public class TestGame {

    public Game game = null;
    Map<Player, Integer> scores = null;
    public AnalysisData data = new AnalysisData();
    public Map<String, List<Integer>> pointsPerGame = new HashMap<>();
    List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");

    @BeforeMethod
    public void methodSetUp() {
    }

    @BeforeSuite
    public void suiteSetUp() {
        for(String key : keys) {
            this.pointsPerGame.put(key, new ArrayList<>());
        }
    }

    @AfterMethod
    public void methodTearDown() {
        data.combineAnalysisData(game.getAnalysisData());
        scores = game.getScores();
        for(String key : keys) {
            for(Map.Entry<Player,Integer> entry : scores.entrySet()) {
                if(entry.getKey().getName() == key) {
                    this.pointsPerGame.get(key).add(entry.getValue());
                }
            }
        }
    }

    @AfterSuite
    public void suiteTearDown() {
        //prints also write to csv
        System.out.println("----------- RESULTS -----------");
        System.out.println("Win Data: ");
        data.printWinsData();
        System.out.println("Point Data: ");
        data.printPointsData();
        System.out.println("Max Score Data: ");
        data.printNumberOfMaxScorePlays();

        System.out.println("----------- RESULTING CALCULATIONS -----------");
        data.printDataCalculations(1);
        System.out.println("----------- List of All points per Game -----------");
        for(Map.Entry<String,List<Integer>> entry : this.pointsPerGame.entrySet()) {
            System.out.println(entry);
        }
        data.writeToCSV(this.pointsPerGame, "totalPointsPerGame");
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

    
    @Test(dataProvider = "allPlayerMatchUps", invocationCount=1)
    public void testAllPlayerMatchUps(Player p1, Player p2, Player p3, Player p4) {
        List<Player> players = Arrays.asList(p1,p2,p3,p4);

        game = new Game(players);
        game.gameLoop();
    }

    @Test(invocationCount=1)
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