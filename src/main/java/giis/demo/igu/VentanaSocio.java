package giis.demo.igu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Actividad;
import giis.demo.model.ModelSocio;

public class VentanaSocio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int INITIALDAY = 19;
	private static final int INITIALMONTH = 10;
	private static final int INITIALYEAR = 2022;
	
	private static final int MONTHCORRECTION = 1;
	private static final int YEARCORRECTION = 1900;
	
	private JPanel contentPane;
	
	private ModelSocio model = null;
	private JLabel lblActividades;
	private JLabel lblDia;
	private JSpinner spDay;
	private JLabel lblMonth;
	private JSpinner spMonth;
	private JSpinner spYear;
	private JLabel lblYear;
	private JButton btnFecha;
	private JScrollPane scPaneList;
	private JList<Actividad> actList;
	private DefaultListModel<Actividad> modelList;
	private JButton btnReserva;

	/**
	 * Create the frame.
	 */
	public VentanaSocio(ModelSocio ms) {
		
		if(ms == null) {
			throw new IllegalArgumentException("El modelo de socio no exixte");
		}
		else {
			model = ms;
		}
		
		setTitle("Aplicacion del gimnasio - Socio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblActividades());
		contentPane.add(getLblDia());
		contentPane.add(getSpDay());
		contentPane.add(getLblMonth());
		contentPane.add(getSpMonth());
		contentPane.add(getSpYear());
		contentPane.add(getLblYear());
		contentPane.add(getBtnFecha());
		contentPane.add(getScPaneList());
		contentPane.add(getBtnReserva());
		
		this.setVisible(true);
	}
	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades disponibles");
			lblActividades.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblActividades.setBounds(34, 46, 223, 35);
		}
		return lblActividades;
	}
	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Selecciona un dia:");
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDia.setBounds(456, 51, 127, 27);
		}
		return lblDia;
	}
	private JSpinner getSpDay() {
		if (spDay == null) {
			spDay = new JSpinner();
			spDay.setModel(new SpinnerNumberModel(INITIALDAY, 1, 31, 1));
			spDay.setBounds(584, 56, 41, 20);
		}
		return spDay;
	}
	private JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Selecciona un mes:");
			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMonth.setBounds(455, 91, 127, 27);
		}
		return lblMonth;
	}
	private JSpinner getSpMonth() {
		if (spMonth == null) {
			spMonth = new JSpinner();
			spMonth.setModel(new SpinnerNumberModel(INITIALMONTH, 1, 12, 1));
			spMonth.setBounds(584, 96, 41, 20);
		}
		return spMonth;
	}
	
	private JSpinner getSpYear() {
		if (spYear == null) {
			spYear = new JSpinner();
			spYear.setModel(new SpinnerNumberModel(INITIALYEAR, INITIALYEAR, 2023, 1));
			spYear.setBounds(568, 134, 57, 20);
		}
		return spYear;
	}
	private JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblYear.setBounds(456, 129, 41, 27);
		}
		return lblYear;
	}
	private JButton getBtnFecha() {
		if (btnFecha == null) {
			btnFecha = new JButton("Ver actividades");
			btnFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int day = (int)spDay.getValue();
					int month = (int)spMonth.getValue();
					int year = (int)spYear.getValue();
					showActivities(year, month, day);
				}
			});
			btnFecha.setBackground(Color.WHITE);
			btnFecha.setBounds(456, 167, 107, 23);
		}
		return btnFecha;
	}
	
	private void showActivities(int year, int month, int day) {
		if (!model.comprobarFecha(day, month, year)) {
			showMessage("Esta fecha no existe, Introduce una fecha correcta",
					"Aviso - Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
		}
		else {
			//Actualizar lista de actividades
			Date date = new Date(year-YEARCORRECTION, month-MONTHCORRECTION, day);
			List <Actividad> activities = model.getListActivitiesFor(date);
			modelList.clear();
			modelList.addAll(activities);
		}
	}

	
	private static void showMessage(String message, String title, int type) {
	    JOptionPane pane = new JOptionPane(message,type,JOptionPane.DEFAULT_OPTION);
	    pane.setOptions(new Object[] {"ACEPTAR"}); //fija este valor para que no dependa del idioma
	    JDialog d = pane.createDialog(pane, title);
	    d.setLocation(200,200);
	    d.setVisible(true);
	}
	private JScrollPane getScPaneList() {
		if (scPaneList == null) {
			scPaneList = new JScrollPane();
			scPaneList.setBounds(34, 95, 398, 356);
			scPaneList.setViewportView(getActList());
		}
		return scPaneList;
	}
	private JList<Actividad> getActList() {
		if (actList == null) {
			actList = new JList<Actividad>();
			Date date = new Date(INITIALYEAR-YEARCORRECTION, 
					INITIALMONTH-MONTHCORRECTION, INITIALDAY);
			
			modelList = new DefaultListModel<>();
			actList.setModel(modelList);
			
			actList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			showActivities(INITIALYEAR, INITIALMONTH, INITIALDAY);
		}
		return actList;
	}
	private JButton getBtnReserva() {
		if (btnReserva == null) {
			btnReserva = new JButton("Reservar actividad");
			btnReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reservar();
				}
			});
			btnReserva.setBackground(Color.GREEN);
			btnReserva.setBounds(290, 46, 142, 35);
		}
		return btnReserva;
	}
	
	private void reservar() {
		if (actList.getSelectedValue() == null ||
				actList.getSelectedValue().getPlazas() == Actividad.ACTIVIDADILIMITADA) {
			showMessage("Asegurese de que ha escogido una actividad con limite de plazas.",
					"Aviso - Actividad no reservable", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int actId = actList.getSelectedValue().getId();
		int userId = askForIdSocio();
		model.reservarActividad(actId, userId);
	}
	
	private int askForIdSocio(){
		String input;
		do {
			input = JOptionPane.showInputDialog("Introduzca su ID de socio (Número)");
		} while (input == null || input.isEmpty() || !model.checkIsInt(input));
		
		int result = Integer.parseInt(input);
		
		if (!model.existsIdSocio(result)) {
			showMessage("No exixte ningun socio con id " + result,
					"Aviso - Socio no válido", JOptionPane.WARNING_MESSAGE);
			return askForIdSocio();
		}
		
		return result;
	}
}
