package defaul;
import java.util.ArrayList;
import java.util.List;

public class intersection {

  /**
   * Utility function to return intersection of two lists
   * 
   * @param list1
   * @param list2
   * @return list
   */
  public static <T> List<T> computeIntersection(List<T> list1, List<T> list2) {
    List<T> list = new ArrayList<T>();

    for (T t : list1) {
      if (list2.contains(t)) {
        list.add(t);
      }
    }

    return list;
  }

}
