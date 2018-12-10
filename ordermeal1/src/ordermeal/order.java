package ordermeal;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import meal.model.MeatMenu;
import meal.model.SoupAndPorridgeMenu;
import meal.model.StapleFoodMenu;
import meal.model.VegetarianMenu;
import ordermeal.orderDish;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
public class order extends JFrame implements ActionListener
{
	JPanel pTop,pCenter,pBottom;//定义面板
	JTextField jtext1,jtext2;//文本区
	JButton btnMeat;//荤菜
	JButton btnVegetarian;//素菜
	JButton btnSoupAndPorridge;//汤粥
	JButton btnStapeFood;//主食
	JButton btnDetail;//点菜详细
	JButton btnStop;//结束点菜
	File file;
	public order()
	{
		setTitle("顾客点菜系统");
		setSize(500,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭时退出整个程序
		setLocationRelativeTo(null);//窗口居中
		setResizable(false);
		//在添加各个部分面板
		pTop=new JPanel();
		pCenter=new JPanel();
		pBottom=new JPanel();
		pCenter.setBorder(new TitledBorder(new LineBorder(Color.blue),"分类点菜"));
	   //以下是各个按钮
		btnMeat=new JButton("荤菜");
		btnVegetarian=new JButton("素菜");
		btnSoupAndPorridge=new JButton("汤粥");
		btnStapeFood=new JButton("主食");
		btnDetail=new JButton("显示点菜明细、结账");
		btnStop=new JButton("结束本次点菜");
		//两个文本框
		jtext1=new JTextField(10);
		jtext2=new JTextField(13);
		jtext2.setEditable(true);
		//界面上部设计
		pTop.add(new JLabel("请输入您的桌号:"));
		pTop.add(jtext1);
		pTop.add(new JLabel("点餐的日期和时间:"));
		pTop.add(jtext2);
		add("North",pTop);
		//界面中部设计
		Box baseBox=Box.createHorizontalBox();//创建基础水平盒子
		//创建垂直盒子1用来装btnMeat，btnStapeFood
		Box box1=Box.createVerticalBox();
		box1.add(btnMeat);
		box1.add(Box.createVerticalStrut(20));//添加盒子之间距离
		box1.add(btnStapeFood);
		baseBox.add(box1);
		//创建垂直盒子2来装btnVegetarian，btnVegetarian
		Box box2=Box.createVerticalBox();
		box2.add(btnSoupAndPorridge);
		box2.add(Box.createVerticalStrut(20));//添加盒子之间距离
		box2.add(btnVegetarian);
		baseBox.add(Box.createHorizontalStrut(50));//添加水平空盒子
		baseBox.add(box2);
		add("Center", pCenter);
		pCenter.add(baseBox);
		//界面底部设计
		add("South", pBottom);
		pBottom.add(btnDetail);
		pBottom.add(btnStop);
		jtext2.setEnabled(false);
		btnMeat.setEnabled(false);
		btnVegetarian.setEnabled(false);
		btnSoupAndPorridge.setEnabled(false);
		btnStapeFood.setEnabled(false);
		btnDetail.setEnabled(false);
		validate();
		btnMeat.addActionListener(this);
		btnDetail.addActionListener(this);
		btnSoupAndPorridge.addActionListener(this);
		btnStapeFood.addActionListener(this);
		btnDetail.addActionListener(this);
		btnVegetarian.addActionListener(this);
		jtext1.addActionListener(this);
		btnStop.addActionListener(this);
		}
	public static void main(String[] args){
		new order();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jtext1)
		{
			try {
				int orderid=Integer.parseInt(jtext1.getText());
				Date date=new Date();
				//时间格式转换
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jtext2.setEnabled(true);
				jtext2.setText(sdf.format(date));
				btnMeat.setEnabled(true);
				btnVegetarian.setEnabled(true);
				btnSoupAndPorridge.setEnabled(true);
				btnStapeFood.setEnabled(true);
				btnDetail.setEnabled(true);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(order.this, "桌号请输入数字！");
				jtext1.setText(null);
			}
		}
		if(e.getSource()==btnMeat) {
			MeatMenu meatMenu=new MeatMenu();
			new orderDish(this,meatMenu,"荤菜          点菜",file);
			
		}
		if(e.getSource()==btnVegetarian) {
			VegetarianMenu vegetarian=new VegetarianMenu();
			new orderDish(this,vegetarian,"素菜          点菜",file);
		}
			
		if(e.getSource()==btnSoupAndPorridge) {
			 SoupAndPorridgeMenu soupAndPorridge=new  SoupAndPorridgeMenu();
			new orderDish(this,soupAndPorridge,"汤粥          点菜",file);
		}
		if(e.getSource()==btnStapeFood) {
			StapleFoodMenu stapeFood=new StapleFoodMenu();
			new orderDish(this,stapeFood, "主食         点菜",file);
		}
		if(e.getSource()==btnDetail) {
			new OrderDetail(this,file);
			try {
				String filename=jtext1.getText()+"号桌点菜清单.txt";
				file=new File(filename);
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(order.this, "桌号请输入数字！");
			}
			
		}
		if(e.getSource()==btnStop) {
			jtext1.setText(null);
			jtext2.setText(null);
			jtext2.setEnabled(false);
			btnMeat.setEnabled(false);
			btnVegetarian.setEnabled(false);
			btnSoupAndPorridge.setEnabled(false);
			btnStapeFood.setEnabled(false);
			btnDetail.setEnabled(false);
		
		}
		
		
	}
	
}
