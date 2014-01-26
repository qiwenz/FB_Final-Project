package defaul;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JaccardCoefficient {

  private double jaccard_coefficient = 0.0;

  /**
   * 
   * @param TAqueryVector
   * @param TAdocVector
   * @return jaccard_coefficient
   */
  public double computeJaccardCoefficient(Map<String, Integer> TAqueryVector,
          Map<String, Integer> TAdocVector) {

    // get the union and intersection of words from the two sentences
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
    List<String> intersectedList = intersection.computeIntersection(qList, dList);
    List<String> unionList = new ArrayList<String>(qList);
    unionList.addAll(dList);

    // compute the Jaccard Coefficient

    int numCommonWords = intersectedList.size();
    int numUnionWords = unionList.size();

    this.jaccard_coefficient = (double) numCommonWords / numUnionWords;
    return this.jaccard_coefficient;
  }
}
