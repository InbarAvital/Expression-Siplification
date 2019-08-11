import java.util.List;
import java.util.Map;

/**
 * @author Inbar Avital
 * In charge of the div functions
 */
public class Div extends BinaryExpression implements Expression {

    // members
    private Expression e1;
    private Expression e2;
    //constructors
    /**
     * constructor #1.
     * @param e1 - the first expression
     * @param e2 - the second expression
     */
    public Div(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     * constructor #2.
     * @param e1 - expression
     * @param num - a number
     */
    public Div(Expression e1, double num) {
        this.e1 = e1;
        this.e2 = new Num(num);
    }
    /**
     * constructor #3.
     * @param num - number
     * @param e2 - expression
     */
    public Div(double num, Expression e2) {
        this.e1 = new Num(num);
        this.e2 = e2;
    }
    /**
     * constructor #4.
     * @param e1 - expression
     * @param var - variable
     */
    public Div(Expression e1, String var) {
        this.e1 = e1;
        this.e2 = new Var(var);
    }
    /**
     * constructor #5.
     * @param var - variable
     * @param e2 - expression
     */
    public Div(String var, Expression e2) {
        this.e1 = new Var(var);
        this.e2 = e2;
    }
    /**
     * constructor #6.
     * @param var - variable
     * @param num - number
     */
    public Div(String var, double num) {
        this.e1 = new Var(var);
        this.e2 = new Num(num);
    }
    /**
     * constructor #7.
     * @param num - number.
     * @param var - variable.
     */
    public Div(double num, String var) {
        this.e1 = new Num(num);
        this.e2 = new Var(var);
    }
    /**
     * constructor #8.
     * @param var1 - variable 1.
     * @param var2 - variable 2.
     */
    public Div(String var1, String var2) {
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * constructor #9.
     * @param num1 - number 1
     * @param num2 - number 2
     */
    public Div(double num1, double num2) {
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
        Expression evaluatedDiv = new Div(this.e1.evaluate(assignment),
                this.e2.evaluate(assignment));
        return evaluatedDiv.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        // in case you divide by 0.
        if (this.e2.equals(new Num(0))) {
            throw new Exception("You cannot divide by 0");
        }
        return this.e1.evaluate() / this.e2.evaluate();
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
        return "(" + this.e1.toString() + " / "
                + this.e2.toString() + ")";
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
        return new Div(this.e1.assign(var, expression)
                , this.e2.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Div(new Minus(new Mult(this.e1.differentiate(var), this.e2)
                , new Mult(this.e1, this.e2.differentiate(var)))
                , new Pow(this.e2, 2.0));
    }
    @Override
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // recursive
        Expression m1 = this.e1.simplify();
        Expression m2 = this.e2.simplify();
        // in case there are no variable, calculate.
        if (m1.getVariables().size() == 0
                && m2.getVariables().size() == 0) {
            try {
                return new Num(new Div(m1, m2).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // if the two expressions are equal, make it 1.
        if (super.equals(m1, m2)) {
            return new Num(1);
        // if the second expression is 1, return the first expression.
        } else if (super.equals(m2, new Num(1))) {
            return m1;
        }
        return new Div(m1, m2);
    }
    /**
     * @return Expression - the intern first expression.
     */
    public Expression getE1() {
        return this.e1;
    }
    /**
     * @return Expression - the intern second expression.
     */
    public Expression getE2() {
        return this.e2;
    }
    /**
     * This is the bonus function for simplifications.
     * @return Expression - the expression after the simplification.
     */
    public Expression bonusSimplify() {
        Expression m1 = this.e1.bonusSimplify().simplify();
        Expression m2 = this.e2.bonusSimplify().simplify();
        // X / X = 1
        if (bonusEquals(m1, m2)) {
            return new Num(1);
        }
        // 0 / X = 0
        if (m1.getVariables().size() == 0) {
            try {
                if (m1.evaluate() == 0) {
                    return new Num(0);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //(a^n)/(a^m) = a^(n-m), (a^b)/(c^b) = (a/c)^b
        if (m1.getClass() == Pow.class) {
            if (m2.getClass() == Pow.class) {
                if (bonusEquals(m1.getE1(), m2.getE1())) {
                    return new Pow(m1.getE1(), new Minus(m1.getE2(), m2.getE2())).bonusSimplify();
                } else if (bonusEquals(m1.getE2(), m2.getE2())) {
                    return new Pow(new Div(m1.getE1(), m2.getE1()), m1.getE2()).bonusSimplify();
                }
            }
            // (X^A)/X = X^(A-1)
            if (bonusEquals(m1.getE1(), m2)) {
                return new Pow(m2, new Minus(m1.getE2(), 1)).bonusSimplify();
            }
        }
        //(a/b)/(n/m) = (a*m)/(b*n)
        if (m1.getClass() == Div.class
                && m2.getClass() == Div.class) {
            return new Div(new Mult(m1.getE1(), m2.getE2())
                    , new Mult(m1.getE2(), m2.getE1()).bonusSimplify());
        }
        // X / (Y / X) = (X*Z)/Y
        if (m2.getClass() == Div.class) {
            return new Div(new Mult(m1, m2.getE2()), m2.getE1()).bonusSimplify();
        }
        if (m1.getClass() == Mult.class) {
            // (a*b)/(a*c) = b/c -- all rotations
            if (m2.getClass() == Mult.class) {
                if (bonusEquals(m1.getE1(), m2.getE1())) {
                    return new Div(m1.getE2(), m2.getE2()).bonusSimplify();
                }
                if (bonusEquals(m1.getE1(), m2.getE2())) {
                    return new Div(m1.getE2(), m2.getE1()).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE1())) {
                    return new Div(m1.getE1(), m2.getE2()).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE2())) {
                    return new Div(m1.getE1(), m2.getE1()).bonusSimplify();
                }
            }
            // (A*B)/A = B
            if (bonusEquals(m1.getE1(), m2)) {
                return m1.getE2().bonusSimplify();
            }
            // (B*A)/A = B
            if (bonusEquals(m1.getE2(), m2)) {
                return m1.getE1().bonusSimplify();
            }
        }
        if (m2.getClass() == Mult.class) {
            // A/(A*B) = 1/B
            if (bonusEquals(m1, m2.getE1())) {
                return new Div(1, m2.getE2()).bonusSimplify();
            }
            // A/(B*A) = 1/B
            if (bonusEquals(m1, m2.getE2())) {
                return new Div(1, m2.getE1()).bonusSimplify();
            }
        }
        // (-a)/b = -(a/b), a/(-b) = -(a/b), (-a)/(-b) = a/b
        if (m1.getClass() == Neg.class) {
            if (m2.getClass() == Neg.class) {
                return new Div(m1.getE1(), m2.getE1()).bonusSimplify();
            }
            return new Neg(new Div(m1.getE1(), m2)).bonusSimplify();
        }
        if (m2.getClass() == Neg.class) {
            return new Neg(new Div(m1, m2.getE1())).bonusSimplify();
        }
        return new Div(m1, m2).simplify();
    }
}
