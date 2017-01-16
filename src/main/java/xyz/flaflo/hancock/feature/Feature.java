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
package xyz.flaflo.hancock.feature;

import xyz.flaflo.hancock.Hancock;
import xyz.flaflo.hancock.config.Configuration;

/**
 * An abstract bot feature module
 *
 * @author Flaflo
 */
public abstract class Feature {

    private String name;
    private String description;
    private String author;
    private double version;

    private Configuration config;

    /**
     * Called when the plugins gets loaded
     */
    public final void onLoad() {
        this.config = new Configuration(Hancock.getHancock().getFeatureManager().getDirectory().getPath() + "/" + this.getName().toLowerCase() + "/", this.getName().toLowerCase(), false);
        this.onEnable();
    }

    /**
     * Called when the feature is been enabled
     */
    public void onEnable() {
    }

    /**
     * Called when the feature is been disabled
     */
    public void onDisable() {
    }

    /**
     * Returns the default configuration
     *
     * @return the config
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Returns the name
     *
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the description
     *
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Returns the author
     *
     * @return the author
     */
    public final String getAuthor() {
        return author;
    }

    /**
     * Returns the version
     *
     * @return the version
     */
    public final double getVersion() {
        return version;
    }

    /**
     * Sets the name
     *
     * @param name the name
     */
    final void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the description
     *
     * @param description the description
     */
    final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the author
     *
     * @param author the author
     */
    final void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Sets the version
     *
     * @param version the version
     */
    final void setVersion(final double version) {
        this.version = version;
    }
}
