import java.io.IOException;
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

public class Sparql {
	
    public static void main (String[] args)throws IOException{
	
    FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
	Model m = FileManager.get().loadModel("alunos.rdf");
	
	String queryString =
			"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>"+
			"SELECT * WHERE {"+
	        " ?person vcard:UID ?x ."+
			" ?person vcard:NAME ?name ."+
			"FILTER(?name = \"LEONARDO ALMEIDA DA SILVA\" )"+   
	        "}";
	
	
	Query query  = QueryFactory.create(queryString);
	QueryExecution qexec = QueryExecutionFactory.create(query, m);
	
	try{
		ResultSet result = qexec.execSelect();
		while(result.hasNext()){
			QuerySolution soln = result.nextSolution();
			Literal name = soln.getLiteral("x");
			System.out.println(name);
		}
	}finally{
		qexec.close();
	}
    }
}