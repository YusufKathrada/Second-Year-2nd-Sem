//Yusuf Kathrada
//CSC2002S
//August 2022
//Assignment 1
//Mean Filter Parallel

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

//Using aspects of sequential algorthim form MeanFilterSerial, adopted from https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html


public class MeanFilterParallel extends RecursiveAction {

    static File inputFile;
    static File outputFile;
    static BufferedImage img = null;
    static int windowWidth = 0;
    static int threshold = 1000;
    int height = 0;
    int width = 0;
    static int start;
    int StartWidth = 0;
    int split;

    public MeanFilterParallel(int start, int width) {
        this.width = width;
        this.StartWidth = start;
    }

    protected void computeDirectly() {

        int r = windowWidth / 2;
        
        int arraySize = windowWidth * windowWidth;
        
        int total = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        
        int totalA = 0;
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;

        height = img.getHeight();
        width = img.getWidth();

        for (int y = r; y < height - r; y++) {                 //Rows of image
            for (int x = r; x < width - r; x++) {              //Columns for image
                for (int row = y - r; row <= y + r; row++) {   //Rows for kernel
                    for (int column = x - r; column <= x + r; column++) {     //Columns for kernel
                        int pixel = img.getRGB(column, row);

                        total = (pixel >> 24) & 0xFF;
                        red = (pixel >> 16) & 0xFF;
                        green = (pixel >> 8) & 0xFF;
                        blue = pixel & 0xFF;

                        totalA = totalA + total;    
                        totalR = totalR + red;
                        totalG = totalG + green;
                        totalB = totalB + blue;
                    }
                }
                int meanAlpha = (int) (totalA / arraySize);
                int meanRed = (int) (totalR / arraySize);
                int meanGreen = (int) (totalG / arraySize);
                int meanBlue = (int) (totalB / arraySize);
                
                int targetPixel = img.getRGB(x, y);

                total = (targetPixel >> 24) & 0xFF;
                red = (targetPixel >> 16) & 0xFF;
                green = (targetPixel >> 8) & 0xFF;
                blue = targetPixel & 0xFF;

                total = meanAlpha;
                red = meanRed;
                green = meanGreen;
                blue = meanBlue;

                targetPixel = total << 24 | red << 16 | green << 8 | blue;
                img.setRGB(x, y, targetPixel);
                totalA = 0;
                totalR = 0;
                totalG = 0;
                totalB = 0;

            }
        }

    }

    public void compute() {

        if ((width - StartWidth) < threshold) {
            computeDirectly();

            return;

        } else {

            split = (StartWidth + width) / 2;

            MeanFilterParallel left = new MeanFilterParallel(StartWidth, split);
            MeanFilterParallel right = new MeanFilterParallel(split + 1, width);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("<Enter input image name> <Enter output image name> <Enter window size>");

        String line = input.nextLine();

        String parameters[] = line.split(" ");  //Splits input by space, according to <Enter input image name> <Enter output image name> <Enter window size>

        inputFile = new File(parameters[0]);
        outputFile = new File(parameters[1]);
        windowWidth = Integer.parseInt(parameters[2]);

        if (windowWidth < 3){    //Exits gracefully if incorrect window size entered
        System.out.println("Input invalid, please try again. Remember to use a window width of 3 or greater.");
        System.exit(0);
        }

        try {

            img = ImageIO.read(inputFile);

            MeanFilterParallel fb = new MeanFilterParallel(0, img.getWidth());

            ForkJoinPool pool = new ForkJoinPool();
            long startTime = System.currentTimeMillis();
            pool.invoke(fb);
            long endTime = System.currentTimeMillis();
            ImageIO.write(img, "png", outputFile);
            System.out.println("Total execution time: " + (endTime - startTime));

        } catch (IOException e) {
            System.out.println("There was an error.");
        }

    }
}