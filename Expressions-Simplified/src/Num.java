import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Inbar Avital
 *
 */
public class Num implements Expression {
    //members
    private double num;
    /**
     * constructor.
     * @param number - a number to enter to Num.
     */
    public Num(double number) {
        this.num = number;
    }
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * @param assignment - the map with the values of the variables.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.num;
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        return this.num;
    }

    /**
     * Returns a list of the variables in the expression.
     * @return List<String> - the list of the variables
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        return variables;
    }
    /**
     * Returns a nice string representation of the expression.
     * @return String - the expression as a string.
     */
    public String toString() {
        return String.valueOf(this.num);
    }
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var - the var
     * @param expression - the expression
     * @return Expression - the updated expression.
     */
    public Expression assign(String var, Expression expression) {
        return this;
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Num(0);
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        return this;
    }
    /**
     * if the number equals the expression, return true, otherwise, return false.
     * @param e1 - expression.
     * @return boolean - yes if equal, else if are not.
     */
    public boolean equal(Expression e1) {
        if (e1.getClass() == Num.class) {
            try {
                if (e1.evaluate() == this.num) {
                    return true;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * @return Expression - the intern first expression.
     */
    public Expression getE1() {
        return this;
    }
    /**
     * @return Expression - the intern second expression.
     */
    public Expression getE2() {
        return null;
    }
    /**
     * This is the bonus function for simplifications.
     * @return Expression - the expression after the simplification.
     */
    public Expression bonusSimplify() {
        // -0.0 = 0.0
        if (num == -0) {
            return new Num(0);
        }
        return this;
    }
}
