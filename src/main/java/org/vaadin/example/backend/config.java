package org.vaadin.example.backend;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;

public class config {
    @Bean
    public ApplicationRunner warmupPdfEngine() {
        return args -> {
            System.out.println("--- Iniciando pre-calentamiento de Flying Saucer ---");
            long start = System.currentTimeMillis();
            try {
                ITextRenderer renderer = new ITextRenderer();
                String warmupHtml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\"><body>Principal</body></html>";
                renderer.setDocumentFromString(warmupHtml);
                renderer.layout();
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    renderer.createPDF(os);
                }
                long end = System.currentTimeMillis();
                System.out.println("--- Warm-up finalizado en " + (end - start) + "ms ---");
            } catch (Exception e) {
                System.err.println("Error en warm-up de PDF: " + e.getMessage());
            }
        };
    }
}
