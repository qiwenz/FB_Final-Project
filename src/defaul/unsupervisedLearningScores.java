package defaul;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class unsupervisedLearningScores {
  public HashMap<Integer, List<Pair<Double, Double>>> Clusters = new HashMap<Integer, List<Pair<Double, Double>>>();

  public void returnClusterScores(HashMap<String, Integer> query,
          List<HashMap<String, Integer>> answerDictionary) {
    createClusters(query, answerDictionary);
    Iterator<Integer> it = Clusters.keySet().iterator();
    System.out.println("\n================================");
    System.out.println("Learning the score from kNN Clustering Algorithm\n");
    while (it.hasNext()) {
      int key = it.next();
      System.out.println("Final Score from kNN Clustering: " + getScoreforCluster(key));
    }
  }

  public double getScoreforCluster(int ClusterNumber) {
    List<Pair<Double, Double>> CoordinateList = this.Clusters.get(ClusterNumber);
    List<Double> Distances = new ArrayList<Double>();

    Pair<Double, Double> origin = new Pair<Double, Double>(0.0, 0.0);
    for (int i = 0; i < CoordinateList.size(); i++) {
      Distances.add(Distance(CoordinateList.get(i), origin));
    }

    double sum = 0.0;
    for (int j = 0; j < Distances.size(); j++) {
      sum += Distances.get(j);
    }

    return (double) sum / (double) Distances.size();

  }

  public void createClusters(HashMap<String, Integer> query,
          List<HashMap<String, Integer>> answerDictionary) {

    List<Pair<Double, Double>> coordinates = new ArrayList<Pair<Double, Double>>();
    HashMap<Double, Pair<Double, Double>> hashCoordinates = new HashMap<Double, Pair<Double, Double>>();

    Pair<Double, Double> myPair;
    for (int i = 0; i < answerDictionary.size(); i++) {
      myPair = toCartesian(query, answerDictionary.get(i));
      coordinates.add(myPair);
      hashCoordinates.put(H(myPair), myPair);
    }

    List<Pair<Double, Double>> tempSentences = new ArrayList<Pair<Double, Double>>();
    Iterator<Double> It = hashCoordinates.keySet().iterator();
    int i = 1;
    while (It.hasNext()) {
      Double key = It.next();

      tempSentences = getSentencesofCluster(hashCoordinates.get(key),
              getRemainingCoordinates(hashCoordinates), 5);
      Clusters.put(i, tempSentences);

      for (int k = 0; k < tempSentences.size(); k++) {
        hashCoordinates.remove(H(tempSentences.get(k)));
      }

      i++;
    }

  }

  public Pair<Double, Double> toCartesian(Map<String, Integer> TAqueryVector,
          Map<String, Integer> TAdocVector) {

    // get the bag of words vector from the two sentences
    Iterator<String> myqvIt = TAqueryVector.keySet().iterator();
    Iterator<String> mydvIt = TAdocVector.keySet().iterator();

    List<String> qList = new ArrayList<String>();
    List<String> dList = new ArrayList<String>();

    while (myqvIt.hasNext()) {
      String qkey = myqvIt.next();
      qList.add(qkey);
    }

    while (mydvIt.hasNext()) {
      String dkey = mydvIt.next();
      dList.add(dkey);
    }
    List<String> unionList = new ArrayList<String>(qList);
    unionList.addAll(dList);

    // create the bag of words vector for both the sentences
    Map<String, Integer> queryVector = new HashMap<String, Integer>();
    Map<String, Integer> docVector = new HashMap<String, Integer>();

    for (int j = 0; j < unionList.size(); j++) {
      String key = unionList.get(j);
      if (TAqueryVector.containsKey(key)) {
        int value = TAqueryVector.get(key);
        queryVector.put(key, value);
      } else
        queryVector.put(key, 0);

      if (TAdocVector.containsKey(key)) {
        int value = TAdocVector.get(key);
        docVector.put(key, value);
      } else
        docVector.put(key, 0);
    }

    double qSquareSum = 0.0, dSquareSum = 0.0;

    // TODO :: compute the cartesian coordinates for the answer sentence
    if (docVector.size() != queryVector.size())
      throw new IllegalArgumentException("Vectors for the two sentences are not of smae size");

    // for Debugging
    /*
     * Iterator<String> debugqvIt = queryVector.keySet().iterator(); Iterator<String> debugdvIt =
     * docVector.keySet().iterator();
     * 
     * System.out.println("Query Vector:"); while (debugqvIt.hasNext()) { String qkey =
     * debugqvIt.next(); System.out.print(qkey+"=>"+queryVector.get(qkey)+"\t"); }
     * System.out.println();
     * 
     * System.out.println("Answer Vector:"); while (debugdvIt.hasNext()) { String dkey =
     * debugdvIt.next(); System.out.print(dkey+"=>"+docVector.get(dkey)+"\t"); }
     * System.out.println();
     */

    Iterator<String> qvIt = queryVector.keySet().iterator();
    Iterator<String> dvIt = docVector.keySet().iterator();

    double distance = 0.0;
    while (qvIt.hasNext() && dvIt.hasNext()) {
      String qkey = qvIt.next();
      String dkey = dvIt.next();

      int diff = queryVector.get(qkey) - docVector.get(dkey);
      distance = distance + (double) Math.pow(diff, 2);

      qSquareSum = qSquareSum + Math.pow(queryVector.get(qkey), 2);
      dSquareSum = dSquareSum + Math.pow(docVector.get(dkey), 2);
      // tempVar = tempVar + (docVector.get(dkey) * queryVector.get(qkey));
    }
    distance = (double) Math.pow(distance, 0.5);

    double x = distance;
    double y = (double) Math.pow(dSquareSum, 0.5);

    return new Pair<Double, Double>(x, y);

  }

  private List<Pair<Double, Double>> getSentencesofCluster(Pair<Double, Double> myPair,
          List<Pair<Double, Double>> coordinates, int sentenceCount) {

    HashMap<Double, Pair<Double, Double>> distanceToCoordinates = new HashMap<Double, Pair<Double, Double>>();
    List<Double> distances = new ArrayList<Double>();
    List<Pair<Double, Double>> result = new ArrayList<Pair<Double, Double>>();

    double tempDistance = 0.0;
    for (int i = 0; i < coordinates.size(); i++) {
      tempDistance = Distance(myPair, coordinates.get(i));
      distances.add(tempDistance);
      distanceToCoordinates.put(tempDistance, coordinates.get(i));
    }

    Collections.sort(distances);
    for (int j = 0; j < sentenceCount && j < coordinates.size(); j++) {
      result.add(distanceToCoordinates.get(distances.get(j)));
    }

    return result;
  }

  private double H(Pair<Double, Double> pair) {
    return (double) (Math.pow(2, pair.first) * Math.pow(3, pair.second));
  }

  private double Distance(Pair<Double, Double> pair1, Pair<Double, Double> pair2) {
    double v1 = Math.pow(pair1.first - pair2.first, 2);
    double v2 = Math.pow(pair1.second - pair2.second, 2);

    return Math.pow(v1 + v2, 0.5);
  }

  private List<Pair<Double, Double>> getRemainingCoordinates(
          HashMap<Double, Pair<Double, Double>> hashCoordinates) {
    List<Pair<Double, Double>> result = new ArrayList<Pair<Double, Double>>();

    Iterator<Double> It = hashCoordinates.keySet().iterator();
    while (It.hasNext()) {
      Double key = It.next();
      result.add(hashCoordinates.get(key));
    }

    return result;
  }

}
