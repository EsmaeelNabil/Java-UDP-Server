package server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;
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
				// -----------------------------Player// Control---------------------------//
				else if (sentence.equals("next")) {
					nextSongKeyStrokes();
				}
				else if (sentence.contains("list")){
					String allsentenc = sentence;
					String[] parts = allsentenc.split("\\s+");
					String playlistname = parts[parts.length-1].toString();
//					System.out.println(parts[parts.length-1].toString());
					playthislist(playlistname);
				}
				else if (sentence.contains("repeat")){
					repeatSongKeyStrokes();
				}
				else if (sentence.contains("random")){
					randomSongKeyStrokes();
				}
				else if (sentence.equals("brb")){
					GetPlayerScreen();
					playOrPauseSongKeyStrokes();
					}

				//-------------------------------------Other things -------------------//
				else if (sentence.equals("click")) {
					LeftClick();
				}
				else if(sentence.contains("screenshot")){
					TakeScreenShot();
				}
				// ---------------------------------------------------------------------//				
				else if (sentence.equals("standby")) {
					cmd("standby");
				} else if (sentence.equals("poweroff")) {
					cmd("exitwin poweroff");
				} else if (sentence.equals("cdon")) {
					cmd("cdrom open");
				}

				else if (sentence.equals("tvon")) {
					sendString = "K1";
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
										"   " + sentence, "\"" });

						if (sentence.equals("music")) {
							Process processs = Runtime.getRuntime().exec(
									new String[] {
											"cmd.exe",
											"/c",
											"start nircmd.exe  speak text",
											"\"",
											"so so you want to start the music? okay, if you want to play all the music type music on, witout spaces in between, and  if you want to close the music, type music off also without spaces, and if you want yo play or pause then type this , brb , and the b is, b for book , type next for playing the next song in the playlist, and if you want to play a specific play list then type the word list, then the name of the play list. thank you. try to check this site, www.Esmaeel.com . "
													, "\"" });
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
						sendData.length, IPAddress, 1111);
				serverSocket.send(sendPacket);

			}
		} catch (IOException e) {
			System.out.println(e);
		}
		// should close serverSocket in finally block
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
	
	public void TakeScreenShot(){
		try {
            Robot robot = new Robot();
            String format = "jpg";
            

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            String fileName = timeStamp+"."+ format;
            String path = "C:\\Users\\esmaeel\\Desktop\\" ;
            File outputFile = new File(path+fileName);
            
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format,outputFile);
            
            System.out.println("A full screenshot saved on Desktop!");
            
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
	}
	
	public void playthislist(String PlayListName){
		try {
			Process process 
			= Runtime.getRuntime().exec(
					new String[] { "cmd.exe","/c","start wmplayer /Playlist "+PlayListName});
			
		} catch (Exception e) {
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

	public void GetPlayerScreen(){
		try {
			Process process = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c",
							"start wmplayer /Task NowPlaying" });
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void LeftClick() {
		try {
			Robot robot = new Robot();
			// Simulate a key press
			int mask = InputEvent.BUTTON1_DOWN_MASK;
			robot.mousePress(mask);
			robot.mouseRelease(mask);
			

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public void playOrPauseSongKeyStrokes() {
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_P);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void repeatSongKeyStrokes() {
		GetPlayerScreen();
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public void randomSongKeyStrokes() {
		GetPlayerScreen();
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_H);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public void nextSongKeyStrokes() {
		GetPlayerScreen();
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_F);

		} catch (AWTException e) {
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
