package com.github.osndok.pulp.cli.wui.pages.pulp.role;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import com.github.osndok.pulp.cli.wui.meta.TodoMultiValue;
import org.apache.tapestry5.beaneditor.Validate;

public
class RoleCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        @Validate("required")
        public String name;
        public String description;
        public Void no_permission;
        @TodoMultiValue
        @Validate("required")
        public String permission;
    }

    private Command command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }
}
