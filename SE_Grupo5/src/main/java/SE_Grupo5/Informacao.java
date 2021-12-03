package SE_Grupo5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kohsuke.github.GitHub;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;

public class Informacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924931067800093425L;
	private JPanel contentPane = new JPanel();
	private Trello trelloApi;
	private String trelloUtilizador;

	private GitHub gitHubApi;
	private List<SprintHoursInformation> sHours = new ArrayList<SprintHoursInformation>();
	private JTextField txtNovoCustohora = new JTextField();;
	private JTable tabelaHoras;
	private JTable tabelaCusto;
	private double custoHora = 20;

	/**
	 * Create the frame. <<<<<<< HEAD
	 * 
	 * @param trelloApi        Representa a conexão ao trello
	 * @param trelloUtilizador Representa o user no trello do Utilizador <<<<<<<
	 *                         HEAD
	 * @param gitHubApi        Representa a conexão ao GitHub =======
	 * @param gitHubApi        Representa a conexão ao GitHub =======
	 * @throws IOException >>>>>>> refs/heads/main2 >>>>>>> branch 'main' of
	 *                     https://github.com/rmrss11-iscte/ES-LETI-1Sem-2021-Grupo5.git
	 */

	public Informacao(Trello trelloApi, String trelloUtilizador, GitHub gitHubApi) throws IOException {

		this.trelloApi = trelloApi;
		this.trelloUtilizador = trelloUtilizador;
		this.gitHubApi = gitHubApi;

		getProjectTime();
		getActivitiesHoursCost();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 952, 751);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel membersLabel = new JLabel("Membros do projeto:");
		membersLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		membersLabel.setBounds(10, 20, 200, 43);
		contentPane.add(membersLabel);

		JTextArea membersDisplay = new JTextArea(getMembers());
		membersLabel.setLabelFor(membersDisplay);
		membersDisplay.setEditable(false);
		membersDisplay.setBounds(20, 65, 120, 90);
		contentPane.add(membersDisplay);

		JLabel dataLabel = new JLabel("Data de Inicio:");
		dataLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		dataLabel.setBounds(10, 160, 160, 30);
		contentPane.add(dataLabel);

		JTextArea dataDisplay = new JTextArea(getDate());
		dataLabel.setLabelFor(dataDisplay);
		dataDisplay.setEditable(false);
		dataDisplay.setBounds(20, 190, 200, 20);
		contentPane.add(dataDisplay);

		JLabel sprintsdurationLabel = new JLabel("Duração de cada Sprint:");
		sprintsdurationLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		sprintsdurationLabel.setBounds(10, 225, 300, 53);
		contentPane.add(sprintsdurationLabel);

		JTextArea sprintsdurationDisplay = new JTextArea(getSprintsDuration());
		sprintsdurationLabel.setLabelFor(sprintsdurationDisplay);
		sprintsdurationDisplay.setEditable(false);
		sprintsdurationDisplay.setBounds(20, 270, 253, 100);
		contentPane.add(sprintsdurationDisplay);

		JLabel productBacklogLabel = new JLabel("Items do ProductBacklog:");
		productBacklogLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		productBacklogLabel.setBounds(10, 409, 263, 28);
		contentPane.add(productBacklogLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(20, 437, 274, 179);
		contentPane.add(scrollPane);

		JTextArea productBacklogDisplay = new JTextArea(getProductBacklog());
		scrollPane.setViewportView(productBacklogDisplay);
		productBacklogLabel.setLabelFor(productBacklogDisplay);

		JScrollPane scrollPane_custo = new JScrollPane();
		scrollPane_custo.setBounds(444, 362, 445, 155);
		contentPane.add(scrollPane_custo);
		tabelaCusto = criarTabela(sHours, 20);
		scrollPane_custo.setViewportView(tabelaCusto);

		JLabel lblCustoHora = new JLabel("Custo-Hora =             €");
		lblCustoHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustoHora.setBounds(587, 538, 203, 24);
		contentPane.add(lblCustoHora);
		txtNovoCustohora.setHorizontalAlignment(SwingConstants.CENTER);
		txtNovoCustohora.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// txt Novo Custo-Hora
		txtNovoCustohora.setText("20");
		txtNovoCustohora.setBounds(699, 538, 67, 24);
		contentPane.add(txtNovoCustohora);
		txtNovoCustohora.setColumns(10);

		JButton buttonApplyNovoCustoHora = new JButton("Apply");
		buttonApplyNovoCustoHora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setNovoCustoHora(Double.parseDouble(txtNovoCustohora.getText()));
			}
		});
		buttonApplyNovoCustoHora.setBounds(800, 538, 89, 24);
		contentPane.add(buttonApplyNovoCustoHora);

		JScrollPane scrollPane_Horas = new JScrollPane();
		scrollPane_Horas.setBounds(444, 160, 445, 155);
		contentPane.add(scrollPane_Horas);
		tabelaHoras = CriarTabela(sHours);
		scrollPane_Horas.setViewportView(tabelaHoras);

		JButton btnObterGraficoHoras = new JButton("Obter Gráficos das Horas de Trabalho");
		btnObterGraficoHoras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Horas de trabalho", "Membro da equipe", "Horas", sHours);
			}
		});
		btnObterGraficoHoras.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoHoras.setBounds(615, 573, 274, 43);
		contentPane.add(btnObterGraficoHoras);

		JButton btnObterGraficoCustos = new JButton("Obter Gráficos dos Custos do Trabalho");
		btnObterGraficoCustos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GraficoBarra("Custos do Projeto", "Membro da equipe", "Euros", sHours, custoHora);
			}
		});
		btnObterGraficoCustos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObterGraficoCustos.setBounds(615, 627, 274, 43);
		contentPane.add(btnObterGraficoCustos);

		JLabel lblTabelaDeHoras = new JLabel("Tabela de horas previstas/usadas");
		lblTabelaDeHoras.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeHoras.setBounds(412, 119, 324, 43);
		contentPane.add(lblTabelaDeHoras);

		JLabel lblTabelaDeCustos = new JLabel("Tabela de custos");
		lblTabelaDeCustos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTabelaDeCustos.setBounds(412, 326, 305, 43);
		contentPane.add(lblTabelaDeCustos);

		JTextArea textDate = new JTextArea(getDate());
		textDate.setBounds(20, 338, 211, 49);
		contentPane.add(textDate);

		JLabel nameDisplay = new JLabel(getNameofProject());
		nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		nameDisplay.setFont(new Font("Felix Titling", Font.BOLD, 25));
		nameDisplay.setBounds(221, 11, 575, 53);
		contentPane.add(nameDisplay);

	}

	private String getNameofProject() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		Organization organization = trelloApi.getBoardOrganization(boards.get(0).getId());
		String name = organization.getDisplayName();
		/*
		 * List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId()); Card
		 * projectcard = trelloApi.getBoardCard(boards.get(0).getId(),
		 * cards.get(0).getId());
		 * 
		 * String name = projectcard.getName();
		 */
		return name;
	}

	/**
	 * Se o parametro por diferente do valor a se pagar por hora (custoHora) então
	 * Altera o valor a se pagar por hora e atualiza a tabela "tabelaCusto" com os
	 * novos custos
	 * 
	 * @param novoCustoHora Representa o novo valor a se pagar por Hora
	 */
	private void setNovoCustoHora(double novoCustoHora) {
		if (novoCustoHora == custoHora)
			return;
		int row = 0;
		while (row < tabelaCusto.getRowCount()) {
			double newValue = ((Number) tabelaCusto.getValueAt(row, 2)).doubleValue() * (novoCustoHora / custoHora);
			tabelaCusto.setValueAt(newValue, row, 2);
			row++;
		}
		custoHora = novoCustoHora;
	}

	/**
	 * Dá return de uma String que contém todos os membros do projeto separados por
	 * \n
	 * 
	 * @return String
	 */
	private String getMembers() {

		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Member> members = trelloApi.getBoardMembers(boards.get(0).getId());
		String membersList = "";
		for (Member m : members) {
			membersList = membersList + m.getFullName() + "\n";
		}
		return membersList;
	}

	/**
	 * Este método dá return da data de fim do projeto
	 * 
	 * @return String
	 */
	private String getDate() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<Card> cards = trelloApi.getBoardCards(boards.get(0).getId());
		Card projectCard = trelloApi.getBoardCard(boards.get(0).getId(), cards.get(0).getId());
		Date dataInicio = projectCard.getDue();
		return dataInicio.toString();
	}

	private String getSprintsDuration() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		List<TList> lists = trelloApi.getBoardLists(boards.get(0).getId());
		List<Card> listCards = trelloApi.getListCards(lists.get(1).getId());
		String sprintsduration = "";
		for (Card c : listCards) {
			sprintsduration += c.getName() + ": " + c.getDue() + "\n";
		}
		return sprintsduration;
	}

	/**
	 * Este método dá return de uma String o Product Backlog
	 * 
	 * @return String
	 */
	private String getProductBacklog() {
		String productBacklogList = "";
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			productBacklogList += b.getName() + ":" + "\n";
			List<TList> lists = trelloApi.getBoardLists(b.getId());
			for (TList l : lists) {
				if (!l.getName().contains("Planning") && !l.getName().contains("Review")
						&& !l.getName().contains("Retrospective") && !l.getName().contains("Scrum")
						&& !l.getName().contains("Project") && !l.getName().contains("Sprints")
						&& !l.getName().contains("Product Backlog")) {
					List<Card> listCards = trelloApi.getListCards(l.getId());
					for (Card c : listCards) {
						productBacklogList += "-" + c.getName() + "\n";
					}
				}
			}
		}
		return productBacklogList;
	}

	/**
	 * Vai ao trello e obtém as horas estimadas e utilizadas de cada membro por
	 * sprint Armazenando-as numa lista de SprintHours
	 */
	private void getProjectTime() {
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			String boardName = b.getName();
			int control1 = 0;
			while (control1 < sHours.size()) {
				if (sHours.get(control1).getSprint().equals(boardName)) {
					break;
				}
				control1++;
			}
			if (sHours.size() == control1) {
				sHours.add(new SprintHoursInformation(boardName));
			}
			List<TList> lists = trelloApi.getBoardLists(b.getId());
			for (TList l : lists) {
				List<Card> cards = trelloApi.getListCards(l.getId());
				for (Card c : cards) {
					List<Action> actions = trelloApi.getCardActions(c.getId());
					for (Action a : actions) {
						if (a.getType().matches("commentCard")) {
							if (a.getData().getText().contains("plus!")) {
								String[] dataText = a.getData().getText().split(" ");

								if (dataText[1].contains("@")) {
									String[] user = dataText[1].split("@");
									String[] doubles = dataText[2].split("/");
									int control = 0;
									while (control < sHours.get(control1).getMemberHoursInformationList().size()) {
										if (sHours.get(control1).getMemberHoursInformationList().get(control).getUser()
												.equals(user[1])) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getMemberHoursInformationList().size() == control) {
										sHours.get(control1).getMemberHoursInformationList()
												.add(new MemberHoursInformation(user[1]));
									}
									sHours.get(control1).getMemberHoursInformationList().get(control)
											.addTime(Double.parseDouble(doubles[0]), Double.parseDouble(doubles[1]));
								} else {
									String user = trelloApi.getActionMemberCreator(a.getId()).getUsername();
									String[] doubles = dataText[1].split("/");

									int control = 0;
									while (control < sHours.get(control1).getMemberHoursInformationList().size()) {
										if (sHours.get(control1).getMemberHoursInformationList().get(control).getUser()
												.equals(user)) {
											break;
										}
										control++;
									}
									if (sHours.get(control1).getMemberHoursInformationList().size() == control) {
										sHours.get(control1).getMemberHoursInformationList()
												.add(new MemberHoursInformation(user));
									}
									sHours.get(control1).getMemberHoursInformationList().get(control)
											.addTime(Double.parseDouble(doubles[0]), Double.parseDouble(doubles[1]));
								}
							}
						}
					}
				}
			}
		}
	}

	private String getActivitiesHoursCost() {
		List<Card> lista = new ArrayList<Card>();
		List<String[]> listauserartifactos = new ArrayList<String[]>();
		List<Board> boards = trelloApi.getMemberBoards(trelloUtilizador);
		for (Board b : boards) {
			List<Card> cards = b.fetchCards();
			for (Card c : cards) {
				List<Label> labels = c.getLabels();
				for (Label l : labels) {
					if (l.getColor().equals("purple")) {
						lista.add(c);
						List<Member> listamembros = trelloApi.getCardMembers(c.getId());
						for (Member m : listamembros) {
							int control = 0;
							while (control < listauserartifactos.size()) {
								if (listauserartifactos.get(control)[0].equals(m.getUsername())) {
									int tempo = Integer.parseInt(listauserartifactos.get(control)[1]);
									tempo++;
									listauserartifactos.get(control)[1] = String.valueOf(tempo);
									break;
								}
								control++;
							}
							if (control == listauserartifactos.size()) {
								String[] vetor = { m.getUsername(), "1" };
								listauserartifactos.add(vetor);
							}

						}

					}
				}
			}
		}

		List<MemberHoursInformation> listamemberhours = new ArrayList<MemberHoursInformation>();
		for (Card card : lista) {
			List<Action> listaacoes = trelloApi.getCardActions(card.getId());
			for (Action a : listaacoes) {
				if (a.getType().matches("commentCard")) {
					if (a.getData().getText().contains("plus!")) {
						String[] dataText = a.getData().getText().split(" ");
						if (dataText[1].contains("@")) {
							String[] user = dataText[1].split("@");
							String[] doubles = dataText[2].split("/");
							int control = 0;
							while (control < listamemberhours.size()) {
								if (listamemberhours.get(control).getUser().equals(user[1])) {
									break;

								}
								control++;
							}
							if (listamemberhours.size() == control) {

								listamemberhours.add(new MemberHoursInformation(user[1]));
							}
							listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
									Double.parseDouble(doubles[1]));
						} else {
							String user = trelloApi.getActionMemberCreator(a.getId()).getUsername();
							String[] doubles = dataText[1].split("/");
							int control = 0;
							while (control < listamemberhours.size()) {
								if (listamemberhours.get(control).getUser().equals(user)) {
									break;
								}
								control++;
							}
							if (listamemberhours.size() == control) {
								listamemberhours.add(new MemberHoursInformation(user));
							}
							listamemberhours.get(control).addTime(Double.parseDouble(doubles[0]),
									Double.parseDouble(doubles[1]));
						}
					}
				}
			}
		}
		String info = "";
		int spentTime = 0;
		for (MemberHoursInformation m : listamemberhours) {
			for (String[] s : listauserartifactos) {
				if (m.getUser().equals(s[0])) {
					info += ("\tO utilizador " + m.getUser() + " originou " + s[1]
							+ " artifactos no repositório \ne gastou " + m.getSpentTime() + " horas "
							+ " o que dá um custo total de: " + m.getSpentTime() * custoHora + " euros\n");
					spentTime += m.getSpentTime();
					break;
				}

			}

		}
		String retorno = "\tNeste projeto foram originados " + lista.size() + " artifactos, \ngastando-se " + spentTime
				+ " horas, o que dá um custo total de: " + spentTime * custoHora + " euros\n" + info;
		System.out.println(retorno);
		return retorno;
	}

	/**
	 * Dá return da conexao ao Trello
	 * 
	 * @return Trello
	 */
	public Trello getTrelloApi() {
		return trelloApi;
	}

	/**
	 * Dá return de uma String que representa o user no trello do Utilizador
	 * 
	 * @return String
	 */
	public String getTrelloUtilizador() {
		return trelloUtilizador;
	}

	/**
	 * Dá return da lista de SprintHours
	 * 
	 * @return List<SprintHours>
	 */
	public List<SprintHoursInformation> getSprintHours() {
		return sHours;
	}

	/**
	 * Cria uma tabela do tipo JTable com as horas previstas e utilizadas por membro
	 * da equipe e por sprint e as horas previstas e utilizadas do projeto
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * 
	 * @return JTable
	 */
	private JTable CriarTabela(List<SprintHoursInformation> sprintHoursList) {
		String[] colunas = { "Sprint", "User", "Horas previstas", "Horas usadas" };
		int numberOfLines = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getMemberHoursInformationList().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][4];
		int line = 1;
		double estimate = 0;
		double spent = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (MemberHoursInformation h : sH.getMemberHoursInformationList()) {
					dados[line][0] = sH.getSprint();
					dados[line][1] = h.getUser();
					dados[line][2] = h.getEstimateTime();
					dados[line][3] = h.getSpentTime();
					estimate += h.getEstimateTime();
					spent += h.getSpentTime();
					line++;
				}
			}
		}

		dados[0][0] = "Projeto";
		dados[0][1] = "Global";
		dados[0][2] = estimate;
		dados[0][3] = spent;

		JTable tabela = new JTable(dados, colunas);
		tabela.setBackground(UIManager.getColor("Button.light"));
		return tabela;
	}

	/**
	 * Cria uma tabela do tipo JTable com os pagamentos por membro de equipe e por
	 * sprint e custo total do projeto
	 * 
	 * @param sprintHoursList Representa uma lista de SprintHours
	 * @param custoHora       Representa o valor a pagar por hora
	 * 
	 * @return JTable
	 */
	private JTable criarTabela(List<SprintHoursInformation> sprintHoursList, double custoHora) {
		String[] colunas = { "Sprint", "User", "Pagamento" };

		int numberOfLines = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime())
				numberOfLines += sH.getMemberHoursInformationList().size();
		}
		Object[][] dados = new Object[numberOfLines + 1][3];
		int line = 1;
		double custo = 0;
		for (SprintHoursInformation sH : sprintHoursList) {
			if (sH.hasSpentTime()) {
				for (MemberHoursInformation h : sH.getMemberHoursInformationList()) {
					dados[line][0] = sH.getSprint();
					dados[line][1] = h.getUser();
					dados[line][2] = h.getSpentTime() * custoHora;
					custo += h.getSpentTime() * custoHora;
					line++;
				}
			}
		}
		dados[0][0] = "Projeto";
		dados[0][1] = "Global";
		dados[0][2] = custo;

		JTable tabela = new JTable(dados, colunas);
		tabela.setBackground(UIManager.getColor("Button.light"));
		return tabela;

	}

}
