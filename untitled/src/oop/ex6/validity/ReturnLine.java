package oop.ex6.validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

/**
 * The class represents code line that returns from a method
 */
public class ReturnLine extends CommandLine {

    /**
     * constructs a new ReturnLine instance
     */
    ReturnLine(){}

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        // changes the status of the scope
        scope.freeze();
    }
}
