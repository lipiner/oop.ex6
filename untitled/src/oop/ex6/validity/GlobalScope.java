package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final String FREEZE_EXCEPTION_MESSAGE = "Cannot freeze global scope";
    private static final String ILLEGAL_SCOPE_OPENING_MESSAGE = "If/while statements not in a method";

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
        throw new CompilingException(FREEZE_EXCEPTION_MESSAGE);
    }

    @Override
    public void close() throws CompilingException {
//        if (this.getInnerScope() != null || !this.getInnerScope().isClosed())
//            throw new CompilingException();
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
