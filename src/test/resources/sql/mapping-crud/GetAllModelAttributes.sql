select attribute_id                                     as id,
       name -> :lang                                    as name,
       description -> :lang                             as description,
       ma.properties -> 'concernedBu'                   as concernedBu,
       ma.properties -> 'isFilledBySupplier'            as isFilledBySupplier,
       type,
       owner,
       ma.constraints -> 'maxLength'                    as maxLength,
       ma.constraints -> 'minValue'                     as minValue,
       ma.constraints -> 'maxValue'                     as maxValue,
       ma.constraints -> 'mask'                         as mask,
       ma.constraints -> 'isMultiValued'                as isMultiValued,
       ma.properties -> 'isMandatoryForSupplier' -> :bu as isMandatoryForSupplier,
       ma.properties -> 'functionalPerimeters'          as functionalPerimeters,
       measure_id
from model_attribute ma
         left join attribute on attribute.id = ma.attribute_id
where (model_id = :modelId
  and ma.properties -> 'concernedBu' ??| array [:bu,'Toutes BU']
  and ma.properties -> 'isFilledBySupplier' @> ('{"' || :bu || '": true}')::jsonb)

union

select id                                            as id,
       name -> :lang                                 as name,
       description -> :lang                          as description,
       properties -> 'concernedBu'                   as concernedBu,
       properties -> 'isFilledBySupplier'            as isFilledBySupplier,
       type,
       owner,
       constraints -> 'maxLength'                    as maxLength,
       constraints -> 'minValue'                     as minValue,
       constraints -> 'maxValue'                     as maxValue,
       constraints -> 'mask'                         as mask,
       constraints -> 'isMultiValued'                as isMultiValued,
       properties -> 'isMandatoryForSupplier' -> :bu as isMandatoryForSupplier,
       properties -> 'functionalPerimeters'          as functionalPerimeters,
       measure_id
from attribute
where owner = 'LOGBRICK'
or id in ('ATT_99659', 'ATT_98807', 'ATT_98822', 'ATT_98823', 'ATT_98808', 'ATT_98950', 'partnerProductMinimumOrderUnit',
       'ATT_99626', 'ATT_99663')