package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

public class ReturnLine extends CommandLine {

    public ReturnLine(){}

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
