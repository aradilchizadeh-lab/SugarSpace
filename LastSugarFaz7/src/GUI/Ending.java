package GUI;

import java.awt.Font;


public class Ending {
    public static void ending() {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        StdDraw.enableDoubleBuffering();
        
        
        new Thread(() -> {
            StdAudio.play("SoundB.wav");
        }).start();

        String[] text = {"I", "In", "In ", "In H", "In Ho", "In Hon", "In Hono",
            "In Honor", "In Honor ", "In Honor O", "In Honor Of"
        };
        for (int i = 0; i < 11; ++i) {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
            
            StdDraw.text(10, 10, text[i]);          

            StdDraw.show();
            StdDraw.pause(70);

        }

        for (int i = 0; i < 60; ++i) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, i));
            StdDraw.filledRectangle(10, 8.5, 10, 1);
            
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Arial", Font.BOLD, i));
            StdDraw.text(10, 8.5, "Flower Mr. Behnam");          

            StdDraw.show();
            StdDraw.pause(50);

        }

        StdDraw.pause(1000);
        int red = 40;
        int green = 40;
        int blue = 40;

        int red1 = 105;
        int green1 = 34;
        int blue1 = 79;

        double[] x1 = {10, 7.5, 12.5};
        double[] y1 = {15, 11, 11};

        double[] x2 = {10, 10.2, 10.5};
        double[] y2 = {15, 14.5, 14.75};

        double[] x3 = {10, 9.8, 9.5};
        double[] y3 = {15, 15.5, 15.25};
        
        for (int i = 0; i < 30; ++i) {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(red, green, blue);
            StdDraw.setFont(new Font("Arial", Font.BOLD, 50));
            StdDraw.filledPolygon(x1, y1);
            
            StdDraw.text(10, 10, "Behnam's");
            StdDraw.text(10, 8, "Favorite Project");
            StdDraw.setPenColor(red1, green1, blue1);
            StdDraw.filledCircle(10, 15, 0.15);
            StdDraw.filledPolygon(x2, y2);
            StdDraw.filledPolygon(x3, y3);
            
            
            red += 7;
            green += 7;
            blue += 7;

            red1 += 5;
            green1 += 1;
            blue1 += 3;


            StdDraw.show();
            StdDraw.pause(50);

        }
        StdDraw.pause(3000);

        
        for (int i = 0; i <= 10; ++i) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(10, 10, i);
              
            StdDraw.show();
            StdDraw.pause(40);

        }
        StdDraw.pause(1000);
        red = 250;
        green = 250;
        blue = 250;

        double[] x4 = {10, 19.05, 0.95};
        double[] y4 = {14, 6.71, 6.71};

        for (int i = 0; i < 25; ++i)
        {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(red, green, blue);
            StdDraw.filledSquare(10, 10, 10);
            
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(x4, y4);

            red -= 10;
            green -= 10;
            blue -= 10;

            StdDraw.show();
            StdDraw.pause(30);

        }
        red = 250;
        green = 250;
        blue = 250;
        for (int i = 0; i < 25; ++i)
        {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenColor(red, green, blue);
            StdDraw.filledPolygon(x4, y4);


        
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
            StdDraw.text(10, 13, "Impl");
            StdDraw.text(10, 12, "emented by");
            StdDraw.setFont(new Font("Arial", Font.BOLD, 28));
            StdDraw.text(10, 11, "Arad Ilchizadeh");
            StdDraw.text(10, 10, "Raha  Bandeh  emptyyy");
            StdDraw.text(10, 9, "Sarin  Esmailpooran  Hadi empty");
            StdDraw.text(10, 8, "Sanay Sadagian  empty  name empty name");
            StdDraw.text(10, 7, "Amir Parvin empty name empty name empty  name");
            StdDraw.show();
            StdDraw.pause(30);

            red -= 10;
            green -= 10;
            blue -= 10;
        }
        StdDraw.pause(6000);

    }
}