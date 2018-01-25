package com.sdust.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sdust.entity.Point;
import com.sdust.entity.RGBValue;

public class ImageUtils {

	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	/**
	 * 获取指定坐标的RGB值
	 */
	public static RGBValue getRGBByPoint(BufferedImage bufferedImage, int x, int y) {
		int pixel = bufferedImage.getRGB(x, y);
		return  new RGBValue((pixel & 0xff0000) >> 16,(pixel & 0xff00) >> 8,(pixel & 0xff));
	}
	
	/**
	 * 图片切割
	 * @param srcImageFile
	 * @param result 保存路径
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public final static boolean cut(String srcImageFile, String savePath, int x, int y, int width, int height) {
		try {
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcHeight = bi.getHeight(); // 源图宽度
			int srcWidth = bi.getWidth(); // 源图高度
			
			if(x+width > srcWidth || y+height>srcHeight){
				return false;
			}
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);
				ImageFilter cropFilter = new CropImageFilter(x, y, width,height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(savePath));
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}