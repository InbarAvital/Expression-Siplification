/**
 * @author Inbar Avital
 *
 */
public class SimplificationDemo {

    /**
     * @param args - the main arguments, in our case, empty.
     * @throws Exception -  evaluation of a var or a math mistake.
     */
    public static void main(String[] args) throws Exception {

        // Trigo
        trigonometricExamples();

        // Div
        divisionExamples();

        // Pow
        powExamples();

        // Log
        logExamples();

        // Neg
        negativeExamples();

        // Plus
        plusExamples();

        // Minus
        minusExamples();

        // Mult
        multiplyExamples();

        // Extras
        extras();
    }


    /**
     * Shows examples of trigo simplifications I did.
     */
    private static void trigonometricExamples() {
        System.out.println("\n\n~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~- SIMPLIFICATIONS"
                + " ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
        System.out.println("\n\n\n________________Trigonometric Simplification:________________");

        System.out.println("\n(1) Cos(x)^2 + Sin(x)^2 = 1:");
        System.out.println("----------------------------");
        // ((cos(x)^2.0) + (sin(x)^2.0)) ==> 1
        Expression e = new Plus(new Pow(new Cos("x"), 2), new Pow(new Sin("x"), 2));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((cos((x/19) - y)^2.0) + (sin((x/19) - y)^2.0)) ==> 1
        e = new Plus(new Div("x", 3), new Neg("y"));
        e = new Plus(new Pow(new Cos(e), 2), new Pow(new Sin(e), 2));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        System.out.println("\n(2) 1 - Cos(x)^2 = Sin(x)^2:");
        System.out.println("----------------------------");
        // 1 - cos(x)^2 ==> sin(x)^2
        e = new Minus(1, new Pow(new Cos("x"), 2));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Minus(1, new Pow(new Cos(new Plus(new Mult("n", "m"), new Log("b", new Mult("a", "c")))), 2));
        // 1 - cos(n*m + log(b, a*c))^2 ==> sin(n*m + log(b, a*c))^2
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) 1 - Sin(x)^2 = Cos(x)^2:");
        System.out.println("----------------------------");
        // 1 - sin(x)^2 ==> cos(x)^2
        e = new Minus(1, new Pow(new Sin("x"), 2));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus(new Mult("n", "m"), new Log("b", new Mult("a", "c")));
        e = new Minus(1, new Pow(new Sin(e), 2));
        // 1 - sin(n*m + log(b, a*c))^2 ==> cos(n*m + log(b, a*c))^2
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) Sin(-x) = -Sin(x):");
        System.out.println("----------------------");
        // sin(-X) ==> -sin(X)
        e = new Sin(new Neg("x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // sin(-(x + (y - z))) ==> -sin((x + (y - z)))
        e = new Sin(new Neg(new Plus("x", new Minus("y", "z"))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) Cos(-x) = Cos(x):");
        System.out.println("---------------------");
        // cos(-x) ==> cos(x)
        e = new Cos(new Neg("x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // cos(-(x + (y - z))) ==> cos((x + (y - z)))
        e = new Cos(new Neg(new Plus("x", new Minus("y", "z"))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * Shows examples of division simplifications I did.
     */
    private static void divisionExamples() {
        System.out.println("\n\n\n\n________________Division Simplification:________________");
        Expression e;
        System.out.println("\n(1) 0 / X = 0:");
        System.out.println("-------------");
        // (0 / x) --> 0
        e = new Div(0, "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((1 - (6 - (2 + 3))) / (x + (3 + (y + 6)))) ==> 0
        e = new Div(new Minus(1, new Minus(6, new Plus(2, 3))), new Plus("x", new Plus(3, new Plus("y", 6))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) X / X = 1:");
        System.out.println("-------------");
        // (x / x) --> 1
        e = new Div("x", "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + (6 + (y + 3))) / (x + (3 + (y + 6)))) ==> 1
        e = new Div(new Plus("x", new Plus(6, new Plus("y", 3))), new Plus("x", new Plus(3, new Plus("y", 6))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) X^N / X = X^(N-1):");
        System.out.println("----------------------");
        // x^5 / x --> x^4
        e = new Div(new Pow("x", 5), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x + y)^5 / (x + y) ==> (x + y)^4
        e = new Plus("x", "y");
        e = new Div(new Pow(e, 5), e);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) X^N / X^M = X^(N-M):");
        System.out.println("------------------------");
        // x^5 / x^4 ==> x
        e = new Div(new Pow("x", 5), new Pow("x", 4));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // x^a / x^b ==> x^(a-b)
        e = new Div(new Pow("x", "a"), new Pow("x", "b"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) X^M / Y^M = (X / Y)^M:");
        System.out.println("--------------------------");
        // x^m / y^m ==> (x/y)^m
        e = new Div(new Pow("x", "m"), new Pow("y", "m"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (n * x)^m / (n * y)^m ==> (x/y)^m
        e = new Div(new Pow(new Mult("n", "x"), "m"), new Pow(new Mult("n", "y"), "m"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(6) X / (Y / Z) = (X * Z) / Y:");
        System.out.println("------------------------------");
        // x^5 / x^4 = x
        e = new Div("x", new Div("y", "z"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x / (y / z)) ==> ((x * z) / y)
        e = new Div(5, new Div("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(7) (X * Y) / Y = X:");
        System.out.println("--------------------");
        // ((x * y) / y) ==> x
        e = new Div(new Mult("x", "y"), "y");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((y * x) / y) ==> x
        e = new Div(new Mult("y", "x"), "y");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(8) Y / (Y * X) = 1 / X:");
        System.out.println("------------------------");
        // (x / (x * y)) ==> (1 / y)
        e = new Div("x", new Mult("x", "y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x / (y * x)) ==> (1 / y)
        e = new Div("x", new Mult("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(9) (X * Y) / (Y * Z) = X / Z:");
        System.out.println("------------------------------");
        // ((x * y) / (y * z)) ==> (x / z)
        e = new Div(new Mult("x", "y"), new Mult("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((5 * x) / (y * x)) ==> (5 / y)
        e = new Div(new Mult("x", 5), new Mult("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x * y) / (y * x)) ==> 1
        e = new Div(new Mult("x", "y"), new Mult("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * Shows examples of power simplifications I did.
     */
    private static void powExamples() {
        System.out.println("\n\n\n\n________________Pow Simplification:________________");

        System.out.println("\n(1) (X^Y)^Z = X^(Y * Z):");
        System.out.println("------------------------");
        // ((x^y)^z) ==> (x^(y * z))
        Expression e = new Pow(new Pow("x", "y"), "z");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x^4)^5) ==> (x^20)
        e = new Pow(new Pow("x", 4), 5);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) 1 ^ X = 1:");
        System.out.println("--------------");
        // (1^x) ==> 1
        e = new Pow(1, "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (1^(x + (y - z))) ==> 1
        e = new Pow(1, new Plus("x", new Minus("y", "z")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) 0 ^ X = 0:");
        System.out.println("--------------");
        // (0^x) ==> 0
        e = new Pow(0, "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (0^(x + (y - z))) ==> 0
        e = new Pow(0, new Plus("x", new Minus("y", "z")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) X ^ 0 = 1:");
        System.out.println("--------------");
        // (x^0) ==> 1
        e = new Pow("X", 0);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + (y - z))^0) ==> 1
        e = new Pow(new Plus("x", new Minus("y", "z")), 0);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (0^0) ==> 1
        e = new Pow(0, 0);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) X ^ 1 = X:");
        System.out.println("--------------");
        // (x^1) ==> x
        e = new Pow("x", 1);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + (y - z))^1) ==> (x + (y - z))
        e = new Pow(new Plus("x", new Minus("y", "z")), 1);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(6) X ^ -1 = 1 / X:");
        System.out.println("-------------------");
        // (x^-1) ==> (1 / x)
        e = new Pow("X", -1);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + (y - z))^-1) ==> 1 / (x + (y - z))
        e = new Pow(new Plus("x", new Minus("y", "z")), -1);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(7) X ^ (-Y) = 1 / X^Y:");
        System.out.println("-----------------------");
        // (x^-(1)) ==> 1 / X
        e = new Pow("x", new Neg(1));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + (y - z))^-(w)) ==> 1 / (x + (y - z))^w
        e = new Pow(new Plus("x", new Minus("y", "z")), new Neg("w"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * Shows examples of log simplifications I did.
     */
    private static void logExamples() {
        System.out.println("\n\n\n\n________________Log Simplification:________________");

        System.out.println("\nLog(A, X ^ N) = N * Log(A, X):");
        System.out.println("------------------------------");
        // log(a, (x^n)) ==> (n * log(a, x))
        Expression e = new Log("a", new Pow("x", "n"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // log((z - w)), (x^y)) ==> (y * log((z - w), x))
        e = new Log(new Minus("z", "w"), new Pow("x", "y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * .
     */
    private static void negativeExamples() {
        System.out.println("\n\n\n\n________________Negative Simplification:________________");

        System.out.println("\n(1) Neg(Num) = -Num:");
        System.out.println("--------------------");
        // -(-1) ==> 1
        Expression e = new Neg(-1);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // -(-7.3) ==> 7.3
        e = new Neg(-7.3);
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) -(-X) = X:");
        System.out.println("--------------");
        // (-(-x)) ==> x
        e = new Neg(new Neg("x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (-(-(x + (4 * x)))) ==> (5 * x)
        e = new Neg(new Neg(new Plus("x", new Mult(4, "x"))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * Shows examples of plus simplifications I did.
     */
    private static void plusExamples() {
        System.out.println("\n\n\n\n________________Plus Simplification:________________");

        System.out.println("\n(1) X + X = 2 * X:");
        System.out.println("------------------");
        // (x + x) ==> (2 * x)
        Expression e = new Plus("X", "X");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + y) + (x + y)) ==> (2 * (x + y))
        e = new Plus(new Plus("x", "y"), new Plus("x", "y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) (X + Y) + (Z + X)) = 2 * X + (Y + Z):");
        System.out.println("-----------------------------------------");
        // (x + y) + (z + x)) ==> (y + ((2 * x)+ z))
        e = new Plus(new Plus("x", "y"), new Plus("z", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + z) + (x + y)) + ((2 * x) + x)) ==> (y + ((5 * x) + z))
        e = new Plus(new Plus(new Plus("x", "z"), new Plus("x", "y")),
                new Plus(new Mult(2, "x"), "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) Same But Recursive:");
        System.out.println("-----------------------");
        // (x + (y + (n + (m + (s + x))))) ==> (y + (n + (m + (s + (2 * x)))))
        e = new Plus("x" , new Plus("y", new Plus("n", new Plus("m", new Plus("s", "x")))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((n * x) + (2 + ((x * m) + 1))) ==> (3 + (x * (n + m))
        e = new Plus(new Mult("n", "x"), new Plus(2, new Plus(new Mult("x", "m"), 1)));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((2 + ((4 * x) + 1)) + (x * 3)) ==> (3 + (7 * x))
        e = new Plus(new Plus(2, new Plus(new Mult(4, "x"), 1)), new Mult("x", 3));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((5 * x) + ((-3 * x) + (y + 1))) ==> ((2* x) + (1 + y))
        e = new Plus(new Mult(5, "x"), new Plus(new Mult(-3, "x"), new Plus("y", 1)));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) (X + (Y + X)) = 2 * X + Y:");
        System.out.println("------------------------------");
        // (x + (y + x)) ==> ((2 * x) + y)
        e = new Plus("x", new Plus("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + x) + (y + (x + x))) ==> (4 * x + y)
        e = new Plus(new Plus("x", "x"), new Plus("y", new Plus("x", "x")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) N * X + X = (N + 1) * X:");
        System.out.println("----------------------------");
        // (n * x) + x ==> x*(n+1)
        e = new Plus(new Mult("n", "x"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((2 * x) + x) ==> (3 * x)
        e = new Plus(new Mult(2, "x"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(6) (N * X) + (M * X) = (N + M) * X:");
        System.out.println("------------------------------------");
        // ((a * x) + (b * x)) ==> ((a + b) * x)
        e = new Plus(new Mult("a", "x"), new Mult("b", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((2 * x) + (x * 4)) ==> (6 * x)
        e = new Plus(new Mult(2, "x"), new Mult("x", 4));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((n * x) + (5 * x)) ==> ((5 + n) * x)
        e = new Plus(new Mult("n", "x"), new Mult(5, "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(7) (-A) + (-B) = -(A + B):");
        System.out.println("---------------------------");
        // (-x) + (-y) ==> -(x + y)
        e = new Plus(new Neg("x"), new Neg("y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (-(a + b)) + (-(c + d)) ==> (-(d + (c + (a + b))))
        e = new Plus(new Neg(new Plus("a", "b")), new Neg(new Plus("c", "d")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (-(a + b)) + (-(a - b)) ==> (-(2 * a))
        e = new Plus(new Neg(new Plus("a", "b")), new Neg(new Minus("a", "b")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(8) A + (-B) = A - B:");
        System.out.println("---------------------");
        // (x + (-y)) ==> (x - y)
        e = new Plus("x", new Neg("y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((a + b) + (-(a - b))) ==> (2 * b)
        e = new Plus(new Plus("a", "b"), new Neg(new Minus("a", "b")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(9) (-A) + B = B - A:");
        System.out.println("---------------------");
        // ((-y) + x) ==> (x - y)
        e = new Plus(new Neg("y"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((-(a - b)) + (a + b)) ==> (2 * b)
        e = new Plus(new Neg(new Minus("a", "b")), new Plus("a", "b"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(10) (num1 + X) + (Z + num2)= (X + Z) + (num1 + num2): (also with mult and minus)");
        System.out.println("-----------------------------------------------------");
        // ((5 + x) + (z + 5)) ==> 10 + (x + z)
        e = new Plus(new Plus(5, "x"), new Plus("z", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x * 4) + (x + 5)) ==> 5 * (x + 1)
        e = new Plus(new Mult("x", 4), new Plus("x", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x * 4) + (x - 4)) ==> (x * 5) - 4
        e = new Plus(new Mult("x", 4), new Minus("x", 4));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }

    /**
     * Shows examples of minus simplifications I did.
     */
    private static void minusExamples() {
        System.out.println("\n\n\n\n________________Minus Simplification:________________");

        System.out.println("\n(1) (X + Y) - (Z + X)) = (Y - Z):");
        System.out.println("---------------------------------");
        // (x + y) - (z + x)) ==> y - z
        Expression e = new Minus(new Plus("x", "y"), new Plus("z", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) Same But Recursive:");
        System.out.println("-----------------------");
        // (x - (y - (n - (m - (s - x))))) ==> - (((s - m) + n) - y)
        e = new Minus("x" , new Minus("y", new Minus("n", new Minus("m", new Minus("s", "x")))));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((n * x) - (2 + ((m * 2) + 1))) ==> (x* (n - m) - 3)
        e = new Minus(new Mult("n", "x"), new Plus(2, new Plus(new Mult("x", "m"), 1)));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((2 + ((4 * x) + 1)) - (x * 3)) ==> (3 + x))
        e = new Minus(new Plus(2, new Plus(new Mult(4, "x"), 1)), new Mult("x", 3));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((5 * x) - ((-3 * x) + (y + 1))) ==> ((8 * x) - (1 + y))
        e = new Minus(new Mult(5, "x"), new Plus(new Mult(-3, "x"), new Plus("y", 1)));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) (X - (Y + X)) = - Y:");
        System.out.println("------------------------");
        // (x - (y + x)) ==> -y
        e = new Minus("x", new Plus("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + x) - (y + (x + x))) ==> -y
        e = new Minus(new Plus("x", "x"), new Plus("y", new Plus("x", "x")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) N * X - X = (N - 1) * X:");
        System.out.println("----------------------------");
        // (n * x) - x ==> x*(n-1)
        e = new Minus(new Mult("n", "x"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((2 * x) - x) ==> (x)
        e = new Minus(new Mult(2, "x"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) (N * X) - (M * X) = (N - M) * X:");
        System.out.println("------------------------------------");
        // ((a * x) - (b * x)) ==> ((a - b) * x)
        e = new Minus(new Mult("a", "x"), new Mult("b", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((4 * x) - (x * 2)) ==> (2 * x)
        e = new Minus(new Mult(4, "x"), new Mult("x", 2));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((n * x) - (5 * x)) ==> ((n - 5) * x)
        e = new Minus(new Mult("n", "x"), new Mult(5, "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(6) (-A) - (-B) = B - A :");
        System.out.println("-------------------------");
        // (-x) - (-y) ==> y - x
        e = new Minus(new Neg("x"), new Neg("y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (-(a + b)) - (-(c + d)) ==>  ((x + d) - (a + b))
        e = new Minus(new Neg(new Plus("a", "b")), new Neg(new Plus("c", "d")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(7) A - (-B) = A + B:");
        System.out.println("---------------------");
        // (x - (-y)) ==> (x + y)
        e = new Minus("x", new Neg("y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((a + b) - (-(a - b))) ==>  2 * a
        e = new Minus(new Plus("a", "b"), new Neg(new Minus("a", "b")));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(8) (-A) - B = -(A + B):");
        System.out.println("------------------------");
        // ((-x) - y) ==> -(x + y)
        e = new Minus(new Neg("x"), "y");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((-(a - b)) - (a + b)) ==> -(2 * a)
        e = new Minus(new Neg(new Minus("a", "b")), new Plus("a", "b"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(9) (num1 + X) - (Z + num2)= (X - Z) + (num1 - num2): (also with mult and minus)");
        System.out.println("-----------------------------------------------------");
        // ((5 - x) + (z - 5)) ==> (x - z)
        e = new Plus(new Minus(5, "x"), new Minus("z", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x * 4) - (x + 3)) ==>  3 * (x - 1)
        e = new Minus(new Mult("x", 4), new Plus("x", 3));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x - 4) - (x + 4)) ==> -8
        e = new Minus(new Minus("x", 4), new Plus("x", 4));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }

    /**
     * Shows examples of multiply simplifications I did.
     */
    private static void multiplyExamples() {
        System.out.println("\n\n\n\n________________Multiply Simplification:________________");

        System.out.println("\n(1) X * X = X^2:");
        System.out.println("----------------");
        // (x * x) ==> (x^2)
        Expression e = new Mult("x", "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x + y) * (x + y)) ==> ((x + y)^2)
        e = new Mult(new Plus("x", "y"), new Plus("x", "y"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(2) (X^A) * (X^B)  ==>  X^(A + B):");
        System.out.println("----------------------------------");
        // (x^a) * (x^b)  ==>  x^(a + b)
        e = new Mult(new Pow("x", "a"), new Pow("x", "b"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x^3.5) * (x^4.5)  ==>  x^(8)
        e = new Mult(new Pow("x", 3.5), new Pow("x", 4.5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(3) (X^A) * X  ==>  X^(A + 1):");
        System.out.println("------------------------------");
        // (x^a) * x  ==>  x^(a + 1)
        e = new Mult(new Pow("x", "a"), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x^4) * x  ==>  x^(5)
        e = new Mult(new Pow("x", 4), "x");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(4) X * (X^A)  ==>  X^(A + 1):");
        System.out.println("------------------------------");
        // x * (x^a)  ==>  x^(a + 1)
        e = new Mult("x", new Pow("x", "a"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // x * (x^4)  ==>  x^(5)
        e = new Mult("x", new Pow("x", 4));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(5) Y * (X / Y) = Y:");
        System.out.println("--------------------");
        // (x * (1 / x)) ==> 1
        e = new Mult("x", new Div(1, "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x / y) * (y / x)) ==> 1
        e = new Mult(new Div("x", "y"), new Div("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (n / m) * m ==> n
        e = new Mult(new Div("n", "m"), "m");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        System.out.println("\n(6) (Y / X) * (X * Z)= Y * Z:");
        System.out.println("-----------------------------");
        // ((y / x) * (x * 5)) ==> 5 * y
        e = new Mult(new Div("y", "x"), new Mult("x", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((x * y) * (y / x)) ==> y ^ 2
        e = new Mult(new Mult("x", "y"), new Div("y", "x"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        System.out.println("\n(7) (num1 * X) * (Z * num2)= (X * Z) * (num1 * num2):");
        System.out.println("-----------------------------------------------------");
        // ((4 * x) * (z * 5)) ==> 20 * (x * z)
        e = new Mult(new Mult(4, "x"), new Mult("z", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x * 4) * (z * 5)) ==> 20 * (x * z)
        e = new Mult(new Mult("x", 4), new Mult("z", 5));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (x * 4) * (5 * z)) ==> 20 * (x * z)
        e = new Mult(new Mult("x", 4), new Mult(5, "z"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // (4 * x) * (5 * z)) ==> 20 * (x * z)
        e = new Mult(new Mult(4, "x"), new Mult(5, "z"));
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }


    /**
     * Some more simplifications of larger expressions.
     */
    private static void extras() {
        Expression e;
        System.out.println("\n\n\n\n________________Puting It All Together:________________");

        // (((4 * x) + (x * 5)) + (((-x) + 0.25) - (y / (4 * y)))) ==> (8 * x)
        e = new Plus(new Plus(new Mult(4, "x"), new Mult("x", 5)),
                new Minus(new Plus(new Neg("x"), 0.25), new Div("y", new Mult(4, "y"))));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus(new Plus(new Plus("x", new Plus("z", "z")), new Plus("z", new Plus("x", "y"))),
                new Plus(new Plus("x", "y"), new Plus("y", "z")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus(new Plus(new Plus("x", new Plus("z", "z")), new Minus("z", new Plus("x", "y"))),
                new Plus(new Minus("x", "y"), new Plus("y", "z")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus(new Plus(new Plus(2, new Plus("z", 11)), new Minus(7, new Plus(11, "y"))),
                new Plus(new Minus(6, new Plus(1.5, "y")), new Plus(7, "z")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus(new Neg(new Mult(new Div("y", new Neg(new Div("y", "x"))), 0)),
                new Neg(new Plus(new Neg(new Neg(new Minus(new Minus(-2, 0), 4))), "x")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = e.differentiate("x");
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Mult(new Mult(new Mult(20, new Pow("x", 5)), new Mult(2, new Pow("e", "x"))),
                new Mult(new Cos(new Pow("x", 2)), new Minus("x", "x")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Mult(new Mult(new Pow("e", "x"), new Div(new Pow(new Pow("e", "x"), 2),
                new Pow(new Pow("e", "x"), 3))), new Mult(new Pow(new Pow("e", "x"), 4),
                        new Div(new Pow(new Pow("e", "x"), 3), new Pow(new Pow("e", "x"), 7))));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        e = new Plus("x", new Plus("j", new Plus("i", new Plus("h", new Plus("g", new Plus("f",
                new Plus("e", new Plus("d", new Plus("c", new Plus("b", new Plus("a", "x")))))))))));
        System.out.println("\n\t Just to show how far can it go(endless) - notice the 2*x in the end:");
        System.out.println("\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
        // ((-y) + x + ((-x) + ((-y) + y))) ==> -y
        e = new Plus(new Plus(new Neg("y"), "x"), new Plus(new Neg("x"), new Plus(new Neg("y"), "y")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // ((-(a - b)) + (a + b)) ==> (2 * b)
        e = new Plus(new Neg(new Minus("a", "b")), new Plus("a", "b"));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // ((-(a - b)) - ((-a) + (-b))) ==> (2 * b)
        e = new Minus(new Neg(new Minus("a", "b")), new Plus(new Neg("a"), new Neg("b")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // log((1/(x^-1)), (y * (x^5 * x^-(4))) / y) ==> 1
        e = new Log(new Div(1, new Pow("x", -1)),
                new Div(new Mult("y", new Mult(new Pow("x", 5), new Pow("x", new Neg(4)))), "y"));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // (x + (3 + (x + 5))) / (4 + (x + (2 + (x + 2)))) ==> 1
        e = new Div(new Plus("x", new Plus(3, new Plus("x", 5))),
                new Plus(4, new Plus("x", new Plus(2, new Plus("x", 2)))));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // (((x + 32) * y) / ((32 + x) * y)) ==> 1
        e = new Div(new Mult(new Plus("x", 32), "y"), new Mult("y", new Plus(32, "x")));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());

        // ((((y^4) / (y * y) * cos(-x)) - (-(-1) - 0)) ==> ((cos(x) * (y^3)) - 1)
        e = new Minus(new Mult(new Div(new Pow("y", 5), new Mult("y", "y")), new Cos(new Neg("x"))),
                new Neg(new Minus(new Neg(1), 0)));
        System.out.println("\n\t" + e.toString() + " ==simplified==> " + e.bonusSimplify().toString());
    }
}

