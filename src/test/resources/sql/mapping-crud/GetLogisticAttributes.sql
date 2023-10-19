with consts (lang, bu) as (values (:lang, :bu))
select id,
       name -> consts.lang        as name,
       description -> consts.lang as description,
       type,
       constraints -> 'maxLength' as maxLength,
       constraints -> 'minValue'  as minValue,
       constraints -> 'maxValue'  as maxValue,
       constraints -> 'isMultiValued'  as isMultiValued,
       properties -> 'isMandatoryForSupplier' -> consts.bu as isMandatoryForSupplier,
       measure_id
from attribute,
     consts
where owner = 'LOGBRICK'
order by id