package game;

import java.io.Serializable;

public class DadosJogoPong implements Serializable {

	private static final long serialVersionUID = -5996099971954958805L;
	
	//placar
	public Integer placarJogador1;
	public Integer placarJogador2;
	//bola
	public Integer eixoXB, eixoYB;
	//raquetes
	public Integer eixoYJ1, eixoYJ2;
	
}
