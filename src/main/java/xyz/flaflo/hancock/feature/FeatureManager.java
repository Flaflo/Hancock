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

import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.apache.commons.lang3.ArrayUtils;
import xyz.flaflo.hancock.config.Configuration;
import xyz.flaflo.hancock.feature.annotations.HancockFeature;

/**
 * Manages features
 *
 * @author Flaflo
 */
public final class FeatureManager {

    private final Configuration config;
    private final File directory;

    private Feature[] features;

    public FeatureManager() {
        this.features = new Feature[0];

        this.config = new Configuration("hancock", "features", ImmutableMap.builder().put("directory", "features/").build());
        this.directory = new File(this.config.getFile().getParentFile(), this.config.getString("directory"));

        this.directory.mkdirs();
    }

    /**
     * Loads the feature jars from directory
     */
    public void loadFeatures() throws MalformedURLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        this.unloadFeatures();

        for (File file : this.getDirectory().listFiles()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                final URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURL()});

                final JarInputStream jis = new JarInputStream(new FileInputStream(file));
                JarEntry entry;

                while ((entry = jis.getNextJarEntry()) != null) {
                    if (entry.getName().endsWith(".class")) {
                        final String classPath = entry.getName().replace("/", ".");
                        final Class clazz = classLoader.loadClass(classPath.substring(0, classPath.length() - ".class".length()));

                        if (Feature.class.isAssignableFrom(clazz)) {
                            for (final Annotation annotation : clazz.getAnnotations()) {
                                if (annotation instanceof HancockFeature) {
                                    final HancockFeature featureAnnotation = (HancockFeature) annotation;
                                    final Constructor<Feature> constructor = (Constructor<Feature>) clazz.getConstructor();
                                    final Feature feature = constructor.newInstance();

                                    feature.setName(featureAnnotation.name());
                                    feature.setDescription(featureAnnotation.description());
                                    feature.setAuthor(featureAnnotation.author());
                                    feature.setVersion(featureAnnotation.version());

                                    this.addFeature(feature);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Unloads the features
     */
    public void unloadFeatures() throws InterruptedException {
        for (final Feature feature : this.getFeatures()) {
            feature.onDisable();
        }
    }

    /**
     * Finds feature by exact name if no feature found it tries to search by
     * ignoring case when still no feature found it returns null
     *
     * @param name the name
     * @return the feature
     */
    public Feature getFeature(final String name) {
        return Arrays.stream(this.getFeatures()).filter(f -> f.getName().equalsIgnoreCase(name)).sorted((f1, f2) -> f1.getName().equals(name) ? -1 : (f2.getName().equals(name) ? 1 : 0)).findFirst().orElse(null);
    }

    /**
     * Adds a feature
     *
     * @param feature the feature
     */
    private void addFeature(final Feature feature) {
        this.features = ArrayUtils.add(this.features, feature);

        feature.onLoad();
    }

    /**
     * Removes a feature
     *
     * @param feature the feature
     */
    private void removeFeature(final Feature feature) {
        this.features = ArrayUtils.removeElement(this.features, feature);
    }

    /**
     * Returns the directory where features are stored
     *
     * @return the directory
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Returns an immutable array of active features
     *
     * @return the features
     */
    private Feature[] getFeatures() {
        return Arrays.copyOf(this.features, this.features.length);
    }
}
