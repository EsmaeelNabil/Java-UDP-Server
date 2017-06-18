package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class mainserver {

	String sendString = "1";
	String sentence = "";

	public static void main(String[] args) {
		LoginGui h = new LoginGui();

		int port = args.length == 0 ? 1111 : Integer.parseInt(args[0]);
		new mainserver().run(port);

	}

	public void run(int port) {
		try {
			DatagramSocket serverSocket = new DatagramSocket(port);
			byte[] receiveData = new byte[80];

			System.out.printf("Listening on udp:%s:%d%n", InetAddress
					.getLocalHost().getHostAddress(), port);
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			while (true) {
				serverSocket.receive(receivePacket);
				String sentencen = new String(receivePacket.getData(), 0,
						receivePacket.getLength());
				System.out.println("RECEIVED: " + sentencen);

				sentence = sentencen;

				if (sentence.equals("musicon")) {

					openMusic();
				} else if (sentence.equals("musicoff")) {
					closeMusic();

				}

				// /////////////////////////////////////////////////// Volume
				// level from ( 0 to 65535 )
				// ///////////////////////////////////////
				else if (sentence.equals("0")) {
					cmd("setsysvolume 0");
				} else if (sentence.equals("1")) {
					cmd("setsysvolume 3000");
				} else if (sentence.equals("2")) {
					cmd("setsysvolume 6000");
				} else if (sentence.equals("3")) {
					cmd("setsysvolume 15000");
				} else if (sentence.equals("4")) {
					cmd("setsysvolume 20000");
				} else if (sentence.equals("5")) {
					cmd("setsysvolume 30000");
				} else if (sentence.equals("6")) {
					cmd("setsysvolume 40000");
				} else if (sentence.equals("7")) {
					cmd("setsysvolume 50000");
				} else if (sentence.equals("8")) {
					cmd("setsysvolume 55000");
				} else if (sentence.equals("9")) {
					cmd("setsysvolume 57000");
				} else if (sentence.equals("10")) {
					cmd("setsysvolume 65535");
				}
				// ////////////////////////////////////////////////////////////////////////
				 else if (sentence.equals("standby")) {
						cmd("standby");
					}
				 else if (sentence.equals("poweroff")) {
						cmd("exitwin poweroff");
					}
				 else if (sentence.equals("cdon")) {
						cmd("cdrom open");
					}

				else if (sentence.equals("next")) {
					nextsong();

				} else if (sentence.equals("tvon")) {
					sendString="K1";
					System.out.println("seted");
				} else if (sentence.equals("tvoff")) {
					UnMute();
				} else if (sentence.equals("mute")) {
					Mute();
				} else if (sentence.equals("unmute")) {
					UnMute();
				} else { // // other words as a message
					try {
						Process process = Runtime.getRuntime().exec(
								new String[] { "cmd.exe", "/c",
										"start nircmd.exe  speak text", "\"",
										"you said      " + sentence, "\"" });

						if (sentence.equals("music")) {
							Process processs = Runtime.getRuntime().exec(
									new String[] {
											"cmd.exe",
											"/c",
											"start nircmd.exe  speak text",
											"\"",
											"what do you want to play   all the music or what  "
													+ sentence, "\"" });
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				
				 // now send acknowledgement packet back to sender
				 InetAddress IPAddress = receivePacket.getAddress();
				 byte[] sendData = sentence.getBytes();
				 DatagramPacket sendPacket = new DatagramPacket(sendData,
				 sendData.length,
				 IPAddress, 1111);
				 serverSocket.send(sendPacket);

			}
		} catch (IOException e) {
			System.out.println(e);
		}
		// should close serverSocket in finally block
	}

	private void nextsong() {

		try {
			Process process = Runtime
					.getRuntime()
					.exec(new String[] { "cmd.exe", "/c",
							"start wmplayer /prefetch:11 /Query:3;3;6;Play all music;29518;-1;;;;0;;;;2;;" });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void Mute() {

		try {

			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe mutesysvolume 1" });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void screenon() {

		try {
			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe monitor async_on" });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void screenof() {

		try {
			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe monitor off" });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void UnMute() {

		try {
			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe mutesysvolume 0" });
			// Process processs = Runtime.getRuntime().exec(new
			// String[]{"cmd.exe","/c","start nircmd.exe  speak text","\"","unmouteeeed"+sentence,"\""});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void closeMusic() {
		// TODO Auto-generated method stub
		try {
			Process processs = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe  speak text", "\"",
							"  music closed    ", "\"" });

			Process process = Runtime.getRuntime().exec(
					"taskkill /IM wmplayer.exe");
			System.out.println("Music closed");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openMusic() {
		// TODO Auto-generated method stub

		try {
			Process processs = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start nircmd.exe  speak text", "\"",
							" the music is starting now    ", "\"" });

			Process process = Runtime
					.getRuntime()
					.exec(new String[] { "cmd.exe", "/c",
							"start wmplayer /prefetch:11 /Query:3;3;6;Play all music;29518;-1;;;;0;;;;2;;" });
			// Process process =
			// Runtime.getRuntime().exec("taskkill /IM wmplayer.exe");
			System.out.println("Music started");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cmd(String commandline) {
		try {
			String nircmd = "nircmd.exe ";
			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c", nircmd, commandline });

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String show() {
		return sentence;
	}
}