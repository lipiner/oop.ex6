package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

    private static final boolean GLOBAL = false;

    /**
     * Constructor for a local scope.
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        super(scopeVariables, unidentifiedCommands);
    }

    /**
     * Constructor for a local scope which is a method.
     * @param methodName
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(String methodName, LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        super(methodName, scopeVariables, unidentifiedCommands);
    }

}
