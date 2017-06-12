package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final String ILLEGAL_SCOPE_OPENING_MESSAGE = "If/while statements not in a method",
            FREEZE_CLOSE_EXCEPTION_MESSAGE = "Illegal operation: return or in } in global scope";

    /**
     * Constructor for a global scope.
     */
    public GlobalScope(){
        super(false);
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

    @Override
    public void freeze() throws CompilingException{
        throw new CompilingException(FREEZE_CLOSE_EXCEPTION_MESSAGE);
    }

    @Override
    public void close() throws CompilingException {
        throw new CompilingException(FREEZE_CLOSE_EXCEPTION_MESSAGE);
    }

    public void closeFile() throws CompilingException {
        status = Status.CLOSED;
        LinkedList<CommandLine> commandLines = GlobalMembers.getInstance().getUnidentifiedCommands();
        for (CommandLine commandLine : commandLines)
            commandLine.check(this);
    }

    @Override
    public void openScope(ScopeChecker scope) throws CompilingException{
        if (!scope.isMethod())
            throw new CompilingException(ILLEGAL_SCOPE_OPENING_MESSAGE);
        super.openScope(scope);
    }

    public Variable addVariable(String variableName, String variableType, boolean isFinal) throws CompilingException {
        Variable variable = new Variable(variableName, variableType, isFinal, true);
        super.addVariable(variable);
        return variable;
    }

    @Override
    public Variable getVariable(String variableName) {
        return GlobalMembers.getInstance().getGlobalVariable(variableName);
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = getVariable(variableName);
        return variable == null;
    }

    @Override
    void addVariableToScope(Variable variable){
        GlobalMembers.getInstance().addVariable(variable);
    }
}
