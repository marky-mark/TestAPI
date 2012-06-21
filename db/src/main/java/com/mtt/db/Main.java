package com.mtt.db;

import liquibase.exception.CommandLineParsingException;

import java.io.IOException;

/**
 * Main db class
 */
public abstract class Main {

    private Main() { }

    /**
     * Runs the liquibase command line
     *
     * @param args The argument parameters
     * @throws liquibase.exception.CommandLineParsingException for errors with liquibase parsing the command line
     * @throws java.io.IOException If there is an IOException while reading the changelog or other files
     */
    public static void main(String[] args) throws CommandLineParsingException, IOException {
        liquibase.integration.commandline.Main.main(args);
    }
}
