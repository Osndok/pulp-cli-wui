package com.github.osndok.pulp.cli.wui.pages.pulp.maven.repository;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import com.github.osndok.pulp.cli.wui.model.FileOrContents;
import org.apache.tapestry5.beaneditor.Validate;

public
class MavenRepositoryCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String description;
        public String remote;
        public Integer retain_repo_versions;
        public FileOrContents labels;
        @Validate("required")
        public String name;
    }

    private Command command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }
}
