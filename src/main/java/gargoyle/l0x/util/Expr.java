package gargoyle.l0x.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Objects;

@Slf4j
public enum Expr {
    ;

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public static Object eval(String expression, Object root, Object defaultValue) {
        try {
            Object eval = eval(expression, root);
            return null == eval || Objects.equals("", eval) ? defaultValue : eval;
        } catch (ParseException | EvaluationException e) {
            log.error(e.getLocalizedMessage());
            return defaultValue;
        }
    }

    public static Object eval(String expression, Object root) {
        return PARSER.parseExpression(expression).getValue(root);
    }
}
