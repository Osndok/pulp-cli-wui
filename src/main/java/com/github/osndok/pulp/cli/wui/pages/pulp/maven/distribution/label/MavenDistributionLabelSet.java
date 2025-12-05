package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.label;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import org.apache.tapestry5.beaneditor.Validate;

public
class MavenDistributionLabelSet extends BasicFormBasedExecution
{
    public static
    class Command
    {
        @Validate("required")
        public String key;
        @Validate("required")
        public String value;
        public String distribution;
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

    public
    void onActivate(String pulp_href)
    {
        command = new Command();
        command.distribution = pulp_href;
    }
}
