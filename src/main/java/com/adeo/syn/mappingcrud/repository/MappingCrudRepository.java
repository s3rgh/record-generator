package com.adeo.syn.mappingcrud.repository;

import com.adeo.syn.enums.Language;
import com.adeo.syn.mappingcrud.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adeo.syn.utils.FileUtils.readString;

@Repository
public class MappingCrudRepository {
    private static final String LANG_NAMES_FROM_MODEL_LIST = "select id, name -> :lang, owner from model where id in (:ids) " +
            "and name ->> :lang not like '%DO NOT USE%';";

    @Autowired
    @Qualifier("mappingCrudJdbc")
    private NamedParameterJdbcTemplate mappingCrudJdbc;

    public List<Model> getModelNamesByListIds(List<String> ids, Language language) {
        return mappingCrudJdbc.query(LANG_NAMES_FROM_MODEL_LIST, Map.of("ids", ids, "lang", language.name()), new DataClassRowMapper<>(Model.class));
    }

    public List<Map<String, Object>> getAllModelAttributes(String bu, Language language, String modelId) {
        String script = readString("sql/mapping-crud/GetAllModelAttributes.sql");

        return mappingCrudJdbc
                .queryForList(script, Map.of("bu", bu, "lang", language.name(), "modelId", modelId));
    }

    public List<Map<String, Object>> getCommonAttributesForTemplate(String bu, Language language) {
        String script = readString("sql/mapping-crud/GetCommonAttributes.sql");
        return mappingCrudJdbc
                .queryForList(script, Map.of("bu", bu,
                        "lang", language.name()));
    }

    public List<String> getLovAttributeValueIds(String attributeId, String modelId) {
        return mappingCrudJdbc.queryForList(
                        "select id from value_entity where id in " +
                                "(select value_id from model_attribute_value where ma_id = " +
                                "(select id from model_attribute where attribute_id = '" + attributeId + "' " +
                                "AND model_id = '" + modelId + "'))",
                        new EmptySqlParameterSource(), String.class)
                .stream().map(s -> s.replace(attributeId + "--", "")).collect(Collectors.toList());
    }

    public List<String> getCommonValueIds(String attributeId) {
        return mappingCrudJdbc
                .queryForList("select value_id from mapping_crud.common_attribute_value where attribute_id = '" + attributeId + "'",
                        new EmptySqlParameterSource(), String.class);
    }
}
