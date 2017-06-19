package oop.ex6.validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

/**
 * The class represents a line the closes the current inner scope
 */
public class CloseScope extends CommandLine {

    /**
     * constructs a new CloseScope instance
     */
    public CloseScope(){}

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        scope.close();
    }
}
