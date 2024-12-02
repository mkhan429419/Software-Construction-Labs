package expressivo;

import java.util.Objects;

/**
 * Immutable class representing a variable.
 */
public class Variable implements Expression {
    private final String name;

    // Abstraction function:
    //   represents a variable with a name
    // Rep invariant:
    //   name is not null and is not empty
    // Safety from rep exposure:
    //   name is private and final, and Strings are immutable

    /**
     * Create a Variable.
     * @param name variable name
     */
    public Variable(String name) {
        this.name = name;
        checkRep();
    }
    
    public String getName() {
        return name;
    }

    private void checkRep() {
        assert name != null && !name.isEmpty();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Variable)) return false;
        Variable thatVariable = (Variable) that;
        return this.name.equals(thatVariable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    public Expression differentiate(String variable) {
        return variable.equals(name) ? new Number(1) : new Number(0);
    }
}
