package oop.ex6.validity;

import oop.ex6.SyntaxChecker;

import java.util.regex.Matcher;
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
     * @param isFinal if the variable is final or not
     */
    public Variable(String name, Type type, boolean isFinal){
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        assigned = false;
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

    public void assign () {
        assigned = true;
    }

    public void assign (Variable assignVariable) throws CompilingException {
        if (type != assignVariable.getType() || !assignVariable.isAssigned()) // MORE COMPLICATED
            throw new CompilingException();

        assigned = true;
    }

    public void assign(String value) throws CompilingException{
        if (assigned && isFinal)
            throw new CompilingException();

        if (isMatchedType(SyntaxChecker.STRING_VALUE, value)) {
            if (!type.equals(Type.STRING))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.INT_VALUE, value)) {
            if (type.equals(Type.STRING) || type.equals(Type.CHAR))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.DOUBLE_VALUE, value)) {
            if (!type.equals(Type.DOUBLE) && !type.equals(Type.BOOLEAN))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.CHAR_VALUE, value)) {
            if (!type.equals(Type.CHAR))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.BOOLEAN_VALUE, value)) {
            if (!type.equals(Type.BOOLEAN)) //SHOULD DO ELSE?
                throw new CompilingException();
        }

        assigned = true;
    }

    private boolean isMatchedType(String stringPattern, String value) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher patternMatcher = pattern.matcher(value);

        return patternMatcher.matches();
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
