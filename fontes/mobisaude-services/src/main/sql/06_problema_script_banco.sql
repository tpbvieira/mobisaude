ALTER TABLE tb_relatorio_ranking
  ADD COLUMN nu_disponibilidade double precision;

CREATE TABLE public.tb_historico_disponibilidade
(
   nu_id_historico_disponibilidade integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_cod_municipio_ibge character varying, 
   no_municipio character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_disponibilidade double precision, 
   CONSTRAINT pk_tb_historico_disponibilidade PRIMARY KEY (nu_id_historico_disponibilidade)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_disponibilidade
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_disponibilidade
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_disponibilidade
  OWNER TO mobisaude_pg_user;

CREATE TABLE public.tb_historico_disponibilidade_uf
(
   nu_id_historico_disponibilidade_uf integer NOT NULL, 
   nu_id_rastreamento integer, 
   sg_uf character varying(2), 
   no_estado character varying, 
   no_prestadora character varying, 
   in_mes_ano character varying(7), 
   nu_disponibilidade double precision, 
   CONSTRAINT pk_tb_historico_disponibilidade_uf PRIMARY KEY (nu_id_historico_disponibilidade_uf)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_historico_disponibilidade_uf
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_historico_disponibilidade_uf
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_historico_disponibilidade_uf
  OWNER TO mobisaude_pg_user;

insert into tb_configuracao (nu_id_configuracao, no_chave, tx_valor) values (nextval('seq_configuracao'), 'TEXTO_AVISO_RELATAR_PROBLEMAS', '');

ALTER TABLE tb_tipo_ambiente
  ADD COLUMN tx_detalhes character varying;

update tb_tipo_ambiente set tx_detalhes = 'A observação foi constatada dentro de casa ou de um estabelecimento.' where tx_descricao = 'Interno';

update tb_tipo_ambiente set tx_detalhes = 'A observação foi constatada em local a céu aberto.' where tx_descricao = 'Externo';

insert into tb_tipo_ambiente (nu_id_tipo_ambiente, tx_descricao, tx_detalhes) values (3, 'Em movimento', 'A observação foi constatada ao ser percorrido um trajeto (por exemplo, caminhada, carro, metrô).');

ALTER TABLE tb_problema
  ADD COLUMN tx_descricao character varying;

ELETE NO ACTION;

ALTER TABLE tb_tipo_problema
  ADD COLUMN tx_detalhes character varying;

DELETE FROM tb_problema;

DELETE FROM tb_tipo_problema;

INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values (1,'A ligação (chamada) não completa',2,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (2,'Queda da ligação (chamada)',2,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (3,'Falta de sinal/Sem serviço',2,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (4,'Ligação ruim (mudo, ruído ou cortando)',2,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (5,'Falta de sinal/Sem conexão',1,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (6,'Internet (conexão) lenta',1,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (7,'Queda na conexão',1,null);
INSERT INTO tb_tipo_problema (nu_id_tipo_problema, tx_descricao, nu_id_tipo_servico, tx_detalhes) values  (8,'Internet (conexão) instável/ruim',1,null);
