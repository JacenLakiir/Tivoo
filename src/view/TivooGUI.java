package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TivooGUI extends JFrame
{
    private static final String TITLE = "TiVOO";
    private static final Dimension SIZE = new Dimension(900, 325);
    
    private InputPanel myInputView;
    private FilterPanel myFilterView;
    private OutputPanel myOutputView;
    private ControlPanel myControlView;
    
    public TivooGUI ()
    {
        myControlView = new ControlPanel();
        myInputView = new InputPanel();
        myFilterView = new FilterPanel();
        myOutputView = new OutputPanel();
        
        setTitle(TITLE);
        setPreferredSize(SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().add(myControlView, BorderLayout.NORTH);
        getContentPane().add(myInputView, BorderLayout.WEST);
        getContentPane().add(myFilterView, BorderLayout.CENTER);
        getContentPane().add(myOutputView, BorderLayout.EAST);
        
        addMouseListener(new UserAction());

        pack();
        setVisible(true);
    }
    
    public List<String> getInput()
    {
        return myInputView.getInput();
    }

    public List<String> getFilters ()
    {
        return myFilterView.getFilters();
    }
    
    public List<String> getOutput ()
    {
        return myOutputView.getOutput();
    }

    private class UserAction implements MouseListener
    {
        @Override
        public void mouseClicked (MouseEvent e)
        {
            myControlView.enableButtons();
        }
        
        @Override
        public void mouseEntered (MouseEvent e)
        {
            myControlView.enableButtons();
        }
        
        @Override
        public void mouseExited (MouseEvent e)
        {
            myControlView.enableButtons();
        }
        
        @Override
        public void mouseReleased (MouseEvent e)
        {
            myControlView.enableButtons();
        }

        @Override
        public void mousePressed (MouseEvent e)
        {
            myControlView.enableButtons();
        }
    }
    
}
