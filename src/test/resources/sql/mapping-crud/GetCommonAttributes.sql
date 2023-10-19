with consts (bu, lang)
         as (values (:bu, :lang))
select id,
       name -> consts.lang                                 as name,
       description -> consts.lang                          as description,
       owner,
       measure_id,
       properties -> 'functionalPerimeters'                as functionalPerimeters,
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
where id in
      ('ATT_99659', 'ATT_98807', 'ATT_99618', 'ATT_98822', 'ATT_98823', 'ATT_98808', 'ATT_98950', 'partnerProductMinimumOrderUnit',
       'ATT_99626', 'ATT_99663');