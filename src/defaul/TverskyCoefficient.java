package defaul;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TverskyCoefficient {

  private double tversky_coefficient = 0.0;

  /**
   * 
   * @param TAqueryVector
   * @param TAdocVector
   * @return tversky_coefficient
   */
  public double computeTverskyCoefficient(Map<String, Integer> TAqueryVector,
          Map<String, Integer> TAdocVector) {

    // get the intersection of words from the two sentences
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

    // compute the tversky Coefficient
    learnTverskyParameters myInstance = new learnTverskyParameters();

    int numCommonWords = intersectedList.size();

    int A_B = qList.size() - numCommonWords;
    int B_A = dList.size() - numCommonWords;

    int a = Math.min(A_B, B_A);
    int b = Math.max(A_B, B_A);

    List<Double> parameters = myInstance.learnParameters(a, b);
    double alpha = parameters.get(0);
    double beta = parameters.get(1);

    // double alpha = 0.8, beta = 0.2;

    /*
     * double TV_min = numCommonWords / (double) (numCommonWords + b); double myVar = Math.pow(a, 2)
     * / (double) (4 * (a - b)); double TV_max = numCommonWords / (double) (numCommonWords + myVar);
     * 
     * double c = TV_min / (TV_min - TV_max); double m = 1 / (TV_min - TV_max);
     */

    double temp1 = alpha * a + (1 - alpha) * b;
    double denominator = numCommonWords + beta * temp1;

    this.tversky_coefficient = numCommonWords / denominator;

    // this.tversky_coefficient = m * this.tversky_coefficient + c;

    return this.tversky_coefficient;
  }
}
