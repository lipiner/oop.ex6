package oop.ex6.program_members;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

    private static final String NESTED_METHODS_EXCEPTION_MESSAGE = "Nested methods";
    private LinkedList<VariableWrapper> variables;
    private ScopeChecker superScope;

    /**
     * Constructor fot a local scope.
     * @param superScope the outer scope of that one.
     */
    public LocalScope(ScopeChecker superScope) throws CompilingException{
        super(false, superScope);
        this.superScope = superScope;
        variables = new LinkedList<VariableWrapper>();
    }

    /**
     * Constructor fot a local scope that is a method.
     * @param superScope the outer scope of that one.
     * @param variables a list of all the variable that the method gets.
     */
    LocalScope(ScopeChecker superScope, LinkedList<VariableWrapper> variables)
            throws CompilingException{
        super(true, superScope);
        this.superScope = superScope;
        this.variables = new LinkedList<VariableWrapper>();

        //Adds the variable that the method gets to its variable list
        for (VariableWrapper variableWrapper: variables)
            addVariable(variableWrapper);
    }

    /**
     * Operation is allowed only if the sub scope is not a method.
     * @param scope the new sub-scope
     * @throws CompilingException if the sub-scope is a method
     */
    @Override
    void openScope(ScopeChecker scope) throws CompilingException{
        if (scope.isMethod())
            throw new CompilingException(NESTED_METHODS_EXCEPTION_MESSAGE);
        super.openScope(scope);
    }

    @Override
    public VariableWrapper getVariable(String variableName) {
        VariableWrapper foundVariable = getScopeVariable(variableName);
        if (foundVariable != null)
            return foundVariable;
        else
            return superScope.getVariable(variableName);
    }

    @Override
    public boolean canVariableBeDeclared(String variableName) {
        VariableWrapper variableWrapper = getScopeVariable(variableName);
        return variableWrapper == null;
    }

    @Override
    void addVariableToScope(VariableWrapper variable) throws CompilingException{
        variables.add(variable);
    }

    /**
     * Checks for a variable only in the current scope.
     * @param variableName the variable's name.
     * @return the variable object if found, null otherwise.
     */
    private VariableWrapper getScopeVariable(String variableName){
        for (VariableWrapper variable: variables)
            if (variable.getVariableName().equals(variableName))
                return variable;
        return null;
    }

    @Override
    VariableWrapper getScopeVariableWrapper(VariableWrapper variable){
        VariableWrapper scopeVariable = null;
        for (VariableWrapper variableWrapper: variables) {
            if (variableWrapper == variable) {
                scopeVariable = variableWrapper;
                break;
            }
        }
        if (scopeVariable == null) {
            scopeVariable = new VariableWrapper(variable.getVariable());
            variables.add(scopeVariable);
        }
        return scopeVariable;
    }
}