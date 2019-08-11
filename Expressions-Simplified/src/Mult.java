import java.util.List;
import java.util.Map;

/**
 * @author Inbar Avital
 * In charge of the multiply functions.
 */
public class Mult extends BinaryExpression implements Expression {
    // members
    private Expression e1;
    private Expression e2;
    //constructors
    /**
     * constructor #1.
     * @param e1 - expression 1.
     * @param e2 - expression 2.
     */
    public Mult(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     * constructor #2.
     * @param e1 - expression.
     * @param num - number.
     */
    public Mult(Expression e1, double num) {
        this.e1 = e1;
        this.e2 = new Num(num);
    }
    /**
     * constructor #3.
     * @param num - number.
     * @param e2 - expression.
     */
    public Mult(double num, Expression e2) {
        this.e1 = new Num(num);
        this.e2 = e2;
    }
    /**
     * constructor #4.
     * @param e1 - expression.
     * @param var - variable.
     */
    public Mult(Expression e1, String var) {
        this.e1 = e1;
        this.e2 = new Var(var);
    }
    /**
     * constructor #5.
     * @param var - variable.
     * @param e2 - expression.
     */
    public Mult(String var, Expression e2) {
        this.e1 = new Var(var);
        this.e2 = e2;
    }
    /**
     * constructor #6.
     * @param var - variable.
     * @param num - number.
     */
    public Mult(String var, double num) {
        this.e1 = new Var(var);
        this.e2 = new Num(num);
    }
    /**
     * constructor #7.
     * @param num - number.
     * @param var - variable.
     */
    public Mult(double num, String var) {
        this.e1 = new Num(num);
        this.e2 = new Var(var);
    }
    /**
     * constructor #8.
     * @param var1 - varialbe 1.
     * @param var2 - variable 2.
     */
    public Mult(String var1, String var2) {
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * constructor #9.
     * @param num1 - number 1.
     * @param num2 - number 2.
     */
    public Mult(double num1, double num2) {
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
        Expression evaluatedMult = new Mult(this.e1.evaluate(assignment),
                this.e2.evaluate(assignment));
        return evaluatedMult.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        return this.e1.evaluate() * this.e2.evaluate();
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
        return "(" + this.e1.toString() + " * "
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
        return new Mult(this.e1.assign(var, expression)
                , this.e2.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Plus(new Mult(this.e1.differentiate(var), this.e2),
                new Mult(this.e1, this.e2.differentiate(var)));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // to do it recursive:
        Expression m1 = this.e1.simplify();
        Expression m2 = this.e2.simplify();
        // in case there are no variables - calculate.
        if (m1.getVariables().size() == 0
                && m2.getVariables().size() == 0) {
            try {
                return new Num(new Mult(m1, m2).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 1 * X = X
        if (super.equals(m1, new Num(1))) {
            return m2;
        }
        // X * 1 = X
        if (super.equals(m2, new Num(1))) {
            return m1;
        }
        // X * 0 = 0, 0 * X = 0.
        if (super.equals(m1, new Num(0))
                || super.equals(m2, new Num(0))) {
            return new Num(0);
        }
        return new Mult(m1, m2);
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
        Expression m1 = this.e1.bonusSimplify().simplify();
        Expression m2 = this.e2.bonusSimplify().simplify();
        // X * X = X^2
        if (bonusEquals(m1, m2)) {
            return new Pow(m1, new Num(2)).bonusSimplify();
        }
        // All cases when there are two numbers, so it will calculate them.
        if (m1.getClass() == Mult.class) {
            if (m2.getClass() == Mult.class) {
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Mult(new Mult(m1.getE1(), m2.getE1()),
                            new Mult(m1.getE2(), m2.getE2()))
                            .bonusSimplify();
                }
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Mult(new Mult(m1.getE2(), m2.getE1()),
                            new Mult(m1.getE1(), m2.getE2()))
                            .bonusSimplify();
                }
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Mult(new Mult(m1.getE1(), m2.getE2()),
                            new Mult(m1.getE2(), m2.getE1()))
                            .bonusSimplify();
                }
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Mult(new Mult(m1.getE2(), m2.getE2()),
                            new Mult(m1.getE1(), m2.getE1()))
                            .bonusSimplify();
                }
            }
            if (m1.getE1().getVariables().size() == 0
                    && m2.getVariables().size() == 0) {
                return new Mult(new Mult(m1.getE1(), m2),
                        m1.getE2()).bonusSimplify();
            }
            if (m1.getE2().getVariables().size() == 0
                    && m2.getVariables().size() == 0) {
                return new Mult(new Mult(m1.getE2(), m2),
                        m1.getE1()).bonusSimplify();
            }
        }
        if (m2.getClass() == Mult.class) {
            if (m2.getE1().getVariables().size() == 0
                    && m1.getVariables().size() == 0) {
                return new Mult(new Mult(m2.getE1(), m1),
                        m2.getE2()).bonusSimplify();
            }
            if (m2.getE2().getVariables().size() == 0
                    && m1.getVariables().size() == 0) {
                return new Mult(new Mult(m2.getE2(), m1),
                        m2.getE1()).bonusSimplify();
            }
        }
        // (X^A)*(X^B) = X^(A+B)
        // (X^A)*X = X*(A+1)
        if (m1.getClass() == Pow.class) {
            if (m2.getClass() == Pow.class) {
                if (bonusEquals(m1.getE1(), m2.getE1())) {
                    return new Pow(m1.getE1(), new Plus(m1.getE2(),
                            m2.getE2())).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE2())) {
                    return new Pow(new Plus(m1.getE1(), m2.getE1()),
                            m1.getE2()).bonusSimplify();
                }
            }
            if (bonusEquals(m1.getE1(), m2)) {
                return new Pow(m2, new Plus(m1.getE2(),
                        new Num(1))).bonusSimplify();
            }
        }
        // X*(X^A) = X*(A+1)
        if (m2.getClass() == Pow.class) {
            if (bonusEquals(m2.getE1(), m1)) {
                return new Pow(m1, new Plus(m2.getE2(),
                        new Num(1))).bonusSimplify();
            }
        }
        if (m1.getClass() == Div.class) {
            // (Y / X) * X = Y
            if (bonusEquals(m1.getE2(), m2)) {
                return m1.getE1().bonusSimplify();
            }
            if (m2.getClass() == Div.class) {
                return new Div(new Mult(m1.getE1(), m2.getE1()),
                        new Mult(m1.getE2(), m2.getE2())).bonusSimplify();
            }
            // (Y / X) * (A * X) = Y * A
            if (m2.getClass() == Mult.class) {
                if (bonusEquals(m2.getE1(), m1.getE2())) {
                    return new Mult(m2.getE2(), m1.getE1()).bonusSimplify();
                }
                if (bonusEquals(m2.getE2(), m1.getE2())) {
                    return new Mult(m2.getE1(), m1.getE1()).bonusSimplify();
                }
            }
            return new Div(new Mult(m1.getE1(), m2), m1.getE2()).bonusSimplify();
        }
        // X * (Y / X) = Y
        if (m2.getClass() == Div.class) {
            // (A*X) * (Y / X)  = Y * A
            if (m1.getClass() == Mult.class) {
                if (bonusEquals(m1.getE1(), m2.getE2())) {
                    return new Mult(m1.getE2(), m2.getE1()).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE2())) {
                    return new Mult(m1.getE1(), m2.getE1()).bonusSimplify();
                }
            }
            if (bonusEquals(m2.getE2(), m1)) {
                return m2.getE1().bonusSimplify();
            }
            return new Div(new Mult(m1, m2.getE1()), m2.getE2()).bonusSimplify();
        }
        return new Mult(m1, m2).simplify();
    }
}
