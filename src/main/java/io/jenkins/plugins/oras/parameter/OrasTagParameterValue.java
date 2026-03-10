package io.jenkins.plugins.oras.parameter;

import java.util.Map;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Parameter value for ORAS tag parameters.
 */
public class OrasTagParameterValue extends AbstractOrasParameterValue {

    @DataBoundConstructor
    public OrasTagParameterValue(
            String name,
            String registry,
            String repository,
            String tag,
            String digest,
            Map<String, String> annotations) {
        super(name, registry, repository, tag, digest, annotations);
    }
}
