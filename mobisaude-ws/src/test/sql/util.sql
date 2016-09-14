select * from tb_user;

select * from tb_sugestao;

select * from tb_avaliacao;

select avg(nu_rating) from tb_avaliacao;

select * from tb_avaliacao_media;

select * from tb_tipo_estabelecimento_saude;

select * from tb_estabelecimento_saude
where nu_id_municipio = 530010 
	and nu_id_cnes = 3470598
	and tx_nome_fantasia like '%CENTRO DE ODONTOLOGIA%'
	and nu_id_tipo_estabelecimento_saude = 9
	and tx_nome_fantasia like '%CENTRO DE ODONTOLOGIA%'
order by tx_nome_fantasia;

/* ======================================================== */

delete from tb_token_sessao where 1 = 1;
delete from tb_sugestao where 1 = 1;
delete from tb_avaliacao where 1 = 1;
delete from tb_avaliacao_media where 1 = 1;
delete from tb_user where 1 = 1;
