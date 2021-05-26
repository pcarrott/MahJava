package MahJavaLib.analysisData;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisData {
    Map<String, List<Integer>> _eagerWins = new HashMap<>();
    Map<String, List<Integer>> _mixedWins = new HashMap<>();
    Map<String, List<Integer>> _concealedWins = new HashMap<>();
    Map<String, List<Integer>> _composedWins = new HashMap<>();

    Map<String, List<Integer>> _eagerPoints = new HashMap<>();
    Map<String, List<Integer>> _mixedPoints = new HashMap<>();
    Map<String, List<Integer>> _concealedPoints = new HashMap<>();
    Map<String, List<Integer>> _composedPoints = new HashMap<>();

    Map<String, List<Integer>> _numberOfMaxScorePlays = new HashMap<>();

    public Map<String, List<Integer>> getEagerWins() {
        return this._eagerWins;
    }

    public Map<String, List<Integer>> getMixedWins() {
        return this._mixedWins;
    }

    public Map<String, List<Integer>> getConcealedWins() {
        return this._concealedWins;
    }

    public Map<String, List<Integer>> getComposedWins() {
        return this._composedWins;
    }

    public Map<String, List<Integer>> getEagerPoints() {
        return _eagerPoints;
    }

    public Map<String, List<Integer>> getMixedPoints() {
        return _mixedPoints;
    }

    public Map<String, List<Integer>> getConcealedPoints() {
        return _concealedPoints;
    }

    public Map<String, List<Integer>> getComposedPoints() {
        return _composedPoints;
    }
    
    public Map<String, List<Integer>> getNumberOfMaxScorePlays() {
        return _numberOfMaxScorePlays;
    }

    public Map<String, List<Integer>> getWinsOf(String str) throws IllegalStateException {
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

    public Map<String, List<Integer>> getPointsOf(String str) {
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
        Map<String,Double> eagerWins = new HashMap<>();
        Map<String,Double> mixedWins = new HashMap<>();
        Map<String,Double> concealedWins = new HashMap<>();
        Map<String,Double> composedWins = new HashMap<>();


        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL", "DRAW");
        for(String key : keys) {
            eagerWins.put(key, 0.0);
        }
        for(String key : keys) {
            mixedWins.put(key, 0.0);
        }

        for(String key : keys) {
            concealedWins.put(key, 0.0);
        }

        for(String key : keys) {
            composedWins.put(key, 0.0);
        }

        Map<String,Map<String,Double>> winsMap = new HashMap<>();
        winsMap.put("EAGER",eagerWins);
        winsMap.put("MIXED",mixedWins);
        winsMap.put("CONCEALED",concealedWins);
        winsMap.put("COMPOSED",composedWins);

        keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        List<Integer> list = new ArrayList<>();

        for(String key : keys) {
            for(Map.Entry<String, List<Integer>> entry : this.getWinsOf(key).entrySet()) {
                list = entry.getValue();
                for(Integer i : list) {
                    winsMap.get(key).replace( entry.getKey(), winsMap.get(key).get(entry.getKey()) + i );
                }
            }      
        }

        
        System.out.println("EAGER: " + eagerWins.entrySet());
        System.out.println("MIXED: " + mixedWins.entrySet());
        System.out.println("CONCEALED: " + concealedWins.entrySet());
        System.out.println("COMPOSED: " + composedWins.entrySet());

        this.writeToCSV(this._eagerWins, "eagerWins");
        this.writeToCSV(this._mixedWins, "mixedWins");
        this.writeToCSV(this._concealedWins, "concealedWins");
        this.writeToCSV(this._composedWins, "composedWins");
    }
    
    public void printPointsData() {
        Map<String,Double> eagerPoints = new HashMap<>();
        Map<String,Double> mixedPoints = new HashMap<>();
        Map<String,Double> concealedPoints = new HashMap<>();
        Map<String,Double> composedPoints = new HashMap<>();


        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL", "DRAW");
        for(String key : keys) {
            eagerPoints.put(key, 0.0);
        }
        for(String key : keys) {
            mixedPoints.put(key, 0.0);
        }

        for(String key : keys) {
            concealedPoints.put(key, 0.0);
        }

        for(String key : keys) {
            composedPoints.put(key, 0.0);
        }

        Map<String,Map<String,Double>> pointsMap = new HashMap<>();
        pointsMap.put("EAGER",eagerPoints);
        pointsMap.put("MIXED",mixedPoints);
        pointsMap.put("CONCEALED",concealedPoints);
        pointsMap.put("COMPOSED",composedPoints);

        keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        List<Integer> list = new ArrayList<>();

        for(String key : keys) {
            for(Map.Entry<String, List<Integer>> entry : this.getPointsOf(key).entrySet()) {
                list = entry.getValue();
                for(Integer i : list) {
                    pointsMap.get(key).replace( entry.getKey(), pointsMap.get(key).get(entry.getKey()) + i );
                }
            }      
        }

        System.out.println("EAGER: " + eagerPoints.entrySet());
        System.out.println("MIXED: " + mixedPoints.entrySet());
        System.out.println("CONCEALED: " + concealedPoints.entrySet());
        System.out.println("COMPOSED: " + composedPoints.entrySet());

        this.writeToCSV(this._eagerPoints, "eagerPoints");
        this.writeToCSV(this._mixedPoints, "mixedPoints");
        this.writeToCSV(this._concealedPoints, "concealedPoints");
        this.writeToCSV(this._composedPoints, "composedPoints");
    }

    public void printNumberOfMaxScorePlays() {
        Map<String,Double> numberOfMaxScorePlays = new HashMap<>();


        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL");
        for(String key : keys) {
            numberOfMaxScorePlays.put(key, 0.0);
        }

        keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        List<Integer> list = new ArrayList<>();

        for(Map.Entry<String, List<Integer>> entry : this.getNumberOfMaxScorePlays().entrySet()) {
            list = entry.getValue();
            for(Integer i : list) {
                numberOfMaxScorePlays.replace(entry.getKey(), numberOfMaxScorePlays.get(entry.getKey()) + i);
            }
        }

        System.out.println(numberOfMaxScorePlays.entrySet());
        this.writeToCSV(this._numberOfMaxScorePlays, "numberOfMaxScorePlays");
    }

    public void printDataCalculations(int totalGamesPlayed) {
        Map<String,Double> eagerWins = new HashMap<>();
        Map<String,Double> mixedWins = new HashMap<>();
        Map<String,Double> concealedWins = new HashMap<>();
        Map<String,Double> composedWins = new HashMap<>();

        Map<String,Double> eagerPoints = new HashMap<>();
        Map<String,Double> mixedPoints = new HashMap<>();
        Map<String,Double> concealedPoints = new HashMap<>();
        Map<String,Double> composedPoints = new HashMap<>();

        Map<String,Double> numberOfMaxScorePlays = new HashMap<>();

        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL", "DRAW");
        
        for(String key : keys) {
            eagerWins.put(key, 0.0);
            eagerPoints.put(key, 0.0);
            numberOfMaxScorePlays.put(key, 0.0);
        }
        numberOfMaxScorePlays.remove("DRAW");

        for(String key : keys) {
            mixedWins.put(key, 0.0);
            mixedPoints.put(key, 0.0);
        }

        for(String key : keys) {
            concealedWins.put(key, 0.0);
            concealedPoints.put(key, 0.0);
        }

        for(String key : keys) {
            composedWins.put(key, 0.0);
            composedPoints.put(key, 0.0);
        }

        Map<String,Map<String,Double>> winsMap = new HashMap<>();
        winsMap.put("EAGER",eagerWins);
        winsMap.put("MIXED",mixedWins);
        winsMap.put("CONCEALED",concealedWins);
        winsMap.put("COMPOSED",composedWins);

        Map<String,Map<String,Double>> pointsMap = new HashMap<>();
        pointsMap.put("EAGER",eagerPoints);
        pointsMap.put("MIXED",mixedPoints);
        pointsMap.put("CONCEALED",concealedPoints);
        pointsMap.put("COMPOSED",composedPoints);

        keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");
        List<Integer> list = new ArrayList<>();
        double totalWinsForKey = 0;
        double totalPointsForKey = 0;
        double totalNumberOfMaxScorePlays = 0;

        for(String key : keys) {
            for(Integer j : this.getWinsOf(key).get("TOTAL"))  {
                winsMap.get(key).replace( "TOTAL", winsMap.get(key).get("TOTAL") + j );
            }
            totalWinsForKey = winsMap.get(key).get("TOTAL").doubleValue();
            for(Map.Entry<String, List<Integer>> entry : this.getWinsOf(key).entrySet()) {
                if(entry.getKey() == "TOTAL") {
                    winsMap.get(key).replace( entry.getKey(), Math.round((totalWinsForKey/totalGamesPlayed)*1000.0)/1000.0 );
                    continue;
                }
                else {
                    list = entry.getValue();
                    for(Integer i : list) {
                        winsMap.get(key).replace( entry.getKey(), winsMap.get(key).get(entry.getKey()) + i );
                    }
                    if(totalWinsForKey > 0) {
                        winsMap.get(key).replace( entry.getKey(), Math.round((winsMap.get(key).get(entry.getKey())/totalWinsForKey)*1000.0)/1000.0 );
                    }
                }
            }      
        }

        for(String key : keys) {
            for(Integer j : this.getPointsOf(key).get("TOTAL"))  {
                pointsMap.get(key).replace( "TOTAL", pointsMap.get(key).get("TOTAL") + j );
            }
            totalPointsForKey = pointsMap.get(key).get("TOTAL").doubleValue();
            for(Map.Entry<String, List<Integer>> entry : this.getPointsOf(key).entrySet()) {
                if(entry.getKey() == "TOTAL") {
                    pointsMap.get(key).replace( entry.getKey(), Math.round((totalPointsForKey/totalGamesPlayed)*1000.0)/1000.0 );
                    continue;
                }
                else {
                    list = entry.getValue();
                    for(Integer i : list) {
                        pointsMap.get(key).replace( entry.getKey(), pointsMap.get(key).get(entry.getKey()) + i );
                    }
                    if(totalPointsForKey > 0) {
                        pointsMap.get(key).replace( entry.getKey(), Math.round((pointsMap.get(key).get(entry.getKey())/totalPointsForKey)*1000.0)/1000.0);
                    }
                    
                }
            }      
        }

        for(Integer j : this.getNumberOfMaxScorePlays().get("TOTAL"))  {
            numberOfMaxScorePlays.replace( "TOTAL", numberOfMaxScorePlays.get("TOTAL") + j );
        }
        totalNumberOfMaxScorePlays = numberOfMaxScorePlays.get("TOTAL").doubleValue();
        for(Map.Entry<String, List<Integer>> entry : this.getNumberOfMaxScorePlays().entrySet()) {
            if(entry.getKey() == "TOTAL") {
                numberOfMaxScorePlays.replace( "TOTAL", Math.round((numberOfMaxScorePlays.get("TOTAL")/totalGamesPlayed)*1000.0)/1000.0 );
                continue;
            }            
            else {
                list = entry.getValue();
                for(Integer i : list) {
                    numberOfMaxScorePlays.replace(entry.getKey(), numberOfMaxScorePlays.get(entry.getKey()) + i);
                }
                if(totalNumberOfMaxScorePlays > 0) {
                    numberOfMaxScorePlays.replace(entry.getKey(), Math.round((numberOfMaxScorePlays.get(entry.getKey())/totalNumberOfMaxScorePlays)*1000.0)/1000.0);
                }
            }
        }

        System.out.println("------------ Average ------------");
        System.out.println("Calculated Average Wins Data:");
        System.out.println("EAGER: " + eagerWins.entrySet());
        System.out.println("MIXED: " + mixedWins.entrySet());
        System.out.println("CONCEALED: " + concealedWins.entrySet());
        System.out.println("COMPOSED: " + composedWins.entrySet());

        System.out.println("Calculated Average Points Data:");
        System.out.println("EAGER: " + eagerPoints.entrySet());
        System.out.println("MIXED: " + mixedPoints.entrySet());
        System.out.println("CONCEALED: " + concealedPoints.entrySet());
        System.out.println("COMPOSED: " + composedPoints.entrySet());

        System.out.println("Calculated Average Number of Max Score Plays");
        System.out.println(numberOfMaxScorePlays.entrySet());

        // Onto calculating the variance

        double sum = 0.0;
        double mean = 0.0;
        double variance = 0.0;
        for(String key : keys) {
            sum = 0;
            for(Integer j : this.getWinsOf(key).get("TOTAL"))  {
                sum += j;
            }
            totalWinsForKey = sum;
            for(Map.Entry<String, List<Integer>> entry : this.getWinsOf(key).entrySet()) {
                list = entry.getValue();
                sum = 0;
                mean = winsMap.get(key).get(entry.getKey()).doubleValue();
                for(Integer xi : list) {
                    sum += Math.pow(xi - mean, 2);
                }
                if(entry.getKey() == "TOTAL") {
                    variance = Math.sqrt(sum/(totalGamesPlayed));
                    winsMap.get(key).replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0 );
                }
                else if(totalWinsForKey > 0) {
                    variance = Math.sqrt(sum/(totalWinsForKey));
                    winsMap.get(key).replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0);
                }
            }    
        }

        for(String key : keys) {
            sum = 0;
            for(Integer j : this.getPointsOf(key).get("TOTAL"))  {
                sum += j;
            }
            totalPointsForKey = sum;
            for(Map.Entry<String, List<Integer>> entry : this.getPointsOf(key).entrySet()) {
                list = entry.getValue();
                sum = 0;
                mean = pointsMap.get(key).get(entry.getKey()).doubleValue();
                for(Integer xi : list) {
                    sum += Math.pow(xi - mean, 2);

                }
                if(entry.getKey() == "TOTAL") {
                    variance = Math.sqrt(sum/(totalGamesPlayed));
                    pointsMap.get(key).replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0 );
                }
                else if(totalPointsForKey > 0) {
                    variance = Math.sqrt(sum/(totalPointsForKey));
                    pointsMap.get(key).replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0 );
                }
            }
        }

        sum = 0;
        for(Integer j : this.getNumberOfMaxScorePlays().get("TOTAL"))  {
            sum += j;
        }
        totalNumberOfMaxScorePlays = sum;
        for(Map.Entry<String, List<Integer>> entry : this.getNumberOfMaxScorePlays().entrySet()) {
            list = entry.getValue();
            sum = 0;
            mean = numberOfMaxScorePlays.get(entry.getKey()).doubleValue();
            for(Integer xi : list) {
                sum += Math.pow(xi - mean, 2);
            }
            if(entry.getKey() == "TOTAL") {
                variance = Math.sqrt(sum/(totalGamesPlayed));
                numberOfMaxScorePlays.replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0 );
            }
            else if(totalNumberOfMaxScorePlays > 0) {
                variance = Math.sqrt(sum/(totalNumberOfMaxScorePlays));
                numberOfMaxScorePlays.replace( entry.getKey(), Math.round((variance)*1000.0)/1000.0 );
            }
        }

        System.out.println("------------ Variance ------------");
        System.out.println("Calculated Variance Wins Data:");
        System.out.println("EAGER: " + eagerWins.entrySet());
        System.out.println("MIXED: " + mixedWins.entrySet());
        System.out.println("CONCEALED: " + concealedWins.entrySet());
        System.out.println("COMPOSED: " + composedWins.entrySet());

        System.out.println("Calculated Variance Points Data:");
        System.out.println("EAGER: " + eagerPoints.entrySet());
        System.out.println("MIXED: " + mixedPoints.entrySet());
        System.out.println("CONCEALED: " + concealedPoints.entrySet());
        System.out.println("COMPOSED: " + composedPoints.entrySet());

        System.out.println("Calculated Variance Number of Max Score Plays");
        System.out.println(numberOfMaxScorePlays.entrySet());
    }

    public void combineAnalysisData(AnalysisData data) {
        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED");

        for(String key : keys) {
            // Combine Win Data
            for(Map.Entry<String, List<Integer>> entry : this.getWinsOf(key).entrySet()) {
                entry.getValue().addAll( data.getWinsOf(key).get(entry.getKey()) );
            }  

            // Combine Point Data
            for(Map.Entry<String, List<Integer>> entry : this.getPointsOf(key).entrySet()) {
                entry.getValue().addAll( data.getPointsOf(key).get(entry.getKey()) );
            }     
        }

        // Combine Max Score Data
        for(Map.Entry<String, List<Integer>> entry : this.getNumberOfMaxScorePlays().entrySet()) {
            entry.getValue().addAll( data.getNumberOfMaxScorePlays().get(entry.getKey()) );
        }   
    }

    public void writeToCSV(Map<String,List<Integer>> map, String filename) {
        String str = null;

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("csv_files/" + filename + ".csv"), "utf-8"))) {
            for(Map.Entry<String,List<Integer>> entry : map.entrySet()) {
                str = entry.getKey() + ",";
                for(Integer i : entry.getValue()) {
                    str += i + ",";
                }
                str = str.substring(0, str.length()-1);
                writer.write(str);
                writer.write("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public AnalysisData() {
        List<String> keys = Arrays.asList("EAGER", "COMPOSED", "CONCEALED", "MIXED", "TOTAL", "DRAW");
        for(String key : keys) {
            this._eagerWins.put(key, new ArrayList<>());
            this._eagerPoints.put(key, new ArrayList<>());
            this._numberOfMaxScorePlays.put(key, new ArrayList<>());
        }
        this._numberOfMaxScorePlays.remove("DRAW");

        for(String key : keys) {
            this._mixedWins.put(key, new ArrayList<>());
            this._mixedPoints.put(key, new ArrayList<>());
        }

        for(String key : keys) {
            this._concealedWins.put(key, new ArrayList<>());
            this._concealedPoints.put(key, new ArrayList<>());
        }

        for(String key : keys) {
            this._composedWins.put(key, new ArrayList<>());
            this._composedPoints.put(key, new ArrayList<>());
        }
    }
}
