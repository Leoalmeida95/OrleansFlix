# OrleansFlix_TEBD
Sistema de consultas SPARQL em arquivos RDF, com Apache Jena.

![20216295_1388848134555598_1396057235_o](https://user-images.githubusercontent.com/25140680/31975523-8612a3a2-b910-11e7-8c0b-02f259366c30.png)

#####1 Descrição do Trabalho

Tudo o que será visto neste documento são as etapas que compõem o trabalho.

Dados fornecidos:

* films.txt - Arquivo contendo informações que representam diversos flmes.

* actors.txt - Arquivo contento informações que representam diversos atores.

* film_actor.txt - Uma associação entre atores e filmes, de onde se pode extrair quais atores
trabalharam em quais filmes.


O objetivo deste trabalho foi criar dois sistemas diferentes que se integrem utilizando RDF. O
primeiro sistema contéem informações sobre filmes, enquanto o segundo, sobre atores. Em cada
um deles, háa um link para consulta de informaçõoes no outro sistema.
Exemplo: no sistema que lista os filmes, há links para o sistema com os atores para recuperar
seus detalhes.


O desenvolvimento do projeto foi dividido em três etapas:

* Conversão dos .txt para RDF.

* Criação das querys SPARQL.

* Desenvolvimento dos Sistemas Web.


#####2 Conversão para RDF

Para poder ser capaz de trocar informações entre dois sistemas diferentes, é necessário que esses
sistemas entendam os dados no mesmo formato. Para isso é necessário utilizar o RDF.


#####2.1 RDF

RDF, acrônimo para Resource Description Framework, é um modelo padrão para o intercâmbio
de dados na web. RDF tem caracteristicas que facilitam a mesclagem de dados mesmo se os
esquemas subjacentes sejam diferentes. O RDF mais especícamente suporta a evolução dos
esquemas ao longo do tempo sem que os consumidores de dados tenham que ser modificados.


#####2.2 Os arquivos de dados

Como entrada de dados ainda não convertidos para RDF, temos os arquivos de texto films.txt,
actors.txt. Cada um desses arquivos tem uma representação para diferentes dados sobre atores
e filmes. E, em particular, o arquivo film_actor.txt representa a ligação entre os atores e filmes
desses dois arquivos. Apesar de representarem informações diferentes, suas estruturas de dados
em arquivo são similares: As informações sãp separadas por pipes ('|'), e uma linha (registro)
termina com um caractere de fim de linha ('\n').


#####2.3 Apache Jena

Apache Jena foi uma das ferramentas utilizadas no desenvolvimento do projeto. Jena é um
framework open source de Web Semântica para Java. Fornece uma API para extrair dados de
RDFs ou para escrever grafos RDF. Os grafos são representados como um modelo abstrato. Um
modelo pode ser suprido com dados de arquivos, bancos de dados, URLs ou uma combinação
destes. Um modelo também pode ser utilizado por queries SPARQL.


#####2.4 Conversão


Primeiramente, para representar os dados dos arquivos no ambiente Java, foi criada uma classe
para cada arquivo de texto. Cada uma dessas classes representa um registro dentro dos arquivos
(um ator, um filme, etc).
Além destas três classes, há a classe principal, que efetivamente converte os arquivos para RDF
utilizando métodos da API Jena. Esta classe abre os arquivos de texto em modo direto (aleatório)
e cria listas de Atores, Filmes e Atores Filmes. Cada uma das listas terá todos os registros dentro
dos arquivos (fragmentos de código abaixo):

```{.java results="none"}
public static ArrayList <Ator> Atores_ToList ( RandomAccessFile file ) throws IOException  {
// comeca do primeiro ator
file . seek (130) ;
// convertendo o randomacessfile para ipunt stream
InputStream is = Channels . newInputStream ( file . getChannel ());
// criando um scanner a partir da inputstream com os caracateres '|' ou '\n'como delimitadores
Scanner scanner = new Scanner (is). useDelimiter (" \\||\\ n");
// criando uma lista de Atores
ArrayList <Ator > atores = new ArrayList <Ator >() ;
 
while ( scanner . hasNext ()){
// garante que a lista vai armazenar os 200 atores

if( atores . size () < 200) {
Ator a = new Ator ();
// usando o trim para retirar espacos em branco do inicio e dofim da linha
a. setAtor_Id ( scanner . next (). trim ());
a. setPrimeiro_Nome ( scanner . next (). trim ());
a. setUltimo_Nome ( scanner . next (). trim ());
a. setUltima_Atualizacao ( scanner . next (). trim ());
atores . add (a);
}
else break ;
}
scanner . close ();
file . close ();
is. close ();

return atores ;
}
```


O algoritmo acima lê todos os registros de actors.txt e os armazena em um ArrayList, para então
poder ser convertido para RDF utilizando os métodos do Apache Jena no algoritmo abaixo:
```{.java results="none"}
public static void Atores_ToRdf ( ArrayList <Ator > lista ) throws IOException {
  
Model m = ModelFactory . createDefaultModel ();

String NS = " http :// OrleansFlix . com / ufrrj / tebd /#";
// criando as propriedades
Property ator_id = m. createProperty (NS+" Actor_id ");
Property primeiro_nome = m. createProperty ( NS + " First_name " );
Property ultimo_nome = m. createProperty ( NS + " Last_name " );
Property ultima_atualizacao = m. createProperty (NS + " Last_Update ");

// criando uma ' resource ' para cada objeto da lista

for ( Ator ator : lista ){
m. createResource (NS+" actor /"+ ator . getAtor_Id ())
. addProperty ( ator_id , ator . getAtor_Id ())
. addProperty ( primeiro_nome , ator . getPrimeiro_Nome ())
. addProperty ( ultimo_nome , ator . getUltimo_Nome ())
. addProperty ( ultima_atualizacao , ator . getUltima_Atualizacao ());
}

String fileName = " actors . rdf";
FileWriter out = new FileWriter ( fileName );
m. write (out , " RDF /XML - ABBREV ");

}
 ``` 
  
Após este último passo, o arquivo em RDF com as informações de actors.txt estará pronto. Vale
ressaltar que todo este processo se aplica de forma similar aos arquivos films.txt e film_actor.txt.


#####2.5 Ontologia por Vocabulário

Como pode ser visto no algoritmo de conversão para RDF, todas as Resources e Properties
compartilham do mesmo prefíxo NS (namespace), possibilitando que os sistemas saibam que
estão "falando a mesma lingua" e, consequentemente, proporcionando uma Ontologia por Vocabulário entre os sistemas que utilizam esses RDFs.


#####3 Consultas com SPARQL

O método doGet é chamado quando se aperta o botão da consulta em umas das páginas.
Esse método abre os arquivos RDFs que foram gerados, recebe o 'nome' que está sendo passado
para a consulta, e executa as queries, gerando uma lista no final.

```{.java results="none"}
public void doGet ( HttpServletRequest request , HttpServletResponse response ) throwsIOException , ServletException{

File file1 = new File ( getClass (). getResource (" film_actor .rdf"). getFile ());
File file2 = new File ( getClass (). getResource (" films . rdf"). getFile ());
File file3 = new File ( getClass (). getResource (" actors .rdf "). getFile ());

Model film_actor = Open_Rdf ( file1 . getPath ());
Model films = Open_Rdf ( file2 . getPath ());
Model actors = Open_Rdf ( file3 . getPath ());

String nomeBuscado = request . getParameter (" nomeAtor ");
String Id = GetIdByNome (films , nomeBuscado );
List < String > result = GetAtoresDeUmFilme ( film_actor , actors ,Id);
PrintWriter out = response . getWriter ();
GeraSaida (out , result , nomeBuscado );

}
 ``` 

A seguir teremos de exemplo a busca dos atores de um filme.

A busca começa pelo método GetIdByNome. Esse método recebe o arquivo RDF 'films' e recebe
o nome do filme que está sendo pesquisado. Cria-se uma query onde se seleciona o '?id' que
é definido como o elemento 'j.0:Film_id' quando o elemento 'j.0:Title', chamado de ' ?title' for
igual ao nome do filme passado como parâmetro para esse metodo. A query é executada e retorna
o Id do filme.

```{.java results="none"}
public static String GetIdByNome ( Model m, String s){

String queryString =
" PREFIX j .0: <http :// OrleansFlix .com/ ufrrj / tebd /#>"+
" SELECT ?id "+
" WHERE {"+
"? element j .0: Film_id ?id ;"+
"j .0: Title ? title ;"+
" FILTER (? title = \""+s+"\" )"+
"}";

QueryExecution qexec = Query ( queryString ,m);

try {
// retorna uma solucao com apenas um resultado
QuerySolution soln = Solution ( qexec );
Literal x = soln . getLiteral ("id");
return x. toString ();
} finally {
qexec . close ();
}

}
 ``` 

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

```{.java results="none"}
public static List <String > GetAtoresDeUmFilme ( Model film_actor , Model actors , String FilmeId ){

ArrayList < String > results = new ArrayList < String >() ;
ArrayList < String > atoresNomes = new ArrayList < String >();

// pego os ids de todos os atores do filme
results = ConsultaRetornaList ( film_actor ,
" PREFIX j .0: <http ://OrleansFlix . com / ufrrj /tebd /#>"+
" SELECT ?x "+
" WHERE {"+
"? element j.0: Film_id ?id ;"+
"j .0: Actor_id ?x ."+
" FILTER (? id = \""+ FilmeId +"\")"+
"}");

for ( String id : results ){
// passo os ids para retornar os nomes dos atores
String queryString =
" PREFIX j .0: <http :// OrleansFlix .com / ufrrj /tebd /#>"+
" SELECT ?x ?y "+
" WHERE {"+
"? element j.0: Actor_id ?id ;"+
"j .0: First_name ?x ;"+
"j .0: Last_name ?y ."+
" FILTER (? id = \""+id+"\" )"+
"}";

QueryExecution qexec= Query ( queryString , actors );

try {
// retorna uma solucao com apenas um resultado
QuerySolution soln = Solution ( qexec );
Literal x = soln . getLiteral ("x");
Literal y = soln . getLiteral ("y");
String nomeCompleto = x+" "+y;
atoresNomes . add ( nomeCompleto );
} finally {
qexec . close ();
}
}

return atoresNomes ;

}
``` 
  
#####4 Funcionamento dos Sistemas Web

Para realizar as consultas definidas pelo SPARQL no passo anterior, foram criadas duas páginas
HTML distintas que utilizam os arquivos RDF criados anteriormente para manterem uma forma
de comunicação comum entre si. Ou seja, na página de busca por filme, o nome de um filme
é passado como o nome a ser buscado e uma lista de atores que participaram desse filme é
retornada, pois o SPARQL pode fazer uma consulta que a outra página entende. A recíproca é
válida para a outra página.

Observe abaixo uma demonstração do funcioanamento do projeto:

![20216621_1388848131222265_1218060584_o](https://user-images.githubusercontent.com/25140680/31975524-872f4c54-b910-11e7-85a3-d5dec0163b18.png)

Figura 1: Buscando atores através de um filme

![20269980_1388848124555599_1744185376_n](https://user-images.githubusercontent.com/25140680/31975528-8934aec2-b910-11e7-8d37-1a6945aa24e9.png)

Figura 2: Atores encontrados

E o oposto pode ser feito no outro site:

![20216295_1388848134555598_1396057235_o](https://user-images.githubusercontent.com/25140680/31975523-8612a3a2-b910-11e7-8c0b-02f259366c30.png)

Figura 3: Buscando filmes através de um ator/atriz

![20269980_1388848124555599_1744185376_n](https://user-images.githubusercontent.com/25140680/31975528-8934aec2-b910-11e7-8d37-1a6945aa24e9.png)

Figura 4: Filmes encontrados



