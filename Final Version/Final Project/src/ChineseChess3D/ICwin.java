package ChineseChess3D;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hackless
 */
public class ICwin extends JFrame {
    JPanel JPimage;
    JLabel JLimage;
        public ICwin(String winside, boolean side) {
        JPimage = new JPanel();
        if(side == false)
            JLimage = new JLabel(new ImageIcon("data\\blackwin.jpg"));
        else
            JLimage = new JLabel(new ImageIcon("data\\whitewin.jpg"));
        
        
        JPimage.add(JLimage);
        
        this.add(JPimage);
        
        this.setSize(640,480);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = this.getSize();
        this.setLocation((screenDimension.width - frameDimension.width) / 2,
        (screenDimension.height - frameDimension.height) / 2);
        
        this.setTitle(winside);
        this.setPreferredSize(frameDimension);
        this.add(JPimage, BorderLayout.NORTH);
//        this.add(JPbutton, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}