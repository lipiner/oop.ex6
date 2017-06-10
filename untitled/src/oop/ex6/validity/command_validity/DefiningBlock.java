package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.ArrayList;

public class DefiningBlock extends CommandLine {

    public DefiningBlock (ArrayList<String> variables){}

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
