/**
 * @author Inbar Avital
 * This is the base expression functions that is in charge of the functions
 * that are used in all of the expression types(both binary and unary).
 */
public abstract class BaseExpression {
    /**
     * Gets two expression and checks if they are equal by their string.
     * @param e1 - the first expression.
     * @param e2 - the second expression.
     * @return boolean - true if they are equal, false if they are not.
     */
    public boolean equals(Expression e1, Expression e2) {
        // if the toString are equal
        if (e1.toString().equals(e2.toString())) {
            return true;
        }
        return false;
    }
    /**
     * Gets two expression and checks if they are equal. This time,
     * not by their string but by their classes.
     * @param e1 - the first expression.
     * @param e2 - the second expression.
     * @return boolean - true if they are equal, false if they are not.
     */
    public boolean bonusEquals(Expression e1, Expression e2) {
        // if there are no variables in any of them,
        // check if they are equals as numbers.
        if (e1.getVariables().size() == 0 && e2.getVariables().size() == 0) {
            try {
                return equals(new Num(e1.evaluate()), new Num(e2.evaluate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // If they have the same class , check intern situations.
        if (e1.getClass() == e2.getClass()) {
            // if they are unary expressions, checks if
            // their intern expressions are equal.
            if (e1.getClass() == Var.class || e1.getClass() == Num.class) {
                return equals(e1, e2);
            }
            if (e1.getClass() == Cos.class || e1.getClass() == Sin.class
                    || e1.getClass() == Neg.class) {
                return bonusEquals(e1.getE1(), e2.getE1());
            }
            // if they are binary expressions, check if any
            // of their intern expressions are equal.
            if (bonusEquals(e1.getE1(), e2.getE1()) && bonusEquals(e1.getE2(), e2.getE2())) {
                return true;
            } else if (bonusEquals(e1.getE1(), e2.getE2()) && bonusEquals(e1.getE2(), e2.getE1())
                    && e1.getClass() != Minus.class && e1.getClass() != Div.class
                    && e1.getClass() != Pow.class) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the two expressions have at least one variable in common.
     * @param e1 - the first expression.
     * @param e2 - the second expression.
     * @return boolean - true if they contain a variable in common, false otherwise.
     */
    public boolean containVar(Expression e1, Expression e2) {
        // For each var in e2, check if this var is in e1.
        for (String i: e2.getVariables()) {
            for (String j: e1.getVariables()) {
                if (i.equals(j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
