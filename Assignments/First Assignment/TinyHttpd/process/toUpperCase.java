import java.util.Arrays;
import java.util.Iterator;

public class reverse{

    public static void main(String[] args){

        String str = null;

        Iterator<String> iterator = Arrays.stream(args).iterator();
        while( iterator.hasNext() && str == null ){

            String arg = iterator.next();
            String[] splitted = arg.split("=");
            if( splitted.length > 1 && splitted[0].equals("-str") ){
                str = splitted[1];
            }

        }

        if( str != null ) {
            System.out.println(str.toUpperCase());
        } else {
            System.err.println("You must provide a parameter with name 'str'");
        }

    }

}