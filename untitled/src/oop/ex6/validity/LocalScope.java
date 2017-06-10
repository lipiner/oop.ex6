package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

    ScopeChecker superScope;

    /**
     * Constructor for a local scope.
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(ScopeChecker superScope){
        this.superScope = superScope;
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

    /**
     * Constructor for a local scope which is a method.
     * @param methodName
     * @param scopeVariables
     * @param unidentifiedCommands
     */
    public LocalScope(String methodName, ScopeChecker superScope){
        this.superScope = superScope;
        scopeName = methodName;
        status = Status.OPEN;
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = super.getVariable(variableName);
        return variable == null;
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

    @Override
    public Variable getVariable(String variableName) {
        Variable variable = super.getVariable(variableName);
        if (variable != null)
            return variable;
        else
            return superScope.getVariable(variableName);
    }

    @Override
    public void close(){
        if (status.equals(Status.SEMI_CLOSED) || status.equals(Status.FROZEN)) {
            status = Status.CLOSED;
            for (String command : unidentifiedCommands)
                superScope.addUnidentifiedCommand(command);
        }
        else
            System.out.println(); //EXCEPTION!!!!!!!!!!!!!!!!!#########################
    }
}
