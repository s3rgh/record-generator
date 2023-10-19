with consts (bu, lang, modelId) as (values (:bu, :lang, :modelId))
select id,
       name -> consts.lang                                 as name,
       description -> consts.lang                          as description,
       properties -> 'concernedBu'                         as concernedBu,
       properties -> 'isFilledBySupplier'                  as isFilledBySupplier,
       type,
       constraints -> 'maxLength'                          as maxLength,
       constraints -> 'minValue'                           as minValue,
       constraints -> 'maxValue'                           as maxValue,
       constraints -> 'mask'                               as mask,
       constraints -> 'isMultiValued'                      as isMultiValued,
       properties -> 'isMandatoryForSupplier' -> consts.bu as isMandatoryForSupplier
from attribute,
     consts
where id in (select attribute_id from model_attribute where model_id = consts.modelId)
  and properties -> 'concernedBu' ??| array [consts.bu,'Toutes BU']
  and properties -> 'isFilledBySupplier' @> ('{"' || consts.bu || '": true}')::jsonb
  and properties -> 'isMandatoryForSupplier' @> ('{"' || consts.bu || '": true}')::jsonb
  and (properties -> 'functionalPerimeters' ?? 'DESCRIPTIVE'
    or properties -> 'functionalPerimeters' ?? 'QUALITY')