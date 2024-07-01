package Mini_Zelda;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/*Implementando o método Runnable responsavel por rodar o meu jogo e o metodo 
 * KeyListener responsavel por adicionar as ações do teclado no Jogo*/
public class Game extends Canvas implements Runnable, KeyListener{

	/*Definindo as variaveis com a resolução do game*/
	public static int WIDTH = 640;
	public static int HEIGHT = 480;
	public static int SCALE = 3;
	
	public static Player player;
	public World world;
	
	public List<Enemy> enemys = new ArrayList<Enemy>();
	
	public Game() {
		this.addKeyListener(this);
		
		/*Setando a resolução do game*/
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		/*instanciando a minha classe spritesheet*/
		new Spritesheet();
		player = new Player(32, 32);
		enemys.add(new Enemy(32, 32));
		
		world = new World();
	}
	
	public void tick() {
		player.tick();
		
		for(int i = 0; i < enemys.size(); i++) {
			enemys.get(i).tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		/*Estou verificando se posso renderizar o Jogo*/
		if(bs == null) {
			/*Metodo responsavel por otimizar o jogo. O numero 3 que esta sendo
			  passado como parametro é responsavel pela otimização grafica do jogo*/
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(0, 135, 3));	
		/*Metodo responsavel por setar a cor preta em toda a área da 
		 janela do Jogo*/
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		player.render(g);
		for(int i = 0; i < enemys.size(); i++) {
			enemys.get(i).render(g);
		}
		world.render(g);
		
		/*Metodo responsavel por mostrar o conteudo na Janela do Jogo*/
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.add(game);
		frame.setTitle("Mini Zelda");
		frame.pack();
		
		/*Metodo responsavel por centralizar a janela do Jogo no centro da tela*/
		frame.setLocationRelativeTo(null);
		
		/*Metodo responsável por fechar todas as operações do Jogo após o usuário fechar a janela*/
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*Metodo responsável por fazer com que a janela do jogo apareça*/
		frame.setVisible(true);	
		
		/*Metodo usando para chamar o mnetodo run. Ele inicia uma thread dentro da classe Game e procura 
		  um metodo run*/
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		/*Looping usando que irá permitir que o nosso Jogo renderize as ações, personagens e frames. Todo jogo possui
		  esse sistema de looping*/
		while(true) {
			tick();
			render();
			try {
				/*Metodo responsável por fazer o meu Jogo rodar em 60FPS*/
				Thread.sleep(1000/60);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			player.shoot = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}	
	}
}
