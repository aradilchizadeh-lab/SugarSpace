package GUI;

import Interfaces.IAgent_Histogram;
import Models.Agent;
import Models.Space;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Histogram {
    public static void saveFileWealth(Space space) {
        try {

            FileWriter myfile = new FileWriter("wealth.txt");
            for (Agent a : space.getAgents()) {
                IAgent_Histogram a1 = (IAgent_Histogram)a;
                myfile.write(a1.getMRS(a1.getWallet().getASugar(), a1.getWallet().getASpice()) + "\n");
            }
            myfile.close();
        } catch (Exception e) {
            System.out.println("There is a problem");
        }
    }




    public static ArrayList<Double> readMRSFromFile() {
        ArrayList<Double> MRSList = new ArrayList<>();
        File file = new File("wealth.txt");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextDouble()) {
                MRSList.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("فایل پیدا نشد! مسیر چک شده: " + file.getAbsolutePath());
        }
        return MRSList;
    }

    public static void processAndDraw() {
        ArrayList<Double> allData = readMRSFromFile();

        ArrayList<Double> cleanData = new ArrayList<>();
        for (Double d : allData) {
            if (d != null && !d.isInfinite() && !d.isNaN()) {
                cleanData.add(d);
            }
        }
        if (cleanData.isEmpty()) {
            //System.out.println("داده‌ای برای رسم وجود ندارد!");
            return;
        }

        double minMRS = Double.POSITIVE_INFINITY;
        double maxMRS = Double.NEGATIVE_INFINITY;

        for (double mrs : cleanData) {
            if (mrs < minMRS) minMRS = mrs;
            if (mrs > maxMRS) maxMRS = mrs;
        }


        int numBins = 8;
        double sizeBin = (maxMRS - minMRS) / numBins;
        int[] bins = new int[numBins];

        for (double mrs : cleanData) {
            int index = (int)((mrs - minMRS) / sizeBin);
            if (index == numBins) index = numBins - 1;
            bins[index]++;
        }

        int maxFreq = 0;
        for (int f : bins) {
            if (f > maxFreq) maxFreq = f;
        }

        int n = bins.length;
        StdDraw.clear();
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-maxFreq * 0.15, maxFreq * 1.2);

        for (int i = 0; i < n; i++) {
            double freq = bins[i];

            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.filledRectangle(i, freq / 2.0, 0.4, freq / 2.0);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(i, freq / 2.0, 0.4, freq / 2.0);

            int start = (int) (i * sizeBin);
            int end = (int) ((i + 1) * sizeBin);
            String rangeText ="(" +start + " - " + end+")";

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(i, -maxFreq * 0.06, rangeText);


            if (bins[i] >= 0) {
                StdDraw.text(i, freq + maxFreq * 0.03, String.valueOf(bins[i]));
            }
        }
        StdDraw.show();
    }
}

