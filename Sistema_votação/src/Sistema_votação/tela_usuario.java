package Sistema_votação;

import java.awt.EventQueue;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Color;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JFormattedTextField;

public class tela_usuario extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField end;
	private JTextField registro;
	private JTextField passw;
	private static usuario usuario;
	private JTextField name1;
	private JTextField regis;
	private JTextField part;
	private JTextField num_partido;
	private JTextField senha1;
	private int idEleicao;
	private List<String> resultConsulta = new ArrayList<>();
	private String indiceSelecionado;
	private String indiSelecionado;
	private String itemSelecionado;
	private JTextField nameEleicao;
	private JTextField data_inicio;
	private JTextField data_fim;
	private JTable table;

	private static final String URL = "jdbc:sqlite:D:\\FACULDADE\\banco sistema.db";
	private JTable table_1;
	private JTextField numCandidato;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tela_usuario frame = new tela_usuario(usuario);
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
	public tela_usuario(usuario user) {
		DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<>();
		this.usuario = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 979, 653);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 107, 159));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(0, 107, 159));
		desktopPane.setBounds(253, 56, 439, 550);
		contentPane.add(desktopPane);

		// System.out.println("Indice selecionado");
		// System.out.println(indiceSelecionado);

		JButton btnNewButton = new JButton("  Votar");
		btnNewButton.setToolTipText("Votar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame internalFrame_6 = new JInternalFrame("");
				internalFrame_6.setClosable(true);
				internalFrame_6.getContentPane().setBackground(new Color(0, 128, 192));
				internalFrame_6.setBounds(0, 0, 439, 550);
				desktopPane.add(internalFrame_6);
				internalFrame_6.getContentPane().setLayout(null);

				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(10, 114, 407, 216);
				internalFrame_6.getContentPane().add(scrollPane_1);

				table_1 = new JTable();
				table_1.setModel(
						new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Partido", "Numero", }));
				scrollPane_1.setViewportView(table_1);

				JComboBox<String> comboBoxVot = new JComboBox<String>();
				try {
					Class.forName("org.sqlite.JDBC");
					Connection connection = DriverManager.getConnection(URL);

					// CONSULTA NOME DA ELEIÇAO
					String sqlEleicao = "SELECT nomeEleicao FROM Eleicoes";
					PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao, Statement.RETURN_GENERATED_KEYS);
					ResultSet retorno = stmt6.executeQuery();
					while (retorno.next()) {
						comboBoxVot.addItem(retorno.getString("nomeEleicao"));
					}

				} catch (Exception e7) {
					e7.printStackTrace();
				}

				comboBoxVot.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						indiceSelecionado = comboBoxVot.getSelectedItem().toString();
						System.out.println("Valor selecionado: " + indiceSelecionado);
					}
				});

				comboBoxVot.setBounds(150, 83, 171, 21);
				internalFrame_6.getContentPane().add(comboBoxVot);

				JButton btnNewButton_7 = new JButton("Votar");
				btnNewButton_7.setForeground(new Color(0, 64, 0));
				btnNewButton_7.setBackground(new Color(128, 255, 128));
				btnNewButton_7.setIcon(null);
				btnNewButton_7.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int numeCandidato = Integer.parseInt(numCandidato.getText());
						System.out.println(idEleicao);
						int idCandidato = 0;
						try {
							Class.forName("org.sqlite.JDBC");
							Connection connection = DriverManager.getConnection(URL);

							String sqlCand = "SELECT idCandidato FROM Candidatos WHERE numPartido = ?";
							PreparedStatement stmt6 = connection.prepareStatement(sqlCand,
									Statement.RETURN_GENERATED_KEYS);
							stmt6.setInt(1, numeCandidato);
							ResultSet retorno = stmt6.executeQuery();
							if (retorno.next()) {
								idCandidato = retorno.getInt("idCandidato");
							}
							String sql = "INSERT INTO Votos (idUsuario, idCandidato, idEleicao)" + "VALUES (?, ?, ?);";
							PreparedStatement stmt2 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							stmt2.setInt(1, usuario.getIdUsuario());
							stmt2.setInt(2, idCandidato);
							stmt2.setInt(3, idEleicao);
							stmt2.execute();

							ResultSet rs = stmt2.getGeneratedKeys();

							if (rs.next()) {
								JOptionPane.showMessageDialog(null, "Voto salvo!");
								connection.close();
							}
						} catch (ClassNotFoundException ex) {
							System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, "Só é possivel votar uma vez!");
						}
					}
				});
				btnNewButton_7.setBounds(275, 351, 85, 22);
				internalFrame_6.getContentPane().add(btnNewButton_7);
				internalFrame_6.setVisible(true);
				btnNewButton_7.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));

				JButton btnNewButton_7_1 = new JButton("Listar");
				btnNewButton_7_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DefaultTableModel tabela = (DefaultTableModel) table_1.getModel();
						tabela.setRowCount(0);
						try {
							Class.forName("org.sqlite.JDBC");
							Connection connection = DriverManager.getConnection(URL);

							String sqlEleicao = "SELECT idEleicao FROM Eleicoes WHERE nomeEleicao = ?";
							PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao,
									Statement.RETURN_GENERATED_KEYS);
							stmt6.setString(1, indiceSelecionado);
							ResultSet retorno = stmt6.executeQuery();
							if (retorno.next()) {
								idEleicao = retorno.getInt("idEleicao");
							}
							String sql = "SELECT Candidatos.nome, Candidatos.partido, Candidatos.numPartido, Eleicoes.nomeEleicao FROM Candidatos INNER JOIN Eleicoes ON Candidatos.idEleicao = Eleicoes.idEleicao WHERE Eleicoes.idEleicao = ? ORDER BY RANDOM()";
							PreparedStatement stmt = connection.prepareStatement(sql);
							stmt.setInt(1, idEleicao);
							ResultSet rs = stmt.executeQuery();

							while (rs.next()) {
								String nome = rs.getString("nome");
								String partido = rs.getString("partido");
								String numPartido = rs.getString("numPartido");

								Object[] rowData = { nome, partido, numPartido };
								tabela.addRow(rowData);

							}
							connection.close();
						} catch (ClassNotFoundException ex) {
							System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				});
				btnNewButton_7_1.setFont(new Font("YuGothic UI Semibold", Font.BOLD, 13));
				btnNewButton_7_1.setBounds(331, 82, 85, 21);
				internalFrame_6.getContentPane().add(btnNewButton_7_1);

				numCandidato = new JTextField();
				numCandidato.setBounds(180, 353, 85, 21);
				internalFrame_6.getContentPane().add(numCandidato);
				numCandidato.setColumns(10);

				JLabel lblNewLabel_9 = new JLabel("Número do candidato:");
				lblNewLabel_9.setForeground(new Color(255, 255, 255));
				lblNewLabel_9.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
				lblNewLabel_9.setBounds(16, 353, 154, 18);
				internalFrame_6.getContentPane().add(lblNewLabel_9);

				JLabel lblNewLabel_10 = new JLabel("Selecione a eleição:");
				lblNewLabel_10.setForeground(new Color(255, 255, 255));
				lblNewLabel_10.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
				lblNewLabel_10.setBounds(10, 84, 140, 13);
				internalFrame_6.getContentPane().add(lblNewLabel_10);

				JLabel lblNewLabel_2_4 = new JLabel("Votação");
				lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_2_4.setForeground(Color.WHITE);
				lblNewLabel_2_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 35));
				lblNewLabel_2_4.setBounds(141, 27, 154, 30);
				internalFrame_6.getContentPane().add(lblNewLabel_2_4);

				JLabel lblNewLabel_11 = new JLabel("");
				lblNewLabel_11.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/candidato.png")));
				lblNewLabel_11.setBounds(42, 384, 128, 128);
				internalFrame_6.getContentPane().add(lblNewLabel_11);

			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		btnNewButton.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/voto.png")));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 128, 189));
		btnNewButton.setBounds(0, 0, 156, 46);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Resultados");
		btnNewButton_1.setToolTipText("Ver resultados das eleições");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame internalFrame_7 = new JInternalFrame("");
				internalFrame_7.setClosable(true);
				internalFrame_7.getContentPane().setBackground(new Color(0, 128, 192));
				internalFrame_7.getContentPane().setLayout(null);

				JScrollPane scrollPane_2 = new JScrollPane();
				scrollPane_2.setBounds(10, 147, 407, 187);
				internalFrame_7.getContentPane().add(scrollPane_2);

				table_2 = new JTable();
				table_2.setModel(new DefaultTableModel(new Object[][] {},
						new String[] { "Candidato", "Partido", "Total de Votos" }));
				scrollPane_2.setViewportView(table_2);

				JComboBox<String> comboBox_21 = new JComboBox<String>();
				comboBox_21.setBounds(143, 116, 141, 21);
				internalFrame_7.getContentPane().add(comboBox_21);

				try {
					Class.forName("org.sqlite.JDBC");
					Connection connection = DriverManager.getConnection(URL);

					String sqlEleicao = "SELECT nomeEleicao FROM Eleicoes";
					PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao, Statement.RETURN_GENERATED_KEYS);
					ResultSet retorno = stmt6.executeQuery();
					while (retorno.next()) {
						comboBox_21.addItem(retorno.getString("nomeEleicao"));
					}

				} catch (Exception e7) {
					e7.printStackTrace();
				}

				comboBox_21.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						indiSelecionado = comboBox_21.getSelectedItem().toString();
						System.out.println("Valor selecionado: " + indiSelecionado);
					}
				});

				JButton btnNewButton_8 = new JButton("Ver resultado");
				btnNewButton_8.setBounds(294, 116, 115, 21);
				internalFrame_7.getContentPane().add(btnNewButton_8);
				btnNewButton_8.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DefaultTableModel tabela = (DefaultTableModel) table_2.getModel();
						tabela.setRowCount(0);
						try {
							Class.forName("org.sqlite.JDBC");
							Connection connection = DriverManager.getConnection(URL);

							String sqlEleicao = "SELECT idEleicao FROM Eleicoes WHERE nomeEleicao = ?";
							PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao,
									Statement.RETURN_GENERATED_KEYS);
							stmt6.setString(1, indiSelecionado);
							ResultSet retorno = stmt6.executeQuery();

							if (retorno.next()) {
								idEleicao = retorno.getInt("idEleicao");
							}

							String sql = "SELECT Candidatos.nome, Candidatos.partido, COUNT(*) as total_votos FROM Candidatos INNER JOIN Votos ON Candidatos.idCandidato = Votos.idCandidato AND Candidatos.idEleicao = Votos.idEleicao WHERE Votos.idEleicao = ? GROUP BY Candidatos.nome, Candidatos.partido";
							PreparedStatement stmt = connection.prepareStatement(sql);
							stmt.setInt(1, idEleicao);
							ResultSet rs = stmt.executeQuery();

							while (rs.next()) {
								String nome = rs.getString("nome");
								String partido = rs.getString("partido");
								String total_votos = rs.getString("total_votos");

								Object[] rowData = { nome, partido, total_votos };
								tabela.addRow(rowData);

							}
							connection.close();
						} catch (ClassNotFoundException ex) {
							System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
						} catch (SQLException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "Só é possivel votar uma vez!");
						}

					}
				});

				JLabel lblNewLabel_12 = new JLabel("Selecione a Eleição:");
				lblNewLabel_12.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
				lblNewLabel_12.setForeground(new Color(255, 255, 255));
				lblNewLabel_12.setBounds(10, 117, 134, 13);
				internalFrame_7.getContentPane().add(lblNewLabel_12);

				JLabel lblNewLabel_2_5 = new JLabel("Resultados");
				lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_2_5.setForeground(Color.WHITE);
				lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 31));
				lblNewLabel_2_5.setBounds(143, 43, 154, 30);
				internalFrame_7.getContentPane().add(lblNewLabel_2_5);

				JLabel lblNewLabel_13 = new JLabel("");
				lblNewLabel_13
						.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/noticia (1)_resized.png")));
				lblNewLabel_13.setBounds(131, 344, 166, 137);
				internalFrame_7.getContentPane().add(lblNewLabel_13);
				internalFrame_7.setBounds(0, 0, 439, 520);
				desktopPane.add(internalFrame_7);
				internalFrame_7.setVisible(true);

			}

		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		btnNewButton_1.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/premio.png")));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 128, 189));
		btnNewButton_1.setBounds(153, 0, 156, 46);
		contentPane.add(btnNewButton_1);

		if (usuario.getPerfil().trim().equalsIgnoreCase("administrador")) {

			JButton btnNewButton_1_1 = new JButton("Cadastrar Candidato");

			btnNewButton_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JInternalFrame internalFrame_3 = new JInternalFrame("");
					internalFrame_3.getContentPane().setBackground(new Color(0, 128, 192));
					internalFrame_3.setToolTipText("");
					internalFrame_3.setClosable(true);
					internalFrame_3.setBounds(0, 0, 439, 550);
					desktopPane.add(internalFrame_3);
					internalFrame_3.getContentPane().setLayout(null);

					name1 = new JTextField();
					name1.setBounds(79, 134, 270, 19);
					internalFrame_3.getContentPane().add(name1);
					name1.setColumns(10);

					JLabel lblNewLabel_2_1 = new JLabel("Cadastro");
					lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_2_1.setForeground(Color.WHITE);
					lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
					lblNewLabel_2_1.setBounds(159, 25, 123, 30);
					internalFrame_3.getContentPane().add(lblNewLabel_2_1);

					JLabel lblNewLabel_3 = new JLabel("New label");
					lblNewLabel_3.setIcon(
							new ImageIcon(tela_usuario.class.getResource("/imagens/politica-de-noticias.png")));
					lblNewLabel_3.setBounds(98, 14, 62, 64);
					internalFrame_3.getContentPane().add(lblNewLabel_3);

					JLabel lblNewLabel_4 = new JLabel("Nome");
					lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4.setForeground(new Color(255, 255, 255));
					lblNewLabel_4.setBounds(79, 113, 70, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4);

					regis = new JTextField();
					regis.setColumns(10);
					regis.setBounds(79, 185, 270, 19);
					internalFrame_3.getContentPane().add(regis);

					JLabel lblNewLabel_4_1_1 = new JLabel("Registro de eleitor");
					lblNewLabel_4_1_1.setForeground(Color.WHITE);
					lblNewLabel_4_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4_1_1.setBounds(79, 163, 160, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4_1_1);

					part = new JTextField();
					part.setColumns(10);
					part.setBounds(79, 238, 270, 19);
					internalFrame_3.getContentPane().add(part);

					num_partido = new JTextField();
					num_partido.setColumns(10);
					num_partido.setBounds(79, 294, 270, 19);
					internalFrame_3.getContentPane().add(num_partido);

					JLabel lblNewLabel_4_1_1_1 = new JLabel("Partido");
					lblNewLabel_4_1_1_1.setForeground(Color.WHITE);
					lblNewLabel_4_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4_1_1_1.setBounds(79, 214, 160, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4_1_1_1);

					JLabel lblNewLabel_4_1_1_2 = new JLabel("Eleição");
					lblNewLabel_4_1_1_2.setForeground(Color.WHITE);
					lblNewLabel_4_1_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4_1_1_2.setBounds(79, 323, 160, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4_1_1_2);

					senha1 = new JTextField();
					senha1.setColumns(10);
					senha1.setBounds(79, 404, 270, 19);
					internalFrame_3.getContentPane().add(senha1);

					JLabel lblNewLabel_4_1_1_2_1 = new JLabel("Senha");
					lblNewLabel_4_1_1_2_1.setForeground(Color.WHITE);
					lblNewLabel_4_1_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4_1_1_2_1.setBounds(79, 381, 160, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4_1_1_2_1);

					JButton btnNewButton_4 = new JButton("Cadastrar");
					btnNewButton_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
					btnNewButton_4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								Class.forName("org.sqlite.JDBC");
								Connection connection = DriverManager.getConnection(URL);
								String nome = name1.getText();
								String numPartido = num_partido.getText();
								String senha = senha1.getText();
								String partido = part.getText();
								String nomeEleicao = indiceSelecionado;

								// CONSULTA ID ELEIÇAO
								String sqlEleicao = "SELECT idEleicao FROM Eleicoes WHERE nomeEleicao = ?";
								PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao,
										Statement.RETURN_GENERATED_KEYS);
								stmt6.setString(1, nomeEleicao);
								ResultSet retorno = stmt6.executeQuery();
								if (retorno.next()) {
									idEleicao = retorno.getInt("idEleicao");
								}
								// INSERÇÃO DE CANDIDATO
								String sql4 = "INSERT INTO Candidatos (nome, partido, numPartido, idEleicao) "
										+ "VALUES (?, ?, ?, ?);";
								PreparedStatement stmt2 = connection.prepareStatement(sql4,
										Statement.RETURN_GENERATED_KEYS);
								stmt2.setString(1, nome);
								stmt2.setString(2, partido);
								stmt2.setString(3, numPartido);
								stmt2.setInt(4, idEleicao);
								stmt2.executeUpdate();

								ResultSet rs = stmt2.getGeneratedKeys();

								if (rs.next()) {

									String perfil = "candidato";
									String login = nome;
									String sql1 = "INSERT INTO Usuarios ( login, senha, perfil) " + "VALUES (?, ?, ?);";
									PreparedStatement stmt1 = connection.prepareStatement(sql1,
											Statement.RETURN_GENERATED_KEYS);
									stmt1.setString(1, login);
									stmt1.setString(2, senha);
									stmt1.setString(3, perfil);
									stmt1.executeUpdate();

									ResultSet rs2 = stmt1.getGeneratedKeys();

									if (rs2.next()) {
										System.out.println("cadastro realizado!");
										JOptionPane.showMessageDialog(null, "cadastro realizado!");
										name1.setText("");
										regis.setText("");
										senha1.setText("");
										part.setText("");
										num_partido.setText("");
									}

									connection.close();
								} else {
									JOptionPane.showMessageDialog(null, "Candidato nao foi salvo!");
								}
							} catch (ClassNotFoundException ex) {
								System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
							} catch (SQLException ex) {
								// ex.printStackTrace();
								JOptionPane.showMessageDialog(null, "Candidato já cadastrado!");
							}
						}
					});

					btnNewButton_4.setBackground(new Color(0, 128, 192));
					btnNewButton_4.setForeground(new Color(255, 255, 255));
					btnNewButton_4.setBounds(159, 451, 123, 30);
					internalFrame_3.getContentPane().add(btnNewButton_4);

					JLabel lblNewLabel_4_1_1_2_2 = new JLabel("Numero do Partido");
					lblNewLabel_4_1_1_2_2.setForeground(Color.WHITE);
					lblNewLabel_4_1_1_2_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_4_1_1_2_2.setBounds(79, 267, 175, 19);
					internalFrame_3.getContentPane().add(lblNewLabel_4_1_1_2_2);

					try {
						Class.forName("org.sqlite.JDBC");
						Connection connection = DriverManager.getConnection(URL);

						// CONSULTA NOME DA ELEIÇAO
						String sqlEleicao = "SELECT nomeEleicao FROM Eleicoes";
						PreparedStatement stmt6 = connection.prepareStatement(sqlEleicao,
								Statement.RETURN_GENERATED_KEYS);

						ResultSet retorno = stmt6.executeQuery();
						resultConsulta.clear();
						while (retorno.next()) {
							resultConsulta.add(retorno.getString("nomeEleicao"));
						}
						connection.close();
					} catch (Exception e7) {

					}

					JComboBox<String> comboBox = new JComboBox<>();
					for (String resultConsult : resultConsulta) {
						comboBox.addItem(resultConsult);
					}
					comboBox.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							indiceSelecionado = comboBox.getSelectedItem().toString();
							System.out.println("Valor selecionado: " + indiceSelecionado);

						}
					});

					comboBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
					comboBox.setBounds(79, 350, 270, 21);
					internalFrame_3.getContentPane().add(comboBox);
					internalFrame_3.setVisible(true);

					System.out.println("Indice selecionado");
					System.out.println(indiceSelecionado);

				}
			});

			btnNewButton_1_1.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/adicionar-usuario.png")));
			btnNewButton_1_1.setAlignmentY(Component.TOP_ALIGNMENT);
			btnNewButton_1_1.setToolTipText("Cadastrar candidatos");
			btnNewButton_1_1.setForeground(new Color(255, 255, 255));
			btnNewButton_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
			btnNewButton_1_1.setBackground(new Color(0, 128, 192));
			btnNewButton_1_1.setBounds(450, 0, 203, 46);
			contentPane.add(btnNewButton_1_1);
		}

		if (usuario.getPerfil().trim().equalsIgnoreCase("administrador")) {
			JButton btnNewButton_1_2 = new JButton("Gerenciar");
			btnNewButton_1_2.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/edit.png")));
			btnNewButton_1_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JInternalFrame internalFrame_5 = new JInternalFrame("");
					internalFrame_5.getContentPane().setForeground(new Color(255, 255, 255));
					internalFrame_5.setClosable(true);
					internalFrame_5.getContentPane().setBackground(new Color(0, 128, 192));
					internalFrame_5.setBounds(0, 0, 439, 550);
					desktopPane.add(internalFrame_5);
					internalFrame_5.getContentPane().setLayout(null);

					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBounds(10, 125, 407, 237);
					internalFrame_5.getContentPane().add(scrollPane);

					JComboBox comboBox_1 = new JComboBox();
					comboBox_1.setBackground(new Color(255, 255, 255));
					comboBox_1.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							itemSelecionado = comboBox_1.getSelectedItem().toString();

							if (itemSelecionado == "Usuarios") {

								table = new JTable();
								table.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "idUsuario", "login", "senha", "perfil" }));
								scrollPane.setViewportView(table);

							}
							if (itemSelecionado == "Candidatos") {
								table = new JTable();
								table.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "idCandidato", "nome", "partido", "numPartido", "idEleicao" }));
								scrollPane.setViewportView(table);
							}
							if (itemSelecionado == "Eleitores") {
								table = new JTable();
								table.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "idEleitor", "nome", "endereco", "numRegistroEleitor" }));
								scrollPane.setViewportView(table);
							}
							if (itemSelecionado == "Eleicoes") {
								table = new JTable();
								table.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "idEleicao", "nomeEleicao", "dataInicio", "dataFim" }));
								scrollPane.setViewportView(table);
							}

							if (itemSelecionado == "dadosExcluidos") {
								table = new JTable();
								table.setModel(new DefaultTableModel(new Object[][] {},
										new String[] { "idExclusao", "tabela", "id", "dados", "dataExclusao" }));
								scrollPane.setViewportView(table);
							}
						}
					});

					comboBox_1.setModel(new DefaultComboBoxModel(
							new String[] { "", "Usuarios", "Candidatos", "Eleitores", "Eleicoes", "dadosExcluidos" }));
					comboBox_1.setBounds(126, 99, 191, 21);
					internalFrame_5.getContentPane().add(comboBox_1);

					JButton btnNewButton_6 = new JButton("Listar");
					btnNewButton_6.setBackground(new Color(255, 255, 255));
					btnNewButton_6.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Statement st = null;

							DefaultTableModel tabela = (DefaultTableModel) table.getModel();
							tabela.setRowCount(0);

							try {
								Class.forName("org.sqlite.JDBC");
								Connection connection = DriverManager.getConnection(URL);

								if (itemSelecionado == "Usuarios") {
									String sql = "SELECT * FROM Usuarios";
									st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql);

									while (rs.next()) {
										int idUsuario = rs.getInt("idUsuario");
										String login = rs.getString("login");
										String senha = rs.getString("senha");
										String perfil = rs.getString("perfil");

										Object[] rowData = { idUsuario, login, senha, perfil };
										tabela.addRow(rowData);
									}
								}
								if (itemSelecionado == "Candidatos") {
									String sql = "SELECT * FROM Candidatos";
									st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql);

									while (rs.next()) {
										int idCandidato = rs.getInt("idCandidato");
										String nome = rs.getString("nome");
										String partido = rs.getString("partido");
										String numPartido = rs.getString("numPartido");
										String idEleicao = rs.getString("idEleicao");

										Object[] rowData = { idCandidato, nome, partido, numPartido, idEleicao };
										tabela.addRow(rowData);
									}
								}
								if (itemSelecionado == "Eleicoes") {
									String sql = "SELECT * FROM Eleicoes";
									st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql);

									while (rs.next()) {
										int idEleicao = rs.getInt("idEleicao");
										String nomeEleicao = rs.getString("nomeEleicao");
										String dataInicio = rs.getString("dataInicio");
										String dataFim = rs.getString("dataFim");

										Object[] rowData = { idEleicao, nomeEleicao, dataInicio, dataFim };
										tabela.addRow(rowData);
									}
								}
								if (itemSelecionado == "Eleitores") {
									String sql = "SELECT * FROM Eleitores";
									st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql);

									while (rs.next()) {
										int idEleitor = rs.getInt("idEleitor");
										String nome = rs.getString("nome");
										String endereco = rs.getString("endereco");
										String numRegistroEleitor = rs.getString("numRegistroEleitor");

										Object[] rowData = { idEleitor, nome, endereco, numRegistroEleitor };
										tabela.addRow(rowData);
									}
								}
								if (itemSelecionado == "dadosExcluidos") {
									String sql = "SELECT * FROM dadosExcluidos";
									st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql);

									while (rs.next()) {
										int idExclusao = rs.getInt("idExclusao");
										String tabelaExclusao = rs.getString("tabelaExclusao");
										String id = rs.getString("id");
										String dados = rs.getString("dados");
										String dataExclusao = rs.getString("dataExclusao");

										Object[] rowData = { idExclusao, tabelaExclusao, id, dados, dataExclusao };
										tabela.addRow(rowData);
									}
								}

								connection.close();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
					btnNewButton_6.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
					btnNewButton_6.setBounds(319, 98, 85, 21);
					internalFrame_5.getContentPane().add(btnNewButton_6);

					JLabel lblNewLabel_7 = new JLabel("Gerenciar tabela:");
					lblNewLabel_7.setForeground(new Color(255, 255, 255));
					lblNewLabel_7.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
					lblNewLabel_7.setBounds(10, 98, 122, 17);
					internalFrame_5.getContentPane().add(lblNewLabel_7);

					JButton excluir = new JButton("Excluir");
					excluir.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
					excluir.setBounds(126, 373, 85, 21);
					internalFrame_5.getContentPane().add(excluir);
					excluir.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							Connection connection = null;
							int row = table.getSelectedRow();

							try {

								int id = Integer.parseInt(table.getValueAt(row, 0).toString());

								Class.forName("org.sqlite.JDBC");

								connection = DriverManager.getConnection(URL);

								if (itemSelecionado == "Usuarios") {
									String sql = "DELETE FROM Usuarios WHERE idUsuario = ?";

									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setInt(1, id);
									statement.executeUpdate();

									((DefaultTableModel) table.getModel()).removeRow(row);
								}
								if (itemSelecionado == "Candidatos") {
									String sql = "DELETE FROM Candidatos WHERE idCandidato = ?";

									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setInt(1, id);
									statement.executeUpdate();

									((DefaultTableModel) table.getModel()).removeRow(row);
								}
								if (itemSelecionado == "Eleicoes") {
									String sql = "DELETE FROM Eleicoes WHERE idEleicao = ?";

									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setInt(1, id);
									statement.executeUpdate();

									((DefaultTableModel) table.getModel()).removeRow(row);
								}
								if (itemSelecionado == "Eleitores") {
									String sql = "DELETE FROM Eleitores WHERE idEleitor = ?";

									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setInt(1, id);
									statement.executeUpdate();

									((DefaultTableModel) table.getModel()).removeRow(row);
								}

								System.out.println("Excluído com sucesso!");

								JOptionPane.showMessageDialog(null, "Excluido com sucesso");
								connection.close();
							} catch (ClassNotFoundException e1) {
								System.err.println("Não foi possível encontrar o driver JDBC: " + e1.getMessage());
							} catch (SQLException e1) {
								System.err.println("Erro ao conectar ao banco de dados: " + e1.getMessage());
							} finally {
								try {
									if (connection != null) {
										connection.close();
									}
								} catch (SQLException e1) {
									System.err.println(
											"Erro ao fechar a conexão com o banco de dados: " + e1.getMessage());
								}
							}
						}
					});

					JButton btnNewButton_6_2 = new JButton("Editar");
					btnNewButton_6_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							DefaultTableModel tabela = (DefaultTableModel) table.getModel();
							int row = table.getSelectedRow();

							if (itemSelecionado == "Usuarios") {
								int idUsuario = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								String login = (tabela.getValueAt(row, 1).toString());
								String senha = (tabela.getValueAt(row, 2).toString());
								String perfil = (tabela.getValueAt(row, 3).toString());
								try {
									Class.forName("org.sqlite.JDBC");
									Connection connection = DriverManager.getConnection(URL);

									String sql = "UPDATE Usuarios SET login = ?, senha = ?, perfil = ? WHERE idUsuario = ?";
									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setString(1, login);
									statement.setString(2, senha);
									statement.setString(3, perfil);
									statement.setInt(4, idUsuario);

									int rowsUpdated = statement.executeUpdate();

									if (rowsUpdated > 0) {
										System.out.println("Editado com sucesso!");
										JOptionPane.showMessageDialog(null, "Editado com sucesso");
									} else {
										System.out.println("Registro não encontrado.");
									}
									connection.close();
								} catch (ClassNotFoundException e1) {
									System.err.println("Não foi possível encontrar o driver JDBC: " + e1.getMessage());
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							if (itemSelecionado == "Candidatos") {
								int idCandidato = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								String nome = (tabela.getValueAt(row, 1).toString());
								String partido = (tabela.getValueAt(row, 2).toString());
								String numPartido = (tabela.getValueAt(row, 3).toString());
								int idEleicao = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								try {
									Class.forName("org.sqlite.JDBC");
									Connection connection = DriverManager.getConnection(URL);

									String sql = "UPDATE Candidatos SET nome = ?, partido = ?, numPartido = ?, idEleicao = ? WHERE idCandidato = ?";
									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setString(1, nome);
									statement.setString(2, partido);
									statement.setString(3, numPartido);
									statement.setInt(4, idCandidato);
									statement.setInt(5, idEleicao);

									int rowsUpdated = statement.executeUpdate();

									if (rowsUpdated > 0) {
										System.out.println("Editado com sucesso!");
										JOptionPane.showMessageDialog(null, "Editado com sucesso");
									} else {
										System.out.println("Registro não encontrado.");
									}
									connection.close();
								} catch (ClassNotFoundException e1) {
									System.err.println("Não foi possível encontrar o driver JDBC: " + e1.getMessage());
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							if (itemSelecionado == "Eleitores") {
								int idEleitor = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								String nome = (tabela.getValueAt(row, 1).toString());
								String endereco = (tabela.getValueAt(row, 2).toString());
								String numRegistroEleitor = (tabela.getValueAt(row, 3).toString());
								int idEleicao = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								try {
									Class.forName("org.sqlite.JDBC");
									Connection connection = DriverManager.getConnection(URL);

									String sql = "UPDATE Eleitores SET nome = ?, endereco = ?, numRegistroEleitor = ?, idEleicao = ? WHERE idEleitor = ?";
									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setString(1, nome);
									statement.setString(2, endereco);
									statement.setString(3, numRegistroEleitor);
									statement.setInt(4, idEleitor);
									statement.setInt(5, idEleicao);

									int rowsUpdated = statement.executeUpdate();

									if (rowsUpdated > 0) {
										System.out.println("Editado com sucesso!");
										JOptionPane.showMessageDialog(null, "Editado com sucesso");
									} else {
										System.out.println("Registro não encontrado.");
									}
									connection.close();
								} catch (ClassNotFoundException e1) {
									System.err.println("Não foi possível encontrar o driver JDBC: " + e1.getMessage());
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							if (itemSelecionado == "Eleicoes") {
								int idEleicao = Integer.parseInt(tabela.getValueAt(row, 0).toString());
								String nomeEleicao = (tabela.getValueAt(row, 1).toString());
								String dataInicio = (tabela.getValueAt(row, 2).toString());
								String dataFim = (tabela.getValueAt(row, 3).toString());
								try {
									Class.forName("org.sqlite.JDBC");
									Connection connection = DriverManager.getConnection(URL);

									String sql = "UPDATE Eleicoes SET nomeEleicao = ?, dataInicio = ?, dataFim = ? WHERE idEleicao = ?";
									PreparedStatement statement = connection.prepareStatement(sql);

									statement.setString(1, nomeEleicao);
									statement.setString(2, dataInicio);
									statement.setString(3, dataFim);
									statement.setInt(4, idEleicao);

									int rowsUpdated = statement.executeUpdate();

									if (rowsUpdated > 0) {
										System.out.println("Editado com sucesso!");
										JOptionPane.showMessageDialog(null, "Editado com sucesso");
									} else {
										System.out.println("Registro não encontrado.");
									}
									connection.close();
								} catch (ClassNotFoundException e1) {
									System.err.println("Não foi possível encontrar o driver JDBC: " + e1.getMessage());
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

						}
					});

					btnNewButton_6_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
					btnNewButton_6_2.setBounds(27, 373, 85, 21);
					internalFrame_5.getContentPane().add(btnNewButton_6_2);

					JLabel lblNewLabel_2_3 = new JLabel("Gerenciamento");
					lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_2_3.setForeground(Color.WHITE);
					lblNewLabel_2_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 27));
					lblNewLabel_2_3.setBounds(118, 34, 191, 30);
					internalFrame_5.getContentPane().add(lblNewLabel_2_3);

					JLabel lblNewLabel_8 = new JLabel("");
					lblNewLabel_8
							.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/gerenciamento-web.png")));
					lblNewLabel_8.setBounds(244, 371, 145, 140);
					internalFrame_5.getContentPane().add(lblNewLabel_8);
					internalFrame_5.setVisible(true);
				}

			});

			btnNewButton_1_2.setToolTipText(" Gerenciamento de dados");
			btnNewButton_1_2.setHorizontalAlignment(SwingConstants.LEADING);
			btnNewButton_1_2.setForeground(Color.WHITE);
			btnNewButton_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
			btnNewButton_1_2.setBackground(new Color(0, 128, 189));
			btnNewButton_1_2.setBounds(822, 0, 143, 46);
			contentPane.add(btnNewButton_1_2);
		}
		if (usuario.getPerfil().trim().equalsIgnoreCase("administrador")) {
			JButton btnNewButton_1_1 = new JButton("Cadastrar Eleição");
			btnNewButton_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JInternalFrame internalFrame_4 = new JInternalFrame("");
					internalFrame_4.setClosable(true);
					internalFrame_4.getContentPane().setBackground(new Color(0, 128, 192));
					internalFrame_4.setBounds(0, 0, 439, 396);
					desktopPane.add(internalFrame_4);
					internalFrame_4.getContentPane().setLayout(null);

					JLabel lblNewLabel_5 = new JLabel("");
					lblNewLabel_5.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/politica (1).png")));
					lblNewLabel_5.setBounds(10, 238, 128, 119);
					internalFrame_4.getContentPane().add(lblNewLabel_5);

					nameEleicao = new JTextField();
					nameEleicao.setBounds(56, 95, 313, 19);
					internalFrame_4.getContentPane().add(nameEleicao);
					nameEleicao.setColumns(10);

					JLabel lblNewLabel_6 = new JLabel(" Nome da Eleição");
					lblNewLabel_6.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
					lblNewLabel_6.setForeground(new Color(255, 255, 255));
					lblNewLabel_6.setBounds(56, 68, 134, 25);
					internalFrame_4.getContentPane().add(lblNewLabel_6);

					data_inicio = new JTextField();
					data_inicio.setColumns(10);
					data_inicio.setBounds(56, 151, 313, 19);
					internalFrame_4.getContentPane().add(data_inicio);

					JLabel lblNewLabel_6_1 = new JLabel("Data de início");
					lblNewLabel_6_1.setForeground(Color.WHITE);
					lblNewLabel_6_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
					lblNewLabel_6_1.setBounds(56, 124, 134, 25);
					internalFrame_4.getContentPane().add(lblNewLabel_6_1);

					JLabel lblNewLabel_2_2 = new JLabel("Cadastro de eleição");
					lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_2_2.setForeground(Color.WHITE);
					lblNewLabel_2_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
					lblNewLabel_2_2.setBounds(89, 28, 234, 30);
					internalFrame_4.getContentPane().add(lblNewLabel_2_2);

					data_fim = new JTextField();
					data_fim.setColumns(10);
					data_fim.setBounds(56, 209, 313, 19);
					internalFrame_4.getContentPane().add(data_fim);

					JLabel lblNewLabel_6_1_1 = new JLabel("Data de encerramento");
					lblNewLabel_6_1_1.setForeground(Color.WHITE);
					lblNewLabel_6_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
					lblNewLabel_6_1_1.setBounds(56, 184, 149, 25);
					internalFrame_4.getContentPane().add(lblNewLabel_6_1_1);

					JButton btnNewButton_5 = new JButton("Cadastrar");
					btnNewButton_5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								Class.forName("org.sqlite.JDBC");
								Connection connection = DriverManager.getConnection(URL);
								System.out.println(connection);
								String nomeEleicao = nameEleicao.getText();
								String dataInicio = data_inicio.getText();
								String dataFim = data_fim.getText();

								String sql = "INSERT INTO Eleicoes (nomeEleicao, dataInicio, dataFim) "
										+ "VALUES (?, ?, ?);";
								PreparedStatement stmt = connection.prepareStatement(sql,
										Statement.RETURN_GENERATED_KEYS);
								stmt.setString(1, nomeEleicao);
								stmt.setString(2, dataInicio);
								stmt.setString(3, dataFim);
								stmt.executeUpdate();

								ResultSet rs = stmt.getGeneratedKeys();

								System.out.println("cadastro realizado!");
								JOptionPane.showMessageDialog(null, "cadastro realizado!");
								nameEleicao.setText("");
								data_inicio.setText("");
								data_fim.setText("");

								connection.close();
							} catch (ClassNotFoundException ex) {
								System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
							} catch (SQLException ex) {
								// ex.printStackTrace();
								JOptionPane.showMessageDialog(null, "Eleição já existente!");
							}
						}
					});
					btnNewButton_5.setForeground(Color.WHITE);
					btnNewButton_5.setBackground(new Color(0, 128, 192));
					btnNewButton_5.setBounds(160, 277, 111, 25);
					internalFrame_4.getContentPane().add(btnNewButton_5);
					internalFrame_4.setVisible(true);
				}
			});
			btnNewButton_1_1.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/note.png")));
			btnNewButton_1_1.setToolTipText("cadastrar nova eleição");
			btnNewButton_1_1.setHorizontalAlignment(SwingConstants.LEADING);
			btnNewButton_1_1.setForeground(Color.WHITE);
			btnNewButton_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
			btnNewButton_1_1.setBackground(new Color(0, 128, 189));
			btnNewButton_1_1.setBounds(649, 0, 176, 46);
			contentPane.add(btnNewButton_1_1);
		}

		if (usuario.getPerfil().trim().equalsIgnoreCase("administrador")) {

			JButton btnNewButton_2 = new JButton("Cadastro");
			btnNewButton_2.setToolTipText("Cadastro eleitor\\adm");
			btnNewButton_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
			btnNewButton_2.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/medico.png")));
			btnNewButton_2.setHorizontalAlignment(SwingConstants.LEADING);

			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JInternalFrame internalFrame_2 = new JInternalFrame("");
					internalFrame_2.setClosable(true);
					internalFrame_2.getContentPane().setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					internalFrame_2.getContentPane().setBackground(new Color(0, 128, 192));
					internalFrame_2.setBounds(0, 0, 439, 550);
					desktopPane.add(internalFrame_2);
					internalFrame_2.getContentPane().setLayout(null);

					JLabel lblNewLabel_2 = new JLabel("Cadastro");
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_2.setForeground(Color.WHITE);
					lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
					lblNewLabel_2.setBounds(160, 24, 123, 30);
					internalFrame_2.getContentPane().add(lblNewLabel_2);

					JLabel lblNewLabel = new JLabel("");
					lblNewLabel.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/cara.png")));
					lblNewLabel.setBounds(93, 0, 64, 64);
					internalFrame_2.getContentPane().add(lblNewLabel);

					name = new JTextField();
					name.setBounds(57, 117, 308, 19);
					internalFrame_2.getContentPane().add(name);
					name.setColumns(10);

					JLabel lblNewLabel_1 = new JLabel("Nome");
					lblNewLabel_1.setForeground(Color.WHITE);
					lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblNewLabel_1.setBounds(57, 92, 77, 19);
					internalFrame_2.getContentPane().add(lblNewLabel_1);

					end = new JTextField();
					end.setColumns(10);
					end.setBounds(57, 173, 308, 19);
					internalFrame_2.getContentPane().add(end);

					JLabel lblEndereo = new JLabel("Endereço");
					lblEndereo.setForeground(Color.WHITE);
					lblEndereo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblEndereo.setBounds(57, 149, 123, 19);
					internalFrame_2.getContentPane().add(lblEndereo);

					registro = new JTextField();
					registro.setColumns(10);
					registro.setBounds(57, 224, 308, 19);
					internalFrame_2.getContentPane().add(registro);

					JLabel lblRegistroDeEleito = new JLabel("Registro de eleitor");
					lblRegistroDeEleito.setForeground(Color.WHITE);
					lblRegistroDeEleito.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblRegistroDeEleito.setBounds(57, 202, 162, 19);
					internalFrame_2.getContentPane().add(lblRegistroDeEleito);

					passw = new JTextField();
					passw.setColumns(10);
					passw.setBounds(57, 274, 308, 19);
					internalFrame_2.getContentPane().add(passw);

					JLabel lblSenha = new JLabel("Senha");
					lblSenha.setForeground(Color.WHITE);
					lblSenha.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					lblSenha.setBounds(57, 253, 77, 19);
					internalFrame_2.getContentPane().add(lblSenha);

					ButtonGroup grupoOpcoes = new ButtonGroup();

					JRadioButton eleitor = new JRadioButton("Eleitor");
					eleitor.setForeground(new Color(255, 255, 255));
					eleitor.setBackground(new Color(0, 128, 192));
					eleitor.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					eleitor.setBounds(75, 335, 103, 21);
					internalFrame_2.getContentPane().add(eleitor);
					grupoOpcoes.add(eleitor);

					JRadioButton adm = new JRadioButton("Administrador");
					adm.setForeground(new Color(255, 255, 255));
					adm.setBackground(new Color(0, 128, 192));
					adm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
					adm.setBounds(224, 335, 180, 21);
					internalFrame_2.getContentPane().add(adm);
					grupoOpcoes.add(adm);

					JButton btnNewButton_3 = new JButton("Cadastrar");
					btnNewButton_3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								Class.forName("org.sqlite.JDBC");
								Connection connection = DriverManager.getConnection(URL);
								System.out.println(connection);

								// conexão com o mongoDB
								MongoClient mongoClient = MongoClients
										.create("mongodb://localhost:27017/sistema_de_eleicao");
								MongoDatabase mongoDatabase = mongoClient.getDatabase("sistema_de_eleicao");
								MongoCollection<Document> mongoCollection = mongoDatabase
										.getCollection("cadastro_eleitor/adm");

								String nome = name.getText();
								String endereco = end.getText();
								String numRegistroEleitor = registro.getText();
								String senha = passw.getText();

								String perfil = "";
								if (adm.isSelected()) {
									perfil = "administrador";

									String login = nome;
									String sql1 = "INSERT INTO Usuarios (login, senha, perfil) VALUES (?, ?, ?);";
									PreparedStatement stmt1 = connection.prepareStatement(sql1,
											Statement.RETURN_GENERATED_KEYS);
									stmt1.setString(1, login);
									stmt1.setString(2, senha);
									stmt1.setString(3, perfil);
									stmt1.executeUpdate();

									ResultSet rs1 = stmt1.getGeneratedKeys();
									JOptionPane.showMessageDialog(null, "Cadastro realizado!");
								}

								if (eleitor.isSelected()) {
									perfil = "eleitor";

									String sql = "INSERT INTO Eleitores (nome, endereco, numRegistroEleitor) VALUES (?, ?, ?);";
									PreparedStatement stmt = connection.prepareStatement(sql,
											Statement.RETURN_GENERATED_KEYS);
									stmt.setString(1, nome);
									stmt.setString(2, endereco);
									stmt.setString(3, numRegistroEleitor);
									stmt.executeUpdate();

									ResultSet rs = stmt.getGeneratedKeys();

									if (rs.next()) {
										String login = nome;
										String sql1 = "INSERT INTO Usuarios (login, senha, perfil) VALUES (?, ?, ?);";
										PreparedStatement stmt1 = connection.prepareStatement(sql1,
												Statement.RETURN_GENERATED_KEYS);
										stmt1.setString(1, login);
										stmt1.setString(2, senha);
										stmt1.setString(3, perfil);
										stmt1.executeUpdate();

										ResultSet rs1 = stmt1.getGeneratedKeys();
									}

									JOptionPane.showMessageDialog(null, "Cadastro realizado!");
								}

								// Inserir dados no mongoDB
								Document document = new Document();
								document.append("nome", nome).append("endereco", endereco)
										.append("numRegistroEleitor", numRegistroEleitor).append("senha", senha)
										.append("perfil", perfil);

								mongoCollection.insertOne(document);

								connection.close();
								mongoClient.close();
								
							} catch (ClassNotFoundException ex) {
								System.err.println("Não foi possível encontrar o driver JDBC: " + ex.getMessage());
							} catch (SQLException ex) {
								JOptionPane.showMessageDialog(null, "Usuário já cadastrado");
							}
						}
					});
					btnNewButton_3.setBackground(new Color(0, 128, 192));
					btnNewButton_3.setForeground(new Color(255, 255, 255));
					btnNewButton_3.setBounds(135, 412, 148, 30);
					internalFrame_2.getContentPane().add(btnNewButton_3);
					internalFrame_2.setVisible(true);

					JButton btnNewButton = new JButton("  Votar");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
							internalFrame.getContentPane().setBackground(new Color(0, 128, 192));
							internalFrame.getContentPane().setLayout(null);
							internalFrame.setClosable(true);
							internalFrame.setBounds(0, 0, 439, 550);
							desktopPane.add(internalFrame);
							internalFrame.setVisible(true);

						}
					});

					btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
					btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
					btnNewButton.setIcon(new ImageIcon(tela_usuario.class.getResource("/imagens/voto.png")));
					btnNewButton.setForeground(new Color(255, 255, 255));
					btnNewButton.setBackground(new Color(0, 128, 189));
					btnNewButton.setBounds(0, 0, 156, 46);
					contentPane.add(btnNewButton);
				}
			});

			btnNewButton_2.setForeground(Color.WHITE);
			btnNewButton_2.setBackground(new Color(0, 128, 189));
			btnNewButton_2.setBounds(308, 0, 156, 46);
			contentPane.add(btnNewButton_2);

		}

	}
}
