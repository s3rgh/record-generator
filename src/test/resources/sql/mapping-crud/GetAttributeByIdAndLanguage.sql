select id,
       name -> :lang        as name,
       description -> :lang as description,
       constraints,
       type,
       properties,
       owner,
       measure_id
from attribute
where id = :id;