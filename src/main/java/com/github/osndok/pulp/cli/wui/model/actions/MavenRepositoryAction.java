package com.github.osndok.pulp.cli.wui.model.actions;

import com.github.osndok.pulp.cli.wui.pages.pulp.maven.repository.MavenRepositoryDestroy;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.repository.MavenRepositoryShow;

public
enum MavenRepositoryAction implements ActionEnum
{
    add_cached_content,
    content_list,
    destroy(MavenRepositoryDestroy.class),
    label_set,
    label_show,
    label_unset,
    show(MavenRepositoryShow.class),
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
