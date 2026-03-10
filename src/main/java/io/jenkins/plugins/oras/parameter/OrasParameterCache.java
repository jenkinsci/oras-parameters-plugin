package io.jenkins.plugins.oras.parameter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Cache digests and annotations and avoid querying the Registry multiple times
 */
final class OrasParameterCache {

    /**
     * Hard cache limit
     */
    public static final int MAX_CACHE_SIZE = 100;

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private OrasParameterCache() {
        // Private constructor to prevent instantiation
    }

    /**
     * The digest cache
     */
    private static final Cache<String, String> DIGESTS = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .maximumSize(MAX_CACHE_SIZE)
            .build();

    /**
     * The digest cache
     */
    private static final Cache<String, Map<String, String>> ANNOTATIONS = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .maximumSize(MAX_CACHE_SIZE)
            .build();

    static void putDigest(String effectiveRef, String digest) {
        DIGESTS.put(effectiveRef, digest);
    }

    static @Nullable String getDigest(String effectiveRef) {
        return DIGESTS.getIfPresent(effectiveRef);
    }

    static void putAnnotations(String effectiveRef, Map<String, String> annotations) {
        ANNOTATIONS.put(effectiveRef, annotations);
    }

    static @Nullable Map<String, String> getAnnotations(String effectiveRef) {
        return ANNOTATIONS.getIfPresent(effectiveRef);
    }
}
