INSERT INTO CATEGORIA(ID, NOME) VALUES(1, 'Holística Saúde, Boa Forma e Dieta'),
                                      (2, 'Ficção científica'),
                                      (3, 'Computação, Informática e Mídias Digitais');

INSERT INTO Livro(ID, TITULO, AUTOR , EDITORA, DESCRICAO, IDIOMA, IMAGEMURL, LINK, DATA_PUBLICACAO, CATEGORIA_ID) 
VALUES(1, 'Você É A Sua Cura', 'Editora Alaúde', 'Deepak Chopra, Rudolph E. Tanzi' ,'Uma visão holística da medicina, de postura preventiva,
colocando o leitor como protagonista da própria saúde; - Testes que fazem refletir sobre o estado de saúde que você vivencia agora; 
- Um plano de ação semanal para repensar sua postura em relação a vários aspectos da vida que podem impactar diretamente na sua saúde.
O livro visa fazer com que o leitor encare a sua saúde de um jeito completamente novo e revolucionário.
O livro tem o objetivo de conclamar o leitor a se situar no presente, prestando atenção aos sinais que seu corpo está emitindo,
para poder realizar pequenas transformações que podem ter muito impacto no jeito como sua saúde se desenvolve.
Expandindo o conceito de imunidade, ganhamos novas formas de cuidar de nós mesmos.', 'Português',
'https://images-americanas.b2w.io/produtos/01/00/img2/33866/4/33866479_1GG.jpg','voce-e-a-sua-cura' , '01/04/2018', 1),

(2, 'Consciência Quântica: Uma nova visão sobre o amor, a morte, e o sentido da vida', 'AMIT GOSWAMI' , 'Goya', 
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
'consciencia-quantica-uma-nova-visao-sobre-o-amor-a-morte-e-o-sentido-da-vida', '16/11/2018', 1),
    
(3, 'Depois da terra: a fera perfeita', 'Suma', 'Peter David, Robert Greenberger, Michael Jan Friedman' , 'A Suma de Letras publica, até o lançamento do longa, uma série de seis e-books
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
cidade de Nova Prime, devastando as tropas de Guardiões e desestabilizando a sociedade humana. Os engenheiros do Savant correm contra
o relógio para desenvolver alguma arma capaz de ferir os inimigos, enquanto os últimos Guardiões usam táticas desesperadas para conter
os Ursas. Uma cientista pode ter encontrado a chave para a vitória, mas pode ser tarde demais para a humanidade. O jovem Conner Raige, 
cujos antepassados participaram da vitória contra os Skrel, é um dos cadetes mais promissores do Corpo de Guardiões, apesar da personalidade
excessivamente ousada e intuitiva. Porém, quando o velho inimigo da raça humana retorna, o jovem Guardião será testado até o limite. Pois,
desta vez, os Skrel e os Ursas estão prontos para acabar com a humanidade de Nova Prime... e do universo.', 'Português', 
'https://images-americanas.b2w.io/produtos/imagens/113725999/113726001_1GG.jpg' , 'depois-da-terra-a-fera-perfeita' , '25/04/2013', 2),

(4, 'Código Limpo: Habilidades Práticas do Agile Software', 'Robert C. Martin', 'Alta Books', 'Mesmo um código ruim pode funcionar.
 Mas se ele não for limpo, pode acabar com uma empresa de desenvolvimento. Perdem-se a cada ano horas incontáveis e recursos importantes
devido a um código mal escrito. Mas não precisa ser assim. O renomado especialista em software, Robert C. Martin, apresenta um paradigma
revolucionário com Código limpo: Habilidades Práticas do Agile Software. Martin se reuniu com seus colegas do Mentor Object para destilar
suas melhores e mais ágeis práticas de limpar códigos “dinamicamente” em um livro que introduzirá gradualmente dentro de você os valores
da habilidade de um profissional de softwares e lhe tornar um programador melhor –mas só se você praticar. Que tipo de trabalho você fará?
Você lerá códigos aqui, muitos códigos. E você deverá descobrir o que está correto e errado nos códigos. E, o mais importante, você terá 
de reavaliar seus valores profissionais e seu comprometimento com o seu ofício.', 'Português', 
'https://images-americanas.b2w.io/produtos/01/00/item/6983/1/6983188GG.jpg' , 'codigo-limpo-habilidades-praticas-do-agile-software' , '08/09/2009', 3);
