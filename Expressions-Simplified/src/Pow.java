import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 *
 */
public class Pow extends BinaryExpression implements Expression {
    // members
    private Expression e1;
    private Expression e2;
    //constructors
    /**
     * constructor #1.
     * @param e1 - expression.
     * @param e2 - expression.
     */
    public Pow(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     * constructor #2.
     * @param e1 - expression.
     * @param num - number.
     */
    public Pow(Expression e1, double num) {
        this.e1 = e1;
        this.e2 = new Num(num);
    }
    /**
     * constructor #3.
     * @param num - number.
     * @param e2 - expression.
     */
    public Pow(double num, Expression e2) {
        this.e1 = new Num(num);
        this.e2 = e2;
    }
    /**
     * constructor #4.
     * @param e1 - expression.
     * @param var - variable.
     */
    public Pow(Expression e1, String var) {
        this.e1 = e1;
        this.e2 = new Var(var);
    }
    /**
     * constructor #5.
     * @param var - variable.
     * @param e2 - expression.
     */
    public Pow(String var, Expression e2) {
        this.e1 = new Var(var);
        this.e2 = e2;
    }
    /**
     * constructor #6.
     * @param var - variable.
     * @param num - number.
     */
    public Pow(String var, double num) {
        this.e1 = new Var(var);
        this.e2 = new Num(num);
    }
    /**
     * constructor #7.
     * @param num - number.
     * @param var - variable.
     */
    public Pow(double num, String var) {
        this.e1 = new Num(num);
        this.e2 = new Var(var);
    }
    /**
     * constructor #8.
     * @param var1 - variable 1.
     * @param var2 - variable 2.
     */
    public Pow(String var1, String var2) {
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * constructor #9.
     * @param num1 - number 1.
     * @param num2 - number 2.
     */
    public Pow(double num1, double num2) {
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
        Expression evaluatedPow = new Pow(e1.evaluate(assignment),
                e2.evaluate(assignment));
        return evaluatedPow.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        double pow = Math.pow(this.e1.evaluate(), this.e2.evaluate());
        if (Double.isNaN(pow)) {
            throw new Exception("Pow.evaluate got an NaN as a result");
        }
        return pow;
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
        return "(" + e1.toString() + "^" + e2.toString() + ")";
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
        return new Pow(e1.assign(var, expression), e2.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Mult(this, new Plus(new Mult(
                this.e1.differentiate(var), new Div(this.e2, this.e1)),
                new Mult(this.e2.differentiate(var), new Log(new Var("e"), this.e1))));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // to make it recursive:
        Expression m1 = this.e1.simplify();
        Expression m2 = this.e2.simplify();
        // if there are no variables, calculate.
        if (m1.getVariables().size() == 0
                && m2.getVariables().size() == 0) {
            try {
                return new Num(new Pow(m1, m2).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //  X^0 = 0
        if (equals(m2, new Num(0))) {
            return new Num(1);
        }
        return new Pow(m1, m2);
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
        // to make it recursive:
        Expression m1 = e1.bonusSimplify().simplify();
        Expression m2 = e2.bonusSimplify().simplify();
        if (m2.getVariables().size() == 0) {
            try {
                // N ^ 0 = 1
                if (m2.evaluate() == 1) {
                    return m1;
                }
                // N ^ -1 = 1
                if (m2.evaluate() == -1) {
                    return new Div(1, m1).bonusSimplify();
                }
                // N ^ 1 = N
                if (m2.evaluate() == 0) {
                    return new Num(1);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (m1.getVariables().size() == 0) {
            try {
                // 1 ^ N = 1
                if (m1.evaluate() == 1) {
                    return new Num(1);
                }
                // 0 ^ N = 0
                if (m1.evaluate() == 0) {
                    return new Num(0);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // (X^Y)^Z = X^(Y * Z)
        if (m1.getClass() == Pow.class) {
            return new Pow(m1.getE1(), new Mult(m1.getE2(), m2)).bonusSimplify();
        }
        // X ^ -1 = 1 / X
        if (m2.getClass() == Neg.class) {
            return new Div(new Num(1), new Pow(m1, m2.getE1())).bonusSimplify();
        }
        return new Pow(m1, m2).simplify();
    }

}
