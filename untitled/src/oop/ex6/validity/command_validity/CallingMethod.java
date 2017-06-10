package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class CallingMethod extends CommandLine {
    private String methodName;
    LinkedList <String> methodInput;

    public CallingMethod(String methodName, LinkedList<String> methodInput) {
        this.methodName = methodName;
        this.methodInput= methodInput;
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
