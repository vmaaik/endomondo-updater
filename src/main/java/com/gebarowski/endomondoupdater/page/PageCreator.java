package com.gebarowski.endomondoupdater.page;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Slf4j
public abstract class PageCreator {

    private String template;
    private String formattedTemplate;

    protected abstract String formatTemplate(final String template);

    public final void exportPage(final String templatePath, final String exportPath) {
        this.loadTemplate(templatePath);
        this.formattedTemplate = this.formatTemplate(template);
        this.saveTemplate(exportPath);
    }

    protected final String replaceTemplateKeys(final Map<String, String> templateKeys, final String template){
       String adjustedTemplate = template;
        for (Map.Entry<String, String> entry : templateKeys.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

          adjustedTemplate = adjustedTemplate.replace(key, value);
        }
        return adjustedTemplate;
    }

    private void loadTemplate(final String templatePath) {
        log.info("Attempting to load template from: " + templatePath);
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(templatePath)) {
            this.template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Template loaded successfully");
        } catch (IOException e) {
            log.error("Template could not be loaded from " + templatePath);
            e.printStackTrace();
        }
    }

    private void saveTemplate(final String exportPath) {
        log.info("Attempting to export template to: " + exportPath);
        try {
            Files.write(Paths.get(exportPath),
                    formattedTemplate.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            log.info("Template exported successfully");
        } catch (IOException e) {
            log.error("Template could not be exported to " + exportPath);
            e.printStackTrace();
        }
    }
}