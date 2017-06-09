package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public abstract class ScopeChecker {

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED};
    private LinkedList<Variable> variables;
    private LinkedList<ScopeChecker> scopes;
    private LinkedList<String> unidentifiedCommands;
    Status status;
    String scopeName;

    ScopeChecker(LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        this.unidentifiedCommands = unidentifiedCommands;
        scopes = new LinkedList<ScopeChecker>();
        variables = new LinkedList<Variable>();
        variables.addAll(scopeVariables);
    }

    boolean isClosed() {
        return status.equals(Status.CLOSED);
    }

    public void freeze(){
        if (status.equals(Status.OPEN))
            status = Status.FROZEN;
        else
            System.out.println(); //EXCEPTION!!!!!!!!!!!!!!!!!!!!!#################################
    }

    public void close(){
        if (status.equals(Status.SEMI_CLOSED) || status.equals(Status.FROZEN))
            status = Status.CLOSED;
        else
            System.out.println(); //EXCEPTION!!!!!!!!!!!!!!!!!#########################
    }

    public LinkedList<Variable> getVariables() {
        return variables;
    }

    public LinkedList<ScopeChecker> getScopes() {
        return scopes;
    }

    /**
     * @return the scope's name. If the scope is not a method (and doesn't have name), returns null
     */
    public String getScopeName() {
        return scopeName;
    }

    private void isActivate(){
        if (status.equals(Status.FROZEN) || status.equals(Status.CLOSED))
            return; // EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public void addScope(ScopeChecker scope){
        isActivate();
        scopes.add(scope);
    }

    public void addVariable(Variable variable){
        isActivate();
        variables.add(variable);
    }

    public void addUnidentifiedCommand(String command){
        unidentifiedCommands.add(command);
    }

    public void readLine(String command){
        ScopeChecker currentScope;
        if (scopes.size() != 0) {
            if (!scopes.getLast().isClosed())
                scopes.getLast().readLine(command);
        } else {
            CommandLine commandLine = null;
            // CALL COMMAND LINE!!!!!!!!!!!!!!!!!!!!!!!!!
        }
    }

    public abstract boolean canBeDeclared(String variableName);

    boolean canShadow(String variableName, boolean canShadow){
        for (Variable variable: variables){
            if (variable.getName().equals(variableName))
                return (variable.isGlobal() && canShadow);
        }
        return true;
    }

    public abstract Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal);
}
