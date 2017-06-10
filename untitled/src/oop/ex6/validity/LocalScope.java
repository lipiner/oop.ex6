package oop.ex6.validity;

import java.util.LinkedList;

public class LocalScope extends MethodScope {

    public LocalScope(ScopeChecker superScope, LinkedList<MethodScope> knownMethods){
        super(null, superScope, knownMethods, null);
        status = Status.SEMI_CLOSED;
    }
}
