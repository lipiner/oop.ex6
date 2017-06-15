package oop.ex6.validity;

import java.util.LinkedList;

public class Method {

    private LinkedList<VariableWrapper> methodVariables;
    private String name;
    private int lineNumber;
    private ScopeChecker superScope;

    /**
     * Constructor for a single method.
     * @param methodName the method's name.
     * @param methodVariables a list of the type of variables that the method gets.
     */
    Method(String methodName, LinkedList<VariableWrapper> methodVariables, ScopeChecker superScope, int lineNumber){
//        LocalScope m = new LocalScope(methodName, scope, knownMethods, methodVariables);
//        scope.addScope(m);
        this.superScope = superScope;
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
    public LinkedList<VariableWrapper> getMethodVariables() {
        return methodVariables;
    }

    /**
     * @return the line number in the file when the method is declared
     */
    public int getLineNumber() {
        return lineNumber;
    }

//    public ScopeChecker getScope() {
//        return scope;
//    }

    public void openScope() throws CompilingException{
        ScopeChecker scope = new LocalScope(superScope, methodVariables);
    }
}
