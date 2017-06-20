package oop.ex6.program_members;

public class Variable {

    public enum Type {INT, DOUBLE, STRING, BOOLEAN, CHAR}
    private final String name;
    private Type type;
    private boolean finalModifier;
    //since final modifier cannot be assigned twice, saves the first assigning status
    private boolean hasBeenAssigned;

    /**
     * Constructor fot a variable object.
     * @param name the variable's name.
     * @param type the variable's type
     * @param isFinal if the variable is final or not
     */
    public Variable(String name, String type, boolean isFinal){
        this.name = name;
        this.type = Type.valueOf(type.toUpperCase());
        finalModifier = isFinal;
        hasBeenAssigned = false;
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

    /**
     * @return true iff the variable type consists with boolean type (int, double or boolean)
     */
    boolean canBeBoolean() {
        return !(type == Type.STRING || type == Type.CHAR);
    }

    /**
     * @return true iff the variable can be assigned (not a final that is being assigned twice)
     */
    boolean canBeAssigned(){
        return !(finalModifier && hasBeenAssigned);
    }

    /**
     * Changes the assigning status of the variable to assigned
     */
    void assign () {
        hasBeenAssigned = true;
    }

}
