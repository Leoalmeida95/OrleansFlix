import java.util.ArrayList;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;

public class Querys {
	
    public static void main (String[] args){
	/*	try{
			//configuração básica log4j
			BasicConfigurator.configure();
    	
			Model film_actor = Open_Rdf("film_actor.rdf");
			Model films = Open_Rdf("films.rdf");
			Model actors = Open_Rdf("actors.rdf");
			
			String ID = GetIdByNome(films,"Chamber Italian");
			GetAtoresDeUmFilme(film_actor,actors,ID);
			//GetFilmesDeUmAtor(film_actor, films, 1);
			//GetAll(films,"Title");
			
			}
			catch(Exception e) {
				System.out.println("Error: \n" + e);
			} */
    	
    	String s = "Chamber Italian";
    	String[] nomeCompleto = s.split(" ");
    	String nome = nomeCompleto[0];
    	String sobrenome = nomeCompleto[1];
    	System.out.println(nome);
    	System.out.println(sobrenome);
    }
    
    public static Model Open_Rdf(String path)
    {
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model m = FileManager.get().loadModel(path);
		return m;
    }
    
    public static ArrayList<String> ConsultaRetornaList(Model m, String queryString)
    {
    	QueryExecution qexec = Query(queryString,m);
		ArrayList<String> results = new ArrayList<String>();
		
		try{
			ResultSet result = qexec.execSelect();
			//retorna uma solucao com varios resultados
			while(result.hasNext()){
				QuerySolution soln = result.nextSolution();
				Literal x = soln.getLiteral("x");
				results.add(x.toString());
			}
			
			return results;
		}finally{
			qexec.close();
		}
    }
    
    public static QueryExecution Query(String queryString, Model m){
	    Query query  = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, m);
		
		return qexec;
   }
   
   public static QuerySolution Solution(QueryExecution qexec){
		ResultSet result = qexec.execSelect();
		QuerySolution soln = result.nextSolution();
	
		return soln;
   }
   
   public static String GetIdByNome(Model m, String s)
   {
	
   	String queryString =
   			"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
   			        "SELECT ?id "+
   			        "WHERE {"+
   					"?element j.0:Film_id ?id ;"+
   					"j.0:Title ?title ;"+
   					"FILTER(?title = \""+s+"\" )"+   
   					"}";
   			
   	QueryExecution qexec = Query(queryString,m);
   	
		try{
			//retorna uma solução com apenas um resultado
			QuerySolution soln = Solution(qexec);
			Literal x = soln.getLiteral("id");
			return x.toString();
		}finally{
			qexec.close();
		}
	   
   }
    
    public static void GetFilmesDeUmAtor(Model film_actor, Model films, int AtorId)
    {
    	ArrayList<String> results = new ArrayList<String>();
    	ArrayList<String> filmesNomes = new ArrayList<String>();
    	
    	//pego os ids de todos os filmes do ator
		results = ConsultaRetornaList(film_actor,
					"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
	                "SELECT ?x "+
	                "WHERE {"+
				    "?element j.0:Actor_id ?id ;"+
				    "j.0:Film_id ?x ."+
				    "FILTER(?id = \""+AtorId+"\" )"+   
				    "}");
		
		
		for(String id : results){
			//passo os ids para retornar os nomes dos filmes
			String queryString =
					"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
			        "SELECT ?x "+
			        "WHERE {"+
					"?element j.0:Film_id ?id ;"+
					"j.0:Title ?x ."+
					"FILTER(?id = \""+id+"\" )"+   
					"}"; 
		
			QueryExecution qexec = Query(queryString,films);
		
			try{
				//retorna uma solução com apenas um resultado
				QuerySolution soln = Solution(qexec);
				Literal x = soln.getLiteral("x");
				
				System.out.println(x);
				filmesNomes.add(x.toString()); 
			}finally{
				qexec.close();
			}
		}
		
    }
    
    public static void GetAtoresDeUmFilme(Model film_actor, Model actors, String Film_Id)
    {
    	ArrayList<String> results = new ArrayList<String>();
    	ArrayList<String> atoresNomes = new ArrayList<String>();
    	
    	//pego os ids de todos os atores do filme
		results = ConsultaRetornaList(film_actor,
				"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
		                "SELECT ?x "+
		                "WHERE {"+
				"?element j.0:Film_id ?id ;"+
				"j.0:Actor_id ?x ."+
				"FILTER(?id = \""+Film_Id+"\" )"+   
				"}");
			
		
		for(String id : results){
			//passo os ids para retornar os nomes dos atores		
			String queryString =
					"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
	                "SELECT ?x ?y "+
	                "WHERE {"+
				    "?element j.0:Actor_id ?id ;"+
				    "j.0:First_name ?x ;"+
				    "j.0:Last_name ?y ."+
				    "FILTER(?id = \""+id+"\" )"+   
				    "}"; 
		
			QueryExecution qexec = Query(queryString,actors);
		
			try{
				//retorna uma solução com apenas um resultado
				QuerySolution soln = Solution(qexec);
				Literal x = soln.getLiteral("x");
				Literal y = soln.getLiteral("y");
				String nomeCompleto = x+" "+y;
				
				System.out.println(nomeCompleto);
				atoresNomes.add(nomeCompleto); 
			}finally{
				qexec.close();
			}
		}
		
    }
    
    public static void GetInformacoesAtor(Model actors, String ActorId)
    {
    	ArrayList<String> results = new ArrayList<String>();
    	
    	//pego todas as informacoes de um ator 
    	String queryString =
    			"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
    			        "SELECT ?Last ?First ?Update "+
    			        "WHERE {"+
    					"?element j.0:Actor_id ?id ;"+
    					"j.0:First_name ?Last ;"+
    					"j.0:Last_name ?First ;"+
    					"j.0:Last_Update ?Update ."+
    					"FILTER(?id = \""+ActorId+"\" )"+   
    					"}";
    			
    	QueryExecution qexec = Query(queryString,actors);
    	
		try{
			//retorna uma solução com apenas um resultado
			QuerySolution soln = Solution(qexec);
			Literal Last = soln.getLiteral("Last");
			Literal First = soln.getLiteral("First");
			Literal Update = soln.getLiteral("Update");
			//String nomeCompleto = x+" "+y;
			
			//System.out.println(nomeCompleto);
			//atoresNomes.add(nomeCompleto); ]
		}finally{
			qexec.close();
		}
    					
    }
    
    public static void GetAll(Model model, String elemento)
    {
    	ArrayList<String> results = new ArrayList<String>();
    	
    	//pego todos os 'elementos' na 'model' 
    			results = ConsultaRetornaList(model,
    					"PREFIX j.0: <http://OrleansFlix.com/ufrrj/tebd/#>"+
    		            "SELECT * "+
    		            "WHERE {"+
    					"?element j.0:"+elemento+" ?x ."+   
    					"}");	
    }
}
