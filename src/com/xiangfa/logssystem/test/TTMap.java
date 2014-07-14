package com.xiangfa.logssystem.test;

public class TTMap implements Comparable<TTMap> {

	private Integer id;
	
	private String name;

	public TTMap(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public TTMap() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(TTMap arg0) {
		if(this.id>arg0.getId()){
			return 1;
		}else if(this.id==arg0.getId()){
			return 0;
		}
		return -1;
	}
}
