package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

import java.util.LinkedList;

public abstract class CommandLine {

    public CommandLine(){
    } // DO I NEED THIS?

    abstract boolean check(ScopeChecker scope);

}
