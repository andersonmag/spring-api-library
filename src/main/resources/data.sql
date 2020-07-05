INSERT INTO CATEGORIA(ID, NOME, LINK) VALUES(1, 'Holística Saúde, Boa Forma e Dieta', 'holistica-saude-boa-forma-e-dieta'),
                                      (2, 'Ficção científica','ficcao-cientifica'),
                                      (3, 'Computação, Informática e Mídias Digitais','computacao-informatica-e-midias-digitais');

INSERT INTO USUARIO(ID, NOME, EMAIL, SENHA, STATUS) VALUES(1, 'Anderson', 'adn@gmail.com', '123', 1);

INSERT INTO PEDIDO(ID, CODIGO, DATA, VALOR_TOTAL, USUARIO_ID) VALUES(1, 192892, '2020-06-02T15:31:35.650', '19.90', 1);

INSERT INTO Livro(ID, TITULO, AUTOR , EDITORA, PRECO, PRECO_ANTERIOR, DESCRICAO, IDIOMA, IMAGEMURL, LINK, DATA_PUBLICACAO, CATEGORIA_ID, DATA_CRIACAO) 
VALUES(1, 'Você É A Sua Cura', 'Deepak Chopra', 'Editora Alaúde, Rudolph E. Tanzi', '19.90', null, 'Uma visão holística da medicina, de postura preventiva,
colocando o leitor como protagonista da própria saúde; - Testes que fazem refletir sobre o estado de saúde que você vivencia agora; 
- Um plano de ação semanal para repensar sua postura em relação a vários aspectos da vida que podem impactar diretamente na sua saúde.
O livro visa fazer com que o leitor encare a sua saúde de um jeito completamente novo e revolucionário.
O livro tem o objetivo de conclamar o leitor a se situar no presente, prestando atenção aos sinais que seu corpo está emitindo,
para poder realizar pequenas transformações que podem ter muito impacto no jeito como sua saúde se desenvolve.
Expandindo o conceito de imunidade, ganhamos novas formas de cuidar de nós mesmos.', 'Português',
'https://images-americanas.b2w.io/produtos/01/00/img2/33866/4/33866479_1GG.jpg','voce-e-a-sua-cura' , '01/04/2018', 1, '2020-06-02T15:31:35.650'),

(2, 'Consciência Quântica: Uma nova visão sobre o amor, a morte, e o sentido da vida', 'AMIT GOSWAMI' , 'Goya', '25.0', '29.99' , 
'Desde a antiguidade, temas como Deus, a morte e o sentido da vida são contemplados por diversas religiões e, mais recentemente,
enquadrados pelo materialismo científico. No entanto, as duas visões de mundo parecem incapazes de dar um sentido satisfatório aos 
fenômenos da nossa existência. Enquanto uma submete o mundo material a regras transmitidas por líderes religiosos que falam em nome
de Deus, a outra suprime a espiritualidade e descarta tudo que não pode ser explicado pela lógica newtoniana. Nesse contexto, 
surge uma nova e integradora visão de nossa existência e da nossa origem com base nos princípios da física quântica, apresentada por 
Amit Goswami nesta obra. Não somos apenas espírito, tampouco puramente matéria: somos todos consciência quântica, um conceito divisor
de águas para a compreensão de nossa existência. A partir de temas como zen, sentimentos, pensamentos e intuição, sonhos, karma, morte
e reencarnação, Goswami nos mostra, com dados científicos, que a nossa existência transcende a experiência física e, portanto, deve ser
nutrida em todas as suas dimensões. Na visão de mundo quântica, a transformação não se dá em busca da iluminação, mas do empoderamento
individual e coletivo.', 'Português', 'https://images-americanas.b2w.io/produtos/imagens/134355229/134355237_1GG.jpg', 
'consciencia-quantica-uma-nova-visao-sobre-o-amor-a-morte-e-o-sentido-da-vida', '16/11/2018', 1, '2020-06-02T15:31:35.650'),
    
(3, 'Depois da Terra: A Fera Perfeita', 'Suma', 'Peter David, Robert Greenberger, Michael Jan Friedman', '24.99', null, 'A Suma de Letras publica, até o lançamento do longa, uma série de seis e-books
- Histórias de fantasmas -, que contextualiza o prólogo da história, além de dois livros impressos: A fera perfeita, romance que precede
o longa e ambienta o leitor ao universo de Nova Prime, e a adaptação literária do roteiro do filme, assinada por Peter David, 
um dos mais respeitados autores no mercado de comic books. David escreve os prelúdios, acompanhado dos antigos colegas Michael 
Jan Friedman e Robert Greenberger, também referências de peso na área de comics: cada autor assina dois dos seis e-books e, juntos, 
construíram A fera perfeita. Aqui, os autores contam como a humanidade se deparou com seu maior inimigo, e como os antepassados dos 
heróis de Depois da Terra aprenderam a enfrentá-los. Expulsos da Terra pela poluição e o esgotamento dos recursos naturais, os últimos
humanos colonizaram e prosperaram em Nova Prime, um planeta remoto. Algumas décadas depois, são surpreendidos por ataques brutais das
naves dos Skrel - raça alienígena que considera o planeta um local sagrado -, mas graças ao Corpo de Guardiões, eficiente organização
militar de defesa, derrotam os agressores. Após 400 anos de paz, alguns habitantes influentes de Nova Prime começam a questionar a 
necessidade do Corpo de Guardiões. O Primus, líder espiritual da nova sociedade, se junta a um jornalista ambicioso numa campanha pelo
fim da força militar. A alta cúpula dos Guardiões está prestes a fazer concessões aos críticos, quando o desastre acontece: naves 
estelares dos Skrel conseguem penetrar as defesas orbitais e depositam sua carga - mais de trinta Ursas, monstros criados em laboratório
para matar e destruir. Grandes, rápidos, ferozes e imunes a todos os armamentos dos Guardiões, os Ursas levam morte e destruição à 
cidade de Nova Prime, devastando as tropas de Guardiões e desestabilizando a sociedade humana.', 'Português', 
'https://images-americanas.b2w.io/produtos/imagens/113725999/113726001_1GG.jpg' , 'depois-da-terra-a-fera-perfeita' , '25/04/2013', 2, '2020-06-02T15:31:35.650'),

(4, 'Código Limpo: Habilidades Práticas do Agile Software', 'Robert C. Martin', 'Alta Books', '29.99', '39.90', 'Mesmo um código ruim pode funcionar.
 Mas se ele não for limpo, pode acabar com uma empresa de desenvolvimento. Perdem-se a cada ano horas incontáveis e recursos importantes
devido a um código mal escrito. Mas não precisa ser assim. O renomado especialista em software, Robert C. Martin, apresenta um paradigma
revolucionário com Código limpo: Habilidades Práticas do Agile Software. Martin se reuniu com seus colegas do Mentor Object para destilar
suas melhores e mais ágeis práticas de limpar códigos “dinamicamente” em um livro que introduzirá gradualmente dentro de você os valores
da habilidade de um profissional de softwares e lhe tornar um programador melhor –mas só se você praticar. Que tipo de trabalho você fará?
Você lerá códigos aqui, muitos códigos. E você deverá descobrir o que está correto e errado nos códigos. E, o mais importante, você terá 
de reavaliar seus valores profissionais e seu comprometimento com o seu ofício.', 'Português', 
'https://images-americanas.b2w.io/produtos/01/00/item/6983/1/6983188GG.jpg' , 'codigo-limpo-habilidades-praticas-do-agile-software' , '08/09/2009', 3, '2020-06-02T15:31:35.650'),

(5, 'Programação Web com Node.js: Completo', 'Luiz Duarte', 'LuizTools', '19.99', '39.99' , 'Desde o início dos anos 2000, com o crescimento da web comercial 
como conhecemos, que empresas e pessoas do mundo inteiro conectam-se na Internet para se divertir, fazer negócios, se comunicar e muito mais.
Programar para a web, seja ela acessada por qualquer dispositivo que for, é programar para o mundo inteiro. Empresas pagam grandes quantias
para implantação de projetos web, desde pequenos sites até grandes aplicações corporativas. Não faltam oportunidades para ganhar dinheiro com
aplicações web! Neste livro será abordado temas fundamentais desde o funcionamento da web até a construção de conhecimentos básicos de
programação para web como as linguagens HTML, JavaScript e CSS, passando por frameworks de front-end consolidados como JQuery e Bootstrap. 
Foco total nos conhecimentos fundamentais de quem está começando na plataforma web.', 'Português', 
'https://images-americanas.b2w.io/produtos/01/00/img/1651615/0/1651615084_1GG.jpg' , 'programacao-web-com-node-js-completo' , '10/09/2017', 3, '2020-06-02T17:08:52.233'),

(6, 'Arquitetura Limpa: O guia do artesão para estrutura e design de software', 'Robert C. Martin', 'Alta Books', '29.99', null, 'As regras universais de 
arquitetura de software aumentam dramaticamente a produtividade dos desenvolvedores ao longo da vida dos sistemas de software. Agora, 
aproveitando o sucesso dos seus best-sellers Código Limpo e O Codificador Limpo, o lendário artesão de software Robert C. Martin ("Uncle Bob")
vai revelar essas regras e ajudar o leitor a aplicá-las. A Arquitetura Limpa de Martin não é só mais um catálogo de opções. 
Com base em meio século de experiência nos mais variados ambientes de software, Martin indica as escolhas que você deve fazer e explica
 por que elas são cruciais para o seu sucesso. Como já era esperado do Uncle Bob, este livro está cheio de soluções simples e diretas para os
  desafios reais que você enfrentará — aqueles que irão influenciar diretamente o sucesso ou fracasso dos seus projetos.', 'Português', 
'https://images-americanas.b2w.io/produtos/01/00/img/73492/5/73492585_1GG.jpg' , 'arquitetura-limpa-o-guia-do-artesao-para-estrutura-e-design-de-software' , '25/03/2019', 3, '2020-06-02T17:08:52.233'),

(7, 'Java e Programação Orientada a Objetos: Uma abordagem Didática', 'HELDER GUIMARÃES ARAGÃO', 'HELDER GUIMARÃES ARAGÃO', '33.0', '39.90',
'O entendimento da Programação Orientada a Objetos é um constante desafio para os estudantes e profissionais da área de Computação. 
Os estudantes, geralmente, têm dificuldade em entender as diferenças entre a programação estruturada e a orientada a objetos. Os profissionais,
 mesmo aqueles que possuem alguma experiência com desenvolvimento de software, também encontram dificuldades em modelar e desenvolver um 
 sistema orientado a objetos. Portanto, este livro pretende de forma didática, através de exemplos práticos, facilitar o entendimento dos
  principais conceitos do paradigma orientado a objetos por parte destes estudantes e profissionais.', 'Português',
'https://images-americanas.b2w.io/produtos/01/00/img/43481/2/43481208_1GG.jpg' , 'java-e-programacao-orientada-a-objetos-uma-abordagem-didatica' , '29/07/2013', 3, '2020-06-02T17:08:52.233'),

(8, 'Matéria escura', 'Blake Crouch', 'Intrínseca', '39.99', null,
'Essas são as últimas palavras que Jason Dessen ouve antes de acordar num laboratório, preso a uma maca. Raptado por um homem mascarado,
Jason é levado para uma usina abandonada e deixado inconsciente. Quando acorda, um estranho sorri para ele, dizendo: “Bem-vindo de volta,
amigo.” Neste novo mundo, Jason leva outra vida. Sua esposa não é sua esposa, seu filho nunca nasceu e, em vez de professor numa universidade
mediana, ele é um gênio da física quântica que conseguiu um feito inimaginável. Algo impossível. Será que é este seu mundo, e o outro é 
apenas um sonho? E, se esta não for a vida que ele sempre levou, como voltar para sua família e tudo que ele conhece por realidade?
Com ritmo veloz e muita ação, Matéria escura nos leva a um universo muito maior do que imaginamos, ao mesmo tempo em que comove ao colocar 
em primeiro plano o amor pela família. Marcante e intimista, seus múltiplos cenários compõem uma história que aborda questões profundamente 
humanas, como identidade, o peso das escolhas e até onde vamos para recuperar a vida com que sonhamos.', 'Português',
'https://images-americanas.b2w.io/produtos/01/00/img/1486851/3/1486851357_1GG.jpg' , 'materia-escura' , '20/02/2017', 2, '2020-06-02T17:08:52.233'),

(9, 'Interestelar', 'Jonathan Nolan', 'Gryphus Editora', '49.99', '19.90',
'"O FIM DA TERRA NÃO SERÁ O NOSSO FIM". Do aclamado cineasta Christopher Nolan, diretor de A origem (Inception) e da trilogia Batman – 
O Cavaleiro das Trevas (Batman - The Dark Knight), INTERESTELAR é a crônica de um grupo de exploradores que se aproveita de um recém-descoberto
buraco de minhoca para ultrapassar os limites das viagens espaciais tripuladas e assim conquistar as grandes distâncias de uma jornada
interestelar. Enquanto viajam, estão em risco o destino do planeta... Terra...e o futuro da raça humana. INTERESTELAR será o primeiro de
uma série de livros que o novo selo editorial GRYPHUS GEEK oferecerá a seus leitores. O selo será dedicado à publicação de obras de ficção
que remetam ao universo geek a partir de filmes, seriados de televisão ou livros originais. Ao lançar os livros Supernatural,
a Gryphus Editora constatou existir um amplo público formado por leitores jovens adultos, ávidos por novidades desse segmento.', 'Português',
'https://images-americanas.b2w.io/produtos/01/00/oferta/12682/3/12682322_1GG.jpg' , 'interestelar' , '17/03/2016', 2, '2020-06-02T17:08:52.233');

INSERT INTO PEDIDO_LIVROS(PEDIDO_ID, LIVRO_ID) VALUES(1, 1), (1, 2);
