package oop.ex6.validity;

import oop.ex6.SyntaxChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {

    public enum Type {INT, DOUBLE, STRING, BOOLEAN, CHAR}
    private final String name;
    private Type type;
    private boolean assigned;
    private boolean finalModifier;
    private boolean global;

    /**
     * Constructor fot a variable object.
     * @param name the variable's name.
     * @param type the variable's type
     * @param isFinal if the variable is final or not
     */
    public Variable(String name, String type, boolean isFinal, boolean isGlobal){
        this.name = name;
        this.type = Type.valueOf(type.toUpperCase());
        this.finalModifier = isFinal;
        assigned = false;
        global = isGlobal;
    }

    /**
     * @return the variable's name
     */
    String getName() {
        return name;
    }

    /**
     * @return the variable's type
     */
    Type getType() {
        return type;
    }

    boolean isGlobal(){
        return global;
    }

    boolean isFinal(){
        return finalModifier;
    }

//    public void assign () {
//        assigned = true;
//    }

//    public void assign (Variable assignVariable) throws CompilingException {
//        if (!assigned && !assignVariable.isGlobal())  //WTF????
//            throw new CompilingException();
////        if (!assigned && assignVariable.isGlobal())
////            throw new ;
//
//        // checking for matching types
//        if(type.equals(Variable.Type.DOUBLE)) {
//            if (!assignVariable.getType().equals(Type.DOUBLE) && !assignVariable.getType().equals(Type.INT))
//                throw new CompilingException();
//        } else if (type.equals(Type.BOOLEAN)) {
//            if (!assignVariable.getType().equals(Type.DOUBLE) && !assignVariable.getType().equals(Type.INT)
//                    && !assignVariable.getType().equals(Variable.Type.BOOLEAN))
//                throw new CompilingException();
//        } else {
//            if (type != assignVariable.getType())
//                throw new CompilingException();
//        }
//
//        assigned = true;
//    }
//
//    public void assign(String value) throws CompilingException{
//        if (assigned && finalModifier)
//            throw new CompilingException();
//
//        if (isMatchedType(SyntaxChecker.STRING_VALUE, value)) {
//            if (!type.equals(Type.STRING))
//                throw new CompilingException();
//        }
//        else if (isMatchedType(SyntaxChecker.INT_VALUE, value)) {
//            if (type.equals(Type.STRING) || type.equals(Type.CHAR))
//                throw new CompilingException();
//        }
//        else if (isMatchedType(SyntaxChecker.DOUBLE_VALUE, value)) {
//            if (!type.equals(Type.DOUBLE) && !type.equals(Type.BOOLEAN))
//                throw new CompilingException();
//        }
//        else if (isMatchedType(SyntaxChecker.CHAR_VALUE, value)) {
//            if (!type.equals(Type.CHAR))
//                throw new CompilingException();
//        }
//        else if (isMatchedType(SyntaxChecker.BOOLEAN_VALUE, value)) {
//            if (!type.equals(Type.BOOLEAN)) //SHOULD DO ELSE?
//                throw new CompilingException();
//        }
//
//        assigned = true;
//    }
//
//    private boolean isMatchedType(String stringPattern, String value) {
//        Pattern pattern = Pattern.compile(stringPattern);
//        Matcher patternMatcher = pattern.matcher(value);
//
//        return patternMatcher.matches();
//    }

//    /**
//     * Convert a given string to a Type object.
//     * @param typeName a variable type as string
//     * @return a Type object of the given string
//     */
//    public static Type getType(String typeName) {
//        return Type.valueOf(typeName);
//    }

}
