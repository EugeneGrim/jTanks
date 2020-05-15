package ru.grim.jtanks.server.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class MonoThreadClientHandler implements Runnable {

//	private static Socket clientDialog;
	private Socket clientDialog;

	public MonoThreadClientHandler(Socket client) {
		
		this.clientDialog = client;
	}

	@Override
	public void run() {

		try {
			// канал записи в сокет следует инициализировать сначала канал чтения для
			// избежания блокировки выполнения программы на ожидании заголовка в сокете
			DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
			DataInputStream in = new DataInputStream(clientDialog.getInputStream());

			System.out.println("DataInputStream created");
			System.out.println("DataOutputStream  created");
			
			while (!clientDialog.isClosed()) {
				System.out.println("Server reading from channel");

				// серверная нить ждёт в канале чтения (inputstream) получения
				// данных клиента после получения данных считывает их
				String entry = in.readUTF();
				System.out.println("READ from clientDialog message - " + entry);

				if (entry.equalsIgnoreCase("quit")) {
					System.out.println("Client initialize connections suicide ...");
					out.writeUTF("Server reply - " + entry + " - OK");
					Thread.sleep(3000);
					break;
				}

				// если условие окончания работы не верно - продолжаем работу -
				System.out.println("Server try writing to channel");
				out.writeUTF("Server reply - " + entry + " - OK");
				System.out.println("Server Wrote message to clientDialog.");

				// освобождаем буфер сетевых сообщений
				out.flush();

				// возвращаемся в началло для считывания нового сообщения
			}

			// если условие выхода - верно выключаем соединения
			System.out.println("Client disconnected");
			System.out.println("Closing connections & channels.");

			// закрываем сначала каналы сокета !
			in.close();
			out.close();

			// потом закрываем сокет общения с клиентом в нити моносервера
			clientDialog.close();

			System.out.println("Closing connections & channels - DONE.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
