/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.entity;

import java.io.Serializable;

/**
 *
 * @author Think
 */
public class Weather implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2064616718788255937L;

	public Weather(String amWeatherDesc, String pmWeatherDesc,
			Double hCentigrade, Double lCentigrade) {
		super();
		this.amWeatherDesc = amWeatherDesc;
		this.pmWeatherDesc = pmWeatherDesc;
		this.hCentigrade = hCentigrade;
		this.lCentigrade = lCentigrade;
	}

	public Weather() {
		
	}

	private String amWeatherDesc;
    
    private String pmWeatherDesc;
    
    private Double hCentigrade;
    
    private Double lCentigrade;

    /**
     * @return the hCentigrade
     */
    public Double gethCentigrade() {
        return hCentigrade;
    }

    /**
     * @param hCentigrade the hCentigrade to set
     */
    public void sethCentigrade(Double hCentigrade) {
        this.hCentigrade = hCentigrade;
    }

    /**
     * @return the lCentigrade
     */
    public Double getlCentigrade() {
        return lCentigrade;
    }

    /**
     * @param lCentigrade the lCentigrade to set
     */
    public void setlCentigrade(Double lCentigrade) {
        this.lCentigrade = lCentigrade;
    }

    /**
     * @return the amWeatherDesc
     */
    public String getAmWeatherDesc() {
        return amWeatherDesc;
    }

    /**
     * @param amWeatherDesc the amWeatherDesc to set
     */
    public void setAmWeatherDesc(String amWeatherDesc) {
        this.amWeatherDesc = amWeatherDesc;
    }

    /**
     * @return the pmWeatherDesc
     */
    public String getPmWeatherDesc() {
        return pmWeatherDesc;
    }

    /**
     * @param pmWeatherDesc the pmWeatherDesc to set
     */
    public void setPmWeatherDesc(String pmWeatherDesc) {
        this.pmWeatherDesc = pmWeatherDesc;
    }
}
