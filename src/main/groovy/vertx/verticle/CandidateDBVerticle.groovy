package vertx.verticle

import groovy.transform.CompileStatic
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import vertx.entity.Candidate

/**
 * @author huantt on 2/27/20
 */
@CompileStatic
class CandidateDBVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(CandidateDBVerticle.class);

    private List<Candidate> candidates = new ArrayList<>()

    @Override
    void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus()

        eventBus.consumer("candidates.get", { Message message ->
            logger.info("candidates.get")
            if (candidates.isEmpty()) {
                message.reply("Anh Vu thay chua <3")
            } else message.reply(candidates.collect { it.toString() }.join("\n"))
        })

        eventBus.consumer("candidates.add", { Message message ->
            logger.info("candidates.add")
            logger.info(Thread.currentThread().getName())
            vertx.executeBlocking({ promise ->
                logger.info(Thread.currentThread().getName())
                Thread.sleep(6000000)
                candidates.add(new Candidate(message.body().toString()))
            }, {})
        })

        eventBus.consumer("candidates.vote", { Message message ->
            Candidate matchingCandidate = candidates.find { it.id == message.body().toString().toInteger() }
            matchingCandidate.setVotes(++matchingCandidate.getVotes())
        })

        eventBus.consumer("candidates.getById", { Message message ->
            message.reply(candidates.find { it.id == message.body().toString().toInteger() }?.toString() ?: "404")
        })
    }
}
