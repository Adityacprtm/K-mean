/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k.mean;

/**
 *
 * @author Aditya C. Pratama
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;



public class KMeans {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = "C:/Users/Aditya C. Pratama/Documents/NetBeansProjects/K-mean/data2.csv";
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filePath)));
        Scanner scanner = new Scanner(new File("C:/Users/Aditya C. Pratama/Documents/NetBeansProjects/K-mean/data2.csv"));
        scanner.useDelimiter(",");
        List<Integer> data = new ArrayList<>();
        List<Integer> fiturX = new ArrayList<>();
        List<Integer> fiturY = new ArrayList<>();
        List<Integer> kelompok = new ArrayList<>();
        String eachLine = "";
        int count = 0;
        System.out.println("Data\t\tFitur X\t\tFitur Y\t\tKelompok");
        while ((eachLine = fileReader.readLine()) != null) {
            String[] dataRow = eachLine.split(",");
            data.add(Integer.parseInt(dataRow[0]));
            fiturX.add(Integer.parseInt(dataRow[1]));
            fiturY.add(Integer.parseInt(dataRow[2]));
            kelompok.add(Integer.parseInt(dataRow[3]));
            System.out.print(data.get(count) + "\t\t" + fiturX.get(count) + "\t\t"
                    + fiturY.get(count) + "\t\t" + kelompok.get(count) + "\t\t");
            System.out.println("");
            count++;
        }
        scanner.close();
        double fLama = 0, fBaru = 0, threshold = 0.1, delta = 0;

        System.out.println("========PENYELESAIAN========");
        do {
            int countK1, countK2, countK3;
            countK1 = Collections.frequency(kelompok, 1);
            countK2 = Collections.frequency(kelompok, 2);
            countK3 = Collections.frequency(kelompok, 3);
            int k1Fx = 0, k1Fy = 0, k2Fx = 0, k2Fy = 0, k3Fx = 0, k3Fy = 0;
            for (int i = 0; i < data.size(); i++) {
                if (kelompok.get(i) == 1) {
                    k1Fx += fiturX.get(i);
                    k1Fy += fiturY.get(i);
                } else if (kelompok.get(i) == 2) {
                    k2Fx += fiturX.get(i);
                    k2Fy += fiturY.get(i);
                } else if (kelompok.get(i) == 3) {
                    k3Fx += fiturX.get(i);
                    k3Fy += fiturY.get(i);
                }

            }
            System.out.println("Count: \nK1 = " + countK1 + " K2 = " + countK2 + " K3 = " + countK3);
            System.out.println("k1Fx = " + k1Fx + ", k1Fy = " + k1Fy + "\nk2Fx = " + k2Fx + ", k2Fy = " + k2Fy + "\nk3Fx = " + k3Fx + ", k3Fy = " + k3Fy);
            double[][] centroid = new double[3][2];
            centroid[0][0] = (double) k1Fx / countK1;
            centroid[0][1] = (double) k1Fy / countK1;
            centroid[1][0] = (double) k2Fx / countK2;
            centroid[1][1] = (double) k2Fy / countK2;
            centroid[2][0] = (double) k3Fx / countK3;
            centroid[2][1] = (double) k3Fy / countK3;
            System.out.println("\nKelompok\t\tCentroid Fitur X\tCentroid Fitur Y");
            for (int i = 0; i < centroid.length; i++) {
                System.out.print((i + 1));
                for (int j = 0; j < centroid[0].length; j++) {
                    System.out.print("\t\t\t" + centroid[i][j]);
                }
                System.out.println("");
            }

            List<Double> d1 = new ArrayList<>();
            List<Double> d2 = new ArrayList<>();
            List<Double> d3 = new ArrayList<>();
            List<Double> min = new ArrayList<>();
            List<Integer> kelompokLama = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                int minVal = 0;
                d1.add(Math.sqrt(Math.pow((fiturX.get(i) - centroid[0][0]), 2) + Math.pow((fiturY.get(i) - centroid[0][1]), 2)));
                d2.add(Math.sqrt(Math.pow((fiturX.get(i) - centroid[1][0]), 2) + Math.pow((fiturY.get(i) - centroid[1][1]), 2)));
                d3.add(Math.sqrt(Math.pow((fiturX.get(i) - centroid[2][0]), 2) + Math.pow((fiturY.get(i) - centroid[2][1]), 2)));
                min.add(Math.min(Math.min(d1.get(i), d2.get(i)), d3.get(i)));
                if (Objects.equals(min.get(i), d1.get(i))) {
                    minVal = 1;
                }
                if (Objects.equals(min.get(i), d2.get(i))) {
                    minVal = 2;
                }
                if (Objects.equals(min.get(i), d3.get(i))) {
                    minVal = 3;
                }
                kelompokLama.add(kelompok.get(i));
                kelompok.set(i, minVal);
            }
            System.out.println("");
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(6);
            System.out.println("Data\t\tFitur X\t\tFitur Y\t\tDistance k1\t\tDistance k2\t\tDistance k3\t\tMin\t\tK Baru\t\tK Lama");
            for (int i = 0; i < data.size(); i++) {
                System.out.print(data.get(i) + "\t\t" + fiturX.get(i) + "\t\t"
                        + fiturY.get(i) + "\t\t" + String.format("%.6f", d1.get(i)) + "\t\t"
                        + String.format("%.6f", d2.get(i)) + "\t\t" + String.format("%.6f", d3.get(i)) + "\t\t"
                        + String.format("%.6f", min.get(i)) + "\t" + kelompok.get(i) + "\t\t" + kelompokLama.get(i));
                System.out.println("");
            }
            double totald1 = 0, totald2 = 0, totald3 = 0;
            for (int i = 0; i < data.size(); i++) {
                if (kelompok.get(i) == 1) {
                    totald1 += d1.get(i);
                } else if (kelompok.get(i) == 2) {
                    totald2 += d2.get(i);
                } else if (kelompok.get(i) == 3) {
                    totald3 += d3.get(i);
                }
            }
            System.out.println("\nTotal : \nD1 = " + totald1 + "\nD2 = " + totald2 + "\nD3 = " + totald3);
            fBaru = totald1 + totald2 + totald3;
            delta = Math.abs(fLama - fBaru);
            System.out.println("F Lama = " + fLama + "\nF Baru = " + fBaru + "\nDelta = " + delta);
            fLama = fBaru;
            System.out.println("\n\n");
        } while (delta > threshold);

    }
}
