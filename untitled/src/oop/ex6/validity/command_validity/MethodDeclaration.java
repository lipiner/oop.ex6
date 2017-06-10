package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class MethodDeclaration extends CommandLine {

    public MethodDeclaration(String methodName, LinkedList<VariableDeclaration> methodVariables){
    }

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
