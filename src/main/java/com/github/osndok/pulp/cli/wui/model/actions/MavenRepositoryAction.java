package com.github.osndok.pulp.cli.wui.model.actions;

public
enum MavenRepositoryAction implements ActionEnum
{
    add_cached_content,
    content_list,
    destroy,
    label_set,
    label_show,
    label_unset,
    show,
    task_list,
    update,
    version_destroy,
    version_list,
    version_repair,
    version_show;

    private final Class actionPageClass;

    MavenRepositoryAction()
    {
        actionPageClass = null;
    }

    MavenRepositoryAction(Class actionPageClass)
    {
        this.actionPageClass = actionPageClass;
    }

    @Override
    public
    Class getActionPageClass()
    {
        return actionPageClass;
    }
}
