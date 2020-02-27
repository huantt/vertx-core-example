package vertx.entity

import groovy.transform.CompileStatic

/**
 * @author huantt on 2/27/20
 */
@CompileStatic
class Candidate {
    String name
    Integer id
    Integer votes = 0

    @Override
    String toString() {
        return "$id|$name|$votes"
    }

    Candidate(Integer id, String name) {
        this.name = name
        this.id = id
    }

    Candidate(String rawData) {
        String[] paths = rawData.split("\\|")
        this.id = paths[0].toInteger()
        this.name = paths[1]
        this.votes = paths[2].toInteger()
    }
}
