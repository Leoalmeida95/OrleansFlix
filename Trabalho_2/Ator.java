public class Ator {
	
	private String ator_Id;
	private String primeiro_Nome;
	private String ultimo_Nome;
	private String ultima_Atualizacao;
	
	//propriedade ator_Id
	public void setAtor_Id(String s){
		this.ator_Id = s;
	}
	public String getAtor_Id(){
		return this.ator_Id;
	}
	
	//propriedade primeiro_Nome
	public void setPrimeiro_Nome(String s){
		this.primeiro_Nome = s;
	}
	public String getPrimeiro_Nome(){
		return this.primeiro_Nome;
	}
	
	//propriedade ultimo_Nome
	public void setUltimo_Nome(String s){
		this.ultimo_Nome = s;
	}
	public String getUltimo_Nome(){
		return this.ultimo_Nome;
	}
	
	//propriedade ultima_Atualizacao
	public void setUltima_Atualizacao(String s){
		this.ultima_Atualizacao = s;
	}
	public String getUltima_Atualizacao(){
		return this.ultima_Atualizacao;
	}
	
	public void imprime()
	{
		System.out.println("---Ator---");
		System.out.printf("%s\n", this.getAtor_Id());
		System.out.printf("%s\n", this.getPrimeiro_Nome());
		System.out.printf("%s\n", this.getUltimo_Nome());
		System.out.printf("%s\n", this.getUltima_Atualizacao());
	}

}
