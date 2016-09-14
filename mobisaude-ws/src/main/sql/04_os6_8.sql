CREATE TABLE public.tb_historico_dados
(
   nu_id_historico_dados integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_cod_municipio_ibge character varying, 
   no_municipio character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_conexao double precision, 
   nu_desconexao double precision, 
   CONSTRAINT pk_tb_historico_dados PRIMARY KEY (nu_id_historico_dados)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_dados
  OWNER TO mobisaude_pg_user;

CREATE TABLE public.tb_historico_voz
(
   nu_id_historico_voz integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_cod_municipio_ibge character varying, 
   no_municipio character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_conexao double precision, 
   nu_desconexao double precision, 
   CONSTRAINT pk_tb_historico_voz PRIMARY KEY (nu_id_historico_voz)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_voz
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_dados
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_dados
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_voz
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_voz
  OWNER TO mobisaude_pg_user;

CREATE INDEX idx_hist_voz_cod_mun
  ON tb_historico_voz
  USING btree
  (no_cod_municipio_ibge COLLATE pg_catalog."default");

CREATE INDEX idx_hist_dados_cod_mun
  ON tb_historico_dados
  USING btree
  (no_cod_municipio_ibge COLLATE pg_catalog."default");

  CREATE TABLE public.tb_historico_dados_uf
(
   nu_id_historico_dados_uf integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_estado character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_conexao double precision, 
   nu_desconexao double precision, 
   nu_conexao_2g double precision, 
   nu_desconexao_2g double precision, 
   nu_conexao_3g double precision, 
   nu_desconexao_3g double precision, 
   nu_conexao_4g double precision, 
   nu_desconexao_4g double precision, 
   CONSTRAINT pk_tb_historico_dados_uf PRIMARY KEY (nu_id_historico_dados_uf)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_dados_uf
  OWNER TO mobisaude_pg_user;

CREATE TABLE public.tb_historico_voz_uf
(
   nu_id_historico_voz_uf integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_estado character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_conexao double precision, 
   nu_desconexao double precision, 
   CONSTRAINT pk_tb_historico_voz_uf PRIMARY KEY (nu_id_historico_voz_uf)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_voz_uf
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_dados_uf
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_dados_uf
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_voz_uf
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_voz_uf
  OWNER TO mobisaude_pg_user;

CREATE INDEX idx_hist_voz_uf
  ON tb_historico_voz_uf
  USING btree
  (sg_uf COLLATE pg_catalog."default");

CREATE INDEX idx_hist_dados_uf
  ON tb_historico_dados_uf
  USING btree
  (sg_uf COLLATE pg_catalog."default");

ALTER TABLE tb_historico_dados
  ADD COLUMN nu_conexao_2g double precision;
ALTER TABLE tb_historico_dados
  ADD COLUMN nu_desconexao_2g double precision;
ALTER TABLE tb_historico_dados
  ADD COLUMN nu_conexao_3g double precision;
ALTER TABLE tb_historico_dados
  ADD COLUMN nu_desconexao_3g double precision;
ALTER TABLE tb_historico_dados
  ADD COLUMN nu_conexao_4g double precision;
ALTER TABLE tb_historico_dados
  ADD COLUMN nu_desconexao_4g double precision;

ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_conexao_dados_2g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_desconexao_dados_2g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_conexao_dados_3g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_desconexao_dados_3g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_conexao_dados_4g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_desconexao_dados_4g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_ranking_dados_2g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_ranking_dados_3g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_ranking_dados_4g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_indice_voz double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_indice_dados double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_indice_dados_2g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_indice_dados_3g double precision;
ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_indice_dados_4g double precision;

CREATE TABLE public.tb_espelhamento_municipio
(
   "nu_codigo_origem" numeric(10,0), 
   "nu_codigo_destino" numeric(10,0), 
   nu_id_espelhamento_municipio serial, 
   CONSTRAINT pk_espelhamento_municipio PRIMARY KEY (nu_id_espelhamento_municipio)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_espelhamento_municipio
  OWNER TO mobisaude_pg_user;

insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (3126901, 3171501);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (3164902, 3152600);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (3166402, 3167004);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (3204252, 3203601);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (2920205, 2907103);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (2800704, 2706802);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (2807303, 2805703);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (2401859, 2411601);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (1503002, 1303007);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4215059, 4218954);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (5203962, 5200803);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (5220686, 5200803);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (1703305, 1716505);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (1720002, 1714302);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (1721257, 1716505);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4309258, 4318440);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4312609, 4315800);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4313441, 4314456);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4314035, 4304689);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4319356, 4316501);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4322525, 4314076);
insert into tb_espelhamento_municipio (nu_codigo_origem, nu_codigo_destino) values (4323507, 4321329);

CREATE TABLE public.tb_configuracao
(
   nu_id_configuracao integer NOT NULL, 
   no_chave character varying(50), 
   tx_valor character varying, 
   CONSTRAINT pk_configuracao PRIMARY KEY (nu_id_configuracao)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_configuracao
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_configuracao
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_configuracao
  OWNER TO mobisaude_pg_user;

INSERT INTO public.tb_configuracao (nu_id_configuracao, no_chave, tx_valor) values (nextval('seq_configuracao'), 'TEXTO_AJUDA_MAPA_PUBLICO', '');

GRANT ALL ON ALL TABLES IN SCHEMA public TO mobisaude_pg_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO mobisaude_pg_user;
