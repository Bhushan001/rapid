package com.techie.rapid.auth.security.annotation;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public @interface SuperAdminOnly {
}
