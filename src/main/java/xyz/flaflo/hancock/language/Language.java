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
package xyz.flaflo.hancock.language;

import java.util.HashMap;

/**
 * Abstract Language
 *
 * @author Flaflo
 */
public abstract class Language {

    private final HashMap<String, String> dictionary;

    private final String name;
    private final String shortName;

    /**
     * @param name the full name
     * @param shortName the language shortcut
     */
    public Language(final String name, final String shortName) {
        this.name = name;
        this.shortName = shortName;

        this.dictionary = new HashMap<>();
    }

    /**
     * @param name the full name
     */
    public Language(final String name) {
        this(name, name.substring(0, 2).toLowerCase());
    }

    /**
     * Adds a language string to the dictionary
     *
     * @param key the key
     * @param value the value
     * @return the value
     */
    public String addString(final String key, final String value) {
        return this.getDictionary().put(key, value);
    }

    /**
     * Returns a string that belongs to the key
     *
     * @param key the key
     * @return the language string
     */
    public String getString(final String key) {
        return this.getDictionary().get(key);
    }

    /**
     * Returns the dictionary
     *
     * @return the dictionary
     */
    public HashMap<String, String> getDictionary() {
        return dictionary;
    }

    /**
     * Returns the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the language shortcut
     *
     * @return the language shortcut
     */
    public String getShortName() {
        return shortName;
    }
}
