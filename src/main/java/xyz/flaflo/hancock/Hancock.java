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
package xyz.flaflo.hancock;

import com.google.common.collect.ImmutableMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;
import xyz.flaflo.hancock.command.CommandDispatcher;
import xyz.flaflo.hancock.config.Configuration;
import xyz.flaflo.hancock.feature.FeatureManager;
import xyz.flaflo.hancock.listeners.MessageReceivedListener;

/**
 * Hancocks Main class
 *
 * @author Flaflo
 */
public final class Hancock implements EventListener {

    private static Hancock HANCOCK;

    private final Configuration config;
    private JDA jda;

    private final CommandDispatcher commandDispatcher;

    private final FeatureManager featureManager;

    public Hancock() {
        this.commandDispatcher = new CommandDispatcher();
        this.featureManager = new FeatureManager();

        this.config = new Configuration("hancock", "hancock", ImmutableMap.builder().put("token", "").put("command", "!").build());
        try {
            this.connect();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Connects the bot to discord
     */
    public void connect() throws LoginException, IllegalArgumentException, RateLimitedException, InterruptedException {
        this.jda = new JDABuilder(AccountType.BOT).setToken(this.config.getString("token")).setAutoReconnect(true).addListener(this, new MessageReceivedListener()).buildAsync();
    }

    /**
     * Disconnects the bot from discord
     */
    public void disconnect() {
        this.jda.shutdown();
    }

    /**
     * Called when the client is ready
     */
    private void onEnable() {
        try {
            this.featureManager.loadFeatures();
        } catch (final Exception ex) {
            Logger.getLogger(Hancock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Called when the bot gets shut down
     */
    private void onDisable() {
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof ReadyEvent) {
            this.onEnable();
        } else if (event instanceof ShutdownEvent) {
            this.onDisable();
        }
    }

    /**
     * Returns the JDA
     *
     * @return the jda
     */
    public JDA getJda() {
        return jda;
    }

    /**
     * Returns the command dispatcher
     *
     * @return the command dispatcher
     */
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    /**
     * Returns the feature manager
     *
     * @return the feature manager
     */
    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    /**
     * Returns the command prefix
     *
     * @return the prefix
     */
    public String getCommandPrefix() {
        return config.getString("command");
    }

    /**
     * Returns the configuration for hancock
     *
     * @return the config
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * The program entry point
     *
     * @param args the args
     */
    public static void main(String[] args) {
        HANCOCK = new Hancock();
    }

    /**
     * Returns the Bots Singleton
     *
     * @return the hancock
     */
    public static Hancock getHancock() {
        return HANCOCK;
    }
}
