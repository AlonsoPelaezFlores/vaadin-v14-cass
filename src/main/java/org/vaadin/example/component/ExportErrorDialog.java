package org.vaadin.example.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.example.backend.dto.ConceptContribution;
import org.vaadin.example.backend.dto.ErrorDTO;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExportErrorDialog extends Dialog {

    Grid<ErrorDTO> grid = new Grid<>(ErrorDTO.class,false);
    public ExportErrorDialog(){
        setWidth("800px");
        Span titleDialog= new Span("Carrega Fitxer XML");
        H3 title = new H3("Errors en la carga de dades d'assegurats");
        Span message = new Span("El fitxer XML no s'ha pogut carregar perque presenta els seguents errors. Corregeix els errors i torna a carregar el fitxer");
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.add(titleDialog,title,message,errorsGrid(),footer());
        add(layout);
    }

    private Component errorsGrid() {
        grid.addColumn(ErrorDTO::getNumInsured).setHeader("Num.").setKey("numInsured")
                .setWidth("auto").setFlexGrow(0);
        grid.addColumn(ErrorDTO::getConcept).setHeader("Concepte").setKey("concept")
                .setWidth("auto").setFlexGrow(0);
        grid.addColumn(ErrorDTO::getMessage).setHeader("Error").setKey("message")
                .setWidth("auto").setFlexGrow(1);
        grid.setItems(getMockData());
        grid.setAllRowsVisible(true);
        return grid;
    }

    private List<ErrorDTO> getMockData() {
        return List.of(
                new ErrorDTO(12345, ConceptContribution.SBA, "El salario base no coincide con el histórico del asegurado."),
                new ErrorDTO(67890, ConceptContribution.CPP, "La contribución al plan de pensiones excede el límite permitido."),
                new ErrorDTO(11223, ConceptContribution.AFI, "Error en la validación del régimen especial de autónomos."),
                new ErrorDTO(44556, ConceptContribution.RES, "Faltan documentos adjuntos para la declaración de rentas en especie.")
        );
    }

    private HorizontalLayout footer(){
        Button cancelBtn = new Button("Tancar",e-> this.close());
        cancelBtn.addClassName("btn-edit");
        HorizontalLayout footerLayout = new HorizontalLayout(exportExcel(),exportPDF(),cancelBtn);
        footerLayout.setWidthFull();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return footerLayout;
    }
    private Component exportExcel(){
        String nameDoc = "12345.csv";
        Button exportExcelButton = new Button("Exportar a Excel");
        exportExcelButton.addClassName("btn-primary");
        StreamResource resource = new StreamResource(nameDoc,()->{
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(0xEF);
                outputStream.write(0xBB);
                outputStream.write(0xBF);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write("Num.;Concepte;Error");
                writer.newLine();
                getMockData().forEach(errorDTO ->{
                    try {
                        writer.write(
                                errorDTO.getNumInsured()+";"+
                                        errorDTO.getConcept()+";"+
                                        errorDTO.getMessage());
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.flush();
                return new ByteArrayInputStream(outputStream.toByteArray());
            }catch (IOException exception){
                return null;
            }
        });

        resource.setContentType("text/csv; charset=utf-8");
        Anchor anchor = new Anchor(resource,"");
        anchor.getElement().setAttribute("download",true);
        anchor.add(exportExcelButton);
        return anchor;
    }

    private Component exportPDF(){
        String nameDoc = "12345678.pdf";
        Button exportPDFButton = new Button("Exportar a PDF");
        exportPDFButton.addClassName("btn-primary");

        StreamResource resource = new StreamResource(nameDoc, () -> {
            try {
                String html = buildErrorTemplate(getMockData());

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ITextRenderer renderer = new ITextRenderer();

                renderer.setDocumentFromString(html);
                renderer.layout();
                renderer.createPDF(outputStream);

                return new ByteArrayInputStream(outputStream.toByteArray());
            } catch (Exception ex) {
                Notification.show("Error al generar PDF: " + ex.getMessage());
                return null;
            }
        });
        resource.setContentType("application/pdf");
        resource.setCacheTime(0);
        Anchor anchor = new Anchor(resource,"");
        anchor.getElement().setAttribute("download",true);
        anchor.add(exportPDFButton);
        return anchor;
    }
    public String buildErrorTemplate(List<ErrorDTO> errors) {
        StringBuilder html = new StringBuilder();
        html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        html.append("<head><style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append("h1 { color: #d32f2f; border-bottom: 2px solid #d32f2f; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        html.append("th { background-color: #f2f2f2; border: 1px solid #ccc; padding: 8px; text-align: left; }");
        html.append("td { border: 1px solid #ccc; padding: 8px; font-size: 12px; }");
        html.append(".critico { color: red; font-weight: bold; }");
        html.append("</style></head><body>");

        html.append("<h1>Reporte de Errores</h1>");
        html.append("<table>");
        html.append("<thead><tr><th>Num.</th><th>Concepte</th><th>Error</th></tr></thead>");
        html.append("<tbody>");

        for (ErrorDTO error : errors) {
            html.append("<tr>");
            html.append("<td>").append(error.getNumInsured()).append("</td>");
            html.append("<td>").append(error.getConcept()).append("</td>");
            html.append("<td>").append(error.getMessage()).append("</td>");
            html.append("</tr>");
        }

        html.append("</tbody></table></body></html>");
        return html.toString();
    }

}
