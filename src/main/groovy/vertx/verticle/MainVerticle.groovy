package vertx.verticle


import groovy.transform.CompileStatic
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import vertx.controller.CandidateController

/**
 * @author huantt on 2/27/20
 */
@CompileStatic
class MainVerticle extends AbstractVerticle {

    private HttpServer server

    @Override
    void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.route(HttpMethod.POST, "/candidates").handler(CandidateController::addCandidate)
        router.route(HttpMethod.GET, "/candidates").handler(CandidateController::getCandidates)
        router.route(HttpMethod.GET, "/candidates/:candidate_id").handler(CandidateController::getCandidate)
        router.route(HttpMethod.GET, "/vote/:candidate_id").handler(CandidateController::vote)
        server = vertx.createHttpServer().requestHandler(router).listen(8080)
    }
}
