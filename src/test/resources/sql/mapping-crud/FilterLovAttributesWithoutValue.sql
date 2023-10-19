with ma_cte as (select ma.id, ma.attribute_id
                from mapping_crud.model_attribute as ma
                where ma.model_id = :modelId
                  and ma.attribute_id in
                      (:attributeIds))
select attribute_id from ma_cte
                             left join mapping_crud.model_attribute_value as mav
                                       on ma_cte.id = mav.ma_id
where mav.ma_id is null;