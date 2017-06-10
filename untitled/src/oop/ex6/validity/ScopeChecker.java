package oop.ex6.validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.command_validity.CommandLine;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class ScopeChecker {

    private static final String UNREACHABLE_STATEMENT_EXCEPTION_MESSAGE = "Unreachable statement";

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED};
    private LinkedList<Variable> variables;
    private LinkedList<ScopeChecker> scopes;
    LinkedList<CommandLine> unidentifiedCommands;
    Status status;
    String scopeName;

    /**
     * Constructor for a scope.
     * @param scopeVariables a list of the variables of the super scopes
     * @param unidentifiedCommands
     */
    ScopeChecker(){
        unidentifiedCommands = new LinkedList<CommandLine>();
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
     * @throws CompilingException if the action is not valid in the scope
     */
    public void freeze() throws CompilingException{
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
     * @throws CompilingException if there is an unreachable statement
     * @throws CallingClosedScopeException if the scope is closed
     */
    private void isActivate() throws CompilingException, CallingClosedScopeException {
        if (status.equals(Status.FROZEN))
            throw new CompilingException(UNREACHABLE_STATEMENT_EXCEPTION_MESSAGE);
        if (status.equals(Status.CLOSED))
            throw new CallingClosedScopeException();
    }

    /**
     * Adds new sub-scope
     * @param scope the new sub-scope
     * @throws CompilingException if the action is not valid in the scope
     */
    public void addScope(ScopeChecker scope) throws CompilingException{
        isActivate();
        scopes.add(scope);
    }

    public void addVariable(Variable variable) throws CompilingException{
        isActivate();
        variables.add(variable);
    }

    public void addUnidentifiedCommand(CommandLine command){
        unidentifiedCommands.add(command);
    }

    public void readLine(String command){
        ScopeChecker currentScope;
        if (scopes.size() != 0) {
            if (!scopes.getLast().isClosed())
                scopes.getLast().readLine(command);
        } else {
            LinkedList<CommandLine> commandLine = SyntaxChecker.checkLine(command);
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

//    public abstract Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal);

}
