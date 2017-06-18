package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel IPANDPORT, DATA;
	JButton Refreshbtn;
	JPanel P,Pn;

	public LoginGui() // the class constructor
	{
		Setupp();
	}

	public void Setupp() // GUI Method
	{
		Refreshbtn = new JButton(" Refresh the data ");
		IPANDPORT = new JLabel();
		DATA = new JLabel();
		P = new JPanel();
		Pn= new JPanel();

		// ///////////////////////////////////////////////////////////////////////
		P.setBackground(Color.DARK_GRAY); // background color
		Pn.setBackground(Color.DARK_GRAY);
		IPANDPORT.setForeground(Color.GRAY);
		DATA.setFont(new Font("Slab Serifs", Font.BOLD, 15)); // first line font
		// type
		DATA.setForeground(Color.white); // first line color
		Pn.add(IPANDPORT);
		P.add(DATA);
		P.add(Pn);
		P.add(Refreshbtn);
		add(P);
		
		// /////////////////////////////////////////////////////////////////////////// This is the DESIGN controls
		setAlwaysOnTop(true);
		setTitle("SmartHouse receiver");
		setVisible(true); 
		setBounds(500, 200, 470, 125);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ////////////////////////////////////////////////////////////////////////
		
		// //////////////////////////////////////////////////////////////////////
		try {
			DATA.setText("Use the SmartHouse Mobile App to Control over this Data");
			IPANDPORT.setText("Listening on IP  : "
					+ InetAddress.getLocalHost().getHostAddress()
					+ "   ||    Port : 1111 ");
			IPANDPORT.setText(IPANDPORT.getText()+ "  ||   " +"External IP : "+Getmyipfrom("http://ipv4bot.whatismyipaddress.com"));
			IPANDPORT.setForeground(Color.black);
			Pn.setBackground(Color.getHSBColor(0.20f, 1.0f, 1f));

		} catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}

		Refreshbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DATA.setText("Use the SmartHouse Mobile App to Control over this Data");
					IPANDPORT.setText("Listening on IP  : "
							+ InetAddress.getLocalHost().getHostAddress()
							+ "   ||    Port : 1111 ");
					IPANDPORT.setText(IPANDPORT.getText()+ "  ||   " +"External IP : "+Getmyipfrom("http://ipv4bot.whatismyipaddress.com"));
				} catch (Exception a) {
					a.printStackTrace();
				}

			}
		});



	}

	// //////////////////////////////////////////geting the real IP address

	public static String Getmyipfrom(String myURL) {
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(40 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null)
			{
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}else{
				
				sb.append("No internet");
			}
			
			in.close();
			
			
		} catch (Exception e) {
			System.out.println(e);
					
		}

	
		return sb.toString();
	}

	// ////////////////////////////////////////////////
}