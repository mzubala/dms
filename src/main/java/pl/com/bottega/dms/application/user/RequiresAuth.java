package pl.com.bottega.dms.application.user;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAuth {

    String[] value() default {};

}
