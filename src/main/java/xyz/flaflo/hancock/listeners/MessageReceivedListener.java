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
package xyz.flaflo.hancock.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import xyz.flaflo.hancock.Hancock;

/**
 * Listens for received messages
 *
 * @author Flaflo
 */
public final class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor() != event.getJDA().getSelfUser() && event.getMessage().getContent().startsWith(Hancock.getHancock().getCommandPrefix())) {
            if (!Hancock.getHancock().getCommandDispatcher().dispatchCommand(event.getMessage())) {
                event.getMessage().getChannel().sendMessage("Command not recognized.").queue();
            }
        }
    }
}
