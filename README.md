# MouseBlender
alpha blending a frame buffer

Question:
A computer system has a frame buffer to store a 2D image which is automatically displayed on an LCD screen. The LCD and framebuffer have a resolution of 640x480. Pixels are stored sequentially in rows, with each subsequent row immediately following the previous in the buffer. The first pixel in the buffer is displayed in the top-left most corner of the LCD. The buffer uses 16-bits to encode each pixel, split into individual red, green & blue components of sizes 5, 6 & 5 bits respectively (blue occupies the least significant bits). A software renderer is required to blend a 32x32 pixel mouse pointer over the frame buffer. The layout of the buffer containing the pointer is the same as the framebuffer, however it uses 32-bits to encode each pixel, split into individual 8-bit red, green, blue & alpha components, respectively (alpha occupies the least significant byte). A value of zero in the alpha channel indicates the pixel is completely transparent and a value of 255 indicates it is completely opaque.

Implement a function as follows:

Function: "overlay_mouse_pointer"
Inputs: The provided frame buffer, frame_buffer, over which the mouse pointer image should be blended
Inputs: Buffer containing mouse pointer image (with per pixel alpha information), mouse_pointer_buffer
Inputs: X co-ordinate of top left pixel of mouse pointer on frame buffer, x_coordinate. Only values within the range 0 -> 639 are permitted.
Inputs: Y co-ordinate of top left pixel of mouse pointer on frame buffer, y_coordinate. Only values within the range 0 -> 479 are permitted.
Outputs: The result of the blend is written back to frame_buffer
Purpose: Blend the image contained in the mouse pointer buffer over the provided frame buffer, at the specified coordinates. The result of the blend should be written back to the frame buffer.

Use any language you choose.