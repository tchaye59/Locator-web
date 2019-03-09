/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.beans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.almightshell.locator.web.api.PowerCenter;
import net.almightshell.locator.web.dao.PowerCenterDAOBeanLocal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Shell
 */
@ManagedBean
@ViewScoped
public class PowerCenterBean implements Serializable {

    @EJB
    private PowerCenterDAOBeanLocal serviceDAO;

    private List<PowerCenter> selecteds = new LinkedList();

    private PowerCenter powerCenter;

    private int id;

    int sheetNumber = 1;
    int sheetFromLine = 1;

    @PostConstruct
    public void init() {
        powerCenter = new PowerCenter();
    }

    public void initPowerCenter() {
        if (id != 0) {
            this.powerCenter = serviceDAO.getOne(id);
        }
    }

    public String save() {
        serviceDAO.updateOne(powerCenter);
        return "list?faces-redirect=true";
    }

    public String delete() {
        for (PowerCenter selected : selecteds) {
            serviceDAO.deleteOne(selected);
        }
        return "";
    }

    public void handleFileUpload(FileUploadEvent event) throws Exception {
        if (event.getFile() != null) {
            try {
                try (XSSFWorkbook workbook = new XSSFWorkbook(event.getFile().getInputstream())) {

                    XSSFSheet sheet = workbook.getSheetAt(sheetNumber - 1);

                    // we iterate on rows
                    Iterator<Row> rowIt = sheet.iterator();

                    int i = 1;
                    while (rowIt.hasNext()) {
                        if (i >= sheetFromLine) {
                            break;
                        }
                        rowIt.next();
                        i++;
                    }

                    while (rowIt.hasNext()) {
                        Row row = rowIt.next();

                        int pcId = (int) row.getCell(0).getNumericCellValue();

                        PowerCenter pc = serviceDAO.getOne("pcId", pcId);
                        if (pc == null) {
                            pc = new PowerCenter();
                            pc.setPcId(pcId);
                        }

                        pc.setRegion(row.getCell(1).getStringCellValue());
                        pc.setName(row.getCell(2).getStringCellValue());

                        String s = row.getCell(3).getStringCellValue();
                        if (s != null) {
                            s = s.replaceAll("°", "").trim();
                            if (!s.isEmpty()) {
                                pc.setLat(Double.valueOf(s));
                            }
                        }

                        s = row.getCell(4).getStringCellValue();
                        if (s != null) {
                            s = s.replaceAll("°", "").trim();
                            if (!s.isEmpty()) {
                                pc.setLon(Double.valueOf(s));
                            }
                        }

                        serviceDAO.updateOne(pc);

                    }

                }

                Messages.addGlobalInfo("Data Imported.");
            } catch (Exception exception) {
                Messages.addGlobalError(exception.getMessage());
            }

            System.gc();

        }

    }

    public PowerCenterDAOBeanLocal getServiceDAO() {
        return serviceDAO;
    }

    public List<PowerCenter> getAll() {
        return serviceDAO.getAll();
    }

    public List<PowerCenter> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<PowerCenter> selecteds) {
        this.selecteds = selecteds;
    }

    public PowerCenter getPowerCenter() {
        return powerCenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public int getSheetFromLine() {
        return sheetFromLine;
    }

    public void setSheetFromLine(int sheetFromLine) {
        this.sheetFromLine = sheetFromLine;
    }

}
