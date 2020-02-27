package vertx

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import vertx.verticle.CandidateDBVerticle
import vertx.verticle.MainVerticle

/**
 * @author huantt on 2/27/20
 */
@CompileStatic
class App {

    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions()
        Vertx vertx = Vertx.vertx(vertxOptions)
        vertx.deployVerticle(CandidateDBVerticle.class.getName(),
                new DeploymentOptions().setInstances(5), res -> {
            if (res.succeeded()) print("Deployed CandidateDBVerticle")
            else res.result()
        })
        vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setInstances(10), res -> {
            if (res.succeeded()) print("Deployed MainVerticle")
            else res.result()
        })
    }
}
