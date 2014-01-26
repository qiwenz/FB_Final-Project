package defaul;
import java.util.ArrayList;
import java.util.List;

public class learnTverskyParameters {

  private double alpha = 0.0;

  private double beta = 1.0;

  public List<Double> parameterList = new ArrayList<Double>();

  /**
   * 
   * @param min
   * @param max
   * @return parameterList
   */
  public List<Double> learnParameters(int min, int max) {
    int min_max = min - max;
    double stepSize = 0.01;
    
    System.out.println("====================================");
    System.out
    .println("Learning Tversky Score parameters using Neural Network with Gradient Descent\n");

    while (true) {
      double alpha_new = this.alpha + stepSize * (this.beta * (double) min_max);
      double beta_new = this.beta - stepSize * (max + (this.alpha * (double) min_max));
      double sum = alpha_new + beta_new;
      alpha_new = (double) alpha_new / sum;
      beta_new = (double) beta_new / sum;

      double old_criterion = this.getCriterion(this.alpha, this.beta, min, max);
      double new_criterion = this.getCriterion(alpha_new, beta_new, min, max);

      this.alpha = alpha_new;
      this.beta = beta_new;

      System.out
              .println("Change in criterion function: " + Math.abs(new_criterion - old_criterion));
      if (Math.abs(new_criterion - old_criterion) < Math.pow(10, -9))
        break;
    }
    this.parameterList.add(this.alpha);
    this.parameterList.add(this.beta);

    return this.parameterList;

  }

  /**
   * 
   * @param alpha
   * @param beta
   * @param min
   * @param max
   * @return criterion
   */
  private double getCriterion(double alpha, double beta, int min, int max) {
    double criterion = (double) alpha * beta * min + (double) (1 - alpha) * beta * max;
    return criterion;
  }

}
