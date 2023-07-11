/**
 * Created on Oct 3, 2011
 */
package com.mikovic.altma.services.aop;

/**
 * @author Clarence
 *
 */
public interface ObscenityFilter {

    public boolean containsObscenities(String data);
    
    public String obfuscateObscenities(String data);	
}
