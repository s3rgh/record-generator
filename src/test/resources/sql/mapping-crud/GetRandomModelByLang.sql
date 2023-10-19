select id
from model
where (name -> :lang) IS NOT NULL
order by random()
LIMIT 1;
