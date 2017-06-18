package oop.ex6.program_members;

import java.util.LinkedList;

public class Method {

    private LinkedList<VariableWrapper> methodVariables;
    private String name;
    private int lineNumber;
    private ScopeChecker superScope;

    /**
     * Constructor for a single method.
     * @param methodName the method's name.
     * @param methodVariables a list of all the variables that the method gets
     * @param superScope the super scope of the method (the outer scope of method)
     * @param lineNumber the line number of the method's declaration
     */
    Method(String methodName, LinkedList<VariableWrapper> methodVariables, ScopeChecker superScope, int lineNumber){
        this.superScope = superScope;
        name = methodName;
        this.methodVariables = methodVariables;
        this.lineNumber = lineNumber;

        // assigning the method parameters
        for (VariableWrapper variable: methodVariables)
            variable.assign();
    }

    /**
     * @return the method's name
     */
    String getName() {
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


    /**
     * Opens a new scope for the method (in it's super scope)
     * @throws CompilingException if the method can't be opened in the super scope or opening operation is invalid
     */
    public void openScope() throws CompilingException{
        new LocalScope(superScope, methodVariables);
    }
}
