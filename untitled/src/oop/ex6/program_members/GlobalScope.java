package oop.ex6.program_members;

public class GlobalScope extends ScopeChecker {

    private static final String ILLEGAL_SCOPE_OPENING_MESSAGE = "If/while statements not in a method",
            FREEZE_CLOSE_EXCEPTION_MESSAGE = "Illegal operation: return or in } in global scope";

    /**
     * Constructor for a global scope. Can only have methods inside a global members.
     */
    public GlobalScope() throws CompilingException{
        super(false, null);
        scopeName = null;
//        status = Status.SEMI_CLOSED;
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
    void openScope(ScopeChecker scope) throws CompilingException{
        if (!scope.isMethod())
            throw new CompilingException(ILLEGAL_SCOPE_OPENING_MESSAGE);
        super.openScope(scope);
    }

//    public VariableWrapper addVariable(String variableName, String variableType, boolean isFinal) throws CompilingException {
//        Variable variable = new Variable(variableName, variableType, isFinal, true);
//        VariableWrapper variableWrapper = new VariableWrapper(variable);
//        super.addVariable(variableWrapper);
//        return variableWrapper;
//    }

    @Override
    public VariableWrapper getVariable(String variableName) {
        return GlobalMembers.getInstance().getGlobalVariable(variableName);
    }

    @Override
    public boolean canVariableBeDeclared(String variableName) {
        VariableWrapper variable = getVariable(variableName);
        return variable == null;
    }

    @Override
    void addVariableToScope(VariableWrapper variable){
        GlobalMembers.getInstance().addVariable(variable);
    }

    @Override
    VariableWrapper getScopeVariableWrapper(VariableWrapper variable){
        return GlobalMembers.getInstance().getGlobalVariable(variable);
    }

    /**
     * @return true iff there is no opened inner scopes (there is no inner method or it is closed)
     */
    public boolean isMethodClosed(){
        return innerScope == null || innerScope.isClosed();
    }

}
