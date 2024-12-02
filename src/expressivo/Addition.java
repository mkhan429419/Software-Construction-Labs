package expressivo;

import java.util.Objects;

/**
 * Immutable class representing addition of two expressions.
 */
public class Addition implements Expression {
    private final Expression left, right;

    // Abstraction function:
    //   represents the addition of two expressions: left + right
    // Rep invariant:
    //   left and right are not null
    // Safety from rep exposure:
    //   left and right are private, final, and immutable

    public Addition(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }
    
    public Expression getLeft() {
        return left;
    }

    // Getter for right
    public Expression getRight() {
        return right;
    }

    private void checkRep() {
        assert left != null && right != null;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }


    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Addition)) return false;
        Addition thatAddition = (Addition) that;
        return this.left.equals(thatAddition.left) && this.right.equals(thatAddition.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
    
    public Expression differentiate(String variable) {
        return new Addition(left.differentiate(variable), right.differentiate(variable));
    }
}
