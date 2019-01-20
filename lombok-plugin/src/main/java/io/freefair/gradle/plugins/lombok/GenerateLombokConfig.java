package io.freefair.gradle.plugins.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Map;

@Getter
@Setter
public class GenerateLombokConfig extends DefaultTask {

    @OutputFile
    private final RegularFileProperty outputFile = getProject().getObjects().fileProperty();

    @Input
    private final MapProperty<String, String> properties = getProject().getObjects().mapProperty(String.class, String.class);

    public GenerateLombokConfig() {
        outputFile.set(getProject().file("lombok.config"));
        onlyIf(t -> getProperties().isPresent() && !getProperties().get().isEmpty());
    }

    @TaskAction
    @SneakyThrows
    public void generateLombokConfig() {
        try (PrintWriter writer = ResourceGroovyMethods.newPrintWriter(outputFile.getAsFile().get(), "ISO-8859-1")) {
            writer.println("# This file is generated by the 'io.freefair.lombok' Gradle plugin");
            properties.get().entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey, String.CASE_INSENSITIVE_ORDER))
                    .forEach(entry ->
                            writer.println(entry.getKey() + " = " + entry.getValue())
                    );
        }
    }
}
