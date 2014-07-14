package com.xiangfa.logssystem.util;

/**
 * 
 * @author Think
 *
 */
public final class EscapeHTMLElement {

	public static final String escape(String target){
		target = target.replaceAll("<", "&lt;");
		target = target.replaceAll(">", "&gt;");
		return target;	
	}
}
