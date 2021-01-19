package br.com.luisfga.controller.jsf;

import br.com.luisfga.config.Property;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author luisfga
 */
@Named
@RequestScoped
public class IndexBean {
    
    @Inject 
    @Property("index.message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
