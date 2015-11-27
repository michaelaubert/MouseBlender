
public class MouseBlender {

	private static final int FRAME_BUFFER_WIDTH = 640;
	private static final int FRAME_BUFFER_HEIGHT = 480;

	private static final int MOUSE_POINTER_BUFFER_WIDTH = 32;
	private static final int MOUSE_POINTER_BUFFER_HEIGHT = 32;

	// 3 max values that 3 colors can have in a short
	private static final int MAX_RED_SHORT = 31;
	private static final int MAX_GREEN_SHORT = 63;
	private static final int MAX_BLUE_SHORT = 31;

	// 3 shift values to deal with 3 colors in a 16 bits short 
	private static final int RED_SHORT_OFFSET = 11;
	private static final int GREEN_SHORT_OFFSET = 5;
	private static final int BLUE_SHORT_OFFSET = 0;

	// 3 shift values to extract 3 colors from a 32 bits int
	private static final int RED_INT_OFFSET = 24;
	private static final int GREEN_INT_OFFSET = 16;
	private static final int BLUE_INT_OFFSET = 8;

	// 3 masks to extract 3 colors from a short
	private static final int RED_INT_MASK = 0x0000001F;
	private static final int GREEN_INT_MASK = 0x0000003F;
	private static final int BLUE_INT_MASK = 0x0000001F;

	// 3 masks to merge 3 colors into a short 
	private static final int RED_SHORT_MASK = 0x0000F800;
	private static final int GREEN_SHORT_MASK = 0x000007E0;
	private static final int BLUE_SHORT_MASK = 0x0000001F;

	//colors encoded in 32 bits are a byte each. 
	private static final int INT_COLOR_MASK = 0x000000FF;

	private static final int MAX_ALPHA = 255;


	public static void overlay_mouse_pointer(short[] frame_buffer, int[] mouse_pointer_buffer, int x_coordinate, int y_coordinate) {

		if(x_coordinate < FRAME_BUFFER_WIDTH &&  y_coordinate < FRAME_BUFFER_HEIGHT) {

			int pixelRed;
			int pixelGreen;
			int pixelBlue;
			int mouseRed;
			int mouseGreen;
			int mouseBlue;
			int alpha;

			//figure out how much of the mouse pointer we really have to draw
			// since a part of it could be outside the frame

			int croppedMouseWidth = Math.min(FRAME_BUFFER_WIDTH - x_coordinate, MOUSE_POINTER_BUFFER_WIDTH);
			int croppedMouseHeight = Math.min(FRAME_BUFFER_HEIGHT - y_coordinate, MOUSE_POINTER_BUFFER_HEIGHT);

			for (int mouseY = 0 ; mouseY < croppedMouseHeight ; ++mouseY) {
				for (int mouseX = 0 ; mouseX < croppedMouseWidth ; ++mouseX) {

					// begin blending one pixel

					int currentMouseBufferIndex = (mouseY * MOUSE_POINTER_BUFFER_WIDTH) + mouseX;
					int currentFrameBufferIndex = ((y_coordinate + mouseY) * FRAME_BUFFER_WIDTH) + x_coordinate + mouseX;

					pixelRed = (frame_buffer[currentFrameBufferIndex] >> RED_SHORT_OFFSET) & RED_INT_MASK;
					pixelGreen = (frame_buffer[currentFrameBufferIndex] >> GREEN_SHORT_OFFSET) & GREEN_INT_MASK;
					pixelBlue = (frame_buffer[currentFrameBufferIndex] >> BLUE_SHORT_OFFSET) & BLUE_INT_MASK;

					alpha = mouse_pointer_buffer[currentMouseBufferIndex] & INT_COLOR_MASK;

					if (0 < alpha) { // nothing to do if mouse pointer is fully transparent

						mouseRed = (mouse_pointer_buffer[currentMouseBufferIndex] >> RED_INT_OFFSET) & INT_COLOR_MASK;
						mouseGreen = (mouse_pointer_buffer[currentMouseBufferIndex] >> GREEN_INT_OFFSET) & INT_COLOR_MASK;
						mouseBlue = (mouse_pointer_buffer[currentMouseBufferIndex] >> BLUE_INT_OFFSET) & INT_COLOR_MASK;

						pixelRed = ((alpha * mouseRed) + ((MAX_ALPHA - alpha) * pixelRed)); // basic alpha blending
						while (pixelRed > MAX_RED_SHORT) {pixelRed = pixelRed >> 1;} // need to fit the new color value in 16 bits

						pixelGreen = ((alpha * mouseGreen) + ((MAX_ALPHA - alpha) * pixelGreen));
						while (pixelGreen > MAX_GREEN_SHORT) {pixelGreen = pixelGreen >> 1;}

						pixelBlue = ((alpha * mouseBlue) + ((MAX_ALPHA - alpha) * pixelBlue));
						while (pixelBlue > MAX_BLUE_SHORT) {pixelBlue = pixelBlue >> 1;}

						frame_buffer[currentFrameBufferIndex] = (short)((RED_SHORT_MASK & (pixelRed << RED_SHORT_OFFSET)) | (GREEN_SHORT_MASK & (pixelGreen << GREEN_SHORT_OFFSET)) | (BLUE_SHORT_MASK & (pixelBlue << BLUE_SHORT_OFFSET)));
					}

					// end blending one pixel

				}
			}
		}
	}
}