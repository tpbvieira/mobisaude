-- Name: tb_token_sessao; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
DROP TABLE IF EXISTS public.tb_token_sessao;
CREATE TABLE public.tb_token_sessao (
    nu_id_token_sessao integer NOT NULL,
    tx_token character varying,
    dh_validade timestamp without time zone,
	CONSTRAINT pk_tb_token_sessao PRIMARY KEY (nu_id_token_sessao)
);
ALTER TABLE public.tb_token_sessao OWNER TO mobisaude_pg_user;


-- Name: seq_token_sessao; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
CREATE SEQUENCE seq_token_sessao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_token_sessao OWNER TO mobisaude_pg_user;


-- Name: seq_token_sessao; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
SELECT pg_catalog.setval('seq_token_sessao', 635, true);


-- Tipo Sistema Operacional
DROP TABLE IF EXISTS public.tb_tipo_sistema_operacional;
CREATE TABLE public.tb_tipo_sistema_operacional(
   nu_id_tipo_sistema_operacional integer NOT NULL, 
   tx_descricao character varying, 
   CONSTRAINT pk_tipo_sistema_operacional PRIMARY KEY (nu_id_tipo_sistema_operacional)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_sistema_operacional OWNER TO mobisaude_pg_user;


-- Tipo Estabelecimento de Saude
DROP TABLE IF EXISTS public.tb_tipo_estabelecimento_saude;
CREATE TABLE public.tb_tipo_estabelecimento_saude(
   nu_id_tipo_estabelecimento_saude smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_estabelecimento_saude PRIMARY KEY (nu_id_tipo_estabelecimento_saude)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_estabelecimento_saude OWNER TO mobisaude_pg_user;


-- Tipo Gestao
DROP TABLE IF EXISTS public.tb_tipo_gestao;
CREATE TABLE public.tb_tipo_gestao(
   nu_id_tipo_gestao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_gestao PRIMARY KEY (nu_id_tipo_gestao)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_gestao OWNER TO mobisaude_pg_user;


-- Região
DROP TABLE IF EXISTS public.tb_regiao;
CREATE TABLE public.tb_regiao(
   nu_id_regiao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_regiao PRIMARY KEY (nu_id_regiao)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_regiao OWNER TO mobisaude_pg_user;


-- Estabelecimento de Saúde
DROP TABLE IF EXISTS public.tb_estabelecimento_saude;
CREATE TABLE public.tb_estabelecimento_saude(
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
WITH (OIDS = FALSE);
ALTER TABLE public.tb_estabelecimento_saude OWNER TO mobisaude_pg_user;
CREATE INDEX idx_nu_id_municipio ON tb_estabelecimento_saude USING btree (nu_id_municipio);
CREATE INDEX idx_nu_id_tipo_estabelecimento_saude ON tb_estabelecimento_saude USING btree (nu_id_tipo_estabelecimento_saude);


-- user
DROP TABLE IF EXISTS public.tb_user;
CREATE TABLE public.tb_user(
	tx_email character varying NOT NULL,
	tx_password character varying NOT NULL,
	tx_name character varying NOT NULL,
	tx_phone character varying,
	bo_contactable boolean,
	CONSTRAINT pk_user PRIMARY KEY (tx_email)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_user OWNER TO mobisaude_pg_user;
CREATE INDEX idx_tx_email ON tb_user USING btree (tx_email);


-- sugestao
-- Name: tb_sugestao; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
DROP TABLE IF EXISTS public.tb_sugestao;
CREATE TABLE public.tb_sugestao (
    nu_id_sugestao integer NOT NULL,
	nu_id_cnes integer NOT NULL, 
   	tx_email character varying NOT NULL,
    tx_sugestao character varying NOT NULL,
	dh_date timestamp without time zone NOT NULL,
	CONSTRAINT pk_sugestao PRIMARY KEY (nu_id_sugestao),
	CONSTRAINT fk_sugestao_estabelecimento_saude FOREIGN KEY (nu_id_cnes) REFERENCES tb_estabelecimento_saude (nu_id_cnes) ON UPDATE NO ACTION ON DELETE NO ACTION, 
	CONSTRAINT fk_sugestao_user FOREIGN KEY (tx_email) REFERENCES tb_user (tx_email) ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE public.tb_sugestao OWNER TO mobisaude_pg_user;
CREATE INDEX idx_sugestao ON tb_sugestao USING btree (nu_id_cnes,tx_email);


-- Name: seq_sugestao; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
DROP SEQUENCE IF EXISTS seq_sugestao;
CREATE SEQUENCE seq_sugestao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_sugestao OWNER TO mobisaude_pg_user;


-- Name: seq_sugestao; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
SELECT pg_catalog.setval('seq_sugestao', 4635, true);


-- avaliacao
-- Name: tb_avaliacao; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
DROP TABLE IF EXISTS public.tb_avaliacao;
CREATE TABLE public.tb_avaliacao (
    nu_id_avaliacao integer NOT NULL,
	nu_id_cnes integer NOT NULL, 
   	tx_email character varying NOT NULL,
    tx_titulo character varying NOT NULL,
    tx_avaliacao character varying NOT NULL,
	nu_rating float NOT NULL, 
	dh_date timestamp without time zone NOT NULL,
	CONSTRAINT pk_avaliacao PRIMARY KEY (nu_id_avaliacao),
	CONSTRAINT fk_avaliacao_estabelecimento_saude FOREIGN KEY (nu_id_cnes) REFERENCES tb_estabelecimento_saude (nu_id_cnes) ON UPDATE NO ACTION ON DELETE NO ACTION, 
	CONSTRAINT fk_avaliacao_user FOREIGN KEY (tx_email) REFERENCES tb_user (tx_email) ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE public.tb_avaliacao OWNER TO mobisaude_pg_user;
CREATE INDEX idx_avaliacao ON tb_avaliacao USING btree (nu_id_cnes,tx_email);


-- Name: seq_avaliacao; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
DROP SEQUENCE IF EXISTS seq_avaliacao;
CREATE SEQUENCE seq_avaliacao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_avaliacao OWNER TO mobisaude_pg_user;


-- Name: seq_avaliacao; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
SELECT pg_catalog.setval('seq_avaliacao', 9635, true);


-- avaliacao_media
-- Name: tb_avaliacao_media; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
DROP TABLE IF EXISTS public.tb_avaliacao_media;
CREATE TABLE public.tb_avaliacao_media (
	nu_id_cnes integer NOT NULL,
	dh_date timestamp without time zone NOT NULL,
	nu_rating float NOT NULL, 
	
	CONSTRAINT pk_avaliacao_media PRIMARY KEY (nu_id_cnes,dh_date),
	CONSTRAINT fk_avaliacao_estabelecimento_saude FOREIGN KEY (nu_id_cnes) REFERENCES tb_estabelecimento_saude (nu_id_cnes) ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE public.tb_avaliacao_media OWNER TO mobisaude_pg_user;
CREATE INDEX idx_avaliacao_media ON tb_avaliacao_media USING btree (nu_id_cnes,dh_date);



--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.4
-- Dumped by pg_dump version 9.2.4
-- Started on 2014-08-05 16:39:59
SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 192 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--
CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 192
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--
COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 193 (class 3079 OID 119368)
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--
CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 193
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--
COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


SET search_path = public, pg_catalog;

--
-- TOC entry 187 (class 1259 OID 121222)
-- Name: seq_historico; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
CREATE SEQUENCE seq_historico
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_historico OWNER TO mobisaude_pg_user;

--
-- TOC entry 188 (class 1259 OID 121224)
-- Name: seq_log; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
--
CREATE SEQUENCE seq_log
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_log OWNER TO mobisaude_pg_user;

--
-- TOC entry 189 (class 1259 OID 121226)
-- Name: seq_relatorio_erbs; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
--
CREATE SEQUENCE seq_relatorio_erbs
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seq_relatorio_erbs OWNER TO mobisaude_pg_user;


SET default_tablespace = '';
SET default_with_oids = false;


--
-- TOC entry 183 (class 1259 OID 121198)
-- Name: tb_historico; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
DROP TABLE IF EXISTS public.tb_historico;
CREATE TABLE public.tb_historico (
    nu_id integer NOT NULL,
    nu_id_log integer,
    no_prestadora character varying,
    qt_registros integer
);
ALTER TABLE public.tb_historico OWNER TO mobisaude_pg_user;


--
-- TOC entry 184 (class 1259 OID 121204)
-- Name: tb_log; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
DROP TABLE IF EXISTS public.tb_log;
CREATE TABLE public.tb_log (
    nu_id_log integer NOT NULL,
    dh_data_hora_inicio timestamp without time zone,
    dh_data_hora_fim timestamp without time zone,
    tx_evento character varying(20),
    tx_observacao character varying
);
ALTER TABLE public.tb_log OWNER TO mobisaude_pg_user;


--
-- TOC entry 185 (class 1259 OID 121210)
-- Name: tb_relatorio_erbs; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
DROP TABLE IF EXISTS public.tb_relatorio_erbs;
CREATE TABLE public.tb_relatorio_erbs (
    sg_uf character varying(2),
    no_cod_municipio_ibge character varying NOT NULL,
    no_municipio character varying,
    no_prestadora character varying,
    tx_nome_fantasia character varying,
    in_tecnologia_2g boolean,
    in_tecnologia_3g boolean,
    in_tecnologia_4g boolean,
    nu_latitude_stel double precision,
    nu_longitude_stel double precision,
    nu_id_relatorio_erbs integer NOT NULL
);
ALTER TABLE public.tb_relatorio_erbs OWNER TO mobisaude_pg_user;


--
-- TOC entry 186 (class 1259 OID 121216)
-- Name: tb_relatorio_ranking; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
DROP TABLE IF EXISTS public.tb_relatorio_ranking;
CREATE TABLE public.tb_relatorio_ranking (
    sg_uf character varying(2),
    no_cod_municipio_ibge character varying NOT NULL,
    no_municipio character varying,
    no_prestadora character varying NOT NULL,
    tx_nome_fantasia character varying,
    qt_tecnologia_2g integer,
    qt_tecnologia_3g integer,
    qt_tecnologia_4g integer,
    nu_conexao_voz real,
    nu_desconexao_voz real,
    nu_conexao_dados real,
    nu_desconexao_dados real,
    nu_ranking_voz integer,
    nu_ranking_dados integer
);
ALTER TABLE public.tb_relatorio_ranking OWNER TO mobisaude_pg_user;


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_historico; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
--
SELECT pg_catalog.setval('seq_historico', 59, true);


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 188
-- Name: seq_log; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
--
SELECT pg_catalog.setval('seq_log', 10, true);


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 189
-- Name: seq_relatorio_erbs; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
--
SELECT pg_catalog.setval('seq_relatorio_erbs', 242207, true);


--
-- TOC entry 3195 (class 2606 OID 134273)
-- Name: pk_tb_historico; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
ALTER TABLE ONLY tb_historico ADD CONSTRAINT pk_tb_historico PRIMARY KEY (nu_id);


--
-- TOC entry 3197 (class 2606 OID 134275)
-- Name: pk_tb_log; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
ALTER TABLE ONLY tb_log ADD CONSTRAINT pk_tb_log PRIMARY KEY (nu_id_log);


--
-- TOC entry 3200 (class 2606 OID 134277)
-- Name: pk_tb_relatorio_erbs; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
ALTER TABLE ONLY tb_relatorio_erbs ADD CONSTRAINT pk_tb_relatorio_erbs PRIMARY KEY (nu_id_relatorio_erbs);


--
-- TOC entry 3203 (class 2606 OID 134279)
-- Name: pk_tb_relatorio_ranking; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
ALTER TABLE ONLY tb_relatorio_ranking ADD CONSTRAINT pk_tb_relatorio_ranking PRIMARY KEY (no_cod_municipio_ibge, no_prestadora);


--
-- TOC entry 3198 (class 1259 OID 121246)
-- Name: idx_relatorio_erbs_cod_mun; Type: INDEX; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
CREATE INDEX idx_relatorio_erbs_cod_mun ON tb_relatorio_erbs USING btree (no_cod_municipio_ibge);


--
-- TOC entry 3201 (class 1259 OID 121247)
-- Name: idx_relatorio_ranking_cod_mun; Type: INDEX; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--
CREATE INDEX idx_relatorio_ranking_cod_mun ON tb_relatorio_ranking USING btree (no_cod_municipio_ibge);


--
-- TOC entry 3189 (class 2618 OID 120067)
-- Name: geometry_columns_delete; Type: RULE; Schema: public; Owner: mobisaude_pg_user
--
CREATE OR REPLACE RULE geometry_columns_delete AS ON DELETE TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3187 (class 2618 OID 120065)
-- Name: geometry_columns_insert; Type: RULE; Schema: public; Owner: mobisaude_pg_user
--
CREATE OR REPLACE RULE geometry_columns_insert AS ON INSERT TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3188 (class 2618 OID 120066)
-- Name: geometry_columns_update; Type: RULE; Schema: public; Owner: mobisaude_pg_user
--
CREATE OR REPLACE RULE geometry_columns_update AS ON UPDATE TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: mobisaude_pg_user
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
