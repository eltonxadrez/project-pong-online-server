package game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	private ServerSocket serverSocket;
	public static final int PORT = 12345;
	public static final String STOP_STRING = "##";
	private Set<ConnectedClient> clientes = ConcurrentHashMap.newKeySet();
	public Boolean jogador1 = false;
	public Boolean jogador2 = false;
	public EstadoJogoAtual estadoJogoAtual;
	
	private Regra regra;
	public Thread regraThread;
	
	//USAR AINDA
	public static final Integer TICKRATE = 20;
	public static final Integer INTERVAL = 1000 / TICKRATE; // em ms
	
	public Server() {
		System.out.println("SERVIDOR INICIADO");
		this.estadoJogoAtual = new EstadoJogoAtual();
		this.dadosIniciais();
//		this.initSending();
		try {
			
			this.regra = new Regra(this, this.estadoJogoAtual);
			this.regraThread = new Thread(regra);
			this.regraThread.start();
			
			serverSocket = new ServerSocket(PORT);
			System.out.println("INICIANDO ABERTURA DE CONEXOES");
			while (true) {
				iniConnections();
			}
		} catch (IOException e) {
			System.out.println("1 Erro na abertura do Servidor");
//			e.printStackTrace();
		}

		
	}
	//enviar estado de jogo
	public void initSending() {
		try {
			for (ConnectedClient connectedClient : clientes) {
				//enviar estado de jogo
				DadosJogoPong dadosEnviados = new DadosJogoPong();
				dadosEnviados = estadoJogoAtual.snapshot();
				
				connectedClient.out.writeObject(dadosEnviados);
				connectedClient.out.flush();
			}
		} catch (IOException e) {
			System.out.println("2 Erro ou desconexao do cliente");
//			e.printStackTrace();
		}
	}
	
	private void dadosIniciais() {
		this.estadoJogoAtual.updateBall(500, 300);
		this.estadoJogoAtual.updatePaddle(20, 20);
		this.estadoJogoAtual.placarJogador1 = 0;
		this.estadoJogoAtual.placarJogador2 = 0;
	}
	
	private void iniConnections() throws IOException {
		Socket clientSocket = serverSocket.accept();
		if(this.clientes.size() < 2) {
			if(clientSocket.isConnected()) {
				new Thread(()->{
					try {
						System.out.println("3 Cliente login");
						ConnectedClient client = new ConnectedClient(clientSocket);							
						if(!jogador1) {
							client.idJogador = "J1";
							this.jogador1 = true;
						}
						else {
							client.idJogador = "J2";
							this.jogador2 = true;
						}
						clientes.add(client);
						client.readMessage(this.regra);
						client.close();
						System.out.println("4 Cliente logoff");
						this.clientes.remove(client);
						if(client.idJogador.equals("J1")) {
							this.jogador1 = false;
							System.out.println("remocao J1");
						}
						else {
							this.jogador2 = false;
							System.out.println("remocao J2");
						}
					} catch (Exception e) {
						System.out.println("5 Cliente logoff ou erro");
						e.printStackTrace();
					} finally {
						
					}
				}).start();			
			}
		}
		else {
			clientSocket.close();
		}
	}
}
