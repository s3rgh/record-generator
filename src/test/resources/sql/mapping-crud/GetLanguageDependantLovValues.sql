select id,
       name -> '%s' as name,
       name[0]      as name2
from value_entity
where id in (select value_id
             from model_attribute_value
             where ma_id = (select id from model_attribute where attribute_id = '%s' AND model_id = '%s'))
union
select id,
       name -> '%s' as name,
       name[0]      as name2
from value_entity
where id in (select value_id from common_attribute_value where attribute_id = '%s')
