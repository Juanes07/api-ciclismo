package com.sofka.cicilismo.service;

import com.sofka.cicilismo.collection.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


public class SequenceGeneradorService {

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    public Mono<Integer> getSequenceNumber(String sequenceName){
        Query query = new Query(Criteria.where("id").is(sequenceName));

        Update update = new Update().inc("seq",1);

        Mono<DbSequence> counter = reactiveMongoOperations
                .findAndModify(query,update,options().returnNew(true).upsert(true),DbSequence.class);
        return Objects.requireNonNull(counter.map(secuencia-> secuencia.getSeq()));
    }

}
