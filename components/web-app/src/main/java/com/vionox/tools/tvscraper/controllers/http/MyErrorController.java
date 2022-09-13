package com.vionox.tools.tvscraper.controllers.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class MyErrorController implements ErrorController
{
    private static final Logger LOG = LoggerFactory.getLogger(MyErrorController.class);

    private static final String PATH = "/error";

    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private MessageSource messages;

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("errors/denied");
        modelAndView.addObject("error", 404);
        return modelAndView;
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request, Exception exception) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ModelAndView modelAndView = new ModelAndView();
        final Locale locale = localeResolver.resolveLocale(request);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            modelAndView.addObject("statusCode", statusCode);
            modelAndView.addObject("exception", exception);
            switch (statusCode)
            {
                case 404:
                    modelAndView.setViewName("errors/not-found");
                    modelAndView.addObject("errorHeading",
                            messages.getMessage("error.not_found.heading.text", null, locale)
                    );
                    modelAndView.addObject("errorMessage",
                            messages.getMessage("error.not_found.body.text", null, locale));
                    break;
                case 500:
                    modelAndView.setViewName("errors/error-500");
                    modelAndView.addObject("errorHeading",
                            messages.getMessage("error.error_500.heading.text", null, locale));
                    modelAndView.addObject("errorMessage",
                            messages.getMessage("error.error_500.body.text", null, locale));
                    break;
                case 403:
                    modelAndView.setViewName("errors/denied");
                    modelAndView.addObject("errorHeading",
                            messages.getMessage("error.denied.heading.text", null, locale));
                    modelAndView.addObject("errorMessage",
                            messages.getMessage("error.denied.body.text", null, locale));
                    break;
            }
        } else {
            modelAndView.setViewName("errors/error-500");
            modelAndView.addObject("errorHeading",
                    messages.getMessage("error.error_500.heading.text", null, locale));
            modelAndView.addObject("errorMessage",
                    messages.getMessage("error.error_500.body.text", null, locale));
        }
        return modelAndView;
    }
}
