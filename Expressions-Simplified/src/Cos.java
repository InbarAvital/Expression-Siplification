import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 * This is the Cos class.
 * The class contains the members, constructors and
 *  methods of the type Cos.
 */
public class Cos extends UnaryExpression implements Expression {
    // members
    private Expression e1;
    // constructors
    /**
     * Sets a new cos to contain the expression it gets.
     * @param e1 - an expression
     */
    public Cos(Expression e1) {
        this.e1 = e1;
    }
    /**
     * Sets a new cos to contain the number it gets.
     * @param num - a number.
     */
    public Cos(double num) {
        this.e1 = new Num(num);
    }
    /**
     * Sets a new cos to contain the variable it gets.
     * @param var - a variable.
     */
    public Cos(String var) {
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
        Expression evaluatedCos = new Cos(this.e1.evaluate(assignment));
        return evaluatedCos.evaluate();
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        return Math.cos(this.e1.evaluate());
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
        return "cos(" + this.e1.toString() + ")";
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
        return new Cos(this.e1.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Mult(new Neg(new Sin(this.e1)), this.e1.differentiate(var));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // if there are no variables in this expression, evaluate.
        if (this.e1.simplify().getVariables().size() == 0) {
            try {
                return new Num(new Cos(e1.simplify()).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // simplify the intern expression of the cos.
        return new Cos(this.e1.simplify());
    }
    /**
     * @return Expression - the expression of the cos.
     */
    public Expression getE1() {
        return e1;
    }
    /**
     * @return Expression - in this case, returns null because this is
     * a unary expression.
     */
    public Expression getE2() {
        return null;
    }
    /**
     * This is the bonus function for simplifications.
     * @return Expression - the expression after the simplification.
     */
    public Expression bonusSimplify() {
        Expression m1 = e1.bonusSimplify().simplify();
        // cos(-X) = cos(X)
        if (m1.getClass() == Neg.class) {
            return new Cos(m1.getE1()).simplify();
        }
        return new Cos(m1).simplify();
    }

}
