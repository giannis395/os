/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os2;

/**
 *
 * @author SHAKA
 */
public class Clock {
    private static int time = 0;

    public static int getTime() {
        return time;
    }

    public static void tick(int units) {
        time += units;
    }
}