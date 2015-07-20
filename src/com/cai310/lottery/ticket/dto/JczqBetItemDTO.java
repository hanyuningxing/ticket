package com.cai310.lottery.ticket.dto;

import java.util.List;

public class JczqBetItemDTO{
	public JczqBetItemDTO(List<JczqMatchItemDTO> items){
		this.items = items;
	}
	public JczqBetItemDTO(){
		
	}
	private List<JczqMatchItemDTO> items;

	public List<JczqMatchItemDTO> getItems() {
		return items;
	}

	public void setItems(List<JczqMatchItemDTO> items) {
		this.items = items;
	}
	
	
}
