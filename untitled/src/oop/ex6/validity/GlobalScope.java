package oop.ex6.validity;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final boolean GLOBAL = true;

    public GlobalScope(){
        super(new LinkedList<Variable>(), new LinkedList<String>());
    }

    public void closeFile(){
        if (!this.getScopes().getLast().isClosed())
            System.out.println(); ///EXCEPTION!!!!!!!!!!!!!!
        // CHECK UNIDENTIFIED!!!!!!!!!!
    }
}
