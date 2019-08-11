import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 * In charge of the log functions
 */
public class Log extends BinaryExpression implements Expression {
    // members
    private Expression e1;
    private Expression e2;
    //constructors
    /**
     * constructor #1.
     * @param e1 - expression 1
     * @param e2 - expression 2
     */
    public Log(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     * constructor #2.
     * @param e1 - expression
     * @param num - number
     */
    public Log(Expression e1, double num) {
        this.e1 = e1;
        this.e2 = new Num(num);
    }
    /**
     * constructor #3.
     * @param num - number
     * @param e2 - expression
     */
    public Log(double num, Expression e2) {
        this.e1 = new Num(num);
        this.e2 = e2;
    }
    /**
     * constructor #4.
     * @param e1 - expression
     * @param var - variable
     */
    public Log(Expression e1, String var) {
        this.e1 = e1;
        this.e2 = new Var(var);
    }
    /**
     * constructor #5.
     * @param var - variable
     * @param e2 - expression
     */
    public Log(String var, Expression e2) {
        this.e1 = new Var(var);
        this.e2 = e2;
    }
    /**
     * constructor #6.
     * @param var - variable
     * @param num - number
     */
    public Log(String var, double num) {
        this.e1 = new Var(var);
        this.e2 = new Num(num);
    }
    /**
     * constructor #7.
     * @param num - number
     * @param var - variable
     */
    public Log(double num, String var) {
        this.e1 = new Num(num);
        this.e2 = new Var(var);
    }
    /**
     * constructor #8.
     * @param var1 - variable 1
     * @param var2 - variable 2
     */
    public Log(String var1, String var2) {
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * constructor #9.
     * @param num1  - number 1
     * @param num2 - number 2
     */
    public Log(double num1, double num2) {
        this.e1 = new Num(num1);
        this.e2 = new Num(num2);
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
        Expression evaluatedLog = new Log(e1.evaluate(assignment),
                e2.evaluate(assignment));
        return evaluatedLog.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        // seperate the two logs for a proper calculation
        double result1 = Math.log(e1.evaluate());
        double result2 = Math.log(e2.evaluate());
        // if there was a problem
        if (Double.isNaN(result1) || Double.isNaN(result2)) {
            throw new Exception("Log.evaluate got an NaN as a result");
        }
        return Math.log(e2.evaluate()) / Math.log(e1.evaluate());
    }

    /**
     * Returns a list of the variables in the expression.
     * @return List<String> - the list of the variables
     */
    public List<String> getVariables() {
        return super.getVariables(this.e1, this.e2);
    }
    /**
     * Returns a nice string representation of the expression.
     * @return String - the expression as a string.
     */
    public String toString() {
        return "log(" + e1.toString() + ", " + e2.toString() + ")";
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
        return new Log(e1.assign(var, expression), e2.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        if (equals(e1, new Var("x"))) {
            return new Div(new Minus(new Mult(this.e1, this.e2.differentiate(var)),
                    new Mult(new Mult(this.e2, this.e1.differentiate(var)), new Log(this.e1, this.e2)))
                            , new Mult(new Mult(this.e1, this.e2), new Log(new Var("e"), this.e1)));
        }
        return new Div(this.e2.differentiate(var), new Mult(new Log(new Var("e"), this.e1), this.e2));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        Expression m1 = this.e1.simplify();
        Expression m2 = this.e2.simplify();
        // if there are no variables, calculate.
        if (m1.getVariables().size() == 0
                && m2.getVariables().size() == 0) {
            try {
                return new Num(new Log(m1, m2).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // if the first and second expressions are equals, return 1.
        if (equals(m1, m2)) {
            return new Num(1);
        }
        return new Log(m1, m2);
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
        return e2;
    }
    /**
     * This is the bonus function for simplifications.
     * @return Expression - the expression after the simplification.
     */
    public Expression bonusSimplify() {
        // to do it recursive:
        Expression m1 = this.e1.bonusSimplify().simplify();
        Expression m2 = this.e2.bonusSimplify().simplify();
        // log(A, X^N) = N * log(A, X)
        if (m2.getClass() == Pow.class) {
            return new Mult(m2.getE2(), new Log(m1, m2.getE1())).bonusSimplify();
        }
        return new Log(m1, m2).simplify();
    }
}
