package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import org.apache.tapestry5.beaneditor.Validate;

public
class MavenDistributionCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        @Validate("required")
        public String name;
        @Validate("required")
        public String base_path;
        public String remote;
        public String repository;
        public String labels;
    }

    private Command command;

    @Override
    public
    Object getCommandObject()
    {
        if (command == null)
        {
            command = new Command();
        }
        return command;
    }
}
