package io.samancore.condition_template.client.rest;

import io.samancore.common.model.condition.Condition;
import io.samancore.common.model.condition.ConditionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

import java.util.List;

@Path("/conditions")
@RegisterClientHeaders
public interface ConditionTemplateRestClient {

    @POST
    @Path("/eval")
    List<Condition> evalBlocking(ConditionRequest initialConditionRequest);

    @POST
    @Path("/eval")
    Uni<List<Condition>> eval(ConditionRequest initialConditionRequest);
}
