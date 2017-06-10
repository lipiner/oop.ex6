package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

import java.util.LinkedList;

public abstract class CommandLine {


    public abstract void check(ScopeChecker scope);

}
