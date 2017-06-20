package oop.ex6.validity;

import oop.ex6.program_members.ScopeChecker;

/**
 * The class represents a code line that does not contain commands: an empty line or a comment line
 */
public class EmptyCommand extends CommandLine {

    /**
     * constructs a new EmptyCommand instance
     */
    EmptyCommand(){
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
