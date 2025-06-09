package game;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Regra implements Runnable {
	
	public final ConcurrentLinkedQueue<InputJogador> fila;
	public int velocidadeTick = 60;
	private Server server;
	private EstadoJogoAtual estadoJogoAtual;
	
	private Integer velBolaX = 10;
	private Integer velBolaY = 3;
	private Integer tamanhoBola = 20;
	
//	private Integer placarJogador1 = 0;
//	private Integer placarJogador2 = 0;

	public Regra(Server server, EstadoJogoAtual estadoJogoAtual) {
		this.server = server;
		this.estadoJogoAtual = estadoJogoAtual;
		this.fila = new ConcurrentLinkedQueue<>();
	}

	@Override
	public void run() {
		Thread.currentThread().setName("TRD-REGRA");
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(true) {
			tick();
			try {
				Thread.sleep(1000/velocidadeTick);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void tick() {
		//logica das raquetes
		this.movimentoJogadores();
		//logica da bola 
		if(this.server.jogador1 && this.server.jogador2) {
			this.fisicaBola();
		}
		this.server.initSending();
		
	}

	private void movimentoJogadores() {
		InputJogador input;
		while ((input = fila.poll()) != null) {
			this.estadoJogoAtual.updateAll(input);
			System.out.println("ID PLAYER: " + input.idJogador +
			", UP:" + input.dadosComandosPong.up +
			", DOWND:" + input.dadosComandosPong.down);
		}
	}
	private void fisicaBola() {
		this.colisaoRaquete();
		this.colisaoCampo();
		this.movimentoBola();
	}
	private void colisaoRaquete() {
		if (this.estadoJogoAtual.getEixoXB() <= 20 + 20 &&
		    this.estadoJogoAtual.getEixoYB() + tamanhoBola >= this.estadoJogoAtual.getEixoYJ1() &&
		    this.estadoJogoAtual.getEixoYB() <= this.estadoJogoAtual.getEixoYJ1() + 200) {
			this.velBolaX *= -1;
		}
		if (this.estadoJogoAtual.getEixoXB() + tamanhoBola >= 970 &&
			this.estadoJogoAtual.getEixoYB() + tamanhoBola >= this.estadoJogoAtual.getEixoYJ2() &&
			this.estadoJogoAtual.getEixoYB() <= this.estadoJogoAtual.getEixoYJ2() + 200) {
			this.velBolaX *= -1;
		}
	}

	private void colisaoCampo() {
		if (this.estadoJogoAtual.getEixoYB() < 10) {
			this.velBolaY *= -1;
		}
		if ((this.estadoJogoAtual.getEixoYB() + tamanhoBola) >= 600) {
			this.velBolaY *= -1;
		}
		if (this.estadoJogoAtual.getEixoXB() < 10) {
			this.velBolaX *= -1;
			System.out.println("ponto para jogador 2");
			this.estadoJogoAtual.placarJogador2 += 1;
			this.estadoJogoAtual.updateBall(500, 300);
		}
		if ((this.estadoJogoAtual.getEixoXB() + tamanhoBola) >= 1000) {
			this.velBolaX *= -1;
			System.out.println("ponto para jogador 1");
			this.estadoJogoAtual.placarJogador1 += 1;
			this.estadoJogoAtual.updateBall(500, 300);
		}
	}

	private void movimentoBola() {
		//executa movimento da bola
		this.estadoJogoAtual.updateBall(this.estadoJogoAtual.getEixoXB() + this.velBolaX, this.estadoJogoAtual.getEixoYB() + this.velBolaY);
//		this.estadoJogoAtual.updateBall(this.estadoJogoAtual.getEixoXB() + this.velBolaX, this.estadoJogoAtual.getEixoYB());
	}
	
}
