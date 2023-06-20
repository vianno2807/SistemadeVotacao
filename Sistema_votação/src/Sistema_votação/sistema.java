package Sistema_votação;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("unused")
public class sistema extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JPasswordField passwordField;
	private usuario usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistema frame = new sistema();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public sistema() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 389);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(0, 107, 159));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		user = new JTextField();
		user.setBounds(89, 142, 172, 20);
		contentPane.add(user);
		user.setColumns(10);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(89, 128, 56, 14);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(89, 185, 37, 17);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean ret = consultaUsuario();

				if (ret) {

					tela_usuario tela = new tela_usuario(usuario);
					tela.setVisible(true);
					tela.setLocationRelativeTo(null);
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Usuario ou senha incorretos!");

				}

			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 107, 159));
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnNewButton.setBounds(124, 267, 100, 20);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setIcon(new ImageIcon(sistema.class.getResource("/imagens/cadeado.png")));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_2.setBounds(111, 41, 113, 43);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(89, 201, 172, 20);
		contentPane.add(passwordField);
	}

	public boolean consultaUsuario() {
		boolean ret = false;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\FACULDADE\\banco sistema.db");
			System.out.println("Conectado com o banco");
			String nome = user.getText();
			@SuppressWarnings("deprecation")
			String password = passwordField.getText();

			String sql = "SELECT idUsuario, login, senha, perfil FROM Usuarios WHERE login = ? and senha =  ? ";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nome);
			stmt.setString(2, password);


			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt(1);

				usuario = new usuario(rs.getInt("idUsuario"), rs.getString("login"), rs.getString("perfil"));
				System.out.println(usuario.getPerfil());


				connection.close();
				ret = true;
			} else {

				connection.close(); 
				ret = false;
			}

		} catch (ClassNotFoundException ex) {
			System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}