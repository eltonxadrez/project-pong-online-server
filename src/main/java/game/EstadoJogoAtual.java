package game;

public class EstadoJogoAtual {
	
	//placar
	public Integer placarJogador1;
	public Integer placarJogador2;
	//bola
	private Integer eixoXB, eixoYB;
	//raquetes
	private Integer eixoYJ1, eixoYJ2;
	
	private Integer velRaquetes = 7;
	
    public synchronized void updateBall(Integer eixoXB, Integer eixoYB) {
        this.eixoXB = eixoXB;
        this.eixoYB = eixoYB;
    }
    
    public synchronized Integer getEixoYJ1() {
    	return this.eixoYJ1;
    }

    public synchronized Integer getEixoYJ2() {
    	return this.eixoYJ2;
    }
    
    public synchronized Integer getEixoXB() {
    	return eixoXB;
    }
    
    public synchronized Integer getEixoYB() {
    	return eixoYB;
    }
    
    public synchronized void updatePaddle(Integer eixoYJ1, Integer eixoYJ2) {
    	this.eixoYJ1 = eixoYJ1;
    	this.eixoYJ2 = eixoYJ2;
    }
    
    public synchronized DadosJogoPong snapshot() {
        DadosJogoPong snapshot = new DadosJogoPong();
        snapshot.eixoXB = this.eixoXB;
        snapshot.eixoYB = this.eixoYB;
        snapshot.eixoYJ1 = this.eixoYJ1;
        snapshot.eixoYJ2 = this.eixoYJ2;
        snapshot.placarJogador1 = this.placarJogador1;
        snapshot.placarJogador2 = this.placarJogador2;
        return snapshot;
    }
    
    public synchronized void updateAll(InputJogador input) {
    	if(input.idJogador.equals("J1")) {
    		if(input.dadosComandosPong.up) {
    			if(this.eixoYJ1 - velRaquetes >= 10) {
    				this.eixoYJ1 -= velRaquetes;
    			}
    		}
    		else if(input.dadosComandosPong.down) {
    			if(this.eixoYJ1 + velRaquetes <= 400) {
    				this.eixoYJ1 += velRaquetes;    				
    			}
    		}    		
    	}
    	else if(input.idJogador.equals("J2")) {
    		if(input.dadosComandosPong.up) {
    			if(this.eixoYJ2 - velRaquetes >= 10) {
    				this.eixoYJ2 -= velRaquetes;
    			}
    		}
    		else if(input.dadosComandosPong.down) {
    			if(this.eixoYJ2 + velRaquetes <= 400) {
    				this.eixoYJ2 += velRaquetes;
    			}
    		}  
    	}
//    	this.eixoXB = dadosComandosPong.eixoXB;
//    	this.eixoYB = dadosComandosPong.eixoYB;
//    	this.eixoYJ1 = dadosComandosPong.eixoYJ1;
//    	this.eixoYJ2 = dadosComandosPong.eixoYJ2;
    }

}
