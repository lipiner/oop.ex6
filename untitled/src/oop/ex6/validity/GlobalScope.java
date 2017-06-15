package oop.ex6.validity;

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

//    public void closeFile() throws CompilingException {
//        status = Status.CLOSED;
////        LinkedList<CommandLine> commandLines = GlobalMembers.getInstance().getUnidentifiedCommands();
////        for (CommandLine commandLine : commandLines)
////            commandLine.check(this);
//    }

    @Override
    public void openScope(ScopeChecker scope) throws CompilingException{
        if (!scope.isMethod())
            throw new CompilingException(ILLEGAL_SCOPE_OPENING_MESSAGE);
        super.openScope(scope);
    }

    public VariableWrapper addVariable(String variableName, String variableType, boolean isFinal) throws CompilingException {
        Variable variable = new Variable(variableName, variableType, isFinal, true);
        VariableWrapper variableWrapper = new VariableWrapper(variable);
        super.addVariable(variableWrapper);
        return variableWrapper;
    }

    @Override
    public VariableWrapper getVariable(String variableName) {
        return GlobalMembers.getInstance().getGlobalVariable(variableName);
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        VariableWrapper variable = getVariable(variableName);
        return variable == null;
    }

    @Override
    void addVariableToScope(VariableWrapper variable){
        GlobalMembers.getInstance().addVariable(variable);
    }

    public boolean isMethodClosed(){
        return innerScope == null || innerScope.isClosed();
    }

}
