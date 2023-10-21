//Yusuf Kathrada
//CSC2002S
//August 2022
//Assignment 1
//Serial Mean Filter


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.Scanner;

public class MeanFilterSerial {

    public static void main(String[] args) {
        System.out.println("<Enter input image name> <Enter output image name> <Enter window size>");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        
        Scanner input = new Scanner(line);
                
        if(line.isEmpty() || line.equals("   ")){
        
        //-------------------------------------------------
        
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);
        }

        String parameters[] = line.split(" ");
        
        if (parameters.length != 3){
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

        
        if (parameters[0].equals(" ") || parameters[1].equals(" ")){
            System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
            System.exit(0);
        }

        //--------------------------------------------------
        
        File f = new File(inputName);
        File output = new File(outputName);
        
        
        Color color;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int r = windowWidth / 2;
        //System.out.println(r);

        long startTime = System.currentTimeMillis();        //Start Timer here


        try {
            BufferedImage img = ImageIO.read(f);
            for (int y = r; y < img.getHeight()-r; y++) {      //Columns of image
                for (int x = r; x < img.getWidth()-r; x++) {   //Rows of image
                    for (int row = y - r; row <= y + r; row++) {     //Row of kernel
                        for (int column = x - r; column <= x + r; column++) {    //Column of kernel
                            int pixel = img.getRGB(column, row);
                            color = new Color(pixel, true);
                            int red = color.getRed();
                            int green = color.getGreen();
                            int blue = color.getBlue();
                            
                            totalRed = totalRed + red;
                            totalGreen = totalGreen + green;
                            totalBlue = totalBlue + blue;


                        }
                    }
                    int meanRed = (int) (totalRed/Math.pow(windowWidth, 2));
                    int meanGreen = (int) (totalGreen/Math.pow(windowWidth, 2));
                    int meanBlue = (int) (totalBlue/Math.pow(windowWidth, 2));
                    
                    color = new Color(meanRed,meanGreen,meanBlue);
                    img.setRGB(x, y, color.getRGB());
                    totalRed = 0;
                    totalGreen = 0;
                    totalBlue = 0;
                }

            }

            long endTime = System.currentTimeMillis();      //End Timer


            ImageIO.write(img, "png", output);
            System.out.println("Done, your picture has been filtered!");
            System.out.println("Total execution time: " + (endTime - startTime));      //Print Time for execution

            
        } catch (IOException e) {
            System.out.println("An error occured, please try again.");
            //e.printStackTrace();

        }

    }

}