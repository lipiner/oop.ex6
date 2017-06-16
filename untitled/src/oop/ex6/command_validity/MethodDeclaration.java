package oop.ex6.command_validity;

import oop.ex6.program_members.ScopeChecker;

import java.util.LinkedList;

public class MethodDeclaration extends CommandLine {

    String methodName;
    LinkedList<VariableDeclaration> methodVariables;

    public MethodDeclaration(String methodName, LinkedList<VariableDeclaration> methodVariables){
        this.methodName = methodName;
        this.methodVariables = methodVariables;
    }

    @Override
    public void check(ScopeChecker scope) {

    }
}
