package ordermeal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import meal.model.Meal;
import meal.model.MealMenu;
import meal.model.MeatMenu;

public class orderDish extends JDialog implements ListSelectionListener,ActionListener{

		private JPanel pTop,pCenter; //上部面板，中部面板
		private JPanel pTopLeft,pTopCenter,pTopRight;	//上部中间容器
		private JList jListMeals; //可选菜单列表
		private JList jListOrdered;	//已点菜品
		private JButton btnOrder;	//点菜按钮
		private JButton btnRecommend;	//推荐
		private JButton btnDelect;	//撤销
		private JButton btnSave;	//下单
		
		private MealMenu mealMenu; //菜单信息
		private Meal curMeal;	//当前菜品
		private Meal currentMeal;//当前菜品（用于更新自定义面板的显示）
		
		private DefaultListModel<Meal> ListModel;
		
		File file;	//保存传入的点菜文件
		
		
		public orderDish(JFrame frame, MealMenu mealMenu,String title,File file) {
			//模态对话框
			super(frame,true);
			this.mealMenu=mealMenu;
			this.file = file;
			setTitle(title);
			setSize(900,600);
			setResizable(false);	//不可调整大小
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);//窗口居中/相对于另外一个界面
			//上部
			pTop = new JPanel(new GridLayout(1,3));
			//上部左侧
			pTopLeft = new JPanel(new BorderLayout());
			pTopLeft.add(new JLabel("菜单",JLabel.CENTER),BorderLayout.NORTH);
			
			Vector<String> mealNames = mealMenu.getMealNames();
			jListMeals =new JList(mealNames);
			//设置默认选项第一项
			jListMeals.setSelectedIndex(0);
			//设置默认菜品是第一项
			currentMeal = mealMenu.getDetail(0);
			
			//jListMeals = new JList();
			jListMeals.addListSelectionListener(this);
			pTopLeft.add(new JScrollPane(jListMeals));
			//上部中
			pTopCenter =new JPanel();
			btnOrder = new JButton("点  菜");
			btnRecommend = new JButton("随机推荐");
			btnDelect = new JButton("撤销点菜");
			btnSave = new JButton("下  单");
			
			btnOrder.addActionListener(this);
			btnRecommend.addActionListener(this);
			btnDelect.addActionListener(this);
			btnSave.addActionListener(this);
			
			
			Box box=Box.createVerticalBox();
			box.add(box.createVerticalStrut(20));
			box.add(btnOrder);
			box.add(box.createVerticalStrut(20));
			box.add(btnRecommend);
			box.add(box.createVerticalStrut(20));
			box.add(btnDelect);
			box.add(box.createVerticalStrut(20));
			box.add(btnSave);
			pTopCenter.add(box);
			//上部右
			pTopRight = new JPanel(new BorderLayout());
			
			pTopRight.add(new JLabel("已点",JLabel.CENTER),BorderLayout.NORTH);
			//创建默认的数据模型
			ListModel = new DefaultListModel<Meal>();
			//根据数据模型创建jlist（jlist的内容可以增加和删除）
			jListOrdered = new JList(ListModel);
			
			pTopRight.add(new JScrollPane(jListOrdered));
			
			
			//中部
			pCenter = new ImagePanel();
			
			//向窗口添加面板
			pTop.add(pTopLeft);
			pTop.add(pTopCenter);
			pTop.add(pTopRight);
			add(pTop,BorderLayout.NORTH);
			add(pCenter);
			
			setVisible(true); //显示窗口
		}
		class ImagePanel extends JPanel {
			@Override
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0,0 , getWidth(),getHeight());
				g.setColor(Color.red);
				g.setFont(new Font("宋体",Font.BOLD,15));
				g.drawString(currentMeal.getName(), 320,40);
				g.drawString("价格："+currentMeal.getPrice(), 470,40);
				//Image image;
				//image = new ImageIcon("image/Tulips.jpg").getImage();
				g.drawImage(currentMeal.getImage(), 300, 50, 300, 250, this);
				g.drawString(currentMeal.getDescription(), 200,350);
			}
		}
		@Override
		public void valueChanged(ListSelectionEvent e) {
		//获取选中项的下标
			int index = jListMeals.getSelectedIndex();
			//根据索引找到菜品详情信息
			currentMeal = mealMenu.getDetail(index);
			//通知pCenter重新绘制
			pCenter.repaint();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getSource()==btnOrder) {
				int index = jListMeals.getSelectedIndex();
				Meal meal = mealMenu.getDetail(index);
				
				ListModel.addElement(meal);
			}
			if(e.getSource()==btnDelect) {
				int index = jListOrdered.getSelectedIndex();
				ListModel.removeElementAt(index);
			}
			if(e.getSource()==btnRecommend) {
				Vector<String> mealNames = mealMenu.getMealNames();
				int size = mealNames.size();
				//生成0-size-1的整数
				Random random = new Random();
				int i = random.nextInt(size);
				//设置选中项
				jListMeals.setSelectedIndex(i);
			}
			if(e.getSource()==btnSave) {
				
				RandomAccessFile raf=null;
				try {
					raf = new RandomAccessFile(file, "rw");
					//找到文件末尾
					long len = file.length();
					raf.seek(len);
					//把所以已点的菜品名称和价格存入文件
					for( int i=0;i<ListModel.size();i++)
					{
						
						Meal meal = ListModel.getElementAt(i);
						//meal.getName();
						raf.writeUTF(meal.getName());	//把菜品名称存入文件
						raf.writeDouble(meal.getPrice());
						
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					try {
						if(raf!=null)raf.close();
					} catch (IOException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
				}
				dispose();	//关闭当前窗口
			}
		}
//		public static void main(String[] args) {
//			MeatMenu meatMenu = new MeatMenu();
//			new OrderDish(meatMenu,"荤菜   点菜");
//		}
}