import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.log4j.BasicConfigurator;

public class ArquivoToRDF {
	
	public static void main (String[] args){
		try{
		
		//configuração básica log4j
		BasicConfigurator.configure();
			
	    //leio os arquivos e retorno um randomacessfile
		RandomAccessFile filmes_arquivo = Open_Arquivo("films.txt");
		RandomAccessFile atores_arquivo = Open_Arquivo("actors.txt");
		RandomAccessFile atores_filmes_arquivo = Open_Arquivo("film_actor.txt");
		
		//crio uma lista de para cada arquivi
		ArrayList<Filme> filmes_lista =  Filmes_ToList(filmes_arquivo);
		ArrayList<Ator> atores_lista = Atores_ToList(atores_arquivo);
		ArrayList<Filme_Ator> atores_filmes_lista = Atores_Filmes_ToList(atores_filmes_arquivo);
		
		//crio o arquivo rdf a partir das listas
		Filmes_ToRdf(filmes_lista);
		Atores_ToRdf(atores_lista);
		FilmesAtores_ToRdf(atores_filmes_lista);
		
		}
		catch(Exception e) {
			System.out.println("Error: \n" + e);
		}
	}
	
	public static RandomAccessFile Open_Arquivo (String path) throws IOException 
	{
		RandomAccessFile raf = new RandomAccessFile(path, "r");
		return raf;		
	}
	
	public static ArrayList<Filme> Filmes_ToList(RandomAccessFile file) throws IOException{
		//uso o seek para começar do primeiro filme
		file.seek(1135);
		//converto o randomacessfile para ipunt stream
		InputStream is = Channels.newInputStream(file.getChannel());
		//crio um scanner a partir da inputstream com os caracateres'|' ou '\n' como delimitadores
		Scanner scanner = new Scanner(is).useDelimiter("\\||\\n");
		//crio uma lista de Filmes
		ArrayList<Filme> filmes = new ArrayList<Filme>();		

		while (scanner.hasNext() ){
			//garanto que a lista armazenará os 1000 filmes
			if(filmes.size() < 1000)
			{
				Filme f = new Filme();
				//uso o trim para retirar espaços em branco do inicio e do fim da linha
				f.setFilme_Id(scanner.next().trim());
				f.setTitulo(scanner.next().trim());
				f.setDescricao(scanner.next().trim());
				f.setAno_Lancamento(scanner.next().trim());
				f.setIdioma_Id(scanner.next().trim());
				f.setAluguel_Duracao(scanner.next().trim());
				f.setAluguel_Preco(scanner.next().trim());
				f.setDuracao_Filme(scanner.next().trim());
				f.setReposicao_Custo(scanner.next().trim());
				f.setClassificacao(scanner.next().trim());
				f.setUltima_Atualizacao(scanner.next().trim());
				f.setCaracteristicas_Especiais(scanner.next().trim());
				f.setTexto_Completo(scanner.next().trim());
				filmes.add(f);
			}
			else				
				break;
		}
		scanner.close();
		file.close();
		is.close();
		
		return filmes;
	}

	public static ArrayList<Ator> Atores_ToList(RandomAccessFile file) throws IOException{
		//uso o seek para começar do primeiro ator
		file.seek(130);
		//converto o randomacessfile para ipunt stream
		InputStream is = Channels.newInputStream(file.getChannel());
		//crio um scanner a partir da inputstream com os caracateres'|' ou '\n' como delimitadores
		Scanner scanner = new Scanner(is).useDelimiter("\\||\\n");
		//crio uma lista de Filmes
		ArrayList<Ator> atores = new ArrayList<Ator>();		

		while (scanner.hasNext() ){
			//garanto que a lista armazenará os 200 atores
			if(atores.size() < 200)
			{
				Ator a = new Ator();
				//uso o trim para retirar espaços em branco do inicio e do fim da linha
				a.setAtor_Id(scanner.next().trim());
				a.setPrimeiro_Nome(scanner.next().trim());
				a.setUltimo_Nome(scanner.next().trim());
				a.setUltima_Atualizacao(scanner.next().trim());
				atores.add(a);
			}
			else				
				break;
		}
		scanner.close();
		file.close();
		is.close();
		
		return atores;
	}

	public static ArrayList<Filme_Ator> Atores_Filmes_ToList(RandomAccessFile file) throws IOException{
		//uso o seek para começar da primeira linha
		file.seek(90);
		//converto o randomacessfile para ipunt stream
		InputStream is = Channels.newInputStream(file.getChannel());
		//crio um scanner a partir da inputstream com os caracateres'|' ou '\n' como delimitadores
		Scanner scanner = new Scanner(is).useDelimiter("\\||\\n");
		//crio uma lista de Filmes
		ArrayList<Filme_Ator> atores_filmes = new ArrayList<Filme_Ator>();		

		while (scanner.hasNext() ){
			//garanto que a lista armazenará os 5462 atores
			if(atores_filmes.size() < 5462)
			{
				Filme_Ator af = new Filme_Ator();
				//uso o trim para retirar espaços em branco do inicio e do fim da linha
				af.setAtor_Id(scanner.next().trim());
				af.setFilme_Id(scanner.next().trim());
				af.setUltima_Atualizacao(scanner.next().trim());
				atores_filmes.add(af);
			}
			else				
				break;
		}
		scanner.close();
		file.close();
		is.close();
		
		return atores_filmes;
	}
	
	public static void Filmes_ToRdf (ArrayList<Filme> lista) throws IOException{
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://OrleansFlix.com/ufrrj/tebd/#";

		//crio as propriedades
		Property filme_id = m.createProperty(NS+"Film_id");
	    Property titulo = m.createProperty( NS + "Title" );
		Property descricao_filme = m.createProperty( NS + "Description" );
		Property ano_lancamento = m.createProperty(NS + "Release_year");
		Property idioma_id = m.createProperty(NS+"Language_id");
		Property aluguel_duracao = m.createProperty(NS+"Rental_duration");
		Property aluguel_preco = m.createProperty(NS+"Rental_rate");
		Property duracao_filme = m.createProperty(NS+"Length");
		Property reposicao_custo = m.createProperty(NS+"Replacement_cost");
		Property classificacao = m.createProperty(NS+"Rating");
		Property ultima_atualizacao = m.createProperty(NS+"Last_update");
		Property caracteristicas_especiais = m.createProperty(NS+"Special_features");
		Property texto_completo = m.createProperty(NS+"Fulltext");
		//m.setNsPrefix("of", NS);
		
		//crio uma 'resource' para cada objeto da lista
		for(Filme filme : lista){
			m.createResource(NS+"film/"+filme.getFilme_Id())
				.addProperty(filme_id, filme.getFilme_Id())
				.addProperty(titulo, filme.getTitulo())
				.addProperty(descricao_filme, filme.getDescricao())
				.addProperty(ano_lancamento, filme.getAno_Lancamento())
				.addProperty(idioma_id, filme.getIdioma_Id())
				.addProperty(aluguel_duracao, filme.getAluguel_Duracao())
				.addProperty(aluguel_preco, filme.getAluguel_Preco())
				.addProperty(duracao_filme, filme.getDuracao_Filme())
				.addProperty(reposicao_custo, filme.getReposicao_Custo())
				.addProperty(classificacao, filme.getClassificacao())
				.addProperty(ultima_atualizacao, filme.getUltima_Atualizacao())
				.addProperty(caracteristicas_especiais, filme.getCaracteristicas_Especiais())
				.addProperty(texto_completo, filme.getTexto_Completo());
		}
		
		String fileName = "films.rdf";
		FileWriter out = new FileWriter(fileName);
		m.write(out, "RDF/XML-ABBREV");
	}
	
	public static void Atores_ToRdf (ArrayList<Ator> lista) throws IOException{
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://OrleansFlix.com/ufrrj/tebd/#";

		//crio as propriedades
		Property ator_id = m.createProperty(NS+"Actor_id");
	    Property primeiro_nome = m.createProperty( NS + "First_name" );
		Property ultimo_nome = m.createProperty( NS + "Last_name" );
		Property ultima_atualizacao = m.createProperty(NS + "Last_Update");
		//m.setNsPrefix("of", NS);
		
		//crio uma 'resource' para cada objeto da lista
		for(Ator ator : lista){
			m.createResource(NS+"actor/"+ator.getAtor_Id())
				.addProperty(ator_id, ator.getAtor_Id())
				.addProperty(primeiro_nome, ator.getPrimeiro_Nome())
				.addProperty(ultimo_nome, ator.getUltimo_Nome())
				.addProperty(ultima_atualizacao, ator.getUltima_Atualizacao());
		}
		
		String fileName = "actors.rdf";
		FileWriter out = new FileWriter(fileName);
		m.write(out, "RDF/XML-ABBREV");
	}
	
	public static void FilmesAtores_ToRdf (ArrayList<Filme_Ator> lista) throws IOException{
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://OrleansFlix.com/ufrrj/tebd/#";

		//crio as propriedades
	    Property ator_id = m.createProperty( NS + "Actor_id" );
		Property film_id = m.createProperty( NS + "Film_id" );
		Property ultima_atualizacao = m.createProperty(NS + "Last_Update");
		//m.setNsPrefix("of", NS);
		
		int Id = 0;
		//crio uma 'resource' para cada objeto da lista
		for(Filme_Ator filme_ator : lista){
			m.createResource(NS+"film-actor/"+Id++)
				.addProperty(ator_id, filme_ator.getAtor_Id())
				.addProperty(film_id, filme_ator.getFilme_Id())
				.addProperty(ultima_atualizacao, filme_ator.getUltima_Atualizacao());
		}
		
		String fileName = "film_actor.rdf";
		FileWriter out = new FileWriter(fileName);
		m.write(out, "RDF/XML-ABBREV");
	}
}

