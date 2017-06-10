package oop.ex6.validity;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final String ILLEGAL_OPERATION_EXCEPTION_MESSAGE = "Illegal operation: ";

    public GlobalScope(){
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

    public void closeFile(){
        if (!this.getScopes().getLast().isClosed())
            System.out.println(); ///EXCEPTION!!!!!!!!!!!!!!
        // CHECK UNIDENTIFIED!!!!!!!!!!
    }

//    @Override
//    public Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal) {
//        return new Variable(name, type, assigned, isFinal, true);
//    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = getVariable(variableName);
        return variable == null;
    }

    @Override
    public void freeze() throws CompilingException{
        throw new CompilingException(ILLEGAL_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void addScope(ScopeChecker scope) throws CompilingException{
        if (scope.getScopeName() == null)
            //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        super.addScope(scope);
    }

    @Override
    public void close() {
        //EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
}
