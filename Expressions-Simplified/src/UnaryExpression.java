import java.util.List;
/**
 * @author Inbar Avital
 *
 */
public abstract class UnaryExpression extends BaseExpression {
    /**
     * @param e1 - an expression.
     * @return List<String> - a list of variables in the expression given.
     */
    public List<String> getVariables(Expression e1) {
        return e1.getVariables();
    }
}
