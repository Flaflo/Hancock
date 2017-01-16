/*
 * Copyright (C) 2017 Flaflo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package xyz.flaflo.hancock.feature.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a feature to be loaded by the manager
 *
 * @author Flaflo
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HancockFeature {

    /**
     * Returns the feature name
     *
     * @return the name
     */
    String name();

    /**
     * Returns the feature description
     *
     * @return the description or "No Description"
     */
    String description() default "No Description";

    /**
     * Returns the feature author
     *
     * @return the author or "Unknown"
     */
    String author() default "Unknown";

    /**
     * Returns the feature version.
     *
     * @return the version or "1.0"
     */
    double version() default 1.0;
}
