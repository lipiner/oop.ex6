package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

public class ReturnLine extends CommandLine {

    public ReturnLine(){}

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        scope.freeze();
    }
}
