-- Tipo Sistema Operacional
CREATE TABLE public.tb_tipo_sistema_operacional
(
   nu_id_tipo_sistema_operacional integer NOT NULL, 
   tx_descricao character varying, 
   CONSTRAINT pk_tipo_sistema_operacional PRIMARY KEY (nu_id_tipo_sistema_operacional)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_sistema_operacional OWNER TO mobisaude_pg_user;


-- Tipo Estabelecimento de Saude
DROP TABLE public.tb_tipo_estabelecimento_saude;
CREATE TABLE public.tb_tipo_estabelecimento_saude
(
   nu_id_tipo_estabelecimento_saude smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_estabelecimento_saude PRIMARY KEY (nu_id_tipo_estabelecimento_saude)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_estabelecimento_saude OWNER TO mobisaude_pg_user;


-- Tipo Gestao
DROP TABLE public.tb_tipo_gestao;
CREATE TABLE public.tb_tipo_gestao
(
   nu_id_tipo_gestao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_tipo_gestao PRIMARY KEY (nu_id_tipo_gestao)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_tipo_gestao OWNER TO mobisaude_pg_user;


-- Região
CREATE TABLE public.tb_regiao
(
   nu_id_regiao smallint NOT NULL, 
   tx_nome character varying, 
   CONSTRAINT pk_regiao PRIMARY KEY (nu_id_regiao)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_regiao OWNER TO mobisaude_pg_user;


-- Estabelecimento de Saúde
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
WITH (OIDS = FALSE);
ALTER TABLE public.tb_estabelecimento_saude OWNER TO mobisaude_pg_user;
CREATE INDEX idx_nu_id_municipio ON tb_estabelecimento_saude USING btree (nu_id_municipio);
CREATE INDEX idx_nu_id_tipo_estabelecimento_saude ON tb_estabelecimento_saude USING btree (nu_id_tipo_estabelecimento_saude);


-- user
DROP TABLE public.tb_user;
CREATE TABLE public.tb_user
(
	tx_email character varying NOT NULL,
	tx_password character varying,
	tx_name character varying,
	tx_telefone character varying, 
	CONSTRAINT pk_user PRIMARY KEY (tx_email)
) 
WITH (OIDS = FALSE);
ALTER TABLE public.tb_user OWNER TO mobisaude_pg_user;
CREATE INDEX idx_tx_email ON tb_user USING btree (tx_email);


















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
--

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

--
-- TOC entry 190 (class 1259 OID 121228)
-- Name: seq_token_sessao; Type: SEQUENCE; Schema: public; Owner: mobisaude_pg_user
--

CREATE SEQUENCE seq_token_sessao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_token_sessao OWNER TO mobisaude_pg_user;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 121198)
-- Name: tb_historico; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

CREATE TABLE tb_historico (
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

CREATE TABLE tb_log (
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

CREATE TABLE tb_relatorio_erbs (
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

CREATE TABLE tb_relatorio_ranking (
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
-- TOC entry 191 (class 1259 OID 121230)
-- Name: tb_token_sessao; Type: TABLE; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

CREATE TABLE tb_token_sessao (
    nu_id_token_sessao integer NOT NULL,
    tx_token character varying,
    dh_validade timestamp without time zone
);


ALTER TABLE public.tb_token_sessao OWNER TO mobisaude_pg_user;

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
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 190
-- Name: seq_token_sessao; Type: SEQUENCE SET; Schema: public; Owner: mobisaude_pg_user
--

SELECT pg_catalog.setval('seq_token_sessao', 635, true);


--
-- TOC entry 3192 (class 0 OID 119636)
-- Dependencies: 171
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3206 (class 0 OID 121198)
-- Dependencies: 183
-- Data for Name: tb_historico; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3207 (class 0 OID 121204)
-- Dependencies: 184
-- Data for Name: tb_log; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3208 (class 0 OID 121210)
-- Dependencies: 185
-- Data for Name: tb_relatorio_erbs; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3209 (class 0 OID 121216)
-- Dependencies: 186
-- Data for Name: tb_relatorio_ranking; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3214 (class 0 OID 121230)
-- Dependencies: 191
-- Data for Name: tb_token_sessao; Type: TABLE DATA; Schema: public; Owner: mobisaude_pg_user
--



--
-- TOC entry 3195 (class 2606 OID 134273)
-- Name: pk_tb_historico; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

ALTER TABLE ONLY tb_historico
    ADD CONSTRAINT pk_tb_historico PRIMARY KEY (nu_id);


--
-- TOC entry 3197 (class 2606 OID 134275)
-- Name: pk_tb_log; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

ALTER TABLE ONLY tb_log
    ADD CONSTRAINT pk_tb_log PRIMARY KEY (nu_id_log);


--
-- TOC entry 3200 (class 2606 OID 134277)
-- Name: pk_tb_relatorio_erbs; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

ALTER TABLE ONLY tb_relatorio_erbs
    ADD CONSTRAINT pk_tb_relatorio_erbs PRIMARY KEY (nu_id_relatorio_erbs);


--
-- TOC entry 3203 (class 2606 OID 134279)
-- Name: pk_tb_relatorio_ranking; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

ALTER TABLE ONLY tb_relatorio_ranking
    ADD CONSTRAINT pk_tb_relatorio_ranking PRIMARY KEY (no_cod_municipio_ibge, no_prestadora);


--
-- TOC entry 3205 (class 2606 OID 134281)
-- Name: pk_tb_token_sessao; Type: CONSTRAINT; Schema: public; Owner: mobisaude_pg_user; Tablespace: 
--

ALTER TABLE ONLY tb_token_sessao
    ADD CONSTRAINT pk_tb_token_sessao PRIMARY KEY (nu_id_token_sessao);


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

CREATE RULE geometry_columns_delete AS ON DELETE TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3187 (class 2618 OID 120065)
-- Name: geometry_columns_insert; Type: RULE; Schema: public; Owner: mobisaude_pg_user
--

CREATE RULE geometry_columns_insert AS ON INSERT TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3188 (class 2618 OID 120066)
-- Name: geometry_columns_update; Type: RULE; Schema: public; Owner: mobisaude_pg_user
--

CREATE RULE geometry_columns_update AS ON UPDATE TO geometry_columns DO INSTEAD NOTHING;


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: mobisaude_pg_user
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-08-05 16:40:01

--
-- PostgreSQL database dump complete
--

