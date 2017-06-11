package oop.ex6.validity;

import java.util.LinkedList;

public class Method {

    private LinkedList<Variable.Type> methodVariables;
    private String name;

    Method(String methodName, LinkedList<Variable.Type> methodVariables){
//        LocalScope m = new LocalScope(methodName, superScope, knownMethods, methodVariables);
//        superScope.addScope(m);
        name = methodName;
        this.methodVariables = methodVariables;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Variable.Type> getMethodVariables() {
        return methodVariables;
    }
}
