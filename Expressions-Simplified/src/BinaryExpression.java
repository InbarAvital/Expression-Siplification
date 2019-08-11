import java.util.List;
/**
 * @author Inbar Avital
 * This expression is in charge of all of the functions used
 * in all, and only, in the binary expressions.
 */
public abstract class BinaryExpression extends BaseExpression {
    /**
     * Get the variables from two different expressions together.
     * This is used for the binary expressions - checks the two
     * expressions inside the binary expressions.
     * @param e1 - the first expression.
     * @param e2 - the second expression.
     * @return List<String> - the list of the variables.
     */
    public List<String> getVariables(Expression e1, Expression e2) {
        // get the first list of the first expression
        List<String> variables = e1.getVariables();
        // add the second list of the second expression.
        for (String var: e2.getVariables()) {
            variables.add(var);
        }
        // remove doubles.
        for (int i = 0; i < variables.size(); i++) {
            for (int j = i + 1; j < variables.size(); j++) {
                if (variables.get(i) == variables.get(j)) {
                    variables.remove(i);
                }
            }
        }
        // return the updated list.
        return variables;
    }

}
