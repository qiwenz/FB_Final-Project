package defaul;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DiceCoefficient {

  private double dice_coefficient = 0.0;

  /**
   * 
   * @param TAqueryVector
   * @param TAdocVector
   * @return dice_coefficient
   */
  public double computeDiceCoefficient(Map<String, Integer> TAqueryVector,
          Map<String, Integer> TAdocVector) {

    // get the number of common words from the two sentences
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

    // compute the Dice Coefficient

    int numCommonWords = intersectedList.size();
    int numqueryWords = TAqueryVector.size();
    int numdocWords = TAdocVector.size();

    this.dice_coefficient = (double) 2 * numCommonWords / (numqueryWords + numdocWords);
    return this.dice_coefficient;
  }
}
