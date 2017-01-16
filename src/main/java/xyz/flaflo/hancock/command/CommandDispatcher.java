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

import java.util.Arrays;
import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.lang3.ArrayUtils;
import xyz.flaflo.hancock.Hancock;

/**
 * Command Dispatcher
 *
 * @author Flaflo
 */
public final class CommandDispatcher {

    private Command[] commands;

    public CommandDispatcher() {
        this.commands = new Command[0];
    }

    /**
     * Invokes a Command by a Message
     *
     * @param message the message to dispatch the command from
     * @return true if the command was found and successfully dispatched
     */
    public boolean dispatchCommand(final Message message) {
        final String[] messageParts = message.getContent().split(" ");

        if (messageParts.length >= 1) {
            final String label = messageParts[0].replaceFirst(Hancock.getHancock().getCommandPrefix(), "");
            final Command command = this.getCommand(label);

            if (command != null) {
                command.onCommand(label, ArrayUtils.subarray(messageParts, 1, messageParts.length), message);

                return true;
            }
        }

        return false;
    }

    /**
     * Finds a Command by an alias or name
     *
     * @param command the command to find
     * @return the command
     */
    public Command getCommand(final String command) {
        for (final Command cmd : this.getCommands()) {
            final String name = cmd.getName();

            if (name.equalsIgnoreCase(command)) {
                return cmd;
            } else {
                final String[] aliases = cmd.getAliases();

                for (final String alias : aliases) {
                    if (alias.equalsIgnoreCase(command)) {
                        return cmd;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Registers a command to the dispatcher
     *
     * @param command the command
     */
    public void registerCommand(final Command command) {
       this.commands = ArrayUtils.add(this.commands, command);
    }

    /**
     * Unregisters a command from the dispatcher
     *
     * @param command the command
     */
    public void unregisterCommand(final Command command) {
        this.commands = ArrayUtils.removeElement(this.commands, command);
    }

    /**
     * Returns an immutable array of registered commands
     *
     * @return the commands
     */
    public Command[] getCommands() {
        return Arrays.copyOf(this.commands, this.commands.length);
    }
}
