import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Inbar Avital
 *
 */
public class ExpressionsTest {
    /**
     * The main function that is in charge of running examples.
     * @param args - main arguments. In our case it is empty.
     */
    public static void main(String[] args) {
        Expression e1 = new Plus(new Plus(new Mult(2, "x"),
                new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        System.out.println(e1.toString());
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x", 2.0);
        assignment.put("y", 0.25);
        assignment.put("e", 2.71);
        try {
            System.out.println(e1.evaluate(assignment));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(e1.differentiate("x"));
        try {
            System.out.println(e1.differentiate("x").evaluate(assignment));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(e1.differentiate("x").simplify());
    }

}
