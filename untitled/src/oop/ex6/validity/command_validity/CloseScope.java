package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

public class CloseScope extends CommandLine {

    public CloseScope(){}

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
