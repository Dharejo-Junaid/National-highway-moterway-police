import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class SlipGenerator {

    JFrame frame=new JFrame();

    // label & text field of left side;
    JLabel l1[]=new JLabel[10];
    JTextField t1[]=new JTextField[10];

    // label & text field of right side;
    JLabel l2[]=new JLabel[11];
    JTextField t2[]=new JTextField[11];

    // button to generate pdf of challan;
    JButton button=new JButton("Generate Slip");

    // font used in this project;
    Font textFont = null;
    Font headingFont = null;

    ImageIcon logo=null;

    // text of labels of left size;
    String[] l1Text={"Beat Name", "Current Time", "Ticket No", "Collection No",
            "Coll: Unit Type", "Date", "Time", "Location", "REG#", "Vehicle"
    };

    // text of labels of right size;
    String[] l2Text={"License No", "License Type", "Challan Count", "Name", "CNIC", "Address",
            "Mobile No", "Doc: Confiscated", "Challan Amount", "Ch: Officer", "Belt No"
    };

    public SlipGenerator() {

        // external "jetbrains mono" & "alkalami" font;
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/java/jetbrains_mono.ttf"));
            textFont = textFont.deriveFont(Font.PLAIN);
            textFont = textFont.deriveFont(13.0f);

            headingFont=Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/java/alkalami.ttf"));
            headingFont = headingFont.deriveFont(Font.BOLD);
            headingFont = headingFont.deriveFont(30.0f);

        } catch (Exception e) {}

        // logo of national highways motorway police;
        logo=new ImageIcon(new ImageIcon("src/main/java/logo.png").getImage()
                .getScaledInstance(70, 70, Image.SCALE_DEFAULT)
        );

        // Main frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("National Highways Motorway Police");
        frame.setIconImage(logo.getImage());
        frame.setLayout(new BorderLayout());
        frame.setSize(980, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // top heading label;
        JLabel heading=new JLabel("National Highway Motorway Police");
        heading.setIcon(logo);
        heading.setPreferredSize(new Dimension(800, 100));
        heading.setFont(headingFont);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setVerticalAlignment(JLabel.CENTER);
        heading.setVerticalTextPosition(JLabel.BOTTOM);
        heading.setIconTextGap(20);
        frame.add(heading, BorderLayout.NORTH);

        // panel to add left and right side panels of input;
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 20));
        frame.add(mainPanel, BorderLayout.CENTER);

        // left side of input;
        JPanel left=new JPanel();
        left.setLayout(null);
        mainPanel.add(left);

        // labels of left side;
        for(int i=0; i<l1.length; i++) {
            l1[i]=new JLabel(l1Text[i]);
            l1[i].setBounds(125, (i*35), 120, 25);
            l1[i].setFont(textFont);
            left.add(l1[i]);
        }

        // text fields of right side;
        for(int i=0; i<t1.length; i++) {
            t1[i]=new JTextField();
            t1[i].setBounds(255, (i*35), 165, 25);
            t1[i].setFont(textFont);
            left.add(t1[i]);
        }

        // right side of input;
        JPanel right=new JPanel();
        right.setLayout(null);
        mainPanel.add(right);

        //labels of right side;
        for(int i=0; i<l2.length; i++) {
            l2[i]=new JLabel(l2Text[i]);
            l2[i].setBounds(25, (i*35), 130, 25);
            l2[i].setFont(textFont);
            right.add(l2[i]);
        }

        // text fields of right side;
        for(int i=0; i<t2.length; i++) {
            t2[i]=new JTextField();
            t2[i].setBounds(165, (i*35), 165, 25);
            t2[i].setFont(textFont);
            right.add(t2[i]);
        }

        // button to generate pdf of slip;
        button.setFont(textFont.deriveFont(20));
        button.setFocusable(false);
        button.addActionListener(e -> {
            try {
                generateSlip(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        frame.add(button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public boolean isAllFilled() {

        for(int i=0; i< t1.length; i++) {
            if(t1[i].getText().trim().equals("")) {
                return false;
            }
        }

        for(int i=0; i< t2.length; i++) {
            if(t2[i].getText().trim().equals("")) {
                return false;
            }
        }

        return true;
    }

    public void generateSlip(ActionEvent e) throws IOException {

        if(! isAllFilled()) {
            JOptionPane.showMessageDialog(this.frame, "Fill all the details first",
                "Error", JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // PDF generating code;
        PdfWriter writer=new PdfWriter(System.getProperty("user.home")
                + "\\Desktop\\" + t2[3].getText() + ".pdf");
        PdfDocument document=new PdfDocument(writer);
        Document doc=new Document(document, new PageSize(300, 670));

        // Logo in pdf;
        ImageData imgData= ImageDataFactory.create("src/main/java/logo.png");
        com.itextpdf.layout.element.Image img=new com.itextpdf.layout.element.Image(imgData);
        img.setWidth(80);
        img.setHeight(80);
        img.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // img.setFixedPosition(100, 580);
        doc.add(img);

        // Fonts
        PdfFont alkalami = PdfFontFactory.createFont("src/main/java/alkalami.ttf", true);
        PdfFont jetbrains = PdfFontFactory.createFont("src/main/java/jetbrains_mono.ttf", true);

        Text headingText=new Text("National Highways Motorway Police")
                .setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(alkalami);
        doc.add(new Paragraph(headingText));

        String text="";
        for(int i=0; i<t1.length; i++) {
            text += l1Text[i] + "   " + t1[i].getText() + "\n";
        }

        for(int i=0; i<t2.length; i++) {
            text += l2Text[i] + "   " + t2[i].getText() + "\n";
        }

        Text tt=new Text(text).setFont(jetbrains).setFontSize(10);
        Paragraph p=new Paragraph(tt);
        doc.add(p);

        document.close();
        doc.close();

        // respond message;
        JOptionPane.showMessageDialog(this.frame,"Slip has been successfully generated",
            "Slip generated", JOptionPane.INFORMATION_MESSAGE
        );
    }
}
