package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class DefiningBlock extends CommandLine {

    public DefiningBlock (LinkedList<String> variables){}

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
