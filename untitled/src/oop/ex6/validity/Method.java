package oop.ex6.validity;

import java.util.LinkedList;

public class Method {

    private LinkedList<Variable.Type> methodVariables;
    private String name;
    private int lineNumber;
    private ScopeChecker scope;

    /**
     * Constructor for a single method.
     * @param methodName the method's name.
     * @param methodVariables a list of the type of variables that the method gets.
     */
    Method(String methodName, LinkedList<Variable.Type> methodVariables, ScopeChecker relatedScope, int lineNumber){
//        LocalScope m = new LocalScope(methodName, scope, knownMethods, methodVariables);
//        scope.addScope(m);
        this.scope = relatedScope;
        name = methodName;
        this.methodVariables = methodVariables;
        this.lineNumber = lineNumber;
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

    /**
     * @return the line number in the file when the method is declared
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public ScopeChecker getScope() {
        return scope;
    }
}
