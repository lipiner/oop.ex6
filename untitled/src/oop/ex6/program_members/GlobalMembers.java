package oop.ex6.program_members;

import java.util.LinkedList;

public class GlobalMembers {

    private static GlobalMembers instance = null;
    private LinkedList<Method> methodsList;
    private LinkedList<VariableWrapper> globalVariables;
    private int lineNumber;

    /**
     * Constructor for the GlobalMembers object that contains all the global members of the program (methods,
     * global variable and the line number in the code). This is a singleton object for all the program.
     */
    private GlobalMembers(){
        methodsList = new LinkedList<Method>();
        globalVariables = new LinkedList<VariableWrapper>();
        lineNumber = 0;
    }

    /**
     * Returns the instance of the object. If the instance has not been initialize, creates a new instance and
     * returns it.
     * @return the GlobalMember instance
     */
    public static GlobalMembers getInstance(){
        if (instance == null)
            instance = new GlobalMembers();
        return instance;
    }

    /**
     * Delete the instance to nullify the global members of the program
     */
    public static void nullifyInstance(){
        instance = null;
    }

    /**
     * Adds a variable to the global variable list
     * @param variable the variable
     */
    void addVariable(VariableWrapper variable){
        globalVariables.add(variable);
    }

    /**
     * Creates and adds a new method to the program's method list.
     * @param methodName the method's name
     * @param methodVariables a list of all the variables that the method gets
     * @param superScope the super scope of the method (the outer scope of method)
     */
    public void addMethod(String methodName, LinkedList<VariableWrapper> methodVariables, ScopeChecker superScope){
        methodsList.add(new Method(methodName, methodVariables, superScope, lineNumber));
    }

    /**
     * Returns a method, given its name.
     * @param methodName the search method's name
     * @return a method the consists with the given name if existed. Null otherwise.
     */
    Method getMethod(String methodName) {
        for (Method method: methodsList)
            if (method.getName().equals(methodName))
                return method;
        return null;
    }

    /**
     * @return a list of all the methods in the program
     */
    public LinkedList<Method> getAllMethods() {
        return methodsList;
    }

    /**
     * Search of a variable in the global variables' list given the variable name
     * @param variableName the searching variable's name
     * @return the variableWrapper object of the given variable name, if existed. Null, otherwise.
     */
    VariableWrapper containsVariable(String variableName) {
        for (VariableWrapper variable: globalVariables)
            if (variable.getVariableName().equals(variableName))
                return variable;
        return null;
    }

    /**
     * Updates the line number (promotes by 1)
     */
    public void updateLineNumber() {
        lineNumber++;
    }
}
