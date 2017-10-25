import java.io.FileWriter;
import java.io.IOException;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class JenaTrab {
	
    public static void main (String[] args) throws IOException{
	
	Model m = ModelFactory.createDefaultModel();
	
	Resource Aline = m.createResource("http://description/Alunos/Aline")
			.addProperty(VCARD.NAME, "ALINE LIMA DE SOUZA")
			.addProperty(VCARD.EMAIL, "Aline@ufrrj.com.br")
			.addProperty(VCARD.UID, "2011715030");
	
	Resource Valber = m.createResource("http://description/Alunos/Valber")
			.addProperty(VCARD.NAME, "VALBER OSCAR LAUX")
			.addProperty(VCARD.EMAIL, "Valber@ufrrj.com.br")
			.addProperty(VCARD.UID, "2011780223");
	
	Resource Luis = m.createResource("http://description/Alunos/Luis")
			.addProperty(VCARD.NAME, "LUIS PAULO DA COSTA CAVALCANTE")
			.addProperty(VCARD.EMAIL, "Luis@ufrrj.com.br")
			.addProperty(VCARD.UID, "2011780282");
	
	Resource Diana = m.createResource("http://description/Alunos/Diana")
			.addProperty(VCARD.NAME, "DIANA SOARES DE ANDRADE")
			.addProperty(VCARD.EMAIL, "Diana@ufrrj.com.br")
			.addProperty(VCARD.UID, "2011785081");
	
	Resource Racicley = m.createResource("http://description/Alunos/Racicley")
			.addProperty(VCARD.NAME, "RACICLEY COSTA CONCEICAO PEDRO")
			.addProperty(VCARD.EMAIL, "Racicley@ufrrj.com.br")
			.addProperty(VCARD.UID, "2011785233");
	
	Resource Jackson = m.createResource("http://description/Alunos/Jackson")
			.addProperty(VCARD.NAME, "JACKSON CARDOSO DE OLIVEIRA")
			.addProperty(VCARD.EMAIL, "Jackson@ufrrj.com.br")
			.addProperty(VCARD.UID, "2012780103");
	
	Resource Francisco = m.createResource("http://description/Alunos/Francisco")
			.addProperty(VCARD.NAME, "FRANCISCO SANCAS JUNIOR")
			.addProperty(VCARD.EMAIL, "Francisco@ufrrj.com.br")
			.addProperty(VCARD.UID, "2012780138");
	
	Resource William = m.createResource("http://description/Alunos/William")
			.addProperty(VCARD.NAME, "WILLIAM MESSIAS DA COSTA")
			.addProperty(VCARD.EMAIL, "William@ufrrj.com.br")
			.addProperty(VCARD.UID, "2012780332");

	Resource Mayara = m.createResource("http://description/Alunos/Mayara")
			.addProperty(VCARD.NAME, "MAYARA MARQUES DA ROSA")
			.addProperty(VCARD.EMAIL, "Mayara@ufrrj.com.br")
			.addProperty(VCARD.UID, "2012785164");
	
	Resource Artur = m.createResource("http://description/Alunos/Artur")
			.addProperty(VCARD.NAME, "ARTUR ALVES SOUZA SILVA")
			.addProperty(VCARD.EMAIL, "Artur@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013780034");
	
	Resource Diego = m.createResource("http://description/Alunos/Diego")
			.addProperty(VCARD.NAME, "DIEGO AMARO FERRAZ DA COSTA")
			.addProperty(VCARD.EMAIL, "Diego@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013780069");
	
	Resource Douglas = m.createResource("http://description/Alunos/Douglas")
			.addProperty(VCARD.NAME, "DOUGLAS CASTRO DA SILVA")
			.addProperty(VCARD.EMAIL, "Douglas@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013780077");
	
	Resource Renan = m.createResource("http://description/Alunos/Renan")
			.addProperty(VCARD.NAME, "RENAN PROCOPIO DUARTE")
			.addProperty(VCARD.EMAIL, "Renan@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013780263");
	
	Resource Bianca = m.createResource("http://description/Alunos/Bianca")
			.addProperty(VCARD.NAME, "BIANCA ALBUQUERQUE MACHADO")
			.addProperty(VCARD.EMAIL, "Bianca@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785036");
	
	Resource Denilson = m.createResource("http://description/Alunos/Denilson")
			.addProperty(VCARD.NAME, "DENILSON DE ARAUJO PIRES FERREIRA")
			.addProperty(VCARD.EMAIL, "Denilson@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785052");
	
	Resource Fellipe = m.createResource("http://description/Alunos/Fellipe")
			.addProperty(VCARD.NAME, "FELLIPE BRAVO RIBEIRO PIMENTEL")
			.addProperty(VCARD.EMAIL, "Fellipe@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785060");
	
	Resource Gabriel = m.createResource("http://description/Alunos/Gabriel")
			.addProperty(VCARD.NAME, "GABRIEL DE OLIVEIRA SEGOBIA")
			.addProperty(VCARD.EMAIL, "Gabriel@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785079");
	
	Resource Paulo = m.createResource("http://description/Alunos/Paulo")
			.addProperty(VCARD.NAME, "PAULO ROBERTO XAVIER JUNIOR")
			.addProperty(VCARD.EMAIL, "Paulo@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785184");
	
	Resource Thiago = m.createResource("http://description/Alunos/Thiago")
			.addProperty(VCARD.NAME, "THIAGO DA COSTA PINTO FRAZAO DE SOUSA")
			.addProperty(VCARD.EMAIL, "Thiago@ufrrj.com.br")
			.addProperty(VCARD.UID, "2013785265");
	
	Resource Nelson = m.createResource("http://description/Alunos/Nelson")
			.addProperty(VCARD.NAME, "NELSON ANTUNES LARANJEIRA NETO")
			.addProperty(VCARD.EMAIL, "Nelson@ufrrj.com.br")
			.addProperty(VCARD.UID, "2015780317");
	
	Resource Lucas = m.createResource("http://description/Alunos/Lucas")
			.addProperty(VCARD.NAME, "LUCAS SOARES DE BARROS VILLARINHO")
			.addProperty(VCARD.EMAIL, "Lucas@ufrrj.com.br")
			.addProperty(VCARD.UID, "2015780473");
	
	Resource Leonardo = m.createResource("http://description/Alunos/Leonardo")
			.addProperty(VCARD.NAME, "LEONARDO ALMEIDA DA SILVA")
			.addProperty(VCARD.EMAIL, "Leonardo@ufrrj.com.br")
			.addProperty(VCARD.UID, "2015780589");
	
	String fileName = "alunos.rdf";
	FileWriter out = new FileWriter( fileName );
	
	m.write(out, "RDF/XML-ABBREV");
	}

}
