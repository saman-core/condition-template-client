package io.samancore.condition_template.client.rest;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import io.samancore.common.model.condition.Condition;
import io.samancore.common.model.condition.ConditionRequest;
import io.samancore.condition_template.client.ConditionTemplateClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class ConditionTemplateRestClientWrapper implements ConditionTemplateClient {

    @Inject
    Logger log;

    @ConfigProperty(name = "conditionTemplateUrl")
    String conditionTemplateUrl;

    @Override
    public List<Condition> evalBlocking(String product, String template, ConditionRequest initialConditionRequest) {
        log.debugf("ConditionTemplateRestClientWrapper.eval %s", initialConditionRequest);
        var url = String.format(conditionTemplateUrl, product, template);
        var conditionTemplateRestClient = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(url))
                .build(ConditionTemplateRestClient.class);
        return conditionTemplateRestClient.evalBlocking(initialConditionRequest);
    }

    @Override
    public Uni<List<Condition>> eval(String product, String template, ConditionRequest initialConditionRequest) {
        log.debugf("ConditionTemplateRestClientWrapper.eval %s", initialConditionRequest);
        var url = conditionTemplateUrl.replace("PRODUCT", product).replace("TEMPLATE", template);
        var conditionTemplateRestClient = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(url))
                .build(ConditionTemplateRestClient.class);
        return Uni.createFrom()
                .item(initialConditionRequest)
                .onItem().transformToUni(request -> conditionTemplateRestClient.eval(request));
    }
}