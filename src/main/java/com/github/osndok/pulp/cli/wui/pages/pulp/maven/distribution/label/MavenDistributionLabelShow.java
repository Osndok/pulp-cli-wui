package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.label;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import org.apache.tapestry5.beaneditor.Validate;

public
class MavenDistributionLabelShow extends BasicFormBasedExecution
{
    public static
    class Command
    {
        @Validate("required")
        public String key;
        public String distribution;
    }

    private Command command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }

    public
    void onActivate(String pulp_href)
    {
        command.distribution = pulp_href;
    }
}
