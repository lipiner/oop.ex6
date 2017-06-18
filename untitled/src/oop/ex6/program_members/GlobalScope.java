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
    }

    /**
     * Operation is not support in the scope. Throws CompilingException
     */
    @Override
    public void freeze() throws CompilingException{
        throw new CompilingException(FREEZE_CLOSE_EXCEPTION_MESSAGE);
    }

    /**
     * Operation is not support in the scope. Throws CompilingException
     */
    @Override
    public void close() throws CompilingException {
        throw new CompilingException(FREEZE_CLOSE_EXCEPTION_MESSAGE);
    }

    /**
     * Operation is allowed only if the sub scope is a method.
     * @param scope the new sub-scope
     * @throws CompilingException if the sub-scope is not a method
     */
    @Override
    void openScope(ScopeChecker scope) throws CompilingException{
        if (!scope.isMethod())
            throw new CompilingException(ILLEGAL_SCOPE_OPENING_MESSAGE);
        super.openScope(scope);
    }

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
