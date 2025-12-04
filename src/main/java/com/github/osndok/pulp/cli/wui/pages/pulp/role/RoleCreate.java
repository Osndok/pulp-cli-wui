package com.github.osndok.pulp.cli.wui.pages.pulp.role;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import com.github.osndok.pulp.cli.wui.meta.TodoMultiValue;

public
class RoleCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        public String name;
        public String description;
        public Void no_permission;
        @TodoMultiValue
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
