package com.harputyazilim.todos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static final int GET = 1;
    public static final int SEND = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;

    static List<Todo> todos = new ArrayList<>();
    static int lastId = 4;
    static MySemaphore mySemaphore = new MySemaphore(true);

    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);


        dummyTodos();

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            switch (inFromClient.readByte()) {
                case GET:
                    System.out.println("GET");
                    Thread get = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                get(inFromClient, outToClient);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    get.start();
                    break;
                case SEND:
                    System.out.println("SEND");
                    Thread send = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                send(inFromClient, outToClient);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    send.start();
                    break;
                case UPDATE:
                    System.out.println("UPDATE");
                    Thread update = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                update(inFromClient, outToClient);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    update.start();
                    break;
                case DELETE:
                    System.out.println("DELETE");
                    Thread delete = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                delete(inFromClient, outToClient);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    delete.start();
                    break;
                default:
                    break;
            }
        }
    }

    public static void get(ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        outToClient.writeInt(todos.size());
        outToClient.flush();
        for (int i = 0; i < todos.size(); i++) {
            outToClient.writeObject(todos.get(i));
        }
    }

    public static void send(ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException, ClassNotFoundException {
        Todo todo = (Todo) inFromClient.readObject();
        todo.setId(lastId);
        lastId++;
        mySemaphore.P();
        todos.add(todo);
        mySemaphore.V();
        outToClient.writeUTF("Created");
        outToClient.flush();
    }

    public static void update(ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException, ClassNotFoundException {
        Todo todo = (Todo) inFromClient.readObject();
        mySemaphore.P();
        int size = todos.size();
        mySemaphore.V();
        for (int i = 0; i < size; i++) {
            mySemaphore.P();
            int id = todos.get(i).getId();
            mySemaphore.V();
            if (todo.getId() == id) {
                todo.setLastUpdate(new SimpleDateFormat("MMMM d, yyyy ").format(new Date()));
                mySemaphore.P();
                todos.set(i, todo);
                mySemaphore.V();
                break;
            }
        }
        outToClient.writeUTF("Updated");
        outToClient.flush();
    }

    public static void delete(ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        int id = inFromClient.readInt();
        mySemaphore.P();
        int size = todos.size();
        mySemaphore.V();
        for (int i = 0; i < size; i++) {
            mySemaphore.P();
            if (id == todos.get(i).getId()) {
                todos.remove(todos.get(i));
                mySemaphore.V();
                break;
            }
            mySemaphore.V();
        }
        outToClient.writeUTF("Closed");
        outToClient.flush();
    }

    public static void dummyTodos() {
        mySemaphore.P();
        todos.add(new Todo(1, "Furkan", "Ahmet", "Backend",
                "Backend will be implemented", "December 4, 2017", "December 10, 2017", "December 4, 2017"));
        todos.add(new Todo(2, "Ahmet", "Ali", "Frontend",
                "description", "December 4, 2017", "December 10, 2017", "December 4, 2017"));
        todos.add(new Todo(3, "Muhammet Ali", "Furkan", "Android",
                "description", "December 5, 2017", "December 10, 2017", "December 5, 2017"));
        mySemaphore.V();
    }
}
