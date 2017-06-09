package oop.ex6.validity;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final boolean GLOBAL = true;

    public GlobalScope(){
        super(new LinkedList<Variable>(), new LinkedList<String>());
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

    public void closeFile(){
        if (!this.getScopes().getLast().isClosed())
            System.out.println(); ///EXCEPTION!!!!!!!!!!!!!!
        // CHECK UNIDENTIFIED!!!!!!!!!!
    }

    @Override
    public Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal) {
        return new Variable(name, type, assigned, isFinal, true);
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        return canShadow(variableName, false);
    }
}
