package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

public class EmptyCommand extends CommandLine {

    public EmptyCommand(){
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
