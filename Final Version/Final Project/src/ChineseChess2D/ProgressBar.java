/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import ChineseChess3D.ChineseChess;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import static java.lang.Thread.sleep;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
/**
 *
 * @author Konatan
 */


public class ProgressBar implements ActionListener{
    private JDialog dialog;
    private JProgressBar progressBar;
    private JLabel lbStatus;
    private JButton btnCancel;
    private Window parent; 
    private Thread thread;
    private String statusInfo;
    private ChessBoard2D CB2D;
    private ChineseChess CH3D;
    private MainFrame mf;
    public ProgressBar(ChessBoard2D CB2D,MainFrame mf){
        this.CB2D = CB2D;
        this.mf = mf;
        Thread thread = new Thread()
        {
            public void run()
            {
                int index = 0;
                while(!CB2D.initOK() && index < 3 )
                {
                    try
                    {
                        sleep(1000); 
                        ++index;
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        new ProgressBar(mf, thread, "Loading......");   
    }
    public ProgressBar(ChineseChess CH3D, MainFrame mf){
        this.CH3D = CH3D;
        this.mf = mf;
        Thread thread = new Thread()
        {
            public void run()
            {
                int index = 0;
                while(!CH3D.initOK() && index < 3)
                {
                    try
                    {   
                        sleep(1000); 
                        ++index;
                        System.out.println(CH3D.initOK());
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        new ProgressBar(mf, thread, "Loading......");   
    }

    private ProgressBar(Window parent, Thread thread, String statusInfo)
    {
        this.parent = parent;
        this.thread = thread;
        this.statusInfo = statusInfo;
        initUI();
        startThread();
        dialog.setVisible(true);
    }
    private void initUI()
    {
        if(parent instanceof Dialog)
        {
            dialog = new JDialog((Dialog)parent,  true);
        }
        else if(parent instanceof Frame)
        {
            dialog = new JDialog((Frame)parent,  true);
        }
        else
        {
            dialog = new JDialog((Frame)null,  true);
        }
        final JPanel mainPane = new JPanel(null);
        progressBar = new JProgressBar();
        lbStatus = new JLabel("" + statusInfo);
        btnCancel = new JButton("Cancel");
        progressBar.setIndeterminate(true);
        btnCancel.addActionListener(this);
        mainPane.add(progressBar);
        mainPane.add(lbStatus);
        dialog.getContentPane().add(mainPane);
        dialog.setUndecorated(true);
        dialog.setResizable(true);
        dialog.setSize(390, 100);
        dialog.setLocationRelativeTo(parent); 
            
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); 
      
        mainPane.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent e)
            {
                layout(mainPane.getWidth(), mainPane.getHeight());
            }
        });
    }
    private void startThread()
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    thread.start(); 
                    thread.join();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    dialog.dispose();
                }
            }
        }.start();
    }
    private void layout(int width, int height)
    {
        progressBar.setBounds(20, 20, 350, 15);
        lbStatus.setBounds(20, 50, 350, 25);
        btnCancel.setBounds(width - 85, height - 31, 75, 21);
    }
    public void actionPerformed(ActionEvent e)
    {
        thread.stop();
    } 

}

