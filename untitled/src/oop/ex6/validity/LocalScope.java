package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

//    private static final String MISSING_RETURN_EXCEPTION_MESSAGE = "Missing return statement";
    private static final String NESTED_METHODS_EXCEPTION_MESSAGE = "Nested methods";
    private LinkedList<Variable> variables;
    private ScopeChecker superScope;

    /**
     * Constructor fot a local scope.
     * @param superScope the outer scope of that one.
     */
    public LocalScope(ScopeChecker superScope){
        super(false);
        this.superScope = superScope;
        variables = new LinkedList<Variable>();
        status = Status.SEMI_CLOSED;
    }

    /**
     * Constructor fot a local scope that is a method.
     * @param superScope the outer scope of that one.
     * @param methodName the method's name
     * @param methodVariables a list of all the variable that the method gets.
     */
    public LocalScope(ScopeChecker superScope, String methodName, LinkedList<Variable> methodVariables){
        super(true);
        this.superScope = superScope;
        variables = methodVariables;
        status = Status.OPEN;

        LinkedList<Variable.Type> types = new LinkedList<Variable.Type>();
        for (Variable variable: variables)
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
    public Variable getVariable(String variableName) {
        Variable foundVariable = getScopeVariable(variableName);
        if (foundVariable != null)
            return foundVariable;
        else
            return superScope.getVariable(variableName);
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = getScopeVariable(variableName);
        return variable == null;
    }

    @Override
    void addVariableToScope(Variable variable) throws CompilingException{
        isActivate();
        variables.add(variable);
    }

    /**
     * Checks for a variable only in the current scope.
     * @param variableName the variable's name.
     * @return the variable object if found, null otherwise.
     */
    private Variable getScopeVariable(String variableName){
        for (Variable variable: variables)
            if (variable.getName().equals(variableName))
                return variable;
        return null;
    }
}
