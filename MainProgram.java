package test;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

// 过滤器接口
interface Filter {
    String process(File inputFile) throws IOException;
}

// 文本过滤器
class TextFilter implements Filter {
    public String process(File inputFile) throws IOException {
        // 读取输入文件并进行处理
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line.toUpperCase()).append("\n");
        }
        reader.close();
        // 返回处理后的结果
        return output.toString();
    }
}

// 主程序
class MainProgram {
    public static void main(String[] args) {
        // 创建窗口和界面组件
        JFrame frame = new JFrame("Classic Software Architecture");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel inputFileLabel = new JLabel("Input File:");
        JTextField inputFileField = new JTextField(20);
        JButton processButton = new JButton("Process");
        JTextArea outputTextArea = new JTextArea(10, 30);

        // 处理按钮点击事件
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = inputFileField.getText();
                File inputFile = new File(inputFilePath);

                // 使用管道-过滤器模式处理输入文件
                try {
                    Filter filter = new TextFilter();  // 示例：使用文本过滤器
                    String result = filter.process(inputFile);

                    // 在文本区域显示处理结果
                    outputTextArea.append("Processed Result:\n" + result + "\n");

                    // 将结果输出到新文件
                    String outputFilePath = "D:\\软件体系结构实验\\output.txt";  // 新文件路径
                    File outputFile = new File(outputFilePath);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                    writer.write(result);
                    writer.close();
                    System.out.println("Output file created: " + outputFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 将组件添加到面板
        panel.add(inputFileLabel);
        panel.add(inputFileField);
        panel.add(processButton);
        panel.add(outputTextArea);

        // 将面板添加到窗口并显示
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}