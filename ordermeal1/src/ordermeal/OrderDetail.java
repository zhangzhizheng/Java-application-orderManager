package ordermeal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.stream.util.EventReaderDelegate;

public class OrderDetail extends JDialog  {
	File file;
	JTextArea jTextInfo;
	public OrderDetail(JFrame frame,File file) {
		super(frame,true);
		setTitle("点菜详情");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//接受点菜详情
		this.file=file;
		//创建文本区并设置字体
		jTextInfo=new JTextArea();
		jTextInfo.setFont(new Font("楷体", Font.BOLD, 20));
		 jTextInfo.setEditable(false);
		 add(new JScrollPane(jTextInfo),BorderLayout.CENTER);
		 jTextInfo.append("-----菜名----------价格---\n");
		 RandomAccessFile rAccessFile=null;
		 double sum=0;
		 try {
			rAccessFile=new RandomAccessFile(file, "r");
			long len=file.length();
			long pos=0;
			while(pos<len) {
				String name=rAccessFile.readUTF();
				double price=rAccessFile.readDouble();
				sum+=price;
				jTextInfo.append(name);
				jTextInfo.append("\t\t\t\t"+price+"\n");
				pos=rAccessFile.getFilePointer();//把当前读到的位置存入到pos
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			// TODO: handle finally clause
			try {
				if(rAccessFile!=null) {
					rAccessFile.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				
			}
		}
		 jTextInfo.append("--------------------------\n");
		 jTextInfo.append("结账：\t\t"+sum);
		 setVisible(true);
		
	}

}