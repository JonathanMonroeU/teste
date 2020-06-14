package mateodragao.components;

import mateodragao.components.personagem.Arqueiro;
import mateodragao.components.personagem.Catapulta;
import mateodragao.components.personagem.Lanceiro;
import mateodragao.components.personagem.Mago;
import mateodragao.components.personagem.Personagem;
import mateodragao.interfaces.IDataProvider;
import mateodragao.interfaces.IMovimento;
import mateodragao.interfaces.IPersonagem;
import mateodragao.interfaces.ITabuleiro;

public class Tabuleiro implements ITabuleiro{
	private IMovimento vPecas[][][];
	private int DragonPosition[];
	
	public Tabuleiro() {
		vPecas = new IMovimento[16][16][2];
		DragonPosition = new int[2];
	}
	
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaTabuleiro() {
		//primeira passagem para mover e disparar ataques
		for (int i=0; i<16; i++) {
			for (int j=0; j<16; j++) {
				if (vPecas[i][j][0] != null) {
					if (vPecas[i][j][0] instanceof Personagem) {
						IPersonagem p = (IPersonagem) vPecas[i][j][0];
						p.disparaAtaque(this);
					}
					vPecas[i][j][0].move(this); 
					//decidir entre tabuleiro fazer movimento ou peca
				}
				
				if (vPecas[i][j][1] != null) {
					vPecas[i][j][1].move(this);
				}	
			}
		}
		
		//segunda passagem para analisar conflitos
		for (int i=0; i<16; i++) {
			for (int j=0; j<16; j++) {
				if (vPecas[i][j][0] != null) {
					if (vPecas[i][j][0] instanceof Personagem) {
						IPersonagem p = (IPersonagem) vPecas[i][j][0];
						p.perdeVida(vPecas[i][j][1]);
						//analisar como vai ser a hora da morte
					}
				}	
			}
		}
		
	}

	@Override
	public void mostraTabuleiro() {
		
		
	}

	@Override
	public IMovimento getPeca(int x, int y) {
		return vPecas[x][y][0];
	}

	@Override
	public void putPeca(int x, int y, int tipo) {
		switch(tipo) {
			case 1:
				vPecas[x][y][0] = new Arqueiro();
				break;
			case 2:
				vPecas[x][y][0] = new Lanceiro();
				break;
			case 3:
				vPecas[x][y][0] = new Mago();
				break;
			case 4:
				vPecas[x][y][0] = new Catapulta();
				break;
		}
	}

	@Override
	public void removePeca(int x, int y) {
		vPecas[x][y][0] = null;
	}

	@Override
	public void receiveData(IDataProvider dataProvider) {
		int position[] = dataProvider.getData();
		if (position[0] == 0) //zero é remocao
			removePeca(position[1],position[2]);
		else
			putPeca(position[1],position[2],position[0]);
	}

	@Override
	public int[] getDragonPosition() {
		return DragonPosition;
	}

}
