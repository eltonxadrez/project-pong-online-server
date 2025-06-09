package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

public class ConnectedClient {
	
	public Socket clientSocket;
	public ObjectInputStream in;
	public ObjectOutputStream out;
	public String idJogador;
	
	public ConnectedClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.idJogador = idJogador;
		try {
			//verificar depois isso BufferedInputStream
			
			//ENVIAR
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
//			this.out.writeObject(estadoJogoAtual);
			
			//RECEBER
			this.in = new ObjectInputStream(clientSocket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readMessage(Regra regra) {
//		String line  = "";
		while(true) {
			try {
				//receber e atualizar estado de jogo
				DadosComandosPong dadosComandosPong = (DadosComandosPong) in.readObject(); //bloqueante
				InputJogador inputJogador = new InputJogador(dadosComandosPong, idJogador);
				regra.fila.add(inputJogador);
//				estadoJogoAtual.updateAll(dadosComandosPong);
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("B1 Cliente logoff ou erro");
				break;
			}
			
			
//			try {
//				line = in.readUTF();
//				for (ObjectOutputStream objOutStream : clientes) {
//					if(objOutStream != this.out) {
////						objOutStream.println(line);
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println("Client -> " + line);
		}
		this.close();
	}

	public void close() {
		try {
			clientSocket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
