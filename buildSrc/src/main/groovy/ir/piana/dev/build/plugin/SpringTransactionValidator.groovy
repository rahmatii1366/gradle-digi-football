package ir.piana.dev.build.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.reflect.JavaReflectionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/17/2019 11:43 AM
 **/
public class SpringTransactionValidator implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task('checkTransactionalAnnotation') {
            doLast {
                project.sourceSets.main.allJava.findAll().each { javaFile ->
                    if (JavaReflectionUtil.isAnnotationPresent(Transactional)) { // TODO by Vesalian : javaFile.isAnnotationPresent(Transactional)
                        throw new IllegalAccessException("You cannot use '@org.springframework.transaction.annotation.Transactional' directly!")
                    }
                }
            }
        }
        project.build.dependsOn project.checkTransactionalAnnotation
    }
}
