package com.cai310.lottery.ticket.dto;

import java.util.List;

public class JclqBetItemDTO{
	public JclqBetItemDTO(List<JclqMatchItemDTO> items){
		this.items = items;
	}
	public JclqBetItemDTO(){
		
	}
	private List<JclqMatchItemDTO> items;

	public List<JclqMatchItemDTO> getItems() {
		return items;
	}

	public void setItems(List<JclqMatchItemDTO> items) {
		this.items = items;
	}
	
	
}
