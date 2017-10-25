public class Filme_Ator {
	
	private String ator_Id;
	private String filme_Id;
	private String ultima_Atualizacao;
	
	//propriedade ator_Id
		public void setAtor_Id(String s){
			this.ator_Id = s;
		}
		public String getAtor_Id(){
			return this.ator_Id;
		}
		
		//propriedade filme_Id
		public void setFilme_Id(String s){
			this.filme_Id = s;
		}
		public String getFilme_Id(){
			return this.filme_Id;
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
			System.out.println("---Filme_Ator---");
			System.out.printf("%s\n", this.getAtor_Id());
			System.out.printf("%s\n", this.getFilme_Id());
			System.out.printf("%s\n", this.getUltima_Atualizacao());
		}


}
