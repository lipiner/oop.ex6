package oop.ex6.program_members;

import oop.ex6.SyntaxChecker;
import oop.ex6.command_validity.CommandLine;

public abstract class ScopeChecker {

    private static final String REPEATED_DECLARATION_EXCEPTION_MESSAGE = "Variable has already declared";
    private static final String MISSING_RETURN_EXCEPTION_MESSAGE = "Missing return statement";

    enum Status {OPEN, FROZEN, SEMI_CLOSED, CLOSED}
    private boolean method;
    private Status status;
    ScopeChecker innerScope;
    String scopeName;

    /**
     * Constructor for a scope.
     * @param isMethod true if the scope is a method
     * @param superScope the scope object of the outer scope or null if there is no outer scope
     * @throws CompilingException if the scope cannot be open in the outer scope
     */
    ScopeChecker(boolean isMethod, ScopeChecker superScope) throws CompilingException{
        innerScope = null;
        method = isMethod;
        activate();

        //open the scope in outer scope
        if (superScope != null)
            superScope.openScope(this);
    }

    /**
     * @return true iff the scope is closed
     */
    boolean isClosed() {
        return status == Status.CLOSED;
    }

    /**
     * Freeze the scope (after a return statement) in case it is a method
     * @throws CompilingException if the action is not valid in the scope
     */
    public void freeze() throws CompilingException{
        if (isMethod())
            status = Status.FROZEN;
    }

    /**
     * Close the scope (after a '}' )
     * @throws CompilingException if a return statement is missing
     */
    public void close() throws CompilingException{
        switch (status) {
            case FROZEN:
            case SEMI_CLOSED:
                status = Status.CLOSED;
                break;
            case OPEN:
                throw new CompilingException(MISSING_RETURN_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Checks if the scope is activate (not closed or frozen) for an action
     * @throws CompilingException if there is an unreachable statement
     * @throws CallingClosedScopeException if the scope is closed
     */
    private void isActivate() throws CompilingException, CallingClosedScopeException {
        if (isClosed())
            throw new CallingClosedScopeException();
    }

    /**
     * Activate the scope as if a return statement has never been.
     */
    private void activate(){
        if (method)
            status = Status.OPEN;
        else
            status = Status.SEMI_CLOSED;
    }

    /**
     * Adds new sub-scope
     * @param scope the new sub-scope
     * @throws CompilingException if the action is not valid in the scope
     */
    void openScope(ScopeChecker scope) throws CompilingException{
        innerScope = scope;
    }

    /**
     * Check if a variable with the given name can be declared in the scope
     * @param variableName the name of the variable to check
     * @return true iff the variable can be declared
     */
    public abstract boolean canVariableBeDeclared(String variableName);

    /**
     * Adds new variable to the scope
     * @param variable the new variable of the scope
     * @throws CompilingException if the variable cannot be declared in the scope or
     * if the operation is not allowed
     */
    public void addVariable(VariableWrapper variable) throws CompilingException{
        if(canVariableBeDeclared(variable.getVariableName()))
            addVariableToScope(variable);
        else
            throw new CompilingException(REPEATED_DECLARATION_EXCEPTION_MESSAGE);
    }

    /**
     * Adds the variable to the scope's variable list.
     * @param variable the new variable of the scope.
     * @throws CompilingException if the operation is not allowed.
     */
    abstract void addVariableToScope(VariableWrapper variable) throws CompilingException;

    /**
     * Gets a variable in the scope given its name.
     * @param variableName the variable's name.
     * @return the found variable if existed, null otherwise.
     */
    public abstract VariableWrapper getVariable(String variableName);

    /**
     * Process a command line.
     * @param command Sjavac command line.
     * @throws CompilingException if the command is invalid.
     */
    public void readLine(String command) throws CompilingException{
        isActivate();
        if (innerScope != null && !innerScope.isClosed())
            innerScope.readLine(command);
        else {
            //There is no open inner scope
            boolean shouldBeActivate = status == Status.FROZEN;
            CommandLine commandLine = SyntaxChecker.checkLine(command);
            commandLine.check(this);
            //activate the scope if a return statement was without closing the scope afterwards
            if (!isClosed() && shouldBeActivate)
                activate();
        }
    }

    /**
     * @return true iff the current scope is a method.
     */
    boolean isMethod(){
        return method;
    }

    /**
     * Change the assigning status in the scope of the given variable.
     * Tries to assign a given variable into another one.
     * @param variable the variable to be assigned
     * @param assignVariable the value of the variable. The variable that is being assign into
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    public void assignVariable(VariableWrapper variable, VariableWrapper assignVariable) throws CompilingException{
        VariableWrapper scopeVariable = getScopeVariableWrapper(variable);
        scopeVariable.assign(assignVariable);
    }

    /**
     * Change the assigning status in the scope of the given variable.
     * Tries to assign a given value (as string) into another one.
     * @param variable the variable to be assigned
     * @param value the value of the variable. It should be variable type as String
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    public void assignVariable(VariableWrapper variable, String value) throws CompilingException{
        VariableWrapper scopeVariable = getScopeVariableWrapper(variable);
        scopeVariable.assign(value);
    }

    /**
     * Gets the VariableWrapper object of the variable in the current scope. If the variable is not
     * existed in the scope, creates a new VariableWrapper object.
     * @param variable the wanted variable
     * @return the VariableWrapper object of the variable in the current scope
     */
    abstract VariableWrapper getScopeVariableWrapper(VariableWrapper variable);

}
