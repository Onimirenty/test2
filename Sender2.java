package test;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import client.FileChooser;
import middleServer.FileParser;



public class Sender2 
{
    FileParser parser=new FileParser();

    public FileParser getParser() {
        return this.parser;
    }

    public void setParser(FileParser p) {
        this.parser = p;
    }

    public void SDiscussion(int port) throws Exception
    {
        ServerSocket serveurSocket ;
        Socket clientSocket ;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        
        try 
        {
            System.out.println(" ");
            System.out.println("SERVEUR");
            System.out.println(" ");
            serveurSocket = new ServerSocket(port);
            clientSocket = serveurSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
            Thread envoi= new Thread
            (
                new Runnable() 
                {
                    String msg;
                    public void run() 
                    {
                        while(true)
                        {
                            msg = sc.nextLine();
                            String mms= new String();
                            if(msg.equalsIgnoreCase("send"))
                            {
                                File file;
                                try 
                                    {
                                        file = FileChooser.YgetFile();
                                        Sender2 huhu=new Sender2();
                                        
                                        String name=file.getName();
                                        String n=  name.substring(name.lastIndexOf(".") + 1);
                                        
                                        OutputStream os=  clientSocket.getOutputStream();
                                        DataOutputStream dos= new DataOutputStream(os);
                                        dos.writeUTF(n);
                                        os.flush();
                                        
                                        FileInputStream fis= new FileInputStream(file);
                                        BufferedInputStream bis= new BufferedInputStream(fis);
                                        byte[] mybytearray = new byte[(int) file.length()];
                                        bis.read(mybytearray, 0, mybytearray.length);
                                        os.write(mybytearray, 0, mybytearray.length);
                                        os.close();
                                    } 
                                catch (Exception e) 
                                    {
                                        e.printStackTrace();
                                    }
                            }
                            else
                            {
                                out.println(msg);
                            }
                            // out.flush();
                        }
                    }
                }   
            );
            envoi.start();

            Thread recevoir= new Thread
            (
                new Runnable() 
                {
                    String msg ;
                    public void run() 
                    {
                        try 
                        {
                            msg = in.readLine();
                            while(msg!=null)
                            {
                                if(msg.equalsIgnoreCase("quit"))
                                {
                                    break;
                                }
                                if(msg.equalsIgnoreCase("send"))
                                {
                                    try
                                    {                                
                                        InputStream is= clientSocket.getInputStream();
                                        byte[] bytes= new byte[1024*16];

                                        DataInputStream dis=new DataInputStream(clientSocket.getInputStream());
                                        String ext=dis.readUTF();

                                        System.out.println("extension read");



                                        String path="F:/Aprotos/"+"huhu."+ext;
                                        File file= new File(path);

                                        OutputStream output = new FileOutputStream(file);
                                        while(is.read(bytes)>0)
                                        {
                                            output.write(bytes);
                                            System.out.println("byte read");
                                        }
                                        System.out.println("Done!!!");

                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println(e);
                                    }
                                }
                                System.out.println("Client : "+msg);
                                msg = in.readLine();
                            }
                            System.out.println("Client s'est déconecté");
                            out.close();
                            clientSocket.close();
                            serveurSocket.close();
                        } 
                        catch (Exception e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }
            );

        recevoir.start();

        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    

    public void SendText()
    {

    } 
    public static void main(String[] args) throws Exception
    {
        Sender2 s=new Sender2();
        s.SDiscussion(5000);
        // File file;
        // String nom="zip.mp3";
        // try {
        //     file = FileChooser.YgetFile();
        //     System.out.println("getname=>"+file.getName());
        //     Sender2 huhu=new Sender2();
        //     Path path =Paths.get(file.getAbsolutePath());
        //     byte[] bite=Files.readAllBytes(path);
            

        //     //fonction
        //     try (FileOutputStream out=new FileOutputStream("F:/Aprotos/"+nom)) 
        //     {
        //         out.write(bite);    
        //     } catch (Exception e) 
        //     {
        //         e.printStackTrace();
        //     }

        // } 
        // catch (Exception e) 
        // {
        //     e.printStackTrace();
        // }
        

        // FileParser fl= new FileParser();
        // fl.copierFichier("D:/");
    }
}
