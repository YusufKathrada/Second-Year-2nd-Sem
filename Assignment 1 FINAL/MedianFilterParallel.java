//Yusuf Kathrada
//CSC2002S
//August 2022
//Assignment 1
//Median Filter Parallel


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.nio.file.*;
import java.util.Arrays;

//Using aspects of sequential algorthim form MedainFilterSerial, adopted from https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html

public class MedianFilterParallel extends RecursiveAction {

    static File inputFile;
    static File outputFile;
    
    static BufferedImage img = null;
    
    static int windowWidth = 0;
    
    static int threshold = 1000;      //Sequential Cutoff
    
    int height = 0;
    int width = 0;
    
    static int start;
    
    int begin = 0;
    int split;

   //Contsructor 
    public MedianFilterParallel(int start, int width) {
        this.width = width;
        this.begin = start;
    }

    protected void computeDirectly() {
        int r = windowWidth / 2;
        int arraySize = windowWidth * windowWidth;
        
        int[] A = new int[arraySize];
        int[] R = new int[arraySize];
        int[] G = new int[arraySize];
        int[] B = new int[arraySize];
        
        int pos = 0;
        int total = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        height = img.getHeight();
        width = img.getWidth();

        for (int y = r; y < height - r; y++) {        //Rows of the image
            for (int x = r; x < width - r; x++) {     //Columns of the image
                for (int row = y - r; row <= y + r; row++) {      //Rows of kernel
                    for (int column = x - r; column <= x + r; column++) {     //Columns of kernel
                       
                        int pixel = img.getRGB(column, row);      
                        
                        total = (pixel >> 24) & 0xFF;            
                        red = (pixel >> 16) & 0xFF;
                        green = (pixel >> 8) & 0xFF;
                        blue = pixel & 0xFF;

                        A[pos] = total;
                        R[pos] = red;
                        G[pos] = green;
                        B[pos] = blue;
                        
                        pos++;
                    }
                }
                pos = 0;
                
                Arrays.sort(A);     //Sorts in ascending order, needed to find median
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                
                int targetPixel = img.getRGB(x, y);
                
                total = (targetPixel >> 24) & 0xFF;
                red = (targetPixel >> 16) & 0xFF;
                green = (targetPixel >> 8) & 0xFF;
                blue = targetPixel & 0xFF;

                int mid = arraySize / 2;

                total = A[mid];     //pixel is set to middle number (median)of array 
                red = R[mid];
                green = G[mid];
                blue = B[mid];

                targetPixel = total << 24 | red << 16 | green << 8 | blue;
                img.setRGB(x, y, targetPixel);

            }
        }

    }

    public void compute() {

        if ((width - begin) < threshold) {    //Compute sequentially if less than threshold
            computeDirectly();

            return;

        } else {

            split = (begin + width) / 2;

            MedianFilterParallel left = new MedianFilterParallel(begin, split);      //Create Threads
            MedianFilterParallel right = new MedianFilterParallel(split + 1, width);

            left.fork();         
            right.compute();
            left.join();      //Threads wait for each other to be complete
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("<Enter input image name> <Enter output image name> <Enter window size>");

        String line = input.nextLine();

        String parameters[] = line.split(" ");     //Splits input by space, according to <Enter input image name> <Enter output image name> <Enter window size>

        inputFile = new File(parameters[0]);
        outputFile = new File(parameters[1]);
        windowWidth = Integer.parseInt(parameters[2]);
        
        if (windowWidth < 3){    //Exits gracefully if incorrect window size is entered.
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);
        }


        try {

            img = ImageIO.read(inputFile);

            MedianFilterParallel fb = new MedianFilterParallel(0, img.getWidth());

            ForkJoinPool pool = new ForkJoinPool();
            long startTime = System.currentTimeMillis();    //Start Time, only times core parallel work
            pool.invoke(fb);
            long endTime = System.currentTimeMillis();      //End Time
            ImageIO.write(img, "png", outputFile);    //For some reason png works better than jpg??

            System.out.println("Total execution time: " + (endTime - startTime));

        } catch (IOException e) {
            System.out.println("There was an error.");
        }

    }
}