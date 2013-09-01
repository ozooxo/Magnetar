import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Magnetar extends JFrame {

	public Magnetar() {
		add(new GamePanel());

		setTitle("Magnetar");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Screen.WIDTH, Screen.HEIGHT);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public static void main (String[] args) {
		new Magnetar();
	}
}
