package io.freefair.gradle.plugins.mkdocs.tasks;

import lombok.Getter;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.process.CommandLineArgumentProvider;
import org.gradle.process.ExecSpec;

import java.util.LinkedList;

/**
 * Create a new MkDocs project
 */
@Getter
public class MkDocsNew extends MkDocs {

    @Optional
    @OutputDirectory
    private final DirectoryProperty projectDirectory = getProject().getObjects().directoryProperty();

    public MkDocsNew() {
        super("new");
        setDescription("Create a new MkDocs project");
    }

    @Override
    void setArgs(ExecSpec mkdocs) {
        if (getProjectDirectory().isPresent()) {
            mkdocs.args(getProjectDirectory().getAsFile().get().getAbsolutePath());
        }
    }
}
