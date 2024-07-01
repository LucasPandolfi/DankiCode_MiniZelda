package Mini_Zelda;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/*Estou extendendo para a classe retangulo, pois ela já possui toda o sistema de 
 colisão necessária para o Player*/
public class Player extends Rectangle {
	
	public int spd = 4;	/*variavel responsavel pela velocidade do Player*/
	public boolean right, up, down, left; /*botões difinidos para o movimento do player*/
	
	public int curAnimation = 0;
	public int curFrames = 0;
	public int targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	public boolean shoot = false;
	public int dir = 1;
	
	/*Os dois parametros x e y são responsaveis pela posição do Player na Janela*/
	public Player(int x, int y) {
		super(x, y, 32, 32);/*os parametros 32 são responsaveis pelo tamanho e largura do player*/
	}
	
	public void tick() {
		boolean moved = false;
		
		if(right && World.isFree(x+spd, y)) {
			x += spd;
			moved = true;
			dir = 1;
		} 
		else if(left && World.isFree(x-spd, y)) {
			x -= spd;
			moved = true;
			dir = -1;
		}
		
		if(up && World.isFree(x, y-spd)) {
			y -= spd;
			moved = true;
		} 
		else if(down && World.isFree(x, y+spd)) {
			y += spd;
			moved = true;
		}
		
		if(moved) {
			curFrames++;
			if(curFrames == targetFrames) {
				curFrames= 0;
				curAnimation++;
				
				if(curAnimation == Spritesheet.player_front.length) {
					curAnimation = 0;
				}
			}
		}
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x, y, dir));
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.player_front[curAnimation], x, y, 32, 32, null);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}
