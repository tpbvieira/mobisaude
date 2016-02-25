CREATE TABLE public.tb_operadora
(
   nu_id_operadora integer NOT NULL, 
   no_codigo character varying(40), 
   tx_nome character varying, 
   CONSTRAINT pk_operadora PRIMARY KEY (nu_id_operadora)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_operadora
  OWNER TO mobisaude_pg_user;

CREATE TABLE public.tb_tipo_problema
(
   nu_id_tipo_problema integer NOT NULL, 
   tx_descricao character varying, 
   CONSTRAINT pk_tipo_problema PRIMARY KEY (nu_id_tipo_problema)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_tipo_problema
  OWNER TO mobisaude_pg_user;

-- Tipo Sistema Operacional
CREATE TABLE public.tb_tipo_sistema_operacional
(
   nu_id_tipo_sistema_operacional integer NOT NULL, 
   tx_descricao character varying, 
   CONSTRAINT pk_tipo_sistema_operacional PRIMARY KEY (nu_id_tipo_sistema_operacional)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_tipo_sistema_operacional
  OWNER TO mobisaude_pg_user;




-- Tipo Estabelecimento de Saude
DROP TABLE public.tb_tipo_estabelecimento_saude;
CREATE TABLE public.tb_tipo_estabelecimento_saude
(
   nu_id_tipo_estabelecimento_saude smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_estabelecimento_saude PRIMARY KEY (nu_id_tipo_estabelecimento_saude)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_tipo_estabelecimento_saude
  OWNER TO mobisaude_pg_user;

-- Tipo Gestao
DROP TABLE public.tb_tipo_gestao;
CREATE TABLE public.tb_tipo_gestao
(
   nu_id_tipo_gestao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_gestao PRIMARY KEY (nu_id_tipo_gestao)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_tipo_gestao
  OWNER TO mobisaude_pg_user;

-- Região
CREATE TABLE public.tb_regiao
(
   nu_id_regiao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_regiao PRIMARY KEY (nu_id_regiao)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_regiao
  OWNER TO mobisaude_pg_user;

CREATE TABLE public.tb_estabelecimento_saude
(
	nu_id_cnes integer NOT NULL, 
	nu_id_municipio numeric(10,0),
	tx_id_cnpj_mantenedora varchar(14), 
	tx_razao_social_mantenedora character varying, 
	tx_razao_social character varying, 
	tx_nome_fantasia character varying, 
	nu_id_tipo_estabelecimento_saude smallint,
	tx_natureza_organizacao character varying, 
	tx_esfera_administrativa character varying, 
	nu_id_tipo_gestao smallint,
	tx_logradouro character varying, 
	tx_endereco character varying, 
	tx_bairro character varying, 
	tx_id_cep varchar(8),
	nu_id_regiao smallint,
	tx_uf varchar(2),
	tx_municipio character varying, 
	nu_latitude double precision, 
	nu_longitude double precision, 
	tx_origem_coordenada character varying, 
	CONSTRAINT pk_estabelecimento_saude PRIMARY KEY (nu_id_cnes), 
	CONSTRAINT fk_estabelecimento_tipo_estabelecimento FOREIGN KEY (nu_id_tipo_estabelecimento_saude) REFERENCES tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude) ON UPDATE NO ACTION ON DELETE NO ACTION, 
	CONSTRAINT fk_estabelecimento_tipo_gestao FOREIGN KEY (nu_id_tipo_gestao) REFERENCES tb_tipo_gestao (nu_id_tipo_gestao) ON UPDATE NO ACTION ON DELETE NO ACTION, 
	CONSTRAINT fk_estabelecimento_regiao FOREIGN KEY (nu_id_regiao) REFERENCES tb_regiao (nu_id_regiao) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_estabelecimento_saude
  OWNER TO mobisaude_pg_user;







-- Problema
CREATE TABLE public.tb_problema
(
   nu_id_problema integer NOT NULL, 
   nu_id_operadora integer, 
   nu_id_operadora_dispositivo integer, 
   nu_id_tipo_problema integer, 
   nu_id_tipo_sistema_operacional integer, 
   no_versao_app character varying, 
   nu_latitude double precision, 
   nu_longitude double precision, 
   nu_nivel_sinal double precision, 
   tx_symcard character varying, 
   CONSTRAINT pk_problema PRIMARY KEY (nu_id_problema), 
   CONSTRAINT fk_problema_operadora FOREIGN KEY (nu_id_operadora) REFERENCES tb_operadora (nu_id_operadora) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT fk_problema_operadora_dispositivo FOREIGN KEY (nu_id_operadora_dispositivo) REFERENCES tb_operadora (nu_id_operadora) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT fk_problema_tipo_problema FOREIGN KEY (nu_id_tipo_problema) REFERENCES tb_tipo_problema (nu_id_tipo_problema) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT fk_problema_tipo_so FOREIGN KEY (nu_id_tipo_sistema_operacional) REFERENCES tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.tb_problema
  OWNER TO mobisaude_pg_user;

CREATE SEQUENCE public.seq_problema
   INCREMENT 1
   START 1;
ALTER SEQUENCE public.seq_problema
  OWNER TO mobisaude_pg_user;

insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (1, 'CLARO', 'CLARO');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (2, 'CTBC', 'CTBC');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (3, 'NEXTEL', 'NEXTEL');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (4, 'OI', 'OI');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (5, 'SERCOMTEL', 'SERCOMTEL');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (6, 'TIM', 'TIM');
insert into tb_operadora (nu_id_operadora, no_codigo, tx_nome) values (7, 'VIVO', 'VIVO');

insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (1, 'Voz/A ligação (chamada) não completa');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (2, 'Voz/Queda da ligação (chamada)');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (3, 'Voz/Falta de sinal/Sem serviço');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (4, 'Voz/Ligação ruim (mudo, ruído ou cortando)');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (5, 'Dados/Falta de sinal/Sem conexão');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (6, 'Dados/Internet (conexão) lenta');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (7, 'Dados/Queda na conexão');
insert into tb_tipo_problema (nu_id_tipo_problema, tx_descricao) values (8, 'Dados/Internet (conexão) instável/ruim');

insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (1, 'iOS');
insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (2, 'Android');
insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (3, 'Windows Phone');

INSERT INTO public.tb_configuracao (nu_id_configuracao, no_chave, tx_valor) values (nextval('seq_configuracao'), 'TEXTO_AJUDA_SERVICO_MOVEL', '');

GRANT ALL ON ALL TABLES IN SCHEMA public TO mobisaude_pg_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO springbatch_user;

﻿ALTER TABLE tb_tipo_problema ADD COLUMN ind_excluido character(1) DEFAULT 'N';

-- Insert into Tipo Estabelecimento de Saude
delete from tb_tipo_estabelecimento_saude where 1 = 1;
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (1, 'CENTRAL DE NOTIFICACAO,CAPTACAO E DISTRIB DE ORGAOS ESTADUAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (2, 'CENTRAL DE REGULACAO DE SERVICOS DE SAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (3, 'CENTRAL DE REGULACAO DO ACESSO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (4, 'CENTRAL DE REGULACAO MEDICA DAS URGENCIAS');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (5, 'CENTRO DE APOIO A SAUDE DA FAMILIA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (6, 'CENTRO DE ATENCAO HEMOTERAPIA E OU HEMATOLOGICA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (7, 'CENTRO DE ATENCAO PSICOSSOCIAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (8, 'CENTRO DE PARTO NORMAL - ISOLADO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (9, 'CENTRO DE SAUDE/UNIDADE BASICA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (10, 'CLINICA/CENTRO DE ESPECIALIDADE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (11, 'CONSULTORIO ISOLADO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (12, 'COOPERATIVA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (13, 'FARMACIA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (14, 'HOSPITAL/DIA - ISOLADO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (15, 'HOSPITAL ESPECIALIZADO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (16, 'HOSPITAL GERAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (17, 'LABORATORIO CENTRAL DE SAUDE PUBLICA LACEN');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (18, 'LABORATORIO DE SAUDE PUBLICA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (19, 'OFICINA ORTOPEDICA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (20, 'POLICLINICA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (21, 'POLO ACADEMIA DA SAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (22, 'POSTO DE SAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (23, 'PRONTO ATENDIMENTO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (24, 'PRONTO SOCORRO ESPECIALIZADO');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (25, 'PRONTO SOCORRO GERAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (26, 'SECRETARIA DE SAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (27, 'SERVICO DE ATENCAO DOMICILIAR ISOLADO(HOME CARE)');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (28, 'TELESSAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (29, 'UNIDADE DE APOIO DIAGNOSE E TERAPIA (SADT ISOLADO)');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (30, 'UNIDADE DE ATENCAO A SAUDE INDIGENA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (31, 'UNIDADE DE ATENCAO EM REGIME RESIDENCIAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (32, 'UNIDADE DE VIGILANCIA EM SAUDE');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (33, 'UNIDADE MISTA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (34, 'UNIDADE MOVEL DE NIVEL PRE-HOSPITALAR NA AREA DE URGENCIA');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (35, 'UNIDADE MOVEL FLUVIAL');
insert into tb_tipo_estabelecimento_saude (nu_id_tipo_estabelecimento_saude, tx_nome) values (36, 'UNIDADE MOVEL TERRESTRE');

-- Insert into Tipo Gestão
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (1, 'Estadual');
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (2, 'Municipal');
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (3, 'Dupla');

-- Insert into regiao
insert into tb_regiao (nu_id_regiao, tx_nome) values (1, 'Norte');
insert into tb_regiao (nu_id_regiao, tx_nome) values (2, 'Centro-Oeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (3, 'Nordeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (4, 'Sudeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (5, 'Sul');
