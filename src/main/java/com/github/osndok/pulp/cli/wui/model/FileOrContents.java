package com.github.osndok.pulp.cli.wui.model;

/**
 * TODO: A multi-purpose field that supports either (1) uploading a file, or (2) pasting the
 * contents of the (text) file into a large text area.
 *
 * If a file is passed to the executor, it should be prefixed with '@', otherwise the argument
 * is simply the raw text of the file contents itself.
 *
 * ? If this was a conventional Tapestry app, we could make a separate upload-then-tag mechanism,
 * and present the uploaded/pasted-but-not-tagged entries is a popup list. But how far do we
 * want to stray from the goal of being a thin CLI wrapper?
 */
public
class FileOrContents
{
}
