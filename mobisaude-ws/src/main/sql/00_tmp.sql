select av.nu_rating, count(av.nu_rating) 
from tb_avaliacao av 
	join tb_estabelecimento_saude es on av.nu_id_cnes = es.nu_id_cnes	
where es.tx_UF = 'DF'
group by av.nu_rating;


select av.nu_rating, count(av.nu_rating) 
from tb_avaliacao av 
	join tb_estabelecimento_saude es on av.nu_id_cnes = es.nu_id_cnes	
where es.nu_id_municipio = 530010
group by av.nu_rating;


select av.nu_rating, count(av.nu_rating) 
from tb_avaliacao av 
	join tb_estabelecimento_saude es on av.nu_id_cnes = es.nu_id_cnes
	join tb_tipo_estabelecimento_saude te on es.nu_id_tipo_estabelecimento_saude = te.nu_id_tipo_estabelecimento_saude
where te.nu_id_tipo_estabelecimento_saude = 9
group by av.nu_rating;
