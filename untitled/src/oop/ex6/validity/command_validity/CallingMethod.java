package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class CallingMethod extends CommandLine {

    public CallingMethod(String methodName, LinkedList<String> methodInput) {

    }

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
