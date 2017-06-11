package oop.ex6.validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.command_validity.CommandLine;

import java.util.regex.Pattern;

public class Variable {

    public enum Type {INT, DOUBLE, STRING, BOOLEAN, CHAR}
    private final String name;
    private Type type;
    private boolean assigned;
    private boolean isFinal;

    /**
     * Constructor fot a variable object.
     * @param name the variable's name.
     * @param type the variable's type
     * @param assigned if the variable has assigned
     * @param isFinal if the variable is final or not
     */
    public Variable(String name, Type type, boolean assigned, boolean isFinal){
        this.name = name;
        this.type = type;
        this.assigned = assigned;
        this.isFinal = isFinal;
    }

    /**
     * @return the variable's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the variable's type
     */
    public Type getType() {
        return type;
    }

    /**
     * Checks if the variable has already assigned or not
     * @return true iff the variable has already assigned
     */
    public boolean isAssigned() {
        return assigned;
    }

    public void assign(String value, ScopeChecker scope, CommandLine line) throws CompilingException{
        if (assigned && isFinal)
            throw new CompilingException();

        if (Pattern.matches(SyntaxChecker.VARIABLE_NAME, value)) {
            Variable assignVariable = scope.getVariable(value);
            if (assignVariable == null)
                scope.addUnidentifiedCommand(line); // SHOULD SOLVE THE RECURSION WHEN CALLING IT IN THE END
            else if (type != assignVariable.getType() || !assignVariable.isAssigned())
                throw new CompilingException();

        }
        else if (Pattern.matches(SyntaxChecker.STRING_VALUE, value)) {
            if (type != Type.STRING) // can I do this with ==?  NO
                throw new CompilingException();
        }
        else if (Pattern.matches(SyntaxChecker.INT_VALUE, value)) {
            if (type == Type.STRING || type == Type.CHAR)
                throw new CompilingException();
        }
        else if (Pattern.matches(SyntaxChecker.DOUBLE_VALUE, value)) {
            if (type != Type.DOUBLE && type != Type.BOOLEAN)
                throw new CompilingException();
        }
        else if (Pattern.matches(SyntaxChecker.CHAR_VALUE, value)) {
            if (type!= Type.CHAR)
                throw new CompilingException();
        }
        else if (Pattern.matches(SyntaxChecker.BOOLEAN_VALUE, value)) {
            if (type!= Type.BOOLEAN)
                throw new CompilingException();
        }

        assigned = true;
    }

//    /**
//     * Convert a given string to a Type object.
//     * @param typeName a variable type as string
//     * @return a Type object of the given string
//     */
//    public static Type getType(String typeName) {
//        return Type.valueOf(typeName);
//    }

}
