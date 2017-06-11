package oop.ex6.validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.command_validity.CommandLine;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class ScopeChecker {

    private static final String UNREACHABLE_STATEMENT_EXCEPTION_MESSAGE = "Unreachable statement";

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED};
    private ScopeChecker innerScope;
//    private GlobalMembers globalMembers;
    private boolean method;

//    LinkedList<Method> knownMethods;
//    LinkedList<CommandLine> unidentifiedCommands;
    Status status;
    String scopeName;

    /**
     * Constructor for a scope
     */
    ScopeChecker(boolean isMethod){
//        unidentifiedCommands = new LinkedList<CommandLine>();
//        scopes = new LinkedList<ScopeChecker>();
        innerScope = null;
        method = isMethod;
//        variables = new LinkedList<Variable>();
//        globalMembers = GlobalMembers.getInstance();
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
    public abstract void freeze() throws CompilingException;

    /**
     * Close the scope (after a '}' )
     */
    public abstract void close() throws CompilingException;

    /**
     * Checks if the scope is activate (not closed or frozen) for an action
     * @throws CompilingException if there is an unreachable statement
     * @throws CallingClosedScopeException if the scope is closed
     */
    void isActivate() throws CompilingException, CallingClosedScopeException {
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
    public void openScope(ScopeChecker scope) throws CompilingException{
        isActivate();
        innerScope = scope;
    }

    /**
     * Check if a variable with the given name can be declared in the scope
     * @param variableName the name of the variable to check
     * @return true iff the variable can be declared
     */
    public abstract boolean canBeDeclared(String variableName);

    public abstract void addVariable(Variable variable) throws CompilingException;

    public abstract Variable getVariable(String variableName);

    public void addUnidentifiedCommand(CommandLine command){
        GlobalMembers.getInstance().addUnidentifiedCommands(command);
    }

    public void readLine(String command) throws CompilingException{
        if (innerScope != null && !innerScope.isClosed())
            innerScope.readLine(command);
        else {
            LinkedList<CommandLine> commandLine = SyntaxChecker.checkLine(command);
            // CALL COMMAND LINE!!!!!!!!!!!!!!!!!!!!!!!!!
        }
    }

    boolean isMethod(){
        return method;
    }

    ScopeChecker getInnerScope(){
        return innerScope;
    }

}
