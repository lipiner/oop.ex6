package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

//    private static final String MISSING_RETURN_EXCEPTION_MESSAGE = "Missing return statement";
    private static final String NESTED_METHODS_EXCEPTION_MESSAGE = "Nested methods";
    private LinkedList<VariableWrapper> variables;
    private ScopeChecker superScope;

    /**
     * Constructor fot a local scope.
     * @param superScope the outer scope of that one.
     */
    public LocalScope(ScopeChecker superScope){
        super(false);
        this.superScope = superScope;
        variables = new LinkedList<VariableWrapper>();
        status = Status.SEMI_CLOSED;
    }

    /**
     * Constructor fot a local scope that is a method.
     * @param superScope the outer scope of that one.
     * @param methodName the method's name
     * @param methodVariables a list of all the variable that the method gets.
     */
    public LocalScope(ScopeChecker superScope, String methodName, LinkedList<VariableWrapper> methodVariables){
        super(true);
        this.superScope = superScope;
        variables = methodVariables;
        status = Status.OPEN;

        LinkedList<Variable.Type> types = new LinkedList<Variable.Type>();
        for (VariableWrapper variable: variables)
            types.add(variable.getType());
        GlobalMembers.getInstance().addMethod(methodName, types);
//        this.methodVariables = methodVariables;
//        variables = new LinkedList<Variable>();
    }

    @Override
    public void freeze() throws CompilingException{
        isActivate();
        status = Status.FROZEN;
    }

//    @Override
//    public void close() throws CompilingException{
//        switch (status){
//            case CLOSED:
//                throw new CallingClosedScopeException();
//            case FROZEN:
//            case SEMI_CLOSED:
//                status = Status.CLOSED;
//            default:
//                throw new CompilingException(MISSING_RETURN_EXCEPTION_MESSAGE);
//        }
//    }

    @Override
    public void openScope(ScopeChecker scope) throws CompilingException{
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
    public boolean canBeDeclared(String variableName) {
        VariableWrapper variableWrapper = getScopeVariable(variableName);
        return variableWrapper == null;
    }

    public VariableWrapper addVariable(String variableName, String variableType, boolean isFinal) throws CompilingException {
        Variable variable = new Variable(variableName, variableType, isFinal, false);
        VariableWrapper variableWrapper = new VariableWrapper(variable);
        super.addVariable(variableWrapper);
        return variableWrapper;
    }

    @Override
    void addVariableToScope(VariableWrapper variable) throws CompilingException{
        isActivate();
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

    public void assignVariable(VariableWrapper variable, Variable assignVariable) throws CompilingException{
        VariableWrapper scopeVariable = getScopeVariableWrapper(variable);
        scopeVariable.assign(assignVariable);
    }

    public void assignVariable(VariableWrapper variable, String value) throws CompilingException{
        VariableWrapper scopeVariable = getScopeVariableWrapper(variable);
        scopeVariable.assign(value);
    }

    public void assignVariable(VariableWrapper variable) throws CompilingException{
        VariableWrapper scopeVariable = getScopeVariableWrapper(variable);
        scopeVariable.assign();
    }

    private VariableWrapper getScopeVariableWrapper(VariableWrapper variable){
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
