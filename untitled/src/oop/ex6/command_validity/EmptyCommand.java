package oop.ex6.command_validity;

import oop.ex6.program_members.ScopeChecker;

public class EmptyCommand extends CommandLine {

    public EmptyCommand(){
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
