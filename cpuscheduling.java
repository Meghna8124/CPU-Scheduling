
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meghna Chaudhary
 * ROLL NO: 1710110212
 */
public class cpuscheduling extends JFrame implements ActionListener{
    JComboBox cbNOP;
    
    JCheckBox cbFCFS = new JCheckBox("FCFS");
    JCheckBox cbRR = new JCheckBox("Round Robin");
    JCheckBox cbPSJF = new JCheckBox("Preemptive SJF");
    JCheckBox cbNPSJF = new JCheckBox("Non-preemptive SJF");
    JCheckBox cbPPri = new JCheckBox("Preemptive Priority");
    JCheckBox cbNPPri = new JCheckBox("Non-preemptive Priority");
    
    JTextField tfBT = new JTextField(20);
    JTextField tfAT = new JTextField(20);
    JTextField tfPri = new JTextField(6);
    JTextField tfQ = new JTextField(3);
    
    boolean[] state = new boolean[6]; 
    
    ArrayList<Integer> bt = new ArrayList<>();
    ArrayList<Integer> at = new ArrayList<>();
    ArrayList<Integer> pri = new ArrayList<>();
    ArrayList<String> p = new ArrayList<>();
    
    ArrayList<Integer> wt = new ArrayList<>();
    ArrayList<Integer> tat = new ArrayList<>();

    cpuscheduling(){
        
        setLayout(new GridLayout(10,1));
        
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JPanel p10 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        JPanel p7 = new JPanel();
        JPanel p8 = new JPanel();
        JPanel p9 = new JPanel();
        
        
        p5.setLayout(new GridLayout(1,2));
        p6.setLayout(new GridLayout(1,2));
        p7.setLayout(new GridLayout(1,2));
        
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p10);
        add(p5);
        add(p6);
        add(p7);
        add(p8);
        add(p9);
        
        JLabel lblBT = new JLabel("Burst Time");
        JLabel lblAT = new JLabel("Arrival Time");
        JLabel lblPri = new JLabel("Priority (lower the number, higher the priority)");
        JLabel lblNOP = new JLabel("Number of Processes");
        JLabel lblQ = new JLabel("Time Quantum (for RR, default 100)");
        
        cbNOP = new JComboBox();
        for(int i = 1; i<7; i++)
            cbNOP.addItem(i);

        
        
        p1.add(lblNOP);
        p1.add(cbNOP);
        
        p2.add(lblBT);
        p2.add(tfBT);
        
        p3.add(lblAT);
        p3.add(tfAT);
        
        p4.add(lblPri);
        p4.add(tfPri);
        
        p10.add(lblQ);
        p10.add(tfQ);
        
        p5.add(cbFCFS);
        p5.add(cbRR);
        p6.add(cbPSJF);
        p6.add(cbNPSJF);
        p7.add(cbPPri);
        p7.add(cbNPPri);
        
        JButton cBtn = new JButton("Compute");
        p8.add(cBtn);
        
        cBtn.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        int nop = (int) cbNOP.getSelectedItem();
        String strBT = tfBT.getText().replaceAll("\\s", "");
        String strAT = tfAT.getText().replaceAll("\\s", "");
        String strPri = tfPri.getText().replaceAll("\\s", "");
        
        p.clear();
        for(int i = 1; i<=nop; i++){
            p.add("p"+i);
        }
        
        String[] BT = (strBT.split(","));
        String[] AT = (strAT.split(","));
        String[] PRI = new String[nop];
        if(strPri.equals("")){
            for(int i = 0; i<nop; i++){
                PRI[i] = ""+0;
            }
        }
        else{
            PRI = strPri.split(",");
        }
        
        
        
        if(BT.length<nop){
            JOptionPane.showMessageDialog(null, "Too few values of BT provided");
            tfBT.setText("");
            return;
        }
        if(BT.length>nop){
            JOptionPane.showMessageDialog(null, "Too many values of BT provided");
            tfBT.setText("");
            return;
        }
        if(AT.length<nop){
            JOptionPane.showMessageDialog(null, "Too few values of AT provided");
            tfAT.setText("");
            return;
        }
        if(AT.length>nop){
            JOptionPane.showMessageDialog(null, "Too many values of AT provided");
            tfAT.setText("");
            return;
        }
        if(PRI.length<nop){
            JOptionPane.showMessageDialog(null, "Too few values of priorities provided");
            tfPri.setText("");
            return;
        }
        if(PRI.length>nop){
            JOptionPane.showMessageDialog(null, "Too many values of priorities provided");
            tfPri.setText("");
            return;
        }
        
        at.clear();
        bt.clear();
        pri.clear();
        
        for (int i = 0; i < nop; i++) {
               try{
                   at.add(Integer.parseInt(AT[i]));
                   bt.add(Integer.parseInt(BT[i]));
                   pri.add(Integer.parseInt(PRI[i]));
               }
               catch(Exception e){
                   JOptionPane.showMessageDialog(null, "Invalid input given");
               }
        }
        
        for (int i = 0; i < nop; i++) {
            wt.add(0);
            tat.add(0);
        }
        
        
        int n = at.size();
                for (int i = 0; i < n-1; i++){ 
                    for (int j = 0; j < n-i-1; j++){ 
                        if (at.get(j) > at.get(j+1)) 
                        { 
                        //SWAP
                        int temp = at.get(j); 
                        at.set(j, at.get(j+1));
                        at.set(j+1, temp);
                        
                        String tem = p.get(j); 
                        p.set(j, p.get(j+1));
                        p.set(j+1, tem);
                        
                        int temp1 = bt.get(j); 
                        bt.set(j, bt.get(j+1));
                        bt.set(j+1, temp1);
                        
                        int temp2 = pri.get(j); 
                        pri.set(j, pri.get(j+1));
                        pri.set(j+1, temp2);
                         }
                    }
                }
                
        
        if(cbFCFS.isSelected()){
            state[0] = true;
        }
        if(cbRR.isSelected()){
            state[1] = true;
        }
        if(cbPSJF.isSelected()){
            state[2] = true;
        }
        if(cbNPSJF.isSelected()){
            state[3] = true;
        }
        if(cbPPri.isSelected()){
            state[4] = true;
        }
        if(cbNPPri.isSelected()){
            state[5] = true;
        }
        
        JFrame f1 = new JFrame();
        
        DrawPanel55 d1 = new DrawPanel55();
        f1.add(d1);
        
        f1.setSize(570,900);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
    }
    
    public static void main(String args[]){
        cpuscheduling c = new cpuscheduling();
        
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setSize(400, 500);
        c.setVisible(true);
    }
    
    class DrawPanel55 extends JPanel{
        int y = 0;
        int x, width, index;
        int smallestAT;
        
        DecimalFormat df2 = new DecimalFormat(".##");
        
        double sumW, avg, sumT, avgT;
        DrawPanel55(){
            
        }
        
         @Override
        protected void paintComponent(Graphics g){
            y = 0;
            super.paintComponent(g);
            
            //FOR FIRST COME FIRST SERVE
            if(state[0]){
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("First Come First Serve", 25, y+25);
                g.setFont(old);
                y+=40;
                drawGantt(g);
            }
            
            
            //FOR ROUND ROBIN
            if(state[1]){
                sumW = 0;
                
                int q = 100;
                
                try{
                    q = Integer.parseInt(tfQ.getText());
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Enter valid time Quantum for RR!");
                }
                
                
                String len;
                x = 25;
                y = y + 25;
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("Round Robin", 25, y+25);
                g.setFont(old);
                
                y+=40;
                
                smallestAT = at.get(0);
                x = x + smallestAT;
                int x1 = x + smallestAT*10;
                
                g.drawString(""+smallestAT, x1, y+35);
                
                ArrayList<Integer> vbt = new ArrayList<>(bt);
                ArrayList<Integer> vat = new ArrayList<>(at);
                ArrayList<String> vp = new ArrayList<>(p);
                
                int currTime = vat.get(0);
                
                while(vbt.isEmpty()==false){
                    for (int i = 0; i < vbt.size(); i++) {
                        if(vat.get(i)<=currTime){
                            if(vbt.get(i)<q){
                                width = vbt.get(i);
                            }
                            else{
                                width = q;
                            }
                            
                            vbt.set(i, vbt.get(i)-width);
                                                        
                            currTime+=width;
                            //draw GanttChart
                            g.drawRect(x1, y, width*10, 20);
                            g.drawString(vp.get(i), x1+2, y+14);
                            x+=width;
                            x1+=width*10;
                            len = (x-25)+"";
                            g.drawString(len, x1, y+35);
                            
                            if(vbt.get(i)<=0){
                                int k = p.indexOf(vp.get(i));
                                
                                
                                sumW = sumW + currTime - vat.get(i) - bt.get(k);
                                wt.set(k, currTime - vat.get(i) - bt.get(k));
                                
                                
                                tat.set(k, currTime - vat.get(i));
                                
                                vbt.remove(i);
                                vat.remove(i);
                                vp.remove(i);
                                
                                i--;
                            }
                        }
                    }
                }
                avg = sumW/bt.size();
                y += 50;
                String strTAT = "Turnaround time: ";
                String strWT = "Waiting time: ";
                sumT = 0;
            
                for (int i = 0; i < tat.size(); i++) {
                    sumT = sumT + tat.get(i);
                
                    strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
                }
                for (int i = 0; i < wt.size(); i++) {
                    strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
                }
                avgT = sumT/tat.size();
                strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
                strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
                g.drawString(strTAT, 25, y);
                y+=20;
                g.drawString(strWT, 25, y);
            }
            
            
            //FOR PREEMPTIVE SJF
            if(state[2]){
               
               sumW = 0;
               
               x = 25;
                y = y + 25;
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("Preemptive Shortest Job First", 25, y+25);
                g.setFont(old);
                
                y+=40;
                
                smallestAT = at.get(0);
                x = x + smallestAT;
                int x1 = x + smallestAT*10;
                
                g.drawString(""+smallestAT, x1, y+35);
                
                ArrayList<Integer> vbt = new ArrayList<>(bt);
                ArrayList<Integer> vat = new ArrayList<>(at);
                ArrayList<String> vp = new ArrayList<>(p);
                int min, currTime, ind;
                String len;
                currTime = vat.get(0);
                
                 min = vbt.get(0);
                    
                    ind = 0;
                    
                    for (int i = 1; i < vbt.size(); i++) {
                        if(vbt.get(i)<min && vat.get(i)<=currTime){
                            min = vbt.get(i);
                            ind = i;
                        }
                    }
                
                int j = ind, flag;
                
                while(vbt.isEmpty()==false){
                    
                    min = vbt.get(ind);
                    
                    
                    flag = 0;
                    for (int i = ind+1; i < vbt.size(); i++) {
                        if(vbt.get(i)<min-(vat.get(i)-vat.get(ind))/*remainng time*/&& vat.get(i)<currTime+vbt.get(ind)/*updated time*/){
                            j = i;
                            flag = 1;
                            if(i<(vat.size()-1)&&vat.get(i+1)>vat.get(i)){
                                break;
                            }
                        }
                    }
                    
                    
                    //printing the Gantt Chart
                    
                    if(flag == 1){
                        width = vat.get(j)-vat.get(ind);
                    }
                    else{
                        width = vbt.get(ind);
                        min = Integer.MAX_VALUE;
                        for (int i = 0; i < vbt.size(); i++) {
                            if(i!=ind && vbt.get(i)<min && vat.get(i)<=currTime+width){
                                min = vbt.get(i);
                                j = i;
                            }
                        }
                    }
                    
                    //System.out.println("width = " + width);
                    
                    currTime += width;
                    
                    g.drawRect(x1, y, width*10, 20);
                    g.drawString(vp.get(ind), x1+2, y+14);
                    x+=width;
                    x1+=width*10;
                    
                    len = (x-25)+"";
                    g.drawString(len, x1, y+35);
                    
                    vbt.set(ind, vbt.get(ind)-width);
                    
                    if(vbt.get(ind)<=0){
                        int k = p.indexOf(vp.get(ind));
                        sumW += currTime-vat.get(ind)-bt.get(k);
                        wt.set(k, currTime-vat.get(ind)-bt.get(k));
                        tat.set(k, currTime-vat.get(ind));
                        
                        vbt.remove(ind);
                        vat.remove(ind);
                        vp.remove(ind);
                        if(ind<j){
                            j--;
                        }
                    }
                    
                    
                    ind = j;
                    
                }
                avg = sumW/bt.size();
                y += 50;
                String strTAT = "Turnaround time: ";
            String strWT = "Waiting time: ";
            sumT = 0;
            
            for (int i = 0; i < tat.size(); i++) {
                sumT = sumT + tat.get(i);
                
                strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
            }
            for (int i = 0; i < wt.size(); i++) {
                strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
            }
            avgT = sumT/tat.size();
            strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
            strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
            g.drawString(strTAT, 25, y);
            y+=20;
            g.drawString(strWT, 25, y);
           }
            
            
            //FOR NON PREEMPTIVE SJF
            if(state[3]){
                
                sumW = 0;
                
                x = 25;
                y = y + 25;
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("Non Preemptive Shortest Job First", 25, y+25);
                g.setFont(old);
                
                y+=40;
                
                smallestAT = at.get(0);
                x = x + smallestAT;
                int x1 = x + smallestAT*10;
                
                g.drawString(""+smallestAT, x1, y+35);
                
                ArrayList<Integer> vbt = new ArrayList<>(bt);
                ArrayList<Integer> vat = new ArrayList<>(at);
                ArrayList<String> vp = new ArrayList<>(p);
                int min, currTime, ind;
                String len;
                currTime = vat.get(0);
                while(vbt.isEmpty()==false){
                    min = vbt.get(0);
                    
                    ind = 0;
                    
                    for (int i = 1; i < vbt.size(); i++) {
                        if(vbt.get(i)<min && vat.get(i)<=currTime){
                            min = vbt.get(i);
                            ind = i;
                        }
                    }
                    currTime = currTime + vbt.get(ind);
                    
                    //printing the Gantt Chart
                    width = vbt.get(ind);
                    g.drawRect(x1, y, width*10, 20);
                    g.drawString(vp.get(ind), x1+2, y+14);
                    x+=vbt.get(ind);
                    x1+=width*10;
                    
                    len = (x-25)+"";
                    g.drawString(len, x1, y+35);
                    
                    int k = p.indexOf(vp.get(ind));
                    sumW += currTime-vat.get(ind)-bt.get(k);
                    
                    wt.set(k, currTime-vat.get(ind)-bt.get(k));
                    tat.set(k, currTime-vat.get(ind));
                
                    vbt.remove(ind);
                    vat.remove(ind);
                    vp.remove(ind);
                }
                avg = sumW/bt.size();
                y += 50;
                String strTAT = "Turnaround time: ";
            String strWT = "Waiting time: ";
            sumT = 0;
            
            for (int i = 0; i < tat.size(); i++) {
                sumT = sumT + tat.get(i);
                
                strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
            }
            for (int i = 0; i < wt.size(); i++) {
                strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
            }
            avgT = sumT/tat.size();
            strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
            strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
            g.drawString(strTAT, 25, y);
            y+=20;
            g.drawString(strWT, 25, y);
            }
            
            
            //FOR PREEMPTIVE PRIORITY
            if(state[4]){
                sumW = 0;
                
                x = 25;
                y = y + 25;
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("Preemptive Priority", 25, y+25);
                g.setFont(old);
                
                y+=40;
                
                smallestAT = at.get(0);
                x = x + smallestAT;
                int x1 = x + smallestAT*10;
                
                g.drawString(""+smallestAT, x1, y+35);
                
                ArrayList<Integer> vbt = new ArrayList<>(bt);
                ArrayList<Integer> vat = new ArrayList<>(at);
                ArrayList<Integer> vpri = new ArrayList<>(pri);
                ArrayList<String> vp = new ArrayList<>(p);
                int min, currTime, ind;
                String len;
                currTime = vat.get(0);
                
                 min = vpri.get(0);
                    
                    ind = 0;
                    
                    for (int i = 1; i < vbt.size(); i++) {
                        if(vpri.get(i)<min && vat.get(i)<=currTime){
                            min = vpri.get(i);
                            ind = i;
                        }
                    }
                
                int j = ind, flag;
                
                while(vbt.isEmpty()==false){
                    
                    min = vpri.get(ind);
                    
                    //System.out.println("min = " + min);
                    
                    flag = 0;
                    for (int i = 0; i < vbt.size(); i++) {
                        if(vpri.get(i)<min && vat.get(i)<currTime+vbt.get(ind)/*updated time*/){
                            j = i;
                            flag = 1;
                            if(i<(vat.size()-1)&&vat.get(i+1)>vat.get(i)){
                                break;
                            }
                            
                        }
                    }
                    
                    //currTime = currTime + vbt.get(ind);
                    
                    //printing the Gantt Chart
                    
                    if(flag == 1){
                        width = vat.get(j)-vat.get(ind);
                    }
                    else{
                        width = vbt.get(ind);
                        min = Integer.MAX_VALUE;
                        for (int i = 0; i < vpri.size(); i++) {
                            if(i!=ind && vpri.get(i)<min && vat.get(i)<=currTime+width){
                                min = vpri.get(i);
                                j = i;
                            }
                        }
                    }
                    
                    //System.out.println("width = " + width);
                    
                    currTime += width;
                    
                    g.drawRect(x1, y, width*10, 20);
                    g.drawString(vp.get(ind), x1+2, y+14);
                    x+=width;
                    x1+=width*10;
                    
                    len = (x-25)+"";
                    g.drawString(len, x1, y+35);
                    
                    vbt.set(ind, vbt.get(ind)-width);
                    
                    if(vbt.get(ind)<=0){
                        
                        int k = p.indexOf(vp.get(ind));
                        //sumW += currTime-vat.get(ind)-bt.get(k);
                        
                        wt.set(k, currTime-vat.get(ind)-bt.get(k));
                        tat.set(k, currTime-vat.get(ind));
                        
                        vbt.remove(ind);
                        vat.remove(ind);
                        vpri.remove(ind);
                        vp.remove(ind);
                        if(ind<j){
                            j--;
                        }
                    }
                    
                    
                    ind = j;
                    
                }
                
                
                y += 50;
                String strTAT = "Turnaround time: ";
            String strWT = "Waiting time: ";
            sumT = 0;
            
            for (int i = 0; i < tat.size(); i++) {
                sumT = sumT + tat.get(i);
                
                strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
            }
            for (int i = 0; i < wt.size(); i++) {
                sumW = sumW + wt.get(i);
                strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
            }
            avg = sumW/bt.size();
                
                
            avgT = sumT/tat.size();
            strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
            strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
            g.drawString(strTAT, 25, y);
            y+=20;
            g.drawString(strWT, 25, y);
            }
            
            
            //FOR NON PREEMPTIVE PRIORITY
            if(state[5]){
                
                sumW = 0;
                
                x = 25;
                y = y + 25;
                
                Font old = g.getFont();
                Font newF = new Font( old.getFontName(), Font.BOLD, (int) (float) (old.getSize()*1.2));
                g.setFont(newF);
                g.drawString("Non Preemptive Priority", 25, y+25);
                g.setFont(old);
                
                y+=40;
                
                smallestAT = at.get(0);
                x = x + smallestAT;
                int x1 = x + smallestAT*10;
                
                g.drawString(""+smallestAT, x1, y+35);
                
                ArrayList<Integer> vbt = new ArrayList<>(bt);
                ArrayList<Integer> vat = new ArrayList<>(at);
                ArrayList<Integer> vpri = new ArrayList<>(pri);
                ArrayList<String> vp = new ArrayList<>(p);
                int min, currTime, ind;
                String len;
                currTime = vat.get(0);
                while(vbt.isEmpty()==false){
                    min = vpri.get(0);
                    
                    ind = 0;
                    
                    for (int i = 1; i < vbt.size(); i++) {
                        if(vpri.get(i)<min && vat.get(i)<=currTime){
                            min = vpri.get(i);
                            ind = i;
                        }
                    }
                    currTime = currTime + vbt.get(ind);
                    
                    //printing the Gantt Chart
                    width = vbt.get(ind);
                    g.drawRect(x1, y, width*10, 20);
                    g.drawString(vp.get(ind), x1+2, y+14);
                    x+=vbt.get(ind);
                    x1+=width*10;
                    
                    len = (x-25)+"";
                    g.drawString(len, x1, y+35);
                    
                    int k = p.indexOf(vp.get(ind));
                    sumW += currTime-vat.get(ind)-bt.get(k);
                    
                    wt.set(k, currTime-vat.get(ind)-bt.get(k));
                    tat.set(k, currTime-vat.get(ind));
                    
                    vbt.remove(ind);
                    vat.remove(ind);
                    vpri.remove(ind);
                    vp.remove(ind);
                }
                avg = sumW/bt.size();
                y += 50;
                String strTAT = "Turnaround time: ";
            String strWT = "Waiting time: ";
            sumT = 0;
            
            for (int i = 0; i < tat.size(); i++) {
                sumT = sumT + tat.get(i);
                
                strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
            }
            for (int i = 0; i < wt.size(); i++) {
                strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
            }
            avgT = sumT/tat.size();
            strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
            strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
            g.drawString(strTAT, 25, y);
            y+=20;
            g.drawString(strWT, 25, y);
            }
        }
        
        public void drawGantt(Graphics g){
            
            sumW = 0;
            
            x = 25;
            String len;
            index = 0;
            smallestAT = at.get(0);
            x = x + smallestAT;
            
            int x1 = x + smallestAT*10;
            
            g.drawString(""+smallestAT, x1, y+35);
            while(index<bt.size()){
                width = bt.get(index);
                g.drawRect(x1, y, width*10, 20);
                g.drawString(p.get(index), x1+2, y+14);
                x+=bt.get(index);
                
                x1+=width*10;
                
                len = (x-25)+"";
                g.drawString(len, x1, y+35);
                
                sumW += x-25-at.get(index)-bt.get(index);
                wt.set(index, x-25-at.get(index)-bt.get(index));
                tat.set(index, x-25-at.get(index));
                
                index++;      
            }
            
            avg = sumW/bt.size();
            y += 50;
            
            String strTAT = "Turnaround time: ";
            String strWT = "Waiting time: ";
            sumT = 0;
            
            for (int i = 0; i < tat.size(); i++) {
                sumT = sumT + tat.get(i);
                
                strTAT = strTAT + p.get(i) + "=" + tat.get(i) + ", ";
            }
            for (int i = 0; i < wt.size(); i++) {
                strWT = strWT + p.get(i) + "=" + wt.get(i) + ", ";
            }
            avgT = sumT/tat.size();
            strTAT = strTAT.substring(0, strTAT.lastIndexOf(",")) + ". Average TAT = " + df2.format(avgT);
            strWT = strWT.substring(0, strWT.lastIndexOf(",")) + ". Average WT = " + df2.format(avg);
            
            g.drawString(strTAT, 25, y);
            y+=20;
            g.drawString(strWT, 25, y);
        }
            
    }
    
}
