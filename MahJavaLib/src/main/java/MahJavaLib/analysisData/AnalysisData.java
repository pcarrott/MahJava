package MahJavaLib.analysisData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisData {
    Map<String, Double> _eagerWins = new HashMap<>();
    Map<String, Double> _mixedWins = new HashMap<>();
    Map<String, Double> _concealedWins = new HashMap<>();
    Map<String, Double> _composedWins = new HashMap<>();

    Map<String, Double> _eagerPoints = new HashMap<>();
    Map<String, Double> _mixedPoints = new HashMap<>();
    Map<String, Double> _concealedPoints = new HashMap<>();
    Map<String, Double> _composedPoints = new HashMap<>();

    Map<String, Double> _numberOfMaxScorePlays = new HashMap<>();

    public Map<String, Double> getEagerWins() {
        return this._eagerWins;
    }

    public Map<String, Double> getMixedWins() {
        return this._mixedWins;
    }

    public Map<String, Double> getConcealedWins() {
        return this._concealedWins;
    }

    public Map<String, Double> getComposedWins() {
        return this._composedWins;
    }

    public Map<String, Double> getEagerPoints() {
        return _eagerPoints;
    }

    public Map<String, Double> getMixedPoints() {
        return _mixedPoints;
    }

    public Map<String, Double> getConcealedPoints() {
        return _concealedPoints;
    }

    public Map<String, Double> getComposedPoints() {
        return _composedPoints;
    }
    
    public Map<String, Double> getNumberOfMaxScorePlays() {
        return _numberOfMaxScorePlays;
    }

    public Map<String, Double> getWinsOf(String str) throws IllegalStateException {
        switch(str) {
            case "EAGER":
                return this._eagerWins; 
            case "MIXED":
                return this._mixedWins;
            case "CONCEALED":
                return this._concealedWins;
            case "COMPOSED":
                return this._composedWins;
            default:
                // This will never happen
                throw new IllegalStateException("Unexpected data point: " + str + " wins");
        }
    }

    public Map<String, Double> getPointsOf(String str) {
        switch(str) {
            case "EAGER":
                return this._eagerPoints; 
            case "MIXED":
                return this._mixedPoints;
            case "CONCEALED":
                return this._concealedPoints;
            case "COMPOSED":
                return this._composedPoints;
            default:
                // This will never happen
                throw new IllegalStateException("Unexpected data point: " + str + " points");
        }
    }

    public void printWinsData() {
        System.out.println("EAGER: " + this._eagerWins.entrySet());
        System.out.println("MIXED: " + this._mixedWins.entrySet());
        System.out.println("CONCEALED: " + this._concealedWins.entrySet());
        System.out.println("COMPOSED: " + this._composedWins.entrySet());
    }
    
    public void printPointsData() {
        System.out.println("EAGER: " + this._eagerPoints.entrySet());
        System.out.println("MIXED: " + this._mixedPoints.entrySet());
        System.out.println("CONCEALED: " + this._concealedPoints.entrySet());
        System.out.println("COMPOSED: " + this._composedPoints.entrySet());
    }

    public AnalysisData() {
        this._eagerWins.put("EAGER",0.0);
        this._eagerWins.put("MIXED",0.0);
        this._eagerWins.put("CONCEALED",0.0);
        this._eagerWins.put("COMPOSED",0.0);
        this._eagerWins.put("TOTAL",0.0);

        this._numberOfMaxScorePlays = new HashMap<String, Double>(this._eagerWins);

        this._eagerWins.put("DRAW",0.0);

        this._mixedWins = new HashMap<String, Double>(this._eagerWins);
        this._concealedWins = new HashMap<String, Double>(this._eagerWins);
        this._composedWins = new HashMap<String, Double>(this._eagerWins);

        this._eagerPoints = new HashMap<String, Double>(this._eagerWins);
        this._mixedPoints = new HashMap<String, Double>(this._eagerWins);
        this._concealedPoints = new HashMap<String, Double>(this._eagerWins);
        this._composedPoints = new HashMap<String, Double>(this._eagerWins);
    }

    public void combineAnalysisData(AnalysisData data) {
        // Combine Wins data
        data.getEagerWins().forEach(
            (key, value) -> this._eagerWins.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getMixedWins().forEach(
            (key, value) -> this._mixedWins.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getConcealedWins().forEach(
            (key, value) -> this._concealedWins.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getComposedWins().forEach(
            (key, value) -> this._composedWins.merge( key, value, (v1, v2) -> v1 + v2)
        );

        // Combine Points data
        data.getEagerPoints().forEach(
            (key, value) -> this._eagerPoints.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getMixedPoints().forEach(
            (key, value) -> this._mixedPoints.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getConcealedPoints().forEach(
            (key, value) -> this._concealedPoints.merge( key, value, (v1, v2) -> v1 + v2)
        );
        data.getComposedPoints().forEach(
            (key, value) -> this._composedPoints.merge( key, value, (v1, v2) -> v1 + v2)
        );

        // Combine Max Score data
        data.getNumberOfMaxScorePlays().forEach(
            (key, value) -> this._numberOfMaxScorePlays.merge( key, value, (v1, v2) -> v1 + v2)
        );
    }

    public void turnDataIntoAverages(int totalGamesPlayed) {
        System.out.println("TESTING");
        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        double numberOfMaxScorePlaysTotal = this._numberOfMaxScorePlays.get("TOTAL");

        for(String key : keys) {
            double totalWinsForKey = this.getWinsOf(key).get("TOTAL");
            for(Map.Entry<String,Double> entry : this.getWinsOf(key).entrySet()) {
                if(entry.getKey() == "TOTAL") {
                    entry.setValue(totalWinsForKey/totalGamesPlayed);
                }
                else {
                    entry.setValue(entry.getValue()/totalWinsForKey);
                }
            }  

            double totalPointsForKey = this.getPointsOf(key).get("TOTAL");
            for(Map.Entry<String,Double> entry : this.getPointsOf(key).entrySet()) {
                if(entry.getKey() == "TOTAL") {
                    entry.setValue(totalPointsForKey/totalGamesPlayed);
                }
                else {
                    entry.setValue(entry.getValue()/totalPointsForKey);
                }
            }
        
            this._numberOfMaxScorePlays.replace(key,this._numberOfMaxScorePlays.get(key).intValue()/numberOfMaxScorePlaysTotal);
        }
        this._numberOfMaxScorePlays.replace("TOTAL",numberOfMaxScorePlaysTotal/totalGamesPlayed);
        System.out.println("TESTING");
    }
}
