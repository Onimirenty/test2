import java.io.File;

public class Test 
{
    public static void main(String[] args) throws Exception {
        String u="12345678910";
        System.out.println(u.length());

        File file;
        String mms=new String();
        file = FileChooser.YgetFile();
        Sender2 huhu=new Sender2();
        
        
        byte[] bite=huhu.getParser().FileToByte(file);
        // out.print("send,,"+file.getName()+",,"+huhu.getParser().FileToByte(file));

        // System.out.println("\n array to string ====================>");
        // System.out.println();
        for (int i = 0; i < bite.length; i++) {
            mms=mms+bite[i];
        }
    }