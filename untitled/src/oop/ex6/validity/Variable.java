package oop.ex6.validity;

public class Variable {

    public enum Type {INT, DOUBLE, STRING, BOOLEAN, CHAR}
    private final String name;
    private Type type;
//    private boolean assigned;
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
//        assigned = false;
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

//    /**
//     * Convert a given string to a Type object.
//     * @param typeName a variable type as string
//     * @return a Type object of the given string
//     */
//    public static Type getType(String typeName) {
//        return Type.valueOf(typeName);
//    }

}
