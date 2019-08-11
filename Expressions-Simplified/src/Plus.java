import java.util.List;
import java.util.Map;
/**
 * @author Inbar Avital
 *
 */
public class Plus extends BinaryExpression implements Expression {
    // members
    private Expression e1;
    private Expression e2;
    //constructors
    /**
     * constructor #1.
     * @param e1 - expression 1.
     * @param e2 - expression 2.
     */
    public Plus(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     * constructor #2.
     * @param e1 - expression.
     * @param num - number.
     */
    public Plus(Expression e1, double num) {
        this.e1 = e1;
        this.e2 = new Num(num);
    }
    /**
     * constructor #3.
     * @param num - number.
     * @param e2 - expression.
     */
    public Plus(double num, Expression e2) {
        this.e1 = new Num(num);
        this.e2 = e2;
    }
    /**
     * constructor #4.
     * @param e1 - expression.
     * @param var - variable.
     */
    public Plus(Expression e1, String var) {
        this.e1 = e1;
        this.e2 = new Var(var);
    }
    /**
     * constructor #5.
     * @param var - variable.
     * @param e2 - expression.
     */
    public Plus(String var, Expression e2) {
        this.e1 = new Var(var);
        this.e2 = e2;
    }
    /**
     * constructor #6.
     * @param var - variable.
     * @param num - number.
     */
    public Plus(String var, double num) {
        this.e1 = new Var(var);
        this.e2 = new Num(num);
    }
    /**
     * constructor #7.
     * @param num - number.
     * @param var - variable.
     */
    public Plus(double num, String var) {
        this.e1 = new Num(num);
        this.e2 = new Var(var);
    }
    /**
     * constructor #8.
     * @param var1 - variable 1.
     * @param var2 - variable 2.
     */
    public Plus(String var1, String var2) {
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * constructor #9.
     * @param num1 - number 1.
     * @param num2 - number 2.
     */
    public Plus(double num1, double num2) {
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
        Expression evaluatedPlus = new Plus(this.e1.evaluate(assignment),
                this.e2.evaluate(assignment));
        return evaluatedPlus.evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return double - the value of the expression after the evaluation.
     * @throws Exception - evaluation of a variable or a math problem.
     */
    public double evaluate() throws Exception {
        return this.e1.evaluate() + this.e2.evaluate();
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
        return "(" + this.e1.toString() + " + " + this.e2.toString() + ")";
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
        return new Plus(this.e1.assign(var, expression),
                this.e2.assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     * @param var - the var string that we will differentiate by.
     * @return Expression - the expression after differentiation
     */
    public Expression differentiate(String var) {
        return new Plus(this.e1.differentiate(var), this.e2.differentiate(var));
    }
    /**
     * @return Expression - a simplified version of the current expression.
     */
    public Expression simplify() {
        // to make it recursive:
        Expression m1 = this.e1.simplify();
        Expression m2 = this.e2.simplify();
        // in case there are no variables - calculate.
        if (m1.getVariables().size() == 0
                && m2.getVariables().size() == 0) {
            try {
                return new Num(new Plus(m1, m2).evaluate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // X + 0 = X;
        if (super.equals(m1, new Num(0))) {
            return m2;
        // 0 + X = X
        } else if (super.equals(m2, new Num(0))) {
            return m1;
        }
        return new Plus(m1, m2);
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
        Expression result = bonusPartOne(m1, m2);
        if (result != null) {
            return result;
        }
        result = bonusPartTwo(m1, m2);
        if (result != null) {
            return result;
        }
        result = bonusPartThree(m1, m2);
        if (result != null) {
            return result;
        }
        return new Plus(m1, m2).simplify();
    }
    /**
     * In charge of the first part of the bonus.
     * @param m1 - the first expression
     * @param m2 - the second expression.
     * @return Expression - the expression after the simplifications.
     */
    public Expression bonusPartOne(Expression m1, Expression m2) {
        //X+X = 2X
        if (super.bonusEquals(m1, m2)) {
            return new Mult(m1, new Num(2)).bonusSimplify();
        }
        // (sin(x))^2 + (cos(x))^2 = 1
        if (m1.getClass() == Pow.class && m2.getClass() == Pow.class
                && bonusEquals(m1.getE2(), new Num(2))
                && bonusEquals(m2.getE2(), new Num(2))
                && ((m1.getE1().getClass() == Sin.class
                && m2.getE1().getClass() == Cos.class) || (m1.getE1().getClass() == Cos.class
                && m2.getE1().getClass() == Sin.class))
                && bonusEquals(m1.getE1().getE1(), m2.getE1().getE1())) {
            return new Num(1);
        }
        // ((X+Y)+X) = 2X + Y
        if (m1.getClass() == Plus.class) {
            if (bonusEquals(m1.getE1(), m2)) {
                return new Plus(new Mult(2, m2),
                        m1.getE2()).bonusSimplify();
            }
            if (bonusEquals(m1.getE2(), m2)) {
                return new Plus(new Mult(2, m2),
                        m1.getE1()).bonusSimplify();
            }
        // (X+(X+Y)) = 2X + Y
        } else if (m2.getClass() == Plus.class) {
            if (bonusEquals(m2.getE1(), m1)) {
                return new Plus(new Mult(2, m1),
                        m2.getE2()).bonusSimplify();
            }
            if (bonusEquals(m2.getE2(), m2)) {
                return new Plus(new Mult(2, m1),
                        m2.getE1()).bonusSimplify();
            }
        }
        if (m1.getClass() == Mult.class) {
            if (m2.getClass() == Mult.class) {
                // (A * B) + (A * C) = A*(B + C)
                if (bonusEquals(m1.getE1(), m2.getE1())) {
                    return new Mult(m1.getE1(), new Plus(m1.getE2(),
                            m2.getE2())).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE1())) {
                    return new Mult(m1.getE2(), new Plus(m1.getE1(),
                            m2.getE2())).bonusSimplify();
                }
                if (bonusEquals(m1.getE1(), m2.getE2())) {
                    return new Mult(m1.getE1(), new Plus(m1.getE2(),
                            m2.getE1())).bonusSimplify();
                }
                if (bonusEquals(m1.getE2(), m2.getE2())) {
                    return new Mult(m1.getE2(), new Plus(m1.getE1(),
                            m2.getE1())).bonusSimplify();
                }
            } // (A * B) + A = A * (B + 1)
            if (bonusEquals(m1.getE1(), m2)) {
                return new Mult(m2, new Plus(m1.getE2(),
                        new Num(1))).bonusSimplify();
            // (A * B) + A = A * (B + 1)
            } else if (bonusEquals(m1.getE2(), m2)) {
                return new Mult(m2, new Plus(m1.getE1(),
                        new Num(1))).bonusSimplify();
            }
        } else if (m2.getClass() == Mult.class) {
            // A + A * C = A * (C + 1)
            if (bonusEquals(m2.getE1(), m1)) {
                return new Mult(m1, new Plus(m2.getE2(),
                        new Num(1))).bonusSimplify();
            }
            if (bonusEquals(m2.getE2(), m1)) {
                return new Mult(m1, new Plus(m2.getE1(),
                        new Num(1))).bonusSimplify();
            }
        } //((-A) + B) = (B - A), (A + (-B))=(A - B)
        if (m1.getClass() == Neg.class && m2.getClass() != Neg.class) {
            return new Minus(m2, m1.getE1()).bonusSimplify();
        }
        if (m2.getClass() == Neg.class && m1.getClass() != Neg.class) {
            return new Minus(m1, m2.getE1()).bonusSimplify();
        } // -(A)+-(B) = -(A+B)
        if (m2.getClass() == Neg.class && m1.getClass() == Neg.class) {
            return new Neg(new Plus(m1.getE1(),
                    m2.getE1())).bonusSimplify();
        }
        return null;
    }
    /**
     * In charge of the second part of the bonus.
     * @param m1 - the first expression
     * @param m2 - the second expression.
     * @return Expression - the expression after the simplifications.
     */
    public Expression bonusPartTwo(Expression m1, Expression m2) {
        if (m1.getClass() == Plus.class) {
            if (m2.getClass() == Plus.class) {
                // (1 + X) + (1 + Y) = 2 + (X + Y)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE1(), m2.getE1()),
                            new Plus(m1.getE2(), m2.getE2()))
                            .bonusSimplify();
                } // (1 + X) + (Y + 1) = 2 + (X + Y)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE1(), m2.getE2()),
                            new Plus(m1.getE2(), m2.getE1()))
                            .bonusSimplify();
                } // (X + 1) + (1 + Y) = 2 + (X + Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE2(), m2.getE1()),
                            new Plus(m1.getE1(), m2.getE2()))
                            .bonusSimplify();
                } // (X + 1) + (Y + 1) = 2 + (X + Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE2(), m2.getE2()),
                            new Plus(m1.getE1(), m2.getE1()))
                            .bonusSimplify();
                }
            }
            if (m2.getClass() == Minus.class) {
                // (1 + X) + (1 - Y) = 2 + (X - Y)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE1(), m2.getE1()),
                            new Minus(m1.getE2(), m2.getE2()))
                            .bonusSimplify();
                } // (2 + X) + (Y - 1) = 1 + (X + Y)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m1.getE1(), m2.getE2()),
                            new Plus(m1.getE2(), m2.getE1()))
                            .bonusSimplify();
                } // (X + 1) + (1 - Y) = 2 + (X - Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE2(), m2.getE1()),
                            new Minus(m1.getE1(), m2.getE2()))
                            .bonusSimplify();
                } // (X + 2) + (Y - 1) = 1 + (X + Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m1.getE2(), m2.getE2()),
                            new Plus(m1.getE1(), m2.getE1()))
                            .bonusSimplify();
                }
            }
            if (m2.getVariables().size() == 0) {
                // (1 + X) + 1 = 2 + X
                if (m1.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m2, m1.getE1()),
                            m1.getE2()).bonusSimplify();
                } // (X + 1) + 1 = 2 + X
                if (m1.getE2().getVariables().size() == 0) {
                    return new Plus(new Plus(m2, m1.getE2()),
                            m1.getE1()).bonusSimplify();
                }
            } // The recursive follow ups of the actions
            if (containVar(m2, m1.getE1())
                    && m1.getE1().getClass() != Cos.class
                    && m1.getE1().getClass() != Sin.class
                    && m1.getE1().getClass() != Log.class
                    && m2.getClass() != Cos.class
                    && m2.getClass() != Sin.class
                    && m2.getClass() != Log.class) {
                return new Plus(m1.getE2(), new Plus(m2,
                        m1.getE1()).bonusSimplify()).bonusSimplify();
            }
            if (containVar(m2, m1.getE2())
                    && m1.getE1().getClass() != Cos.class
                    && m1.getE1().getClass() != Sin.class
                    && m1.getE1().getClass() != Log.class
                    && m2.getClass() != Cos.class
                    && m2.getClass() != Sin.class
                    && m2.getClass() != Log.class) {
                return new Plus(m1.getE1(), new Plus(m2,
                        m1.getE2())).bonusSimplify();
            }
        }
        if (m1.getClass() == Minus.class) {
            if (m2.getClass() == Minus.class) {
                // (2 - X) + (1 - Y) = 3 - (X + Y)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Minus(new Plus(m1.getE1(), m2.getE1()),
                            new Plus(m1.getE2(), m2.getE2()))
                            .bonusSimplify();
                } // (2 - X) + (Y - 1) = 1 + (Y - X)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m1.getE1(), m2.getE2()),
                            new Minus(m2.getE1(), m1.getE2()))
                            .bonusSimplify();
                } // (X - 1) + (2 - Y) = 1 + (X - Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Minus(m2.getE1(), m1.getE2()),
                            new Minus(m1.getE1(), m2.getE2()))
                            .bonusSimplify();
                } // (X - 1) + (Y - 2) = -1 + (X + Y)
                if (m1.getE2().getVariables().size() == 0
                        && m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Neg(new Plus(m1.getE2(), m2.getE2())),
                            new Plus(m1.getE1(), m2.getE1()))
                            .bonusSimplify();
                }
            }
            if (m2.getVariables().size() == 0) {
                // (1 - X) + 1 = 2 - X
                if (m1.getE1().getVariables().size() == 0) {
                    return new Minus(new Plus(m1.getE1(), m2),
                            m1.getE2()).bonusSimplify();
                } // (X - 1) + 2 = 1 + X
                if (m1.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m2, m1.getE2()),
                            m1.getE1()).bonusSimplify();
                }
            } // The recursive follow ups of the actions
            if (containVar(m2, m1.getE1()) && m1.getE1().getClass() != Cos.class
                    && m1.getE1().getClass() != Sin.class && m1.getE1().getClass() != Log.class
                    && m2.getClass() != Cos.class
                    && m2.getClass() != Sin.class
                    && m2.getClass() != Log.class) {
                return new Minus(new Plus(m2, m1.getE1())
                        , m1.getE2()).bonusSimplify();
            }
            if (containVar(m2, m1.getE2()) && m1.getE2().getClass() != Cos.class
                    && m1.getE2().getClass() != Sin.class && m1.getE2().getClass() != Log.class
                    && m2.getClass() != Cos.class
                    && m2.getClass() != Sin.class
                    && m2.getClass() != Log.class) {
                return new Plus(new Minus(m2, m1.getE2())
                        , m1.getE1()).bonusSimplify();
            }
        }
        return null;
    }
    /**
     * In charge of the third part of the bonus.
     * @param m1 - the first expression
     * @param m2 - the second expression.
     * @return Expression - the expression after the simplifications.
     */
    public Expression bonusPartThree(Expression m1, Expression m2) {
        if (m1.getClass() == Div.class) {
            if (containVar(m2, m1.getE1())) {
                return new Div(new Plus(new Mult(m2, m1.getE2())
                        , m1.getE1()), m1.getE2()).bonusSimplify();
            }
        }
        if (m2.getClass() == Plus.class) {
            if (m1.getClass() == Minus.class) {
                // (1 - X) + (1 + Y) = 2 + (Y - X)
                if (m1.getE1().getVariables().size() == 0
                        && m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1.getE1(), m2.getE1()),
                            new Minus(m2.getE2(), m1.getE2()))
                            .bonusSimplify();
                }
                // (1 - X) + (Y + 1) = 2 + (Y - X)
                if (m2.getE1().getVariables().size() == 0
                        && m1.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m2.getE1(), m1.getE2()),
                            new Plus(m2.getE2(), m1.getE1()))
                            .bonusSimplify();
                }
                // (X - 1) + (2 + Y) = 1 + (Y + X)
                if (m2.getE2().getVariables().size() == 0
                        && m1.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m2.getE2(), m1.getE1()),
                            new Minus(m2.getE1(), m1.getE2()))
                            .bonusSimplify();
                }
                // (X - 1) + (Y + 2) = 1 + (X + Y)
                if (m2.getE2().getVariables().size() == 0
                        && m1.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m2.getE2(), m1.getE2()),
                            new Plus(m2.getE1(), m1.getE1()))
                            .bonusSimplify();
                }
            }
            if (m1.getVariables().size() == 0) {
                // 1 + (1 + X) = 2 + X
                if (m2.getE1().getVariables().size() == 0) {
                    return new Plus(new Plus(m1, m2.getE1()),
                            m2.getE2()).bonusSimplify();
                }
                // 1 + (X + 1) = 2 + X
                if (m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Plus(m1, m2.getE2()),
                            m2.getE1()).bonusSimplify();
                }
            }
            // The recursive follow ups of the actions
            if (containVar(m1, m2.getE1())
                    && m2.getE1().getClass() != Cos.class
                    && m2.getE1().getClass() != Sin.class
                    && m2.getE1().getClass() != Log.class
                    && m1.getClass() != Cos.class
                    && m1.getClass() != Sin.class
                    && m1.getClass() != Log.class) {
                return new Plus(m2.getE2(), new Plus(m1,
                        m2.getE1())).bonusSimplify();
            }
            if (containVar(m1, m2.getE2())
                    && m2.getE2().getClass() != Cos.class
                    && m2.getE2().getClass() != Sin.class
                    && m2.getE2().getClass() != Log.class
                    && m1.getClass() != Cos.class
                    && m1.getClass() != Sin.class
                    && m1.getClass() != Log.class) {
                return new Plus(m2.getE1(), new Plus(m1,
                        m2.getE2())).bonusSimplify();
            }
        }
        if (m2.getClass() == Minus.class) {
            if (m1.getVariables().size() == 0) {
                // 1 + (1 - X) = 2 - X
                if (m2.getE1().getVariables().size() == 0) {
                    return new Minus(new Plus(m2.getE1(), m1),
                            m2.getE2()).bonusSimplify();
                }
                // 2 + (X - 1) = 1 + X
                if (m2.getE2().getVariables().size() == 0) {
                    return new Plus(new Minus(m1, m2.getE2()),
                            m2.getE1()).bonusSimplify();
                }
            }
            // The recursive follow ups of the actions
            if (containVar(m1, m2.getE1())
                    && m2.getE1().getClass() != Cos.class
                    && m2.getE1().getClass() != Sin.class
                    && m2.getE1().getClass() != Log.class
                    && m1.getClass() != Cos.class
                    && m1.getClass() != Sin.class
                    && m1.getClass() != Log.class) {
                return new Minus(new Plus(m1, m2.getE1())
                        , m2.getE2()).bonusSimplify();
            }
            if (containVar(m1, m2.getE2())
                    && m2.getE1().getClass() != Cos.class
                    && m2.getE1().getClass() != Sin.class
                    && m2.getE1().getClass() != Log.class
                    && m1.getClass() != Cos.class
                    && m1.getClass() != Sin.class
                    && m1.getClass() != Log.class) {
                return new Plus(new Minus(m1, m2.getE2())
                        , m2.getE1()).bonusSimplify();
            }
        }
        // X + (X + Y) / N = (X + (X + Y)) / N
        if (m2.getClass() == Div.class) {
            if (containVar(m1, m2.getE1())) {
                return new Div(new Plus(new Mult(m1, m2.getE2())
                        , m2.getE1()), m2.getE2()).bonusSimplify();
            }
        }
        return null;
    }
}
