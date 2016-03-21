USE [FOCUS]
GO

CREATE VIEW [dbo].[VW_INTEGRACAO_SIEC]    
AS    
select     
       So.idtSolicitacao,    
       Ar.idtArvore,    
       Se.idtEnderecoInvalido,     
       convert(varchar(30),codSolicitacao)+convert(varchar(30),numAnoSolicitacao) AS [Protocolo Anatel],    
       nomeOrgaoPrest,  
       CodHolding,    
       CASE     
   WHEN nomeOrgaoPrest LIKE 'ASCENTY%' THEN 'ASCENTY'     
   WHEN CodHolding in ('40','40','1267','1442','41','1271','1346','1487','1269','1259','1263','1265','1257','1261','1255','1517','1574','1573','1479','1371') THEN 'CLARO'    
   WHEN CodHolding in ('1204','606','46','1246','1361','1183') THEN 'CTBC'    
   WHEN CodHolding in ('56','1206') THEN 'EBT'    
   WHEN nomeOrgaoPrest LIKE 'ELETRONORTE%' THEN 'ELETRONORTE'    
   WHEN nomeOrgaoPrest LIKE 'ELETROSUL%' THEN 'ELETROSUL'    
   WHEN nomeOrgaoPrest LIKE 'ENGEREDES%' THEN 'ENGEREDES'    
   WHEN nomeOrgaoPrest LIKE 'GEODEX%' THEN 'GEODEX'     
   WHEN CodHolding in ('62','1389') THEN 'GVT'     
   WHEN CodHolding in ('1442') THEN 'HUGHES'     
   WHEN nomeOrgaoPrest LIKE 'INFOVIAS%' THEN 'INFOVIAS'     
   WHEN CodHolding in ('1206') THEN 'LEVEL 3'     
   WHEN CodHolding in ('1529','87','87','1454','87','180','1471','87','1564','1548','1560','1562','1564','1475','1540','87','87','1444','1472','1464','87','87','87','1519','58','1487','1490','1481','2','1450','1352','1492','1446','1354','87','1448','1162','1474','87','1515','1507','1515','1355','1517','87','1544','1513','1359','1361','1363','1365','1158','1519','87','1312','1521','87','1523','87','1494','1521','1295','1367','1534','1369','1450','1496','1371','1497','1525','1373','1375','1527','1529','1485','1531','1379','1454','1501','1503','1456','1161','1499','1479','1505','1383','1385','1536','1556','1533','87','130','613','16','16','1490','613','1492','613','16','16','613','613','16','1494','613','1299','613','16','16','1496','87','87','87','868','1566','868','1513','868','1442','1527','88','1444','156','1336','1476','1338','1525','1340','1478','1485','1483','1499','1342','1478','1479','1501','87','1509','1344','1481','87','1346','1160','1483','1485','1503','83','1517','1505','28','1523','1507','1348','1179','1452','1509','1511') THEN 'NET'     
   WHEN CodHolding in ('89','1350') THEN 'NEXTEL'     
   WHEN nomeOrgaoPrest LIKE 'NQT%' THEN 'NQT'     
   WHEN CodHolding in ('92','1192','1194','1432','1293','17','18','1273','139','1151','1165','888','1160','1208','1162','1527','1164','1154','1161','504','1158','1173','1145','1167','884','1156','19','1275','20','1288','21','1289','1529','22','1278','23','1291','24','1284','25','128126','1277','1574','91','1389','1391','1387','1573','1190','1186','1183','1185','1179','1196','1198','1177') THEN 'OI'     
   WHEN nomeOrgaoPrest LIKE 'PETROBRAS%' THEN 'PETROBRAS'     
   WHEN nomeOrgaoPrest LIKE 'SAMMCCR%' THEN 'SAMMCCR'     
   WHEN CodHolding in ('119','120','1181','1257') THEN 'SERCOMTEL'     
   WHEN CodHolding in ('60','1259') THEN 'SKY'     
   WHEN CodHolding in ('1448','1527') THEN 'TELEBRAS'     
   WHEN CodHolding in ('161','165','1434','1330','1438','164','1226','1212','1214','1218','1216','1222','1220','1224','1230','1252','1228','166','1234','1232','1242','1240','1236','1238','1436','168','1200','1202','1198','1206','1208','1210','1204','169','1250','170','1244','1248','1246') THEN 'TIM'     
   WHEN CodHolding in ('217','1200','1242','1248','1250','1261','1263','1265','1267','1269','1271','1273','1288','1289','1316','1373','1497','1499','1501','1503','1505','1507','1509','1154','214','1385','1378') THEN 'VIVO'    
   ELSE 'Operadora Independente'  END AS  "Reclamado",    
   codUF,    
     CASE     
   WHEN codUF = 'AC' THEN 'Acre'    
   WHEN codUF = 'AL' THEN 'Alagoas'    
   WHEN codUF = 'AM' THEN 'Amazonas'    
   WHEN codUF = 'AP' THEN 'Amapá'    
   WHEN codUF = 'BA' THEN 'Bahia'    
   WHEN codUF = 'CE' THEN 'Ceará'    
   WHEN codUF = 'DF' THEN 'Distrito Federal'    
   WHEN codUF = 'ES' THEN 'Espírito Santo'    
   WHEN codUF = 'GO' THEN 'Goiás'    
   WHEN codUF = 'MA' THEN 'Maranhão'    
   WHEN codUF = 'MG' THEN 'Minas Gerais'    
   WHEN codUF = 'MS' THEN 'Mato Grosso do Sul'    
   WHEN codUF = 'MT' THEN 'Mato Grosso'    
   WHEN codUF = 'PA' THEN 'Pará'    
   WHEN codUF = 'PB' THEN 'Paraíba'    
   WHEN codUF = 'PE' THEN 'Pernambuco'    
   WHEN codUF = 'PI' THEN 'Piauí'    
   WHEN codUF = 'PR' THEN 'Paraná'    
   WHEN codUF = 'RJ' THEN 'Rio de Janeiro'    
   WHEN codUF = 'RN' THEN 'Rio Grande do Norte'    
   WHEN codUF = 'RO' THEN 'Rondônia'    
   WHEN codUF = 'RR' THEN 'Roraima'    
   WHEN codUF = 'RS' THEN 'Rio Grande do Sul'     
   WHEN codUF = 'SC' THEN 'Santa Catarina'     
   WHEN codUF = 'SE' THEN 'Sergipe'     
   WHEN codUF = 'SP' THEN 'São Paulo'     
   WHEN codUF = 'TO' THEN 'Tocantins'     
   ELSE 'NA' END [Localização],     
   TxtNomeLocalidade,    
   CodIBGE AS [Localização - CODIGO IBGE],     
       dataRegistroSolicitacao as [Data do Cadastro],    
       nomeServico,    
       CASE WHEN nomeServico ='Banda Larga Fixa' THEN 'SCM'    
            WHEN nomeServico ='TV por Assinatura' THEN 'STVA'    
            WHEN nomeServico IN('Celular Pós-Pago','Celular Pré-Pago') THEN 'SMP'    
            WHEN nomeServico ='Telefone Fixo' THEN 'STFC'    
            ELSE 'Outros' END [Serviço],              
         nomeTipoAtendimento,    
         nomeModalidadeServico,    
         nomeMotivo AS Motivo    
            
from Solicitacao So    
inner join SolicitacaoEndereco Se on So.idtSolicitacao = Se.idtSolicitacao    
inner join EnderecoInvalido E on Se.idtEnderecoInvalido = E.idtEnderecoInvalido    
inner join FOCUS_CORPORATIVO.dbo.IBGE I ON E.TxtNomeLocalidade = I.NomeMunicipio    
inner join v_Arvore Ar on Ar.idtArvore = So.idtArvore    
inner join ReclamadoResponsavel R on R.idtSolicitacao = So.idtSolicitacao    
inner join v_OrgaoPrest O on O.idtOrgaoPrest = R.idtOrgaoPrest    
inner join Prestadora P on p.IdtPrestadora = O.IdtPrestadora  
/************************************************************/    
/******************Pega a data de hoje***********************/    
/************************************************************/    
where convert(date,SO.dataRegistroSolicitacao) = CONVERT(date,getdate())    
--Excluí dados sujos de municípios    
and codUF not in ('  ','Di','Ri') and TxtNomeLocalidade not in ('')    
and     
/*Filtro dos serviços da solicitação do sistema FOCUS Atual*/    
nomeServico   in ('Banda Larga Fixa','TV por Assinatura','Celular Pós-Pago','Celular Pré-Pago','Telefone Fixo')    
/*Filtro das modalidades de serviços do sistema FOCUS Atual*/    
and nomeModalidadeServico in ('Qualidade e Funcionamento do Serviço ou Equipamento','Mudança de Endereço','Ressarcimento','Longa Distância/Interurbano')