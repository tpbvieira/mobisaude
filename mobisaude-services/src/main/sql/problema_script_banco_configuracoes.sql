ALTER TABLE tb_problema ADD COLUMN dh_data_hora timestamp without time zone;
ALTER TABLE tb_problema ADD COLUMN no_cod_municipio_ibge character varying;
ALTER TABLE tb_problema ADD COLUMN sg_uf character varying(2);

delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DISPONIBILIDADE';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_VOZ';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DADOS';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DADOS_2G';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DADOS_3G';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DADOS_4G';
delete from tb_configuracao where no_chave = 'EXIBIR_RANKING_DADOS_GLOBAL';

insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DISPONIBILIDADE', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_VOZ', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DADOS', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DADOS_2G', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DADOS_3G', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DADOS_4G', 'S');
insert into tb_configuracao values (nextval('seq_configuracao'), 'EXIBIR_RANKING_DADOS_GLOBAL', 'S');