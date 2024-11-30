package expressivo;

import java.util.Objects;

/**
 * Immutable class representing multiplication of two expressions.
 */
public class Multiplication implements Expression {
    private final Expression left, right;

    // Abstraction function:
    //   represents the multiplication of two expressions: left * right
    // Rep invariant:
    //   left and right are not null
    // Safety from rep exposure:
    //   left and right are private, final, and immutable

    public Multiplication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }

    private void checkRep() {
        assert left != null && right != null;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Multiplication)) return false;
        Multiplication thatMultiplication = (Multiplication) that;
        return this.left.equals(thatMultiplication.left) && this.right.equals(thatMultiplication.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
