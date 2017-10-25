public class Filme {
	
	private String filme_Id;
	private String titulo;
	private String descricao;
	private String ano_Lancamento;
	private String idioma_Id;
	private String aluguel_Duracao;
	private String aluguel_Preco;
	private String duracao_Filme;
	private String reposicao_Custo;
	private String classificacao;
	private String ultima_Atualizacao;
	private String caracteristicas_Especiais;
	private String texto_Completo;
	
	//propriedade filme_Id
	public void setFilme_Id(String s){
		this.filme_Id = s;
	}
	public String getFilme_Id(){
		return this.filme_Id;
	}
	
	//propriedade aluguel_Duracao
	public void setAluguel_Duracao(String s){
		this.aluguel_Duracao = s;
	}
	public String getAluguel_Duracao(){
		return this.aluguel_Duracao + " days";
	}
	
	//propriedade aluguel_Preco
	public void setAluguel_Preco(String s){
		this.aluguel_Preco = s;
	}
	public String getAluguel_Preco(){
		return this.aluguel_Preco + "$";
	}
	
	//propriedade duracao_Filme
	public void setDuracao_Filme(String s){
		this.duracao_Filme = s;
	}
	public String getDuracao_Filme(){
		return this.duracao_Filme +" minutes";
	}
	
	//propriedade reposicao_Custo
	public void setReposicao_Custo(String s){
		this.reposicao_Custo = s;
	}
	public String getReposicao_Custo(){
		return this.reposicao_Custo + "$";
	}
	
	//propriedade ano_Lancamento
	public void setAno_Lancamento(String s){
		this.ano_Lancamento = s;
	}
	public String getAno_Lancamento(){
		return this.ano_Lancamento;
	}
	
	//propriedade idioma_Id
	public void setIdioma_Id(String s){
		this.idioma_Id = s;
	}
	public String getIdioma_Id(){
		return this.idioma_Id;
	}
	
	//propriedade titulo
	public void setTitulo(String s){
		this.titulo = s;
	}
	public String getTitulo()
	{
		return this.titulo;
	}
	
	//propriedade descricao
	public void setDescricao(String s){
		this.descricao = s;
	}
	public String getDescricao()
	{
		return this.descricao;
	}
	
	//propriedade classificao
	public void setClassificacao(String s){
		this.classificacao = s;
	}
	public String getClassificacao()
	{
		return this.classificacao;
	}
	
	//propriedade ultima_Atualizacao
	public void setUltima_Atualizacao(String s){
		this.ultima_Atualizacao = s;
	}
	public String getUltima_Atualizacao()
	{
		return this.ultima_Atualizacao;
	}
	
	//propriedade caracteristicas_Especiais
	public void setCaracteristicas_Especiais(String s){
		this.caracteristicas_Especiais = s;
	}
	public String getCaracteristicas_Especiais()
	{
		return this.caracteristicas_Especiais;
	}
	
	//propriedade texto_Completo
	public void setTexto_Completo(String s){
		this.texto_Completo = s;
	}
	public String getTexto_Completo()
	{
		return this.texto_Completo;
	}
	
	public void imprime()
	{
		System.out.println("---Filme---");
		System.out.printf("%s\n", this.getFilme_Id());
		System.out.printf("%s\n", this.getTitulo());
		System.out.printf("%s\n", this.getDescricao());
		System.out.printf("%s\n", this.getAno_Lancamento());
		System.out.printf("%s\n", this.getIdioma_Id());
		System.out.printf("%s\n", this.getAluguel_Duracao());
		System.out.printf("%s\n", this.getAluguel_Preco());
		System.out.printf("%s\n", this.getDuracao_Filme());
		System.out.printf("%s\n", this.getReposicao_Custo());
		System.out.printf("%s\n", this.getClassificacao());
		System.out.printf("%s\n", this.getUltima_Atualizacao());
		System.out.printf("%s\n", this.getCaracteristicas_Especiais());
		System.out.printf("%s\n", this.getTexto_Completo());
	}
		
}
