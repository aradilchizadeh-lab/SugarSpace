package GUI;

import java.awt.Font;


public class Ending {
    public static void ending(){
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        String[] titles = {"Directed by", "Prepared by", "War Minister", "Grand Vizier", "Chief Inspector", "Motemdo Ssaltaneh"};
        String[] names = {"Dr. Alireza Sokhandan", "Zahara Malaki", "Mohsen Rasulkhani", "Behnam Nahari", "Amirhossin Alimardani", "Masud Hagigatdoost"};
        // StdAudio.play(filename);
        new Thread(() -> {
            StdAudio.play("Sound.wav");
        }).start();
        for (int i = 0; i < 6; ++i)
        {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
            StdDraw.text(10, 10, titles[i]);
            StdDraw.setFont(new Font("Arial", Font.BOLD, 28));
            StdDraw.text(10, 9.25, names[i]);
            StdDraw.show();
            StdDraw.pause(3000);
        }
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.text(10, 13, "Implemented by (Group 2)");
        StdDraw.setFont(new Font("Arial", Font.BOLD, 28));
        StdDraw.text(10, 12, "Arad Ilchizadeh(Arad Advance)");
        StdDraw.text(10, 11, "Raha Bandeh");
        StdDraw.text(10, 10, "Sarina Esmailpooran");
        StdDraw.text(10, 9, "Sanay Sadagian");
        StdDraw.text(10, 8, "Amir Parvin(Aga Amir)");
        StdDraw.show();
        StdDraw.pause(4000);

        int red = 60;
        int green = 60; 
        int blue = 60;
        for (int i = 1; i < 10; ++i)
        {
            
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(red, green, blue);
            StdDraw.setFont(new Font("Arial", Font.BOLD, 70));
            StdDraw.text(10, 10, "THE END");
            red += 23;
            green += 23;
            blue += 23;
            StdDraw.show();
            StdDraw.pause(100);
        }
        
    }
}
