package sseffortless.annotations;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface SSEvent {
    String action() default "";
}
