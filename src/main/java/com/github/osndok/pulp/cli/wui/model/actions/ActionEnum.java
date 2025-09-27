package com.github.osndok.pulp.cli.wui.model.actions;

public
interface ActionEnum
{
    /**
     * @return The page responsible for allowing this action to be performed, or null if it is not yet implemented.
     */
    Class getActionPageClass();

    /**
     * @return The query string key that will help differentiate
     */
    default
    String getQueryStringKey()
    {
        var className = getClass().getSimpleName();
        return className.substring(0, className.length() - "Action".length());
    }
}
