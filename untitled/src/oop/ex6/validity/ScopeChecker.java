package oop.ex6.validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.command_validity.CommandLine;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class ScopeChecker {

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED};
    private LinkedList<Variable> variables;
    private LinkedList<ScopeChecker> scopes;
    LinkedList<String> unidentifiedCommands;
    Status status;
    String scopeName;

    /**
     * Constructor for a scope.
     * @param scopeVariables a list of the variables of the super scopes
     * @param unidentifiedCommands
     */
    ScopeChecker(){
        unidentifiedCommands = new LinkedList<String>();
        scopes = new LinkedList<ScopeChecker>();
        variables = new LinkedList<Variable>();
//        variables.addAll(scopeVariables);
    }

    /**
     * @return if the scope is closed
     */
    boolean isClosed() {
        return status.equals(Status.CLOSED);
    }

    /**
     * Freeze the scope (after a return statement)
     */
    public void freeze(){
        isActivate();
        status = Status.FROZEN;
    }

    /**
     * Close the scope (after a '}' )
     */
    public abstract void close();

//    /**
//     * @return a list of all the variables declared in the scope
//     */
//    public LinkedList<Variable> getVariablesList() {
//        return variables;
//    }

    /**
     * @return a list of all the direct sub-scopes
     */
    public LinkedList<ScopeChecker> getScopes() {
        return scopes;
    }

    /**
     * @return the scope's name. If the scope is not a method (and doesn't have name), returns null
     */
    public String getScopeName() {
        return scopeName;
    }

    /**
     * Checks if the scope is activate (not closed or frozen) for an action
     */
    private void isActivate(){
        if (status.equals(Status.FROZEN) || status.equals(Status.CLOSED))
            return; // EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    /**
     * Adds new sub-scope
     * @param scope the new sub-scope
     */
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
            CommandLine commandLine = SyntaxChecker.CheckLine(command);
            // CALL COMMAND LINE!!!!!!!!!!!!!!!!!!!!!!!!!
        }
    }

    /**
     * Check if a variable with the given name can be declared in the scope
     * @param variableName the name of the variable to check
     * @return true iff the variable can be declared
     */
    public abstract boolean canBeDeclared(String variableName);

    public Variable getVariable(String variableName){
//        Iterator<Variable> variableIterator = variables.descendingIterator();
//        Variable currentVariable;
//        while (variableIterator.hasNext()) {
//            currentVariable = variableIterator.next();
//            if (currentVariable.getName().equals(variableName))
//                return currentVariable;
//        }

        for (Variable variable: variables)
            if (variable.getName().equals(variableName))
                return variable;
        return null;
    }

    public abstract Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal);

}
