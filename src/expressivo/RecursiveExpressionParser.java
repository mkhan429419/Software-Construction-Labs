package expressivo;

public class RecursiveExpressionParser {

    // Main method to evaluate an expression
    public static double evaluateExpression(String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }

        // Remove all spaces from the expression for easier processing
        expression = expression.replaceAll("\\s+", "");
        return evaluate(expression, 0, expression.length() - 1);
    }

    // Recursive method to evaluate a sub-expression
    private static double evaluate(String expr, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Invalid expression");
        }

        // Handle parentheses
        if (expr.charAt(start) == '(' && expr.charAt(end) == ')') {
            return evaluate(expr, start + 1, end - 1);
        }

        // Process addition and subtraction (lowest precedence)
        int index = findOperator(expr, start, end, '+', '-');
        if (index != -1) {
            double left = evaluate(expr, start, index - 1);
            double right = evaluate(expr, index + 1, end);
            return expr.charAt(index) == '+' ? left + right : left - right;
        }

        // Process multiplication and division (higher precedence)
        index = findOperator(expr, start, end, '*', '/');
        if (index != -1) {
            double left = evaluate(expr, start, index - 1);
            double right = evaluate(expr, index + 1, end);
            if (expr.charAt(index) == '/') {
                if (right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return left / right;
            }
            return left * right;
        }

        // Handle numbers
        try {
            return Double.parseDouble(expr.substring(start, end + 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }
    }

    // Find the operator in the expression that is not within parentheses
    private static int findOperator(String expr, int start, int end, char op1, char op2) {
        int parenthesesCount = 0;
        for (int i = start; i <= end; i++) {
            char ch = expr.charAt(i);
            if (ch == '(') {
                parenthesesCount++;
            } else if (ch == ')') {
                parenthesesCount--;
            } else if ((ch == op1 || ch == op2) && parenthesesCount == 0) {
                return i;
            }
        }
        return -1;
    }
}
