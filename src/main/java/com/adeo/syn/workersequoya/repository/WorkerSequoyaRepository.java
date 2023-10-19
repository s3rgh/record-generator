package com.adeo.syn.workersequoya.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class WorkerSequoyaRepository {
    private static final String GET_DEPARTMENTS_BY_MODEL = "select org_perimeter_ids from worker_sequoya.org_perimeters_model_link where model_id = :id;";

    @Autowired
    @Qualifier("workerSequoyaJdbc")
    private NamedParameterJdbcTemplate workerSequoyaJdbc;

    public List<Integer> getDepartmentsForModel(String id) {
        var listOfDepartments = workerSequoyaJdbc.queryForObject(GET_DEPARTMENTS_BY_MODEL, Map.of("id", id), String.class);
        return Stream.of(listOfDepartments.split("\\D+"))
                .filter(e -> !e.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }
}
