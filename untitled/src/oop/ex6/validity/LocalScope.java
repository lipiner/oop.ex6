package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends ScopeChecker {

    private LinkedList<Variable> variables;
    private ScopeChecker superScope;

    public LocalScope(ScopeChecker superScope){
        super(false);
        this.superScope = superScope;
        variables = new LinkedList<Variable>();
        status = Status.SEMI_CLOSED;
    }

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

    @Override
    public void close() throws CompilingException{
        switch (status){
            case CLOSED:
                throw new CallingClosedScopeException();
            case FROZEN:
            case SEMI_CLOSED:
                status = Status.CLOSED;
            default:
                throw new CompilingException();
        }
    }

    @Override
    public void openScope(ScopeChecker scope) throws CompilingException{
        if (scope.isMethod())
            throw new CompilingException();
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

    public void addVariable(Variable variable) throws CompilingException{
        isActivate();
        if(canBeDeclared(variable.getName()))
            variables.add(variable);
        else
            throw new CompilingException();
    }

    private Variable getScopeVariable(String variableName){
        for (Variable variable: variables)
            if (variable.getName().equals(variableName))
                return variable;
        return null;
    }
}
