/*
 * Decompiled with CFR 0_132.
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnegative;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

@Documented
@Nonnegative(when=When.MAYBE)
@Retention(value=RetentionPolicy.RUNTIME)
@TypeQualifierNickname
public @interface CheckForSigned {
}
