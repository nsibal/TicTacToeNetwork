import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Player2 extends JFrame {
	
	private JPanel contentPane;
	private JLabel status;
	
	private JButton[] buttons = new JButton[9];
	
	private String yourInput = "X";
	
	private boolean yourTurn = false;
	
	private Socket socket;
	private DataInputStream input;
	private static DataOutputStream output;
	private final int PORT = 9001;
	
	public void initiate() throws IOException {
		socket = new Socket(InetAddress.getLocalHost(), PORT); // This is called Handshaking.
		status.setText("OTHER PLAYER'S TURN");
		enableButtons(true);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}
	
	public void enableButtons(boolean bool) {
		for(JButton btn : buttons) {
			btn.setEnabled(bool);
		}
	}
	
	public void enableButtons(boolean bool, JButton winningbtn1, JButton winningbtn2, JButton winningbtn3) {
		for(JButton btn : buttons) {
			if(btn==winningbtn1 || btn==winningbtn2 || btn==winningbtn3) {
				btn.setEnabled(bool);
			}
			else
				btn.setEnabled(!bool);
		}
	}
	
	public void takeTurn(int btn) {
		if(yourTurn) {
			if(buttons[btn].getText().trim().length()==0) {
				buttons[btn].setText(yourInput);
				try {
					output.writeUTF(yourInput+","+btn);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				yourTurn = false;
				status.setText("OTHER PLAYER'S TURN");
			}
		}
	}
	
	public void othersTurn() throws IOException {
		while(true) {
			String incoming = new String(input.readUTF());
			String result[] = incoming.trim().split(",");
			if(result.length==4) {
				int winningbtn1 = Integer.parseInt(result[1]);
				int winningbtn2 = Integer.parseInt(result[2]);
				int winningbtn3 = Integer.parseInt(result[3]);
				enableButtons(true, buttons[winningbtn1], buttons[winningbtn2], buttons[winningbtn3]);
				status.setText(result[0]);
			}
			else if(result.length==1) {
				enableButtons(false);
				status.setText(result[0]);
			}
			else {
				int position = Integer.parseInt(result[1]);
				buttons[position].setText(result[0]);
				yourTurn = true;
				status.setText("YOUR TURN");
			}
		}
	}

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {*/
					Player2 frame = new Player2();
					frame.setVisible(true);
					try {
						frame.initiate();
						frame.othersTurn();
					}
					catch(ConnectException e) {
						frame.status.setText("CONNECTION ERROR");
						frame.enableButtons(false);
					}
					catch(EOFException e) {
						frame.status.setText("CONNECTION LOST");
						frame.enableButtons(false);
					}
				/*} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	/**
	 * Create the frame.
	 */
	public Player2() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttons[0] = new JButton("");
		buttons[0].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[0].setBounds(206, 6, 192, 192);
		buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(0);
			}
		});
		contentPane.add(buttons[0]);
		
		buttons[1] = new JButton("");
		buttons[1].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[1].setBounds(404, 6, 192, 192);
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(1);
			}
		});
		contentPane.add(buttons[1]);
		
		buttons[2] = new JButton("");
		buttons[2].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[2].setBounds(602, 6, 192, 192);
		buttons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(2);
			}
		});
		contentPane.add(buttons[2]);
		
		buttons[3] = new JButton("");
		buttons[3].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[3].setBounds(206, 204, 192, 192);
		buttons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(3);
			}
		});
		contentPane.add(buttons[3]);
		
		buttons[4] = new JButton("");
		buttons[4].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[4].setBounds(404, 204, 192, 192);
		buttons[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(4);
			}
		});
		contentPane.add(buttons[4]);
		
		buttons[5] = new JButton("");
		buttons[5].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[5].setBounds(602, 204, 192, 192);
		buttons[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(5);
			}
		});
		contentPane.add(buttons[5]);
		
		buttons[6] = new JButton("");
		buttons[6].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[6].setBounds(206, 402, 192, 192);
		buttons[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(6);
			}
		});
		contentPane.add(buttons[6]);
		
		buttons[7] = new JButton("");
		buttons[7].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[7].setBounds(404, 402, 192, 192);
		buttons[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(7);
			}
		});
		contentPane.add(buttons[7]);
		
		buttons[8] = new JButton("");
		buttons[8].setFont(new Font("Lucida Grande", Font.BOLD, 50));
		buttons[8].setBounds(602, 402, 192, 192);
		buttons[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(8);
			}
		});
		contentPane.add(buttons[8]);
		
		status = new JLabel("SERVER IS DOWN");
		enableButtons(false);
		status.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setBounds(6, 606, 988, 168);
		contentPane.add(status);
	}
}
