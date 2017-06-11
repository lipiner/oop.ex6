package oop.ex6.validity;

import java.util.LinkedList;

public class Variable {

    public enum Type {INT, DOUBLE, STRING, BOOLEAN, CHAR}
    private final String name;
    private Type type;
    private boolean assigned;
    private boolean isFinal;
    private boolean global;
//    private Object value;

    Variable(String name, Type type, boolean assigned, boolean isFinal, boolean isGlobal){
        this.name = name;
        this.type = type;
        this.assigned = assigned;
        this.isFinal = isFinal;
        global = isGlobal;
    }

//    Variable(Type type, Object value){
////        this.value = value;
//        this.type = type;
//        assigned = true;
//
//    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void assign(String value){
        if (assigned && isFinal)
            //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        assigned = true;
    }

    public boolean isGlobal() {
        return global;
    }

//    public boolean isFinal() {
//        return isFinal;
//    }

}
