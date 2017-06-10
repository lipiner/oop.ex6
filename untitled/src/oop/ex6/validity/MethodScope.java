package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public class MethodScope extends ScopeChecker {

    private ScopeChecker superScope;
    private LinkedList<String> methodVariables;
    private LinkedList<MethodScope> knownMethods;

//    public MethodScope(ScopeChecker superScope, LinkedList<MethodScope> knownMethods){
//        scopeName = null;
//        this.superScope = superScope;
//        this.knownMethods = knownMethods;
//        this.methodVariables = null;
//        status = Status.SEMI_CLOSED;
//    }

    public MethodScope(String methodName, ScopeChecker superScope, LinkedList<MethodScope> knownMethods,
                       LinkedList<String> methodVariables){
        scopeName = methodName;
        this.superScope = superScope;
        this.knownMethods = knownMethods;
        this.knownMethods.add(this); // FIX THATTTTTTTTTTTTTTTTT
        this.methodVariables = methodVariables;
        status = Status.OPEN;
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = super.getVariable(variableName);
        return variable == null;
    }

//    @Override
//    public Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal) {
//        return new Variable(name, type, assigned, isFinal, false);
//    }

    @Override
    public void addScope(ScopeChecker scope) throws CompilingException{
        if (scope.getScopeName() != null)
            //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        super.addScope(scope);
    }

    @Override
    public Variable getVariable(String variableName) {
        Variable variable = super.getVariable(variableName);
        if (variable != null)
            return variable;
        else
            return superScope.getVariable(variableName);
    }

    @Override
    public void close(){
        switch (status){
            case CLOSED:
                throw new CallingClosedScopeException();
            case FROZEN:
            case SEMI_CLOSED:
                status = Status.CLOSED;
                for (CommandLine command : unidentifiedCommands)
                    superScope.addUnidentifiedCommand(command);
                break;
            default:
                //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
//        if (status.equals(Status.SEMI_CLOSED) || status.equals(Status.FROZEN)) {
//            status = Status.CLOSED;
//            for (String command : unidentifiedCommands)
//                superScope.addUnidentifiedCommand(command);
//        }
//        else
//            System.out.println(); //EXCEPTION!!!!!!!!!!!!!!!!!#########################
    }

    public LinkedList<String> getMethodVariables() {
        return methodVariables;
    }

//    public MethodScope getKnownMethods(String methodName) {
//        for (MethodScope method: knownMethods)
//            if (methodName.equals(method.getScopeName()))
//                return method;
//        return null;
//    }
}
