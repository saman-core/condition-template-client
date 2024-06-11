package io.samancore.condition_template.client;

import io.samancore.common.model.condition.Condition;
import io.samancore.common.model.condition.ConditionRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ConditionTemplateClient {

    List<Condition> evalBlocking(String product, String template, ConditionRequest initialConditionRequest);

    Uni<List<Condition>> eval(String product, String template, ConditionRequest initialConditionRequest);
}