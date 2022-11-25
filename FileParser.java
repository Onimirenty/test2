package middleServer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import client.*;
import middleServer.*;
import multipleUse.*;
import test.*;

public class FileParser 
{
    public boolean Filechaker(String path) //verifie l'etat du fichier
    {
        File bruno =new File(path);
        if(bruno.canExecute() && bruno.canRead() && bruno.canWrite())
        {
            return true;
        }
        return false;
    }
    public byte[] FichierEnbyte(String pathString) throws Exception
    {
        //retourne le fichier indiquer par le chemin en byte code
        Path path=Paths.get(pathString);//meme que path string mais de type path
        byte[] bits = Files.readAllBytes(path);//transforme le fichier en byte code 
        return bits;
    }
    public Path pathFichier(String pathInsert,byte[] bits) throws Exception
    {
        Path path =Paths.get(pathInsert);
        Path path2=Files.write(path, bits);
        return path2;
    }

    public byte[] FileToByte(File file) throws Exception
    {
        String path=file.getAbsolutePath();
        return  FichierEnbyte(path);
    }
    public void copierFichier(String nvChemin )
    {
        File file;
        
        try {
            file = FileChooser.YgetFile();
            Sender2 huhu=new Sender2();
            Path path =Paths.get(file.getAbsolutePath());
            byte[] bite=Files.readAllBytes(path);
            //fonction
            try (FileOutputStream out=new FileOutputStream(nvChemin+file.getName())) 
            {
                // System.out.println("chemin ="+ nvChemin+file.getName());
                out.write(bite);    
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public void DepotFichierDansServeur(Socket soc,String msg ,String nvChemin)
    {
        try 
        {
                // ByteArrayOutputStream outpoot=new ByteArrayOutputStream();
                // OutputStream oupou=soc.getOutputStream();

                
                String[] msgn=msg.split(",,");
                
                String code=msgn[0];
                String NomFichier=msgn[1];
                String bytecode=msgn[2];
                
                OutputStream out2=soc.getOutputStream();
                DataOutputStream dataout= new DataOutputStream(out2);
                
                File file=new File(nvChemin+NomFichier);
                FileOutputStream oupout= new FileOutputStream(file);

                byte[] bite=bytecode.getBytes();
                oupout.write(bite);
                
                try (FileOutputStream out=new FileOutputStream(nvChemin+NomFichier)) 
                {
                    out.write(bite);    
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception
    {
        String pathString = "D:/mp3java/bruno.m4a";
        Path path=Paths.get(pathString);
        try 
        {
            
            File bruno =new File(pathString);
            System.out.println();

            byte[] bits = Files.readAllBytes(Paths.get(pathString));
            // System.out.println(Arrays.toString(bits));
            System.out.println(Paths.get(pathString));
        } catch (IOException e) 
        {
            System.out.println(e.toString());
        }
    }
}
