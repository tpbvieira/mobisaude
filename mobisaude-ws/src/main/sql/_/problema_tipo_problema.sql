delete from tb_problema;
delete from tb_tipo_problema;

Insert into tb_tipo_problema values (1,'A ligação não completa',2,'Após a discagem do número desejado, não ocorre o “tom” de chamada ou há desvio imediato  para a caixa de mensagens.');
Insert into tb_tipo_problema values (2,'Queda da ligação',2,'Durante a chamada ocorre queda, desconectando a ligação.');
Insert into tb_tipo_problema values (3,'Falta de sinal/Sem serviço',2,'Todos os pontos da barra de sinal (ooooo) aparecem vazios, impossibilitando a ligação.');
Insert into tb_tipo_problema values (4,'Ligação ruim',2,'Chamada com ruído, cortando, ou muda.');
Insert into tb_tipo_problema values (5,'Falta de sinal/Sem conexão',1,'Todos os pontos da barra de sinal (ooooo) aparecem vazios, impossibilitando o acesso à internet ou redes sociais.');
Insert into tb_tipo_problema values (6,'Internet lenta',1,'Lentidão ou instabilidade para acessar conteúdos da internet, aplicativos ou redes sociais.');
Insert into tb_tipo_problema values (7,'Queda na conexão',1,'Durante a conexão, ocorre queda e a página da internet, aplicativos e redes sociais param de ser atualizadas, sendo necessário proceder nova conexão.');
