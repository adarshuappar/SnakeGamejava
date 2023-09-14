import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.jar.Attributes;

public class Board extends JPanel implements ActionListener {
    int B_height = 400;
    int B_width = 400;
    int MAX_DOT = 1600;
    int DOT_size = 10;
    int DOT;
    int[] x= new int[MAX_DOT];
    int[] y= new int[MAX_DOT];
    int applex;
    int appley;
    Image apple,body,head;
    Timer timer;
    int DELAY=150;
    boolean leftdir=true;
    boolean rightdir =false;
    boolean updir=false;
    boolean downdir=false;
    boolean ingame=true;

    Board(){
        TAdapter tAdapter= new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_height,B_width));
       setBackground(Color.black);
       initgame();
        loadimage();

    }
//initailze the game
    public void initgame(){
        DOT=3;
        x[0]=50;
        y[0]=50;
        //intilize the snake postion
        for(int i=0;i<DOT;i++){
            x[i]=x[0]+DOT_size*i;
            y[i]=y[0];
        }

        loacateApple();
        timer = new Timer(DELAY,this);
        timer.start();;

    }
    //load the image
    public void loadimage(){
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        body = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }


    //draw image at particular postion
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        dodrawing(g);
    }
    public void dodrawing(Graphics g){

        if(ingame){
            g.drawImage(apple,applex,appley,this);
            for(int i=0;i<DOT;i++){
                if(i==0){
                    g.drawImage(head,x[0],y[0],this);
                }
                else
                    g.drawImage(body,x[i],y[i],this);
            }
        }else {
            timer.stop();
            gameover(g);
        }
    }
    //random to the apple postion
    public void loacateApple(){
        applex= ((int)(Math.random()*39))*DOT_size;
        appley= ((int)(Math.random()*39))*DOT_size;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(ingame){
            checkApple();
            checkcoll();
            move();
            repaint();
        }

    }
    //make move
    public void move(){
        for(int i=DOT;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftdir)
            x[0]-=DOT_size;
        if(rightdir)
            x[0]+=DOT_size;
        if(updir)
            y[0]-=DOT_size;
        if(downdir)
            y[0]+=DOT_size;
    }
    //implement the snake eating fruit
    public void checkApple(){
        if(applex==x[0] && appley==y[0]){
            DOT++;
            loacateApple();
        }
        //check collosion for the body and border

    }
    public void checkcoll(){
        //collosion wth the body
        for(int i=1;i<DOT;i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                ingame=false;
            }
        }
        //collosin with the border
        if(x[0]<0)
            ingame=false;
        if(y[0]<0)
            ingame=false;
        if(x[0]>=B_width)
            ingame=false;
        if(y[0]>=B_height)
            ingame=false;
    }
    //for the game over and score message
    public void gameover(Graphics g){
        String msg = "Game over";
        int score= (DOT-3)*50;
        String scoremesg = "Score="+Integer.toString(score);
        Font small = new Font("Helvetica",Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);

        g.drawString(msg,(B_width-fontMetrics.stringWidth(msg))/2,B_height/4);
        g.drawString(scoremesg,(B_width-fontMetrics.stringWidth(scoremesg))/2,3*(B_height/4));
    }
    //implement control
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightdir){
                leftdir=true;
                updir=false;
                downdir=false;
            }
            if(key==KeyEvent.VK_UP && !downdir){
                leftdir=false;
                updir=true;
                rightdir=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftdir){
                rightdir=true;
                updir=false;
                downdir=false;
            }
            if(key==KeyEvent.VK_DOWN && !updir){
                leftdir=false;
                rightdir=false;
                downdir=true;
            }
        }
    }
}
