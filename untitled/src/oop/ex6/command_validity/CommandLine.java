package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

public abstract class CommandLine {

    public abstract void check(ScopeChecker scope) throws CompilingException;

}
