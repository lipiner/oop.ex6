package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public abstract class ScopeChecker {

    private enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED};
    private Status status;
    private LinkedList<Variable> variables;
    private LinkedList<ScopeChecker> scopes;
    private LinkedList<String> unidentifiedCommands;
    private String scopeName;

    ScopeChecker(LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        scopeName = null;
        status = Status.SEMI_CLOSED;
        initialize(scopeVariables, unidentifiedCommands);
    }

    ScopeChecker(String methodName, LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        scopeName = methodName;
        status = Status.OPEN;
        initialize(scopeVariables, unidentifiedCommands);
    }

    private void initialize(LinkedList<Variable> scopeVariables, LinkedList<String> unidentifiedCommands){
        this.unidentifiedCommands = unidentifiedCommands;
        scopes = new LinkedList<ScopeChecker>();
        variables = new LinkedList<Variable>();
        variables.addAll(scopeVariables);
    }

//    public boolean isGlobal() {
//        return global;
//    }

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

    public void addScope(ScopeChecker scope){
        scopes.add(scope);
    }

    public void addVariable(Variable variable){
        variables.add(variable);
    }

    public void addUnidentifiedCommands(String command){
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

    public abstract boolean canBeDeclared(Variable variable);
}
