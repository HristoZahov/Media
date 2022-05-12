import Classes.Media;
import Utilities.MediaUtilities;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.ArrayList;

import static Classes.GenerateQRCode.makeQRCode;
import static Utilities.MediaUtilities.getOneMedia;

public class Test {
    public static void main(String[] args) {
//        MediaUtilities.insertMedia("Grisho","Ivan Vazov",1989,"novel","Mnogo e qka",10);
//        MediaUtilities.insertMedia("Pesho","Ivan ",1989,"novel","Mnogo e qka",3);
        //MediaUtilities.deleteMedia(2);
//        Media media = new Media(2,"Petko", "Stoqnov",2000, "not novel","", 3,"");
//        MediaUtilities.updateMedia(media);
//        ArrayList<Media> media = MediaUtilities.getAllMedias();
//
//        for (Media media1: media){
//            System.out.println(media1.toString());
//        }
//
//        Media media = getOneMedia(7);
//
//        System.out.println(media.getName());
//        System.out.println(media.getAuthor());

        Media media = getOneMedia(1);
        try {
            makeQRCode(media.toString(), "src\\QR_Codes\\Ivan.png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
