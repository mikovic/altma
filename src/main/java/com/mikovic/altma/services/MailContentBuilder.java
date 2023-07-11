package com.mikovic.altma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String confirmUrl, String firstName) {
        final Context context = new Context();
        context.setVariable("name", firstName );
        context.setVariable("url", confirmUrl);

        return templateEngine.process("mail", context);
    }

}
