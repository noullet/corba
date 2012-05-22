package utils;

import interfaces.Orientation;

public class ServerUtils {

	public static int[] getNewCoordinates(int x, int y, Orientation orientation) {
		if (Orientation.EAST.equals(orientation)) {
			x++;
		} else if (Orientation.WEST.equals(orientation)) {
			x--;
		} else if (Orientation.SOUTH.equals(orientation)) {
			y++;
		} else if (Orientation.NORTH.equals(orientation)) {
			y--;
		}
		int[] coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
		return coordinates;
	}
}
