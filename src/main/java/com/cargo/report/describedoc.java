/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.report;

import com.cargo.storage.dao.OrderDAO;
import com.cargo.storage.model.Order;
import com.cargo.storage.model.Path;
import com.cargo.storage.model.Products;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

/**
 *
 * @author yura
 */
public class describedoc {
    
    OrderDAO orddao;
    
    int idord;
    
   public describedoc(int idord){
    
        this.idord = idord;
        try {
            //orddao = new OrderDAO();
          //  orddao.get
        } catch (Exception ex) {
            Logger.getLogger(describedoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    Path path;
//    List<Order> list;
   public void create(File file) throws SQLException, Exception{
        
        orddao = new OrderDAO();
        
        String nameOrd = orddao.getOrder(idord).getName();
        String from = orddao.getFrom(idord).getStorehouse().getName();
        String to = orddao.getTo(idord).getStorehouse().getName();
        
        String dateB = orddao.getOrder(idord).getDateOfBirth();
        String dateF = orddao.getOrder(idord).getDateOfFinish();
        
        List<Products> listProdOfOrd = null;
        listProdOfOrd = orddao.getProductsOfOrder(idord);
        
        List<Path> listPath = null;
        listPath = orddao.getPath(idord);
        
        
        XWPFDocument doc = new XWPFDocument();
      //  CTSectPr ctSectPr = doc.getDocument().getBody().addNewSectPr();
           
     //   XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, ctSectPr);
        XWPFParagraph bodyParagraph = doc.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setItalic(false);
            paragraphConfig.setFontSize(12);
            paragraphConfig.setColor("000000");
            paragraphConfig.setFontSize(14);
            paragraphConfig.setFontFamily("Times New Roman");
            paragraphConfig.setText("Отчёт о заказе: " + nameOrd);
            paragraphConfig.addBreak();
            
             XWPFParagraph bodyParagraph2 = doc.createParagraph();
            bodyParagraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun paragraphConfig2 = bodyParagraph2.createRun();
            //paragraphConfig2.setItalic(true);
            paragraphConfig2.setFontSize(14);
            paragraphConfig2.setFontFamily("Times New Roman");
            paragraphConfig2.setText("Откуда:  " + from + "  Куда:  " + to );
            paragraphConfig2.addBreak();
    
            paragraphConfig2.setText("Дата выпуска: " + dateB);
            paragraphConfig2.addBreak();
            
            paragraphConfig2.setText("Дата исполнения: " + dateF);
            paragraphConfig2.addBreak();
            
            paragraphConfig2.setText(" " );
            paragraphConfig2.addBreak();
            
            paragraphConfig2.setText("Маршрут заказа: ");
            paragraphConfig2.addBreak();
            
            for (int i = 0 ; i< listPath.size(); i++){
                
                String fromP = listPath.get(i).getPointFrom().getStorehouse().getName();
                String rolefromP = listPath.get(i).getPointFrom().getNameOfRole();
                String toP = listPath.get(i).getPointTo().getStorehouse().getName();
                String roletoP = listPath.get(i).getPointTo().getNameOfRole();
                String transport = listPath.get(i).getTransport();
                String time = listPath.get(i).getTime();
                paragraphConfig2.setText(rolefromP + ": " + fromP + "-->" + roletoP + ": " + toP);
                paragraphConfig2.addBreak();
                paragraphConfig2.setText("Транспорт: " + transport + " Время: " + time);
                paragraphConfig2.addBreak();
                paragraphConfig2.addBreak();
            
            }
            
             paragraphConfig2.addBreak();
             paragraphConfig2.addBreak();
             paragraphConfig2.addBreak();
             paragraphConfig2.addBreak();
             paragraphConfig2.setText("Состав заказа: ");
             paragraphConfig2.addBreak();
              
              for (int i = 0; i < listProdOfOrd.size(); i++) {
              
                  String nameProd = listProdOfOrd.get(i).getElement().getName();
                  String count = String.valueOf(listProdOfOrd.get(i).getCount());
                  
                  paragraphConfig2.setText("Продукт: " + nameProd + " Количество: " + count);
                  paragraphConfig2.addBreak();
                  
              
              }
              
            FileOutputStream outputStream = new FileOutputStream(file);

            doc.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
    }
    
}
