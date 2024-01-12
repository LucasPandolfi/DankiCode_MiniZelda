package Mini_Zelda;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*Estou extendendo para a classe retangulo, pois ela já possui toda o sistema de 
 colisão necessária para o Player*/
public class Player extends Rectangle {
	/*variavel responsavel pela velocidade do Player*/
	public int spd = 4;
	
	public boolean right, up, down, left;
	
	/*Os dois parametros x e y são responsaveis pela posição do Player na Janela*/
	public Player(int x, int y) {
		/*os parametros 32 são responsaveis pelo tamanho e largura do player*/
		super(x, y, 32, 32);
	}
	
	public void tick() {
		if(right && World.isFree(x+spd, y)) {
			x += spd;
		} else if(left && World.isFree(x-spd, y)) {
			x -= spd;
		}
		
		if(up && World.isFree(x, y-spd)) {
			y -= spd;
		} else if(down && World.isFree(x, y+spd)) {
			y += spd;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}
}
