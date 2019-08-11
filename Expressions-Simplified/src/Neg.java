import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 *
 */
public class Neg extends UnaryExpression implements Expression {
    // members
    private Expression e1;
    /**
     * constructor #1.
     * @param e1 - expression.
     */
    public Neg(Expression e1) {
        this.e1 = e1;
    }
    /**
     * constructor #2.
     * @param num - number.
     */
    public Neg(double num) {
        this.e1 = new Num(num);
    }
    /**
     * constructor #3.
     * @param var - variable.
     */
    public Neg(String var) {
        this.e1 = new Var(var);
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
        Expression evaluatedNeg = new Cos(e1.evaluate(assignment));
        return evaluatedNeg.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        return -(this.e1.evaluate());
    }

    /**
     * Returns a list of the variables in the expression.
     * @return List<String> - the list of the variables
     */
    public List<String> getVariables() {
        return super.getVariables(this.e1);
    }
    /**
     * Returns a nice string representation of the expression.
     * @return String - the expression as a string.
     */
    public String toString() {
        return "(-" + e1.toString() + ")";
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
        return new Neg(e1.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Neg(this.e1.differentiate(var));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // if there are no variables - calculate.
        if (e1.simplify().getVariables().size() == 0) {
            try {
                return new Num(new Neg(e1.simplify()).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new Neg(this.e1.simplify());
    }
    /**
     * @return Expression - the intern first expression.
     */
    public Expression getE1() {
        return e1;
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
        // to make it recursive:
        Expression m1 = e1.bonusSimplify().simplify();
        // -(-A) = A
        if (m1.getClass() == Neg.class) {
            return m1.getE1().bonusSimplify();
        }
        // -(A-B) = B-A
        if (m1.getClass() == Minus.class) {
            return new Minus(m1.getE2(), m1.getE1()).bonusSimplify();
        }
        return new Neg(m1).simplify();
    }
}
