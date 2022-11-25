package test;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import middleServer.*;
import client.*;
import multipleUse.*;

public class Receiver2 
{

    FileParser parser=new FileParser();

    public FileParser getParser() {
        return this.parser;
    }

    public void setParser(FileParser p) {
        this.parser = p;
    }
    public void CDiscussion(int port) throws Exception
    {
        Socket clientSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        
        try 
        {
            System.out.println(" ");
            System.out.println("CLIENT");
            System.out.println(" ");
            
            clientSocket = new Socket("127.0.0.1",port);
            
            //pour envoyer
            out = new PrintWriter(clientSocket.getOutputStream());
            //pour recevoir
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
            Thread envoyer = new Thread
                (   
                    new Runnable() 
                    {
                        String msg;
                        public void run() 
                        {
                            while(true)
                            {
                                msg = sc.nextLine();
                                if(msg.equalsIgnoreCase("send"))
                                {
                                    ///envoyer fichier

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
                                // out.println(msg);
                                // out.flush();
                            }
                        }
                    }
                );
            envoyer.start();

            Thread recevoir = new Thread
            (   
                new Runnable() 
                {
                    String msg;
                    String code="send";
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

                                String[] msgn=msg.split(",,");
                                String msg1=msgn[0];
                                
                                if(code.equalsIgnoreCase(msg1)) 
                                {
                                    
                                //     try 
                                //     {
                                //         FileParser fl = new FileParser();
                                //         fl.DepotFichierDansServeur(msg,"F:/Aprotos/");
                                //     } catch (Exception e) 
                                //     {
                                //         e.printStackTrace();    
                                //     }
                                    
                                }
                                System.out.println("Serveur : "+msg);
                                msg = in.readLine();
                            }
                            System.out.println("le Serveur s'est déconnecté");
                            out.close();
                            clientSocket.close();
                        } catch (IOException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }
            );
            recevoir.start();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        Receiver2 s=new Receiver2();
        s.CDiscussion(5000);
    }
}
