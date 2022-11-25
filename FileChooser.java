package client;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooser 
{
    public static File YgetFile() throws Exception
    {
        //ouvre le fichier
        JFileChooser choose=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //enregistre le fichier
        int res=choose.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION)
        {
            File file=choose.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            return file;
        }
        else
        {
            throw new Exception("file Not selected Exception");
        }
        

    }
    public static void main(String[] args) 
    {
        
        
    }
}                                                                                                                                                                                                                                                                                                                                                                                   

