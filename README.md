# OrleansFlix_TEBD
Sistema de consultas SPARQL em arquivos RDF, com Apache Jena.

1 Descrição do Trabalho

Tudo o que será visto neste documento são as etapas que compõem o trabalho.

Dados fornecidos:

--films.txt - Arquivo contendo informações que representam diversos flmes.

--actors.txt - Arquivo contento informações que representam diversos atores.

--film_actor.txt - Uma associação entre atores e filmes, de onde se pode extrair quais atores
trabalharam em quais filmes.


O objetivo deste trabalho foi criar dois sistemas diferentes que se integrem utilizando RDF. O
primeiro sistema contéem informações sobre filmes, enquanto o segundo, sobre atores. Em cada
um deles, háa um link para consulta de informaçõoes no outro sistema.
Exemplo: no sistema que lista os filmes, há links para o sistema com os atores para recuperar
seus detalhes.


O desenvolvimento do projeto foi dividido em três etapas:

-- Conversão dos .txt para RDF.

-- Criação das querys SPARQL.

-- Desenvolvimento dos Sistemas Web.


2 Conversão para RDF

Para poder ser capaz de trocar informações entre dois sistemas diferentes, é necessário que esses
sistemas entendam os dados no mesmo formato. Para isso é necessário utilizar o RDF.


2.1 RDF

RDF, acrônimo para Resource Description Framework, é um modelo padrão para o intercâmbio
de dados na web. RDF tem caracteristicas que facilitam a mesclagem de dados mesmo se os
esquemas subjacentes sejam diferentes. O RDF mais especícamente suporta a evolução dos
esquemas ao longo do tempo sem que os consumidores de dados tenham que ser modificados.


2.2 Os arquivos de dados

Como entrada de dados ainda não convertidos para RDF, temos os arquivos de texto films.txt,
actors.txt. Cada um desses arquivos tem uma representação para diferentes dados sobre atores
e filmes. E, em particular, o arquivo film_actor.txt representa a ligação entre os atores e filmes
desses dois arquivos. Apesar de representarem informações diferentes, suas estruturas de dados
em arquivo são similares: As informações sãp separadas por pipes ('|'), e uma linha (registro)
termina com um caractere de fim de linha ('\n').


2.3 Apache Jena

Apache Jena foi uma das ferramentas utilizadas no desenvolvimento do projeto. Jena é um
framework open source de Web Semântica para Java. Fornece uma API para extrair dados de
RDFs ou para escrever grafos RDF. Os grafos são representados como um modelo abstrato. Um
modelo pode ser suprido com dados de arquivos, bancos de dados, URLs ou uma combinação
destes. Um modelo também pode ser utilizado por queries SPARQL.


2.4 Conversão


Primeiramente, para representar os dados dos arquivos no ambiente Java, foi criada uma classe
para cada arquivo de texto. Cada uma dessas classes representa um registro dentro dos arquivos
(um ator, um filme, etc).
Além destas três classes, há a classe principal, que efetivamente converte os arquivos para RDF
utilizando métodos da API Jena. Esta classe abre os arquivos de texto em modo direto (aleatório)
e cria listas de Atores, Filmes e Atores Filmes. Cada uma das listas terá todos os registros dentro
dos arquivos (fragmentos de código abaixo):

1 public static ArrayList <Ator> Atores_ToList ( RandomAccessFile file ) throws IOException
  
{

2 // comeca do primeiro ator

3 file . seek (130) ;

4 // convertendo o randomacessfile para ipunt stream

5 InputStream is = Channels . newInputStream ( file . getChannel ());

6 // criando um scanner a partir da inputstream com os caracateres '|' ou '\n'como delimitadores

7 Scanner scanner = new Scanner (is). useDelimiter (" \\||\\ n");

8 // criando uma lista de Atores

9 ArrayList <Ator > atores = new ArrayList <Ator >() ;
  
10
  
11 while ( scanner . hasNext ()){

12 // garante que a lista vai armazenar os 200 atores

13 if( atores . size () < 200) {

14 Ator a = new Ator ();

15 // usando o trim para retirar espacos em branco do inicio e dofim da linha

16 a. setAtor_Id ( scanner . next (). trim ());

17 a. setPrimeiro_Nome ( scanner . next (). trim ());

18 a. setUltimo_Nome ( scanner . next (). trim ());

19 a. setUltima_Atualizacao ( scanner . next (). trim ());

20 atores . add (a);

21 }

22 else break ;

23 }

24 scanner . close ();

25 file . close ();

26 is. close ();

27

28 return atores ;

29 }



O algoritmo acima lê todos os registros de actors.txt e os armazena em um ArrayList, para então
poder ser convertido para RDF utilizando os métodos do Apache Jena no algoritmo abaixo:

1 public static void Atores_ToRdf ( ArrayList <Ator > lista ) throws IOException {
  
2 Model m = ModelFactory . createDefaultModel ();

3 String NS = " http :// OrleansFlix . com / ufrrj / tebd /#";

4 // criando as propriedades

5 Property ator_id = m. createProperty (NS+" Actor_id ");

6 Property primeiro_nome = m. createProperty ( NS + " First_name " );

7 Property ultimo_nome = m. createProperty ( NS + " Last_name " );

8 Property ultima_atualizacao = m. createProperty (NS + " Last_Update ");

9 // criando uma ' resource ' para cada objeto da lista

10 for ( Ator ator : lista ){

11 m. createResource (NS+" actor /"+ ator . getAtor_Id ())

12 . addProperty ( ator_id , ator . getAtor_Id ())

13 . addProperty ( primeiro_nome , ator . getPrimeiro_Nome ())

14 . addProperty ( ultimo_nome , ator . getUltimo_Nome ())

15 . addProperty ( ultima_atualizacao , ator . getUltima_Atualizacao ());

16 }

17 String fileName = " actors . rdf";

18 FileWriter out = new FileWriter ( fileName );

19 m. write (out , " RDF /XML - ABBREV ");

20 }
  
  
Após este último passo, o arquivo em RDF com as informações de actors.txt estará pronto. Vale
ressaltar que todo este processo se aplica de forma similar aos arquivos films.txt e film_actor.txt.


2.5 Ontologia por Vocabulário

Como pode ser visto no algoritmo de conversão para RDF, todas as Resources e Properties
compartilham do mesmo prefíxo NS (namespace), possibilitando que os sistemas saibam que
estão "falando a mesma lingua" e, consequentemente, proporcionando uma Ontologia por Vocabulário entre os sistemas que utilizam esses RDFs.


3 Consultas com SPARQL

O método doGet é chamado quando se aperta o botão da consulta em umas das páginas.
Esse método abre os arquivos RDFs que foram gerados, recebe o 'nome' que está sendo passado
para a consulta, e executa as queries, gerando uma lista no final.


1 public void doGet ( HttpServletRequest request , HttpServletResponse response ) throwsIOException , ServletException

2 {

3 File file1 = new File ( getClass (). getResource (" film_actor .rdf"). getFile ());

4 File file2 = new File ( getClass (). getResource (" films . rdf"). getFile ());

5 File file3 = new File ( getClass (). getResource (" actors .rdf "). getFile ());

6

7 Model film_actor = Open_Rdf ( file1 . getPath ());

8 Model films = Open_Rdf ( file2 . getPath ());

9 Model actors = Open_Rdf ( file3 . getPath ());

10

11 String nomeBuscado = request . getParameter (" nomeAtor ");

12

13 String Id = GetIdByNome (films , nomeBuscado );

14 List < String > result = GetAtoresDeUmFilme ( film_actor , actors ,Id);

15

16 PrintWriter out = response . getWriter ();

17

18 GeraSaida (out , result , nomeBuscado );

19 }


A seguir teremos de exemplo a busca dos atores de um filme.

A busca começa pelo método GetIdByNome. Esse método recebe o arquivo RDF 'films' e recebe
o nome do filme que está sendo pesquisado. Cria-se uma query onde se seleciona o '?id' que
é definido como o elemento 'j.0:Film_id' quando o elemento 'j.0:Title', chamado de ' ?title' for
igual ao nome do filme passado como parâmetro para esse metodo. A query é executada e retorna
o Id do filme.


1 public static String GetIdByNome ( Model m, String s)

2 {

3 String queryString =

4 " PREFIX j .0: <http :// OrleansFlix .com/ ufrrj / tebd /#>"+

5 " SELECT ?id "+

6 " WHERE {"+

7 "? element j .0: Film_id ?id ;"+

8 "j .0: Title ? title ;"+

9 " FILTER (? title = \""+s+"\" )"+

10 "}";

11

12 QueryExecution qexec = Query ( queryString ,m);

13

14 try {

15 // retorna uma solucao com apenas um resultado

16 QuerySolution soln = Solution ( qexec );

17 Literal x = soln . getLiteral ("id");

18 return x. toString ();

19 } finally {

20 qexec . close ();

21 }

22

23 }


Posteriormente, esse Id é passado para o método GetAtoresDeUmFilme, que recebe os RDFs
'film_actor', que representa a relação dos filmes e atores, e 'actors', além do Id do filme. Primeiro,
criamos uma query que vai selecionar um '?x' que é definido como o elemento 'j.0:Actor_id',
quando o elemento 'Film_id', chamado de '?id', é igual ao id passado como parametro no metodo.

Essa query retorna a lista de todos os Ids dos atores.

Após termos essa lista dos id, nós faremos um laço nessa lista, passando um id por vez para a
outra query que retornará o nome dos Atores pelo id.
Essa query irá selecionar o '?x', que é 'j.0:First name' e o '?y', que é o 'j.0:Last_name', quando
o '?id' que é o 'j.0:Actor_id', for igual ao id do laço.

No final, teremos a lista de nomes dos atores.


1 public static List <String > GetAtoresDeUmFilme ( Model film_actor , Model actors , String FilmeId )
  
2 {

3 ArrayList < String > results = new ArrayList < String >() ;

4 ArrayList < String > atoresNomes = new ArrayList < String >();

5

6 // pego os ids de todos os atores do filme

7 results = ConsultaRetornaList ( film_actor ,

8 " PREFIX j .0: <http ://OrleansFlix . com / ufrrj /tebd /#>"+

9 " SELECT ?x "+

10 " WHERE {"+

11 "? element j.0: Film_id ?id ;"+

12 "j .0: Actor_id ?x ."+

13 " FILTER (? id = \""+ FilmeId +"\")"+

14 "}");

15

16

17 for ( String id : results ){

18 // passo os ids para retornar os nomes dos atores

19 String queryString =

20 " PREFIX j .0: <http :// OrleansFlix .com / ufrrj /tebd /#>"+

21 " SELECT ?x ?y "+

22 " WHERE {"+

23 "? element j.0: Actor_id ?id ;"+

24 "j .0: First_name ?x ;"+

25 "j .0: Last_name ?y ."+

26 " FILTER (? id = \""+id+"\" )"+

27 "}";

28

29 QueryExecution qexec= Query ( queryString , actors );

30

31 try {

32 // retorna uma solucao com apenas um resultado

33 QuerySolution soln = Solution ( qexec );

34 Literal x = soln . getLiteral ("x");

35 Literal y = soln . getLiteral ("y");

36 String nomeCompleto = x+" "+y;

37

38 atoresNomes . add ( nomeCompleto );

39 } finally {

40 qexec . close ();

41 }

42 }

43 return atoresNomes ;

44 }
  
  
4 Funcionamento dos Sistemas Web

Para realizar as consultas definidas pelo SPARQL no passo anterior, foram criadas duas páginas
HTML distintas que utilizam os arquivos RDF criados anteriormente para manterem uma forma
de comunicação comum entre si. Ou seja, na página de busca por filme, o nome de um filme
é passado como o nome a ser buscado e uma lista de atores que participaram desse filme é
retornada, pois o SPARQL pode fazer uma consulta que a outra página entende. A recíproca é
válida para a outra página.
