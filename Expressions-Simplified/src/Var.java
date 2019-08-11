import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 *
 */
public class Var implements Expression {
    //members
    private String vari;

    /**
     * constructor #1.
     * @param variable - the string of the variable.
     */
    public Var(String variable) {
        this.vari = variable;
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
        for (String str: assignment.keySet()) {
            if (this.vari == str) {
                return assignment.get(str);
            }
        }
        throw new Exception("Variables in assignment does not fit this.var");
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        throw new Exception("Cannot return a double out of a variable");
    }

    /**
     * Returns a list of the variables in the expression.
     * @return List<String> - the list of the variables
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.add(this.vari);
        return variables;
    }
    /**
     * Returns a nice string representation of the expression.
     * @return String - the expression as a string.
     */
    public String toString() {
        return this.vari;
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
        if (this.vari == var) {
            return expression;
        }
        return this;
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        if (this.vari != var) {
            return new Num(0);
        }
        return new Num(1);
    }
    /**
     * @return Expression - the intern first expression.
     */
    public Expression simplify() {
        return this;
    }
    /**
     * returns true if the expression equals this var, false otherwise.
     * @param e1 - expression
     * @return boolean - true or false.
     */
    public boolean equal(Expression e1) {
        if (e1.getClass() == Var.class) {
            try {
                if (e1.toString().equals(this.vari)) {
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
        return null;
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
        return this;
    }
}
