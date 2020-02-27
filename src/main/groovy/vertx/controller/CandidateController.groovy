package vertx.controller

import groovy.transform.CompileStatic
import io.vertx.core.eventbus.EventBus
import io.vertx.ext.web.RoutingContext
import vertx.entity.Candidate

/**
 * @author huantt on 2/27/20
 */
@CompileStatic
class CandidateController {

    public static void addCandidate(RoutingContext context) {
        Integer id = new Random().nextInt(10000)
        String name = context.getBodyAsJson().getString("name")
        Candidate newCandidate = new Candidate(id, name)

        EventBus eventBus = context.vertx().eventBus()
        eventBus.publish("candidates.add", newCandidate.toString())
        context.response().setStatusCode(202).end(id.toString())
    }

    public static void getCandidate(RoutingContext context) {
        Integer candidateId = context.request().getParam("candidate_id").toInteger()
        EventBus eventBus = context.vertx().eventBus()
        eventBus.request("candidates.getById", candidateId, { responseMessage ->
            String rawData = responseMessage.result().body()
            context.response().setStatusCode(200).end(rawData)
        })
    }

    public static void vote(RoutingContext context) {
        Integer candidateId = context.request().getParam("candidate_id").toInteger()
        EventBus eventBus = context.vertx().eventBus()
        eventBus.publish("candidates.vote", candidateId)
        context.response().setStatusCode(202).end()
    }

    public static void getCandidates(RoutingContext context) {
        EventBus eventBus = context.vertx().eventBus()
        eventBus.request("candidates.get", "all", { responseMessage ->
            String rawData = responseMessage.result().body()
            context.response().setStatusCode(200).end(rawData)
        })
    }
}
