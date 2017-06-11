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

    public Variable(String name, Type type, boolean assigned, boolean isFinal){
        this.name = name;
        this.type = type;
        this.assigned = assigned;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

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

    public static Type getType(String typeName) {
        switch (Type.valueOf(typeName)) {
            case INT:
                return Type.INT;
            case DOUBLE:
                return Type.DOUBLE;
            case STRING:
                return Type.STRING;
            case CHAR:
                return Type.CHAR;
            case BOOLEAN:
                return Type.BOOLEAN;
            default:
                throw new UnexpectedTypeException();
        }
    }

}
