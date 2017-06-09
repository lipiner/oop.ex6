package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

    /**
     * Constructor for a local scope.
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        super(scopeVariables, unidentifiedCommands);
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

    /**
     * Constructor for a local scope which is a method.
     * @param methodName
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(String methodName, LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        super(scopeVariables, unidentifiedCommands);
        scopeName = methodName;
        status = Status.OPEN;
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        return canShadow(variableName, true);
    }

    @Override
    public Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal) {
        return new Variable(name, type, assigned, isFinal, false);
    }

    @Override
    public void addScope(ScopeChecker scope){
        if (scope.getScopeName() != null)
            //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        super.addScope(scope);
    }
}
