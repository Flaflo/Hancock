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
package xyz.flaflo.hancock.command;

import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Abstract command base
 *
 * @author Flaflo
 */
public abstract class Command {

    private final String name;
    private final String description;
    private final String[] aliases;

    /**
     * @param name the name
     * @param description the description
     * @param aliases the aliases
     */
    public Command(final String name, final String description, final String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    /**
     * @param name the name
     * @param description the description
     */
    public Command(final String name, final String description) {
        this(name, description, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * @param name the name
     */
    public Command(final String name) {
        this(name, "No Description");
    }

    /**
     * Executed on command invokation
     *
     * @param label the used alias
     * @param args the args
     * @param message the message
     */
    public abstract void onCommand(final String label, final String[] args, final Message message);

    /**
     * Returns the command description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the command aliases
     *
     * @return the aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Returns the command name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
