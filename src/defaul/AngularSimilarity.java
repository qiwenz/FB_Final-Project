package defaul;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AngularSimilarity {
  
  private double angular_similarity = 0.0;

  /**
   * 
   * @param TAqueryVector
   * @param TAdocVector
   * @return angular_similarity
   */
  public double computeAngularSimilarity(Map<String, Integer> TAqueryVector,
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

    // TODO :: compute angular similarity between two sentences
    if (docVector.size() != queryVector.size())
      throw new IllegalArgumentException("Vectors for the two sentences are not of smae size");

    Iterator<String> qvIt = queryVector.keySet().iterator();
    Iterator<String> dvIt = docVector.keySet().iterator();

    double tempVar=0.0;
    while (qvIt.hasNext() && dvIt.hasNext()) {
      String qkey = qvIt.next();
      String dkey = dvIt.next();

      qSquareSum = qSquareSum + Math.pow(queryVector.get(qkey),2);
      dSquareSum = dSquareSum + Math.pow(docVector.get(dkey),2);
      tempVar = tempVar+ (docVector.get(dkey) * queryVector.get(qkey));

    }
    this.angular_similarity = tempVar / (Math.sqrt(qSquareSum) * Math.sqrt(dSquareSum));
    this.angular_similarity = 1-(Math.acos(angular_similarity)/Math.PI);
    return this.angular_similarity;
  }
}
