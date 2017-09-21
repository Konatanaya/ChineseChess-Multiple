package ChineseChess3D;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static ChineseChess3D.ChineseChess.ICframe;

/**
 *
 * @author hackless
 */
public class ICstart extends JFrame implements ActionListener {
    JPanel JPimage, JPbutton;
    JLabel JLimage;
    JButton JBstart;
        public ICstart() {
        JPimage = new JPanel();
        JPbutton = new JPanel();
        JLimage = new JLabel(new ImageIcon("data\\ICstart.jpg"));
        JBstart = new JButton("Start");
        
        JBstart.addActionListener(this);
        
        JPimage.add(JLimage);
        JPbutton.add(JBstart);
        
        this.add(JPimage);
        this.add(JPbutton);
        
        this.setSize(640,480);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = this.getSize();
        this.setLocation((screenDimension.width - frameDimension.width) / 2,
        (screenDimension.height - frameDimension.height) / 2);
        
        this.setTitle("Welcome to Chinese Chess");
        this.setPreferredSize(frameDimension);
        this.add(JPimage, BorderLayout.NORTH);
        this.add(JPbutton, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == JBstart){
            this.setVisible(false);
            ICframe.setVisible(true);
        }
    }
    
    
}
