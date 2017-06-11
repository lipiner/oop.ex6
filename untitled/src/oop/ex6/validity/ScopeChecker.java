package oop.ex6.validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public abstract class ScopeChecker {

    private static final String UNREACHABLE_STATEMENT_EXCEPTION_MESSAGE = "Unreachable statement";
    private static final String REPEATED_DECLARATION_EXCEPTION_MESSAGE = "Variable has already declared";

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED}
    private ScopeChecker innerScope;
//    private GlobalMembers globalMembers;
    private boolean method;
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
     * @return true iff the scope is closed
     */
    private boolean isClosed() {
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

    /**
     * Adds new variable to the scope
     * @param variable the new variable of the scope
     * @throws CompilingException if the variable cannot be declared in the scope or
     * if the operation is not allowed
     */
    public void addVariable(Variable variable) throws CompilingException{
        if(canBeDeclared(variable.getName()))
            addVariableToScope(variable);
        else
            throw new CompilingException(REPEATED_DECLARATION_EXCEPTION_MESSAGE);
    }

    /**
     * Adds the variable to the scope's variable list.
     * @param variable the new variable of the scope
     * @throws CompilingException if the operation is not allowed
     */
    abstract void addVariableToScope(Variable variable) throws CompilingException;

    /**
     * Gets a variable in the scope given it's name
     * @param variableName the variable's name
     * @return the found variable if existed, null otherwise
     */
    public abstract Variable getVariable(String variableName);

    /**
     * Adds an unidentified command to the global unidentified commands list.
     * @param command the unidentified command
     */
    public void addUnidentifiedCommand(CommandLine command){
        GlobalMembers.getInstance().addUnidentifiedCommands(command);
    }

    /**
     * Process a command line.
     * @param command Sjavac command line.
     * @throws CompilingException if the command is invalid.
     */
    public void readLine(String command) throws CompilingException{
        if (innerScope != null && !innerScope.isClosed())
            innerScope.readLine(command);
        else {
            LinkedList<CommandLine> commandLines = SyntaxChecker.checkLine(command);
            for (CommandLine commandLine: commandLines)
                commandLine.check(this);
        }
    }

    /**
     * @return true iff the current scope is a method.
     */
    boolean isMethod(){
        return method;
    }

//    ScopeChecker getInnerScope(){
//        return innerScope;
//    }

}
