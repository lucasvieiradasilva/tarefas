package br.com.tarefas.gui.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.MathUtils;


public class FrameUtils {
	public static FrameBounds getFrameBounds(int width, int height) throws Exception {
		Dimension screenSize = getScreenSize();

		double freeWidth = screenSize.getWidth() - width;
		double freeHeight = screenSize.getHeight() - height;

		int posX = MathUtils.getInteger(MathUtils.division(freeWidth, 2));

		int posY = MathUtils.getInteger(MathUtils.division(freeHeight, 2));

		if(posX < 0) {
			posX = 0;
		}

		if(posY < 0) {
			posY = 0;
		}

		return new FrameBounds(posX, posY, width, height);
	}

	public static Dimension getScreenSize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
	    return tk.getScreenSize();
	}
}
