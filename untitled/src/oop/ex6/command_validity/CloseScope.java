package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

public class CloseScope extends CommandLine {

    public CloseScope(){}

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        scope.close();
    }
}
