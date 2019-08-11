import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 * In charge of all of the expression's methode - the interface.
 */
public interface Expression {
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * @param assignment - the map with the values of the variables.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    double evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     * @return List<String> - the list of the variables
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     * @return String - the expression as a string.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var - the var
     * @param expression - the expression
     * @return Expression - the updated expression.
     */
    Expression assign(String var, Expression expression);
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    Expression differentiate(String var);
    /**
     * @return Expression - a simplified version of the current expression.
     */
    Expression simplify();
    /**
     * @return Expression - the intern first expression.
     */
    Expression getE1();
    /**
     * @return Expression - the intern second expression.
     */
    Expression getE2();
    /**
     * This is the bonus function for simplifications.
     * @return Expression - the expression after the simplification.
     */
    Expression bonusSimplify();
}