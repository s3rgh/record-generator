with consts (lang) as (values (:lang))
select id,
       name -> consts.lang        as name,
       description -> consts.lang as description,
       type,
       constraints -> 'maxLength' as maxLength,
       constraints -> 'minValue'  as minValue,
       constraints -> 'maxValue'  as maxValue,
       measure_id
from mapping_crud.attribute,
     consts
where owner = 'LOGBRICK'
or id = 'inner_barcode'
or id = 'outer_barcode';