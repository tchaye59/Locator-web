/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.almightshell.locator.web.api.Event;
import net.almightshell.locator.web.dao.EventDAOBeanLocal;
import net.almightshell.locator.web.dao.PowerCenterDAOBeanLocal;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Shell
 */
@ManagedBean
@ViewScoped
public class EventBean implements Serializable {

    @EJB
    private EventDAOBeanLocal serviceDAO;

    @EJB
    private PowerCenterDAOBeanLocal pcServiceDAO;

    private LazyDataModel<Event> lazyModel;

    private List<Event> selecteds = new LinkedList();

    private Event event;

    private int id;

    int sheetNumber = 1;
    int sheetFromLine = 1;

    @PostConstruct
    public void init() {
        event = new Event();

        lazyModel = new LazyDataModel<Event>() {
            @Override
            public List<Event> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
                Map<String, String> map = new HashMap<>();
                if (multiSortMeta != null) {
                    for (SortMeta sortMeta : multiSortMeta) {
                        map.put(sortMeta.getSortField(), sortMeta.getSortOrder().toString());
                    }
                }
                return serviceDAO.getAll(first, pageSize, map, filters);
            }

            @Override
            public List<Event> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                return serviceDAO.getAll(first, pageSize, sortField, sortOrder.toString(), filters);
            }

            @Override
            public Object getRowKey(Event object) {
                return object.getId();
            }

            public Event get(int index) {
                try {
                    return ((List<Event>) getWrappedData()).get(index);
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            public int getRowCount() {
                return serviceDAO.count().intValue();
            }

            @Override
            public Event getRowData(String rowKey) {
                if (rowKey != null && !rowKey.isEmpty()) {
                    return serviceDAO.getOne(Integer.valueOf(rowKey.trim()));
                }
                return null;
            }

        };
    }

    public void initEvent() {
        if (id != 0) {
            this.event = serviceDAO.getOne(id);
        }
    }

    public String save() {
        serviceDAO.updateOne(event);
        return "list?faces-redirect=true";
    }

    public String delete() {
        for (Event selected : selecteds) {
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

                        String eventId = ((int) (row.getCell(0).getNumericCellValue())) + "";

                        Event e = serviceDAO.getOne("eventId", eventId);
                        if (e == null) {
                            e = new Event();
                            e.setEventId(eventId);
                        }
                        String s = row.getCell(1).getStringCellValue().trim();
                        if (s != null && !s.isEmpty()) {
                            e.setEventTime(new SimpleDateFormat("yyyy-MM-dd / hh:mm:ss").parse(s));
                        }

                        e.setScadaCategory(row.getCell(2).getStringCellValue());
                        e.setPriorityCode((int) row.getCell(3).getNumericCellValue());
                        e.setRegion(row.getCell(4).getStringCellValue());
                        e.setSubstation(row.getCell(7).getStringCellValue());
                        e.setDeviceType(row.getCell(8).getStringCellValue());
                        e.setDevice(getValue(row.getCell(9)));
                        e.setEvent_message(getValue(row.getCell(10)));

                        s = row.getCell(5).getStringCellValue();
                        if (s != null) {
                            s = s.replaceAll("°", "").trim();
                            if (!s.isEmpty()) {
                                e.setLat(Double.valueOf(s));
                            }
                        }

                        s = row.getCell(6).getStringCellValue();
                        if (s != null) {
                            s = s.replaceAll("°", "").trim();
                            if (!s.isEmpty()) {
                                e.setLon(Double.valueOf(s));
                            }
                        }

                        serviceDAO.updateOne(e);

                    }

                }

                Messages.addGlobalInfo("Data Imported.");
            } catch (Exception e) {
                e.printStackTrace();
                Messages.addGlobalError(e.getMessage());
            }

            System.gc();

        }

    }

    public String getValue(Cell cellule) throws Exception {
        if (cellule == null) {
            return null;
        }
        switch (cellule.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                return String.valueOf((int) cellule.getNumericCellValue());
            case HSSFCell.CELL_TYPE_BLANK:
                return null;
            case HSSFCell.CELL_TYPE_STRING:
                String s = cellule.getStringCellValue();
                s = s != null ? s.trim() : null;
                if (s != null && s.equals("-")) {
                    return null;
                }
                return s;
        }

        return "";
    }

    public Date getDateValue(Cell cellule) throws Exception {
        if (cellule == null) {
            return null;
        }
        switch (cellule.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                return cellule.getDateCellValue();
            case HSSFCell.CELL_TYPE_BLANK:
                return null;
            case HSSFCell.CELL_TYPE_STRING:
                String s = cellule.getStringCellValue();
                s = s != null ? s.trim() : null;
                if (s != null && s.equalsIgnoreCase("null")) {
                    return null;
                }
                return s != null ? new SimpleDateFormat("dd/MM/yyyy").parse(s) : null;
        }
        return null;
    }

    public EventDAOBeanLocal getServiceDAO() {
        return serviceDAO;
    }

//    public List<Event> getAll() {
//        return serviceDAO.getAll();
//    }
    public List<Event> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<Event> selecteds) {
        this.selecteds = selecteds;
    }

    public Event getEvent() {
        return event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSheetFromLine() {
        return sheetFromLine;
    }

    public void setSheetFromLine(int sheetFromLine) {
        this.sheetFromLine = sheetFromLine;
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public LazyDataModel<Event> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Event> lazyModel) {
        this.lazyModel = lazyModel;
    }

}
