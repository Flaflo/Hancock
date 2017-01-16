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
package xyz.flaflo.hancock.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Saveable configuration
 *
 * @author Flaflo
 */
public class Configuration {

    private final String name;
    private final File file;

    private Map<Object, Object> defaults;
    private Map<Object, Object> configuration;

    /**
     * @param name the name
     */
    public Configuration(final String name) {
        this("/", name);
    }

    /**
     * @param directory the directory
     * @param name the name
     */
    public Configuration(final String directory, final String name) {
        this(directory, name, "cfg");
    }

    /**
     * @param name the name
     * @param defaults the defaults
     */
    public Configuration(final String name, final Map<Object, Object> defaults) {
        this("/", name, "cfg", defaults);
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param extension the extension
     */
    public Configuration(final String directory, final String name, final String extension) {
        this(directory, name, extension, new HashMap<>());
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param defaults the defaults
     */
    public Configuration(final String directory, final String name, final Map<Object, Object> defaults) {
        this(directory, name, "cfg", defaults);
    }

     /**
     * @param name the name
     * @param setup should the config setup
     */
    public Configuration(final String name, boolean setup) {
        this("/", name, setup);
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param setup should the config setup
     */
    public Configuration(final String directory, final String name, boolean setup) {
        this(directory, name, "cfg", setup);
    }

    /**
     * @param name the name
     * @param defaults the defaults
     * @param setup should the config setup
     */
    public Configuration(final String name, final Map<Object, Object> defaults, boolean setup) {
        this("/", name, "cfg", defaults, setup);
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param extension the extension
     * @param setup should the config setup
     */
    public Configuration(final String directory, final String name, final String extension, boolean setup) {
        this(directory, name, extension, new HashMap<>(), setup);
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param defaults the defaults
     * @param setup should the config setup
     */
    public Configuration(final String directory, final String name, final Map<Object, Object> defaults, boolean setup) {
        this(directory, name, "cfg", defaults, setup);
    }
    
    /**
     * @param directory the directory
     * @param name the name
     * @param extension the extension
     * @param defaults the defaults
     */
    public Configuration(final String directory, final String name, final String extension, final Map<Object, Object> defaults) {
        this(directory, name, extension, defaults, true);
    }

    /**
     * @param directory the directory
     * @param name the name
     * @param extension the extension
     * @param defaults the defaults
     */
    public Configuration(final String directory, final String name, final String extension, final Map<Object, Object> defaults, final boolean setup) {
        this.name = name;
        this.file = new File(directory, String.join(".", name, extension));
        this.defaults = defaults;

        if (setup) {
            this.reload();
        }
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public Object get(final String key) {
        return configuration.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public byte getByte(final String key) {
        return (byte) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public short getShort(final String key) {
        return (short) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public int getInt(final String key) {
        return (int) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public float getFloat(final String key) {
        return (float) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public double getDouble(final String key) {
        return (double) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public boolean getBoolean(final String key) {
        return (boolean) this.get(key);
    }

    /**
     * Returns the config value
     *
     * @param key the key bount to the value
     * @return the value bount to the key
     */
    public String getString(final String key) {
        return (String) this.get(key);
    }

    /**
     * Puts a config with a key and a value
     *
     * @param key the key
     * @param object the object
     */
    public void put(final String key, final Object object) {
        this.configuration.put(key, object);
    }

    /**
     * Puts a default config with a key and a value
     *
     * @param key the key
     * @param object the object
     */
    public void putDefault(final String key, final Object object) {
        this.defaults.put(key, object);
    }

    /**
     * Sets the default config
     *
     * @param defaults the defaults
     */
    public void setDefaults(final Map<Object, Object> defaults) {
        this.defaults = defaults;
    }

    /**
     * Loads the configuration from file
     */
    public void load() {
        if (file.exists()) {
            try {
                final BufferedReader fr = new BufferedReader(new FileReader(file));
                final String content = String.join("\n", fr.lines().toArray(String[]::new));

                final JSONObject json = new JSONObject(content);
                this.configuration = (Map) json.getJSONObject(name).toMap();

                fr.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Reloads the configuration
     */
    public void reload() {
        this.load();
        this.save();
    }
    
    /**
     * Saves the configuration to file
     */
    public void save() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.reset();
        }

        final JSONObject json = new JSONObject();
        json.put(name, configuration);

        try {
            final FileWriter fw = new FileWriter(file);
            fw.write(json.toString());
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Resets the configuration to its defaults
     */
    public void reset() {
        this.configuration = this.defaults;
    }

    /**
     * Returns the config defaults
     *
     * @return the defaults
     */
    public Map<Object, Object> getDefaults() {
        return defaults;
    }

    /**
     * Returns the configuration file
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the configuration name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    protected void finalize() throws Throwable {
        this.save();
    }
}
