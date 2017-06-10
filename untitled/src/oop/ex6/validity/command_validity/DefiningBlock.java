package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class DefiningBlock extends CommandLine {

    LinkedList<String> conditionVariables;

    public DefiningBlock (LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
