//Yusuf Kathrada
//CSC2002S
//August 2022
//Assignment 1
//Serial Mean Filter



import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class MedianFilterSerial {

    public static void main(String[] args) {
        System.out.println("<Enter input image name> <Enter output image name> <Enter window size>");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        
        
        Scanner input = new Scanner(line);
         
        if(line.isEmpty() || line.equals("   ")){
        
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);
        }
         
        String parameters[] = line.split(" ");
        
        if (parameters.length != 3){
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);

        }
        
        if (parameters[0].equals(" ") || parameters[1].equals(" ")){
            System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
            System.exit(0);
        }
        
        
        String inputName = parameters[0];
        String outputName = parameters[1];
        int windowWidth = Integer.parseInt(parameters[2]);
        
        if (windowWidth < 3){
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);
        }        
        
        File f = new File(inputName);
        File output = new File(outputName);
        
        
        Color color;

        int r = windowWidth / 2;
        int arraySize = windowWidth * windowWidth;
        int[] R = new int[arraySize];
        int[] G = new int[arraySize];
        int[] B = new int[arraySize];
        int pos = 0;
        
        long startTime = System.currentTimeMillis();        //Start Timer here
        
        try {
            BufferedImage img = ImageIO.read(f);

            for (int y = r; y < img.getHeight() - r; y++) {
                for (int x = r; x < img.getWidth() - r; x++) {
                    for (int row = y - r; row <= y + r; row++) {
                        for (int column = x - r; column <= x + r; column++) {
                            int pixel = img.getRGB(column, row);
                            color = new Color(pixel, true);
                            int red = color.getRed();
                            int green = color.getGreen();
                            int blue = color.getBlue();

                            R[pos] = red;
                            G[pos] = green;
                            B[pos] = blue;
                            pos++;
                        }
                    }
                    pos = 0;
                    Arrays.sort(R);
                    Arrays.sort(G);
                    Arrays.sort(B);
                    color = new Color(R[arraySize/2],G[arraySize/2] ,B[arraySize/2] );
                    img.setRGB(x, y, color.getRGB());
                }

            }

            ImageIO.write(img, "png", output);
            
            long endTime = System.currentTimeMillis();      //End Timer
            
            System.out.println("Done, your picture has been filtered!");
            
            System.out.println("Total execution time: " + (endTime - startTime));      //Print Time for execution
            
        } catch (IOException e) {
            System.out.println("An error occured, please try again.");
            //e.printStackTrace();

        }

    }
}