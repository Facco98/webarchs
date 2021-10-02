import java.util.Arrays;
import java.util.Iterator;

public class reverse{

    public static void main(String[] args){

        String str = null;

        Iterator<String> iterator = Arrays.stream(args).iterator();
        while( iterator.hasNext() && str == null ){

            String arg = iterator.next();
            String[] splitted = arg.split("=");
            if( splitted.length > 1 && splitted[0].equals("-par1") ){
                str = splitted[1];
            }

        }

        if( str != null ) {
            StringBuilder sb = new StringBuilder(str);
            System.out.println(sb.reverse().toString());
        } else {
            System.err.println("You must provide a parameter with name 'par1'");
        }

    }

}