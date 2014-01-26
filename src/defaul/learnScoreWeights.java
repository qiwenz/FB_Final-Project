package defaul;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class learnScoreWeights {
  private static List<ArrayList<Double>> ListScoreWeights = new ArrayList<ArrayList<Double>>();

  public void printWeights() {
    System.out.println();
    for (int i = 0; i < ListScoreWeights.size(); i++) {
   //   System.out.print("Weight vector for Sentence" + (i + 1) + ": ");
      double tempSum = 0.0;
      for (int j = 0; j < ListScoreWeights.get(i).size(); j++)
        tempSum += ListScoreWeights.get(i).get(j) / 5;
      System.out.println(tempSum);
    }
  }
  
  public double[] getWeights() {
	    double[] weights=new double[ListScoreWeights.size()];
	    for (int i = 0; i < ListScoreWeights.size(); i++) {
	     // System.out.print("Weight vector for Sentence" + (i + 1) + ": ");
	      double tempSum = 0.0;
	      for (int j = 0; j < ListScoreWeights.get(i).size(); j++)
	        tempSum += ListScoreWeights.get(i).get(j) / 5;
	      weights[i]=tempSum;
	    }
	    return weights;
	  }

  /**
   * 
   * @param query
   * @param answerDictionary
   */
  public void train(HashMap<String, Integer> query, List<HashMap<String, Integer>> answerDictionary) {

    System.out.println("For reference: Order of the scores");
    System.out.println("AS CS DC JC TC\n");

    List<ArrayList<Double>> scores = this.getScoresList(query, answerDictionary);

    System.out.println("\n======================================");
//    System.out.println("Learning the weights for the scores using Perceptron Algorithm\n");

    for (int i = 0; i < scores.size(); i++) {
      ArrayList<Double> scoreWeights = new ArrayList<Double>(Collections.nCopies(5, 1.0));
      scoreWeights = (ArrayList<Double>) this.toUnitVector(scoreWeights);
    //  System.out.print("Intial Weight Vector: ");
      this.printWeights();

    //  System.out.println("Scores for this answer: " + scores.get(i).toString());

      double temp = dotProduct(scoreWeights, scores.get(i));
      temp = (double) temp / this.getL2Norm(scores.get(i));
    //  System.out.println("Value after computing dot-product: " + temp);

      if (temp < scores.get(i).get(4)) {
        for (int j = 0; j < 10; j++) {
          for (int k = 0; k < scores.get(i).size(); k++) {
            scoreWeights
                    .set(k, scoreWeights.get(k) + (scores.get(i).get(4)) * scores.get(i).get(k));
          }
          scoreWeights = (ArrayList<Double>) this.toUnitVector(scoreWeights);
          this.printWeights();
          if ((this.dotProduct(scoreWeights, scores.get(i)) / this.getL2Norm(scores.get(i))) > scores
                  .get(i).get(4))
            break;
        }
      }
      ListScoreWeights.add(scoreWeights);
    }

  }

  /**
   * 
   * @param query
   * @param answerDictionary
   * @return scores
   */
  public List<ArrayList<Double>> getScoresList(HashMap<String, Integer> query,
          List<HashMap<String, Integer>> answerDictionary) {

    List<ArrayList<Double>> scores = new ArrayList<ArrayList<Double>>();
    AngularSimilarity angSim = new AngularSimilarity();
    CosineSimilarity cosSim = new CosineSimilarity();
    DiceCoefficient dcCoeff = new DiceCoefficient();
    JaccardCoefficient jcCoeff = new JaccardCoefficient();
    TverskyCoefficient tvCoeff = new TverskyCoefficient();

    ArrayList<Double> tempList;
    for (int i = 0; i < answerDictionary.size(); i++) {
      tempList = new ArrayList<Double>();
      tempList.add(angSim.computeAngularSimilarity(query, answerDictionary.get(i)));
      tempList.add(cosSim.computeCosineSimilarity(query, answerDictionary.get(i)));
      tempList.add(dcCoeff.computeDiceCoefficient(query, answerDictionary.get(i)));
      tempList.add(jcCoeff.computeJaccardCoefficient(query, answerDictionary.get(i)));
      tempList.add(tvCoeff.computeTverskyCoefficient(query, answerDictionary.get(i)));
      System.out.println(tempList.toString());

      scores.add(tempList);
    }

    return scores;
  }

  /**
   * 
   * @param vector1
   * @param vector2
   * @return dotP
   */
  private double dotProduct(List<Double> vector1, List<Double> vector2) {
    if (vector1.size() != vector2.size())
      throw new IllegalArgumentException("Vectors for the two sentences are not of smae size");

    double dotP = 0.0;
    for (int i = 0; i < vector1.size(); i++) {
      dotP = dotP + ((double) vector1.get(i) * vector2.get(i));
    }

    return dotP;
  }

  /**
   * 
   * @param vector
   * @return result
   */
  private List<Double> toUnitVector(List<Double> vector) {
    List<Double> result = new ArrayList<Double>();
    double l2Norm = getL2Norm(vector);

    for (int i = 0; i < vector.size(); i++) {
      result.add((double) vector.get(i) / l2Norm);
    }

    return result;
  }

  /**
   * 
   * @param vector
   * @return l2Norm
   */
  private double getL2Norm(List<Double> vector) {
    double l2Norm = 0.0;
    for (int i = 0; i < vector.size(); i++) {
      l2Norm = l2Norm + (double) Math.pow(vector.get(i), 2);
    }
    l2Norm = (double) Math.pow(l2Norm, 0.5);
    return l2Norm;
  }
}
