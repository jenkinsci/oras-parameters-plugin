package io.jenkins.plugins.oras.parameter;

import java.util.Map;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Parameter value for ORAS tag parameters.
 */
public class OrasPlatformParameterValue extends AbstractOrasParameterValue {

    private final OrasPlatformParameterDefinition.PlatformWrapper platform;

    @DataBoundConstructor
    public OrasPlatformParameterValue(
            String name,
            String registry,
            String repository,
            String tag,
            String digest,
            Map<String, String> annotations,
            OrasPlatformParameterDefinition.PlatformWrapper platform) {
        super(name, registry, repository, tag, digest, annotations);
        this.platform = platform;
    }

    public OrasPlatformParameterDefinition.PlatformWrapper getPlatform() {
        return platform;
    }

    @Override
    public String getDigest() {
        return platform.getDigest();
    }

    @Override
    public void addExtraVars(Map<String, String> vars) {
        String name = getName();
        OrasPlatformParameterDefinition.PlatformWrapper platform = getPlatform();
        vars.put(name + "_PLATFORM_OS", platform.os());
        vars.put(name + "_PLATFORM_ARCH", platform.architecture());
        if (platform.getVariant() != null) {
            vars.put(name + "_PLATFORM_VARIANT", platform.architecture());
        }
    }
}
