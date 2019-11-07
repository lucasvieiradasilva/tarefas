package br.com.tarefas.gui.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.MathUtils;


public class FrameUtils {
   public static FrameBounds getFrameBounds(int width, int height) throws Exception{
      return getFrameBounds(width, height, null);
   }

	public static FrameBounds getFrameBounds(int width, int height, JFrame frameAnterior) throws Exception {
	   Dimension screenSize = getScreenSize();

	   int posX = 0;
	   int posY = 0;

	   if(frameAnterior == null) {
   		double freeWidth = screenSize.getWidth() - width;
   		double freeHeight = screenSize.getHeight() - height;

   		posX = MathUtils.getInteger(MathUtils.division(freeWidth, 2));

   		posY = MathUtils.getInteger(MathUtils.division(freeHeight, 2));

   		if(posX < 0) {
   			posX = 0;
   		}

   		if(posY < 0) {
   			posY = 0;
   		}

   		return new FrameBounds(posX, posY, width, height);
	   }

      int metadeWidth = MathUtils.getInteger(MathUtils.division(width, 2));
      int metadeHeight = MathUtils.getInteger(MathUtils.division(height, 2));

      int centroX = (int)frameAnterior.getBounds().getCenterX();
      int centroY = (int)frameAnterior.getBounds().getCenterY();

      posX = centroX - metadeWidth;
      posY = centroY - metadeHeight;

	   return new FrameBounds(posX, posY, width, height);
	}

	public static Dimension getScreenSize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
	    return tk.getScreenSize();
	}
}
