-- Insert into tipo sistema operacional
delete from tb_tipo_sistema_operacional where 1 = 1;
insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (1, 'iOS');
insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (2, 'Android');
insert into tb_tipo_sistema_operacional (nu_id_tipo_sistema_operacional, tx_descricao) values (3, 'Windows Phone');

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
delete from tb_tipo_gestao where 1 = 1;
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (1, 'Estadual');
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (2, 'Municipal');
insert into tb_tipo_gestao (nu_id_tipo_gestao, tx_nome) values (3, 'Dupla');

-- Insert into regiao
delete from tb_regiao where 1 = 1;
insert into tb_regiao (nu_id_regiao, tx_nome) values (1, 'Norte');
insert into tb_regiao (nu_id_regiao, tx_nome) values (2, 'Centro-Oeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (3, 'Nordeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (4, 'Sudeste');
insert into tb_regiao (nu_id_regiao, tx_nome) values (5, 'Sul');
