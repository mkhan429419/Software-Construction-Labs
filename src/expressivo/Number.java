package expressivo;

import java.util.Objects;

/**
 * Immutable class representing a numeric expression.
 */
public class Number implements Expression {
    private final double value;

    // Abstraction function:
    //   represents the numeric value of an expression
    // Rep invariant:
    //   true
    // Safety from rep exposure:
    //   value is a private and immutable primitive type

    /**
     * Create a Number.
     * @param value numeric value
     */
    public Number(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Number)) return false;
        Number thatNumber = (Number) that;
        return Double.compare(this.value, thatNumber.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
