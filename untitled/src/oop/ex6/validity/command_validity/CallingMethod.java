package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;

import java.util.ArrayList;

public class CallingMethod extends CommandLine {

    public CallingMethod(String methodName, ArrayList<String> methodInput) {

    }

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
