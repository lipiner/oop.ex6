package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

public class CloseScope extends CommandLine {

    public CloseScope(){}

    @Override
    public void check(ScopeChecker scope) {
        scope.close();
    }
}
