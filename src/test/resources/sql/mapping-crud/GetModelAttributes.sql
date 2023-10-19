select id,
       name -> :lang                                 as name,
       description -> :lang                          as description,
       properties -> 'concernedBu'                   as concernedBu,
       properties -> 'isFilledBySupplier'            as isFilledBySupplier,
       properties -> 'functionalPerimeters'          as functionalPerimeters,
       type,
       constraints -> 'maxLength'                    as maxLength,
       constraints -> 'minValue'                     as minValue,
       constraints -> 'maxValue'                     as maxValue,
       constraints -> 'mask'                         as mask,
       constraints -> 'isMultiValued'                as isMultiValued,
       properties -> 'isMandatoryForSupplier' -> :bu as isMandatoryForSupplier,
       measure_id
from attribute
where id in ('ATT_99659', 'ATT_98807')

union

select attribute_id,
       name -> :lang                                    as name,
       description -> :lang                             as description,
       ma.properties -> 'concernedBu'                   as concernedBu,
       ma.properties -> 'isFilledBySupplier'            as isFilledBySupplier,
       ma.properties -> 'functionalPerimeters'          as functionalPerimeters,
       type,
       ma.constraints -> 'maxLength'                    as maxLength,
       ma.constraints -> 'minValue'                     as minValue,
       ma.constraints -> 'maxValue'                     as maxValue,
       ma.constraints -> 'mask'                         as mask,
       ma.constraints -> 'isMultiValued'                as isMultiValued,
       ma.properties -> 'isMandatoryForSupplier' -> :bu as isMandatoryForSupplier,
       measure_id
from model_attribute ma
         left join attribute on attribute.id = ma.attribute_id
where model_id = :modelId
  and ma.properties -> 'concernedBu' ??| array [:bu,'Toutes BU']
  and ma.properties -> 'isFilledBySupplier' @> ('{"' || :bu || '": true}')::jsonb
  and ma.properties -> 'functionalPerimeters' ?? :functionalPerimeters