package oop.ex6.validity;

import java.util.LinkedList;

public class Method {

    private LinkedList<Variable.Type> methodVariables;
    private String name;

    /**
     * Constructor for a single method.
     * @param methodName the method's name.
     * @param methodVariables a list of the type of variables that the method gets.
     */
    Method(String methodName, LinkedList<Variable.Type> methodVariables){
//        LocalScope m = new LocalScope(methodName, superScope, knownMethods, methodVariables);
//        superScope.addScope(m);
        name = methodName;
        this.methodVariables = methodVariables;
    }

    /**
     * @return the method's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return a list of the type of variables that the method gets
     */
    public LinkedList<Variable.Type> getMethodVariables() {
        return methodVariables;
    }
}
