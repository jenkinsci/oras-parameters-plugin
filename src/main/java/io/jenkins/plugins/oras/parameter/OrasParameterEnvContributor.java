package io.jenkins.plugins.oras.parameter;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentContributor;
import hudson.model.ParametersAction;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.Run;
import hudson.model.TaskListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension
@SuppressWarnings("unused")
public class OrasParameterEnvContributor extends EnvironmentContributor {

    private static final Logger LOG = LoggerFactory.getLogger(OrasParameterEnvContributor.class);

    @Override
    @SuppressWarnings("unchecked")
    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Both implementation never return null")
    public void buildEnvironmentFor(@NonNull Run build, @NonNull EnvVars variables, @NonNull TaskListener listener) {

        List<ParametersAction> actions = build.getActions(ParametersAction.class);
        ParametersDefinitionProperty defs =
                (ParametersDefinitionProperty) build.getParent().getProperty(ParametersDefinitionProperty.class);

        // Default definition
        if (defs != null) {
            for (AbstractOrasParameterDefinition d : defs.getParameterDefinitions().stream()
                    .filter(d -> d instanceof AbstractOrasParameterDefinition)
                    .map(d -> (AbstractOrasParameterDefinition) d)
                    .toList()) {
                OrasTagParameterValue.addVars((AbstractOrasParameterValue) d.getDefaultParameterValue(), variables);
            }
        }

        // Found values
        for (ParametersAction parameterAction : actions) {
            for (AbstractOrasParameterValue p : parameterAction.getParameters().stream()
                    .filter(p -> p instanceof AbstractOrasParameterValue)
                    .map(p -> (AbstractOrasParameterValue) p)
                    .toList()) {
                OrasTagParameterValue.addVars(p, variables);
            }
        }
    }
}
