package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

public class MethodDeclaration extends CommandLine {

    MethodDeclaration(String command){
    }

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
